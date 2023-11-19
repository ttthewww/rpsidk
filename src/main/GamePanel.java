package main;

import entity.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    public static WindowContainer window;

    public int windowPosX = 500;
    public int windowPosY = 250;

    public double windowSpeed = 1;
    public int mouseX;
    public int mouseY;
    public int absoluteMouseX;
    public int absoluteMouseY;
    private int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    MouseMotionHandler mouseMotionH = new MouseMotionHandler();
    Thread gameThread;

    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<FrameEnemy> frameEnemies = new ArrayList<>();

    CollisionChecker cChecker = new CollisionChecker();
    boolean gameOver = false;

    double spawnChance = 0.2;
    public Player player = new Player(this, this.keyH);
    private Background background1;
    private Background background2;

    public FPS fps = new FPS();
    public GamePanel() throws IOException {
        setWindowDefaults();
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseMotionListener(mouseMotionH);
        this.setFocusable(true);
        this.setSize((int) window.windowHeight, (int)window.windowWidth);
        this.setFocusTraversalKeysEnabled(false);
        this.background1 = new Background(0);
        this.background2 = new Background(-this.getHeight());
//        new MaskHandler();
    }

    public void setWindowDefaults(){
        this.window =  new WindowContainer(576, 576, 300, 0, 0);
        window.setFocusTraversalKeysEnabled(false);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Game");
        window.setAlwaysOnTop(true);
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

//        enemies.add(new ScissorEnemy(this, enemies));
//        enemies.add(new ScissorEnemy(this, enemies));
        enemies.add(new RockEnemy(this, enemies));
        enemies.add(new RockEnemy(this, enemies));


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
                try {
                    update(window, enemies);
                    background1.update(fps.delta, this);
                    background2.update(fps.delta, this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
//                    enemies.add(new ScissorEnemy(this, enemies));
//                    enemies.add(new RockEnemy(this, enemies));
//                    enemies.add(new PaperEnemy(this, enemies));
                }
                fps.currentFPS = fps.drawCount;
                fps.drawCount= 0;
                drawCount = 0;
                fps.timer = 0;
            }
        }
    }

    public void update(JFrame window, ArrayList<Enemy> enemies) throws IOException {
        cChecker.checkPlayerBulletCollision(player.bullets, enemies);
        enemies.removeIf(enemy -> !enemy.isActive);
        player.bullets.removeIf(bullet -> !bullet.isActive);

        player.update(this, this.window);
        for(Enemy e : enemies){
            e.update();
        }
        gameOver = cChecker.check(player, enemies);
    }

    public void paintComponent(Graphics g){
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D)g;

         background1.draw(g2);
         background2.draw(g2);
         player.draw(g2, this);
         for(Enemy e : enemies) {
             e.draw(g2);
         }

        for (Bullet bullet : player.bullets) {
            bullet.draw(g2, this);
        }

        g2.setColor(Color.GREEN);
        g2.drawString("FPS: " + fps.currentFPS, 5, 10);
        g2.dispose();
    }
}
