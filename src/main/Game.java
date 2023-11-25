package main;

import entity.*;
import handlers.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;

public class Game extends JPanel implements Runnable{
    public WindowContainer window;
    public int windowPosX = 500;
    public int windowPosY = 250;
    public int screenWidth = 576;
    public int screenHeight = 576;
    public int mouseX;
    public int mouseY;
    public int absoluteMouseX;
    public int absoluteMouseY;
    Thread gameThread;
    public static MaskCreationThread maskCreationThread;
    public EnemyHandler enemyHandler;
    CollisionChecker cChecker;
    boolean gameOver = false;
    public Player player;
    public KeyHandler keyH;
    MouseHandler mouseH;
    MouseMotionHandler mouseMotionH;
    private Background background1;
    private Background background2;

    //gameState
    public int mainMenuState = 0, mainGameState = 1, gameOverState = 2;
    public int gameState = mainMenuState;
    public boolean paused;
    public MainMenu mainMenu;
    public PauseMenu pauseMenu;
    public GameOverMenu gameOverMenu;
    public FPS fps = new FPS();
    ScoreBoard scoreBoard;
    public Game(){
        setWindowDefaults();
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setSize((int) window.windowHeight, (int)window.windowWidth);
        this.setFocusTraversalKeysEnabled(false);

        this.maskCreationThread = new MaskCreationThread();

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
        this.background1 = new Background(0);
        this.background2 = new Background(-this.getHeight());

        this.enemyHandler = new EnemyHandler(this);

        new MaskHandler();

        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        player.setHandlers(keyH, mouseH);
        this.scoreBoard = new ScoreBoard();
        this.requestFocusInWindow();
    }

    public void setWindowDefaults(){
        this.window =  new WindowContainer(screenWidth, screenHeight, 300, 0, 0);
        window.setFocusTraversalKeysEnabled(false);
        window.setResizable(false);
//        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Game");
//        window.setAlwaysOnTop(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(dim.width / 2 - windowPosX / 2, dim.height / 2 - windowPosY);
        window.setLayout(null);
        window.setVisible(true);
        window.add(this);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();

        Thread maskThread = new Thread(maskCreationThread);
        maskThread.start();
    }

    public void reset(){
        this.player.reset();
        this.enemyHandler.reset();
        this.gameState = mainGameState;
    }


    public void run(){
//        enemyHandler.summonEnemy(this);
        try {
            SoundHandler.playSound("../resource/sounds/bgm.wav", true);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        SoundHandler.setVolume(0.7f);

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
        if(this.gameState == this.mainGameState){
            if(this.keyH.escToggled){
                this.paused = true;
            }

            if(!this.keyH.escToggled){
                this.paused = false;
            }

            if(!this.paused){
                background1.update(fps.delta, this);
                background2.update(fps.delta, this);

                if(this.player.health <= 0){
                    scoreBoard.addScore(String.valueOf(LocalDate.now()), player.score);
                    this.gameState = gameOverState;
                }

                player.update(this, this.window);
                enemyHandler.update();
            }
        }
    }

    public void paintComponent(Graphics g){
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D)g;

         if(this.gameState == 0){
             mainMenu.draw(g2);
         }

         if(this.gameState ==  1){

             background1.draw(g2);
             background2.draw(g2);

             this.enemyHandler.draw(g2);
             for (Bullet bullet : player.bullets) {
                 bullet.draw(g2, this);
             }

             player.draw(g2, this);
             g2.setColor(Color.GREEN);
             g2.drawString("Score: " + player.score, 5, 10);
             g2.drawString("Health: " + player.health,400, 10);

             if(this.paused){
                 this.pauseMenu.draw(g2);
             }
         }

         if(this.gameState == 2){
             this.gameOverMenu.draw(g2);
         }

         g2.dispose();
    }
}
