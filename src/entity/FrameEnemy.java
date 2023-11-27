package entity;

import handlers.*;
import main.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class FrameEnemy extends JPanel implements Runnable, Rotate{
    public JFrame window;
    public int windowPosX, windowPosY;
    public int enemyX, enemyY;
    public int enemyXLocationOnScreen, enemyYLocationOnScreen;
    public FPS fps = new FPS();
    Thread frameEnemyThread;
    Game game;
    BufferedImage image;
    private int maxWindowWidth = 200;
    private int maxWindowHeight = 200;
    private int windowWidth = 200;
    private int windowHeight = 200;

    private int minWindowWidth = 133;
    private int minWindowHeight = 133;


    public boolean isShooting;
    private int isShootingDuration = 100;
    public int isShootingTimer = 0;
    private int maxStrokeWidth = 20;
    private int minStrokeWidth = 1;
    private int strokeWidth = minStrokeWidth;

    private int playerX;
    private int playerY;
    private Point destination;
    private int speed = 2;
    double directionX;
    double directionY;
    Line2D line;
    Random random = new Random();

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public FrameEnemy(Game game){
        getImage();
        this.game = game;
        setWindowDefaults();
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setSize(window.getWidth(), window.getHeight());
        this.setFocusTraversalKeysEnabled(false);
        this.enemyX = this.window.getWidth() / 2;
        this.enemyY = this.window.getHeight() / 2;
    }

    public void getImage(){
        this.image = ImageHandler.boss1;
    }

    public void setWindowDefaults(){
        this.window = new JFrame();
        this.window.setSize(maxWindowWidth, maxWindowHeight);
        Point spawn = setValidSpawnPoint();
        this.destination = spawn;
        this.windowPosX = spawn.x;
        this.windowPosY = spawn.y;

//        this.window.setAlwaysOnTop(true);
        this.window.setLocation(spawn.x, spawn.y);
        this.window.setVisible(true);
        ImageIcon img = new ImageIcon("src/resource/pepe.png");
        this.window.setIconImage(img.getImage());

        window.setFocusTraversalKeysEnabled(false);
        window.setFocusableWindowState(false);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.add(this);
    }

    private Point setValidSpawnPoint(){
        Random random = new Random();
        int posX;
        int posY;


        posY = random.nextInt(dim.height - this.window.getHeight());

        if(random.nextInt(2) == 1){
            posX = random.nextInt(this.game.getLocationOnScreen().x - this.window.getWidth());
        }else{
            posX = random.nextInt(dim.width - (this.game.getLocationOnScreen().x + this.game.getWidth())) + this.game.getLocationOnScreen().x + this.game.getWidth();
        }
        return new Point(posX, posY);
    }


    public void startFrameEnemyThread(){
        frameEnemyThread = new Thread(this);
        frameEnemyThread.start();
    }

    public void run(){
        while(frameEnemyThread != null){
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
            if(fps.timer >= 1000000000){
                double random = this.random.nextDouble();
                if(random < 0.5){
                    getNewDestination();
                }

                fps.currentFPS = fps.drawCount;
                fps.drawCount = 0;
                fps.timer = 0;
            }
        }
    }
    public void getNewDestination(){
        this.destination = setValidSpawnPoint();
    }

    public void rotate(BufferedImage image, AffineTransform at){
        if(this.isShootingTimer < 30){
            directionX = this.game.player.xLocationOnScreen - (this.getLocationOnScreen().x +  image.getWidth() / 2);
            directionY = this.game.player.yLocationOnScreen - (this.getLocationOnScreen().y +  image.getHeight() / 2);
        }

        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void move(){
        if(!isShooting){
            Point destinationdirection = new Point(this.destination.x - this.getLocationOnScreen().x, this.destination.y - this.getLocationOnScreen().y);
            double angle = Math.atan2(destinationdirection.y, destinationdirection.x);
            double dx = Math.cos(angle) * this.speed;
            double dy = Math.sin(angle) * this.speed;

//            if(Math.abs(this.windowPosX - this.destination.x) > 100 && (this.windowPosX < this.game.getLocationOnScreen().y || this.windowPosX > this.game.getLocationOnScreen().x + this.game.getWidth())){
//                this.windowPosX += (int) dx;
//            }

            if(Math.abs(this.windowPosX - this.destination.x) > 100){
                this.windowPosX += (int) dx;
            }
            if(Math.abs(this.windowPosY - this.destination.y) > 100){
                this.windowPosY += (int) dy;
            }
            this.window.setLocation(windowPosX, windowPosY);
        }
    }

    public void update(){
        this.enemyXLocationOnScreen = this.enemyX + this.window.getLocationOnScreen().x;
        this.enemyYLocationOnScreen = this.enemyY + this.window.getLocationOnScreen().y;
        move();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        AffineTransform at = AffineTransform.getTranslateInstance(this.enemyX - image.getWidth() / 2.0, this.enemyY - image.getHeight() / 2.0);
        rotate(image, at);

        g2.setColor(Color.RED);

        if (this.isShooting) {
            if (isShootingTimer < isShootingDuration) {
                if(isShootingTimer < 30){
                    playerX = (int) (-this.getLocationOnScreen().x + this.game.player.xLocationOnScreen);
                    playerY = (int) (-this.getLocationOnScreen().y + this.game.player.yLocationOnScreen);
                }

                this.line = new Line2D.Double(this.enemyX, this.enemyY, playerX, playerY);
                g2.setStroke(new BasicStroke(strokeWidth));
                isShootingTimer++;
                if(isShootingTimer > 60){
                    g2.setColor(Color.BLUE);
                    strokeWidth = maxStrokeWidth;
                }else{
                    strokeWidth = minStrokeWidth + ((maxStrokeWidth - minStrokeWidth) * isShootingTimer / isShootingDuration);
                }
                g2.draw(this.line);
            } else {
                this.isShooting = false;
                isShootingTimer = 0;
                strokeWidth = minStrokeWidth;
            }
        }

        g2.drawImage(this.image, at, null);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.dispose();
    }
}