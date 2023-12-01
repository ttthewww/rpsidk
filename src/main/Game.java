package main;

import entity.*;
import handlers.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class Game extends JPanel implements Runnable, Sound{
    public WindowContainer window;
    public int windowPosX = 500;
    public int windowPosY = 250;
    public int screenWidth = 576;
    public int screenHeight = 576;
    public int mouseX;
    public int mouseY;
    public int absoluteMouseX;
    public int absoluteMouseY;
    public MaskHandler maskCreationThread;
    public EnemyHandler enemyHandler;
    public CollisionChecker cChecker;
    public Player player;
    public KeyHandler keyH;
    public MouseHandler mouseH;
    public MouseMotionHandler mouseMotionH;
    public BackgroundHandler backgroundHandler;
    // GameState variables
    public int mainMenuState = 0, mainGameState = 1, gameOverState = 2;
    public int gameState = mainMenuState;
    public boolean paused;
    public MainMenu mainMenu;
    public PauseMenu pauseMenu;
    public GameOverMenu gameOverMenu;
    public FPS fps = new FPS();
    public ScoreBoard scoreBoard;
    Thread gameThread;
    public Game(){
        setWindowDefaults();
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setSize((int) window.windowWidth, (int)window.windowHeight);
        this.setFocusTraversalKeysEnabled(false);

        this.maskCreationThread = new MaskHandler();

        new ImageHandler();
        this.player = new Player(this);
        this.mouseMotionH = new MouseMotionHandler();
        this.addMouseMotionListener(mouseMotionH);
        this.keyH = new KeyHandler();

        this.mainMenu = new MainMenu(this);
        this.pauseMenu = new PauseMenu(this);
        this.gameOverMenu = new GameOverMenu(this);

        this.mouseH = new MouseHandler(this);

        this.cChecker = new CollisionChecker(this.player);
        this.backgroundHandler = new BackgroundHandler(this);

        this.enemyHandler = new EnemyHandler(this);

        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        player.setHandlers(keyH, mouseH);
        this.scoreBoard = new ScoreBoard();
        this.requestFocusInWindow();
    }

    public void setWindowDefaults(){
        this.window =  new WindowContainer(screenWidth, screenHeight,0, 0);
        window.setFocusTraversalKeysEnabled(false);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Game");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - windowPosX / 2, dim.height / 2 - windowPosY - 50);
        window.setLayout(null);
        window.add(this);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
//        playMusic(0);
    }

    public void reset(){
        this.player.reset();
        this.enemyHandler.reset();
        this.gameState = mainGameState;
    }


    public void run(){
        while(gameThread != null){
                this.absoluteMouseX =  MouseInfo.getPointerInfo().getLocation().x;
                this.absoluteMouseY = MouseInfo.getPointerInfo().getLocation().y;
                if(mouseMotionH.hasMouseMoved()){
                    this.mouseX = mouseMotionH.getMouseX();
                    this.mouseY = mouseMotionH.getMouseY();
                }

                fps.update();
                fps.currentTime = System.nanoTime();
                fps.delta += (fps.currentTime - fps.lastTime) / fps.drawInterval;
                fps.timer += (fps.currentTime - fps.lastTime);
                fps.lastTime = fps.currentTime;

                if(fps.delta >= 1){
                    update();
                    repaint();
                    fps.delta--;
                    fps.drawCount++;
                }
                if(fps.timer>= 1000000000){
                    fps.currentFPS = fps.drawCount;
                    fps.drawCount = 0;
                    fps.timer = 0;
                }
        }
    }
    public void update(){
        if(this.gameState == this.mainMenuState){
            this.enemyHandler.reset();
        }

        if(this.gameState == this.mainGameState){
            if(this.keyH.escToggled){
                this.paused = true;
            }

            if(!this.keyH.escToggled){
                this.paused = false;
            }

            if(!this.paused){
                this.backgroundHandler.update();

                if(this.player.health <= 0){
                    scoreBoard.addScore(String.valueOf(LocalDate.now()), player.score);
                    this.gameState = gameOverState;
                }

                player.update(this, this.window);
                enemyHandler.update();
            }
        }
    }

    protected void paintComponent(Graphics g){
        if(this.gameThread != null){
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D)g;

         if(this.gameState == mainMenuState){
             this.mainMenu.draw(g2);
         }

         if(this.gameState ==  mainGameState){
             this.backgroundHandler.draw(g2);
             if(!this.paused){
                 this.enemyHandler.draw(g2);
             }
             for (Bullet bullet : player.bullets) {
                 bullet.draw(g2);
             }

             g2.setColor(Color.GREEN);
             g2.drawString("Score: " + player.score, 5, 10);
             g2.drawString("Health: " + player.health,400, 10);

             player.draw(g2);

             if(this.paused){
                 this.pauseMenu.draw(g2);
             }
         }

         if(this.gameState == gameOverState){
             this.gameOverMenu.draw(g2);
         }

         g2.dispose();
        }
    }
}
