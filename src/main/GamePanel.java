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

    public double windowSpeed = 1;
    public int mouseX;
    public int mouseY;
    public int absoluteMouseX;
    public int absoluteMouseY;
    private int FPS = 60;

    MouseMotionHandler mouseMotionH = new MouseMotionHandler();
    Thread gameThread;

    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<FrameEnemy> frameEnemies = new ArrayList<>();

    CollisionChecker cChecker;
    boolean gameOver = false;

    double spawnChance = 0.2;
    public Player player;
    KeyHandler keyH;
    private Background background1;
    private Background background2;

    public FPS fps = new FPS();
    public GamePanel() throws IOException {
        setWindowDefaults();
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addMouseMotionListener(mouseMotionH);
        this.setFocusable(true);
        this.setSize((int) window.windowHeight, (int)window.windowWidth);
        this.setFocusTraversalKeysEnabled(false);

        this.player = new Player(this);
        this.keyH = new KeyHandler(player);
        this.addKeyListener(keyH);
        player.setKeyHandler(keyH);
        this.cChecker = new CollisionChecker(this.player);
        new MaskHandler();
        this.background1 = new Background(0);
        this.background2 = new Background(-this.getHeight());
    }

    public void setWindowDefaults(){
        this.window =  new WindowContainer(576, 576, 300, 0, 0);
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
    }

    @Override
    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

//        enemies.add(new RockEnemy(this, enemies));
//        enemies.add(new PaperEnemy(this, enemies));
//        enemies.add(new ScissorEnemy(this, enemies));

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

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(fps.delta >= 1){
                update(enemies);
                background1.update(fps.delta, this);
                background2.update(fps.delta, this);

                repaint();
                delta--;
                drawCount++;

                fps.delta--;
                fps.drawCount++;
//                if(Math.random() < .05){
//                    this.window.shrink();
//                }
            }
            if(fps.timer>= 1000000000){
                if (Math.random() < spawnChance) {
                    enemies.add(new RockEnemy(this, enemies));
                    enemies.add(new PaperEnemy(this, enemies));
                    enemies.add(new ScissorEnemy(this, enemies));
                }
                fps.currentFPS = fps.drawCount;
                fps.drawCount= 0;
                drawCount = 0;
                fps.timer = 0;
            }
        }
    }

    public void update(ArrayList<Enemy> enemies){
        cChecker.checkPlayerBulletCollision(player.bullets, enemies);
        enemies.removeIf(enemy -> !enemy.isActive);
        player.bullets.removeIf(bullet -> !bullet.isActive);

        player.update(this, this.window);
        for(Enemy e : enemies){
            e.update();
//            e.enemyWindowContainer.update();
        }
        gameOver = cChecker.check(enemies);
    }

    public void paintComponent(Graphics g){
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D)g;

         background1.draw(g2);
         background2.draw(g2);
         for(Enemy e : enemies) {
             e.draw(g2);
         }

        for (Bullet bullet : player.bullets) {
            bullet.draw(g2, this);
        }
        player.draw(g2, this);

        g2.setColor(Color.GREEN);
        g2.drawString("Score: " + player.score, 5, 10);
        g2.drawString("Health: " + player.health,400, 10);
        g2.dispose();
    }
}
