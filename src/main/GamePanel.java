package main;

import entity.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    public WindowContainer window;

    public int windowPosX = 500;
    public int windowPosY = 250;
    public int screenWidth = 576;
    public int screenHeight = 576;

    public double windowSpeed = 1;
    public int mouseX;
    public int mouseY;
    public int absoluteMouseX;
    public int absoluteMouseY;
    private int FPS = 60;
    Thread gameThread;
    public static MaskCreationThread maskCreationThread;

    public EnemyHandler enemyHandler;

    ArrayList<FrameEnemy> frameEnemies = new ArrayList<>();
    CollisionChecker cChecker;
    boolean gameOver = false;
    double spawnChance = 0.2;
    public Player player;
    KeyHandler keyH;
    MouseHandler mouseH;
    MouseMotionHandler mouseMotionH;
    private Background background1;
    private Background background2;

    //gameState
    int mainMenuState = 0, mainGameState = 1, pauseState = 2;
    int gameState = mainGameState;
    MainMenu mainMenu;

    public FPS fps = new FPS();

    public GamePanel() throws IOException {
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
        this.keyH = new KeyHandler(this.player);
        this.mouseH = new MouseHandler();

        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        player.setHandlers(keyH, mouseH);

        this.cChecker = new CollisionChecker(this.player);
        this.background1 = new Background(0);
        this.background2 = new Background(-this.getHeight());

        this.mainMenu = new MainMenu(this);
        this.enemyHandler = new EnemyHandler();

        new MaskHandler();
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
        window.add(this);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();

        Thread maskThread = new Thread(maskCreationThread);
        maskThread.start();
    }

    public void run(){
//        enemyHandler.summonEnemy(this);

        try {
            SoundHandler.playSound("../resource/sounds/bgm.wav");
        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
        SoundHandler.setVolume(0.7f);

        while(gameThread != null){
            //I FIX ANG MASK NA DLI MU LAG!
            if(gameOver){
                while(true){
                    System.out.println("GAME OVER");
                }
            }

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
                background1.update(fps.delta, this);
                background2.update(fps.delta, this);

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
    int timer = 0;
    public void update(){
        if(this.gameState == mainGameState){
            cChecker.checkPlayerBulletCollision(player.bullets, this.enemyHandler.enemies);
            this.enemyHandler.enemies.removeIf(enemy -> !enemy.isActive);
            gameOver = cChecker.check(this.enemyHandler.enemies);

            player.bullets.removeIf(bullet -> !bullet.isActive);

            player.update(this, this.window);

            this.enemyHandler.update();

            timer++;
            if(timer >= 100){
                if (Math.random() < spawnChance) {
                    this.enemyHandler.summonEnemy(this);
                }
                timer = 0;
            }
        }
    }

    public void paintComponent(Graphics g){
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D)g;

         if(this.gameState == mainMenuState){
             this.mainMenu.draw(g2);
         }

         if(this.gameState == mainGameState){
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
         }

        g2.dispose();
    }
}
