package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.time.LocalDate;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.Player;
import handlers.EntityHandler;
import handlers.ImageHandler;
import handlers.KeyHandler;
import handlers.MaskHandler;
import handlers.MouseHandler;
import handlers.MouseMotionHandler;
import handlers.Sound;
import object.ObjectDrawerThread;

public class Game extends JPanel implements Runnable, Sound{
    public JFrame window;
    public int windowPosX;
    public int windowPosY;

    public int windowWidth = 576;
    public int windowHeight = 576;
    public int mouseX;
    public int mouseY;
    public int absoluteMouseX;
    public int absoluteMouseY;
    public MaskHandler maskHandler;
    public EntityHandler entityHandler;
    public Player player;
    public KeyHandler keyH;
    public MouseHandler mouseH;
    public MouseMotionHandler mouseMotionH;

    // GameState variables
    public int mainMenuState = 0, mainGameState = 1, gameOverState = 2;
    public int gameState = mainGameState;
    public boolean paused;
    public MainMenu mainMenu;
    public PauseMenu pauseMenu;
    public GameOverMenu gameOverMenu;
    public FPS fps = new FPS();
    public ScoreBoard scoreBoard;
    Thread gameThread;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    ObjectDrawerThread objectDrawerThread;
    AssetSetter  assetSetter;
    Graphics2D g2;


    public Game(){
        setWindowDefaults();
        new ImageHandler();
        this.objectDrawerThread = new ObjectDrawerThread(this);
        assetSetter = new AssetSetter(this, objectDrawerThread);
        assetSetter.setObject();

        this.maskHandler = new MaskHandler();
        this.player = new Player(this);
        this.mouseMotionH = new MouseMotionHandler();
        this.addMouseMotionListener(mouseMotionH);
        this.keyH = new KeyHandler(this);
        this.mouseH = new MouseHandler(this);
        this.mainMenu = new MainMenu(this);
        this.pauseMenu = new PauseMenu(this);
        this.gameOverMenu = new GameOverMenu(this);

        this.entityHandler = new EntityHandler(this, g2);

        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        player.setHandlers(keyH, mouseH);
        this.scoreBoard = new ScoreBoard();
        this.requestFocusInWindow();
    }

    public void setWindowDefaults(){
        this.window =  new JFrame();
        this.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.window.setUndecorated(true);
        this.window.setBackground(new Color(0, 0, 0, 0));
        // this.setPreferredSize(new Dimension(dim.height, dim.width));
        this.setOpaque(false);
        this.window.setAlwaysOnTop(true);
        this.window.add(this);

        this.window.setVisible(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        playMusic(0);
    }

    public void reset(){
        this.player.reset();
        this.entityHandler.reset();
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

            if(fps.delta >= 1){
                update();
                repaint();

                fps.delta--;
                fps.drawCount++;
            }
        }
    }

    public void update(){
        if(this.gameState == this.mainMenuState){
            this.entityHandler.reset();
        }

        if(this.gameState == this.mainGameState){
            if(KeyHandler.escToggled){
                this.paused = true;
            }

            if(!KeyHandler.escToggled){
                this.paused = false;
            }

            if(!this.paused){
                if(this.player.getHealth() <= 0){
                    scoreBoard.addScore(String.valueOf(LocalDate.now()), player.score);
                    this.gameState = gameOverState;
                }

                entityHandler.update();
            }
        }
    }

    protected void paintComponent(Graphics g){
        if(this.gameThread != null){
            super.paintComponent(g);
            g2 = (Graphics2D)g;

            if(this.gameState == mainMenuState){
                this.mainMenu.draw(g2);
            }

            if(this.gameState ==  mainGameState){
                objectDrawerThread.drawObjects(g2);

                if(!this.paused){
                    this.entityHandler.draw(g2);
                }

                if(this.paused){
                    this.pauseMenu.draw(g2);
                }

                g2.setColor(Color.GREEN);
                g2.drawString("Score: " + player.score, 5, 10);
                g2.drawString("Health: " + player.getHealth() ,400, 10);
            }

            if(this.gameState == gameOverState){
                this.gameOverMenu.draw(g2);
            }
            g2.dispose();
        }
    }
}