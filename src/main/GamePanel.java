package main;

import entity.Enemy;
import entity.Player;
import entity.FrameEnemy;
import jdk.jfr.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable{

    int screenWidth = 576;
    int screenHeight = 576;

    public int windowX = 500;
    public int windowY = 250;

    public double windowSpeed = 1;
    public int mouseX;
    public int mouseY;
    public int absoluteMouseX;
    public int absoluteMouseY;
    private int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    MouseMotionHandler mouseMotionH = new MouseMotionHandler();
    Thread gameThread;

    public Player player = new Player(this, this.keyH);
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<FrameEnemy> frameEnemies = new ArrayList<>();

    CollisionChecker cChecker = new CollisionChecker();
    boolean gameOver = false;

    double spawnChance = 0.2;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseMotionListener(mouseMotionH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){
        JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Game");
        window.setLocation(windowX, windowY);
        window.setAlwaysOnTop(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//        new FrameEnemies();
        window.setLocation(dim.width / 2 - windowX / 2, dim.height / 2 - windowY);

        window.setSize(this.getPreferredSize());

        window.add(this);
        window.setVisible(true);

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            if(gameOver){
                while(true){
                    System.out.println("GAME OVER");
                }
//                System.out.println("Pildi na");
            }


            this.absoluteMouseX =  MouseInfo.getPointerInfo().getLocation().x;
            this.absoluteMouseY = MouseInfo.getPointerInfo().getLocation().y;

            if(mouseMotionH.hasMouseMoved()){
                this.mouseX = mouseMotionH.getMouseX();
                this.mouseY = mouseMotionH.getMouseY();
            }
//            System.out.println(mouseX + " " + mouseY);

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            if(delta >= 1){
                update(window, enemies);
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                if (Math.random() < spawnChance) {
                    System.out.println("ADDING ENEMIES");
                    System.out.println("MU LAG AGI RECURSION");
                    enemies.add(new Enemy(this, enemies));
                    enemies.add(new Enemy(this, enemies));
                    enemies.add(new Enemy(this, enemies));
                    enemies.add(new Enemy(this, enemies));
                    enemies.add(new Enemy(this, enemies));
                    enemies.add(new Enemy(this, enemies));
                    enemies.add(new Enemy(this, enemies));
                    enemies.add(new Enemy(this, enemies));
//                    frameEnemies.add(new FrameEnemy());
                }
//                System.out.println("FPS" + drawCount);

                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(JFrame window, ArrayList<Enemy> enemies){
        cChecker.checkPlayerBulletCollision(player.bullets, enemies);
        enemies.removeIf(enemy -> !enemy.isActive);

        for (FrameEnemy e : frameEnemies){
            e.update(player);
        }

        player.update(window, this);
        for(Enemy e : enemies){
            e.update(this);
        }
        gameOver = cChecker.check(player, enemies);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2, this);
        for(Enemy e : enemies) {
            e.draw(g2, this);
        }
        g2.dispose();
    }
}
