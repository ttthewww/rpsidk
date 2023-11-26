package entity;

import handlers.*;
import main.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class FrameEnemy extends JPanel implements Runnable, Rotate{
    public JFrame window;
    public int x, y;
    public int enemyX, enemyY;
    public int xLocationOnScreen, yLocationOnScreen;
    public FPS fps = new FPS();
    Thread frameEnemyThread;
    Game game;
    BufferedImage image;
    public boolean isShooting;
    private int isShootingDuration = 100;
    private int isShootingTimer = 0;
    private int maxStrokeWidth = 20;
    private int minStrokeWidth = 1;
    private int strokeWidth = minStrokeWidth;

    private int playerX;
    private int playerY;
    double directionX;
    double directionY;

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
        this.window.setLocation(0, 0);
        this.window.setSize(200, 200);
        this.window.setVisible(true);
        ImageIcon img = new ImageIcon("src/resource/pepe.png");
        this.window.setIconImage(img.getImage());

        window.setFocusTraversalKeysEnabled(false);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(null);
        window.add(this);
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
            if(fps.timer>= 1000000000){
                fps.currentFPS = fps.drawCount;
                fps.drawCount = 0;
                fps.timer = 0;
            }
        }
    }

    public void rotate(BufferedImage image, AffineTransform at){
        if(!this.isShooting){
            directionX = this.game.player.xLocationOnScreen - (this.xLocationOnScreen + (double) image.getWidth() / 2);
            directionY = this.game.player.yLocationOnScreen - (this.yLocationOnScreen + (double) image.getHeight() / 2);
        }

        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }


    public void update(){
        this.xLocationOnScreen = this.enemyX + this.window.getLocationOnScreen().x;
        this.yLocationOnScreen = this.enemyY + this.window.getLocationOnScreen().y;
//        this.y += 1;
//        this.window.setLocation(x, y);
//        if(this.y > 500){
//            this.y = 500;
//        }


        if (this.isShooting) {
            if (isShootingTimer < isShootingDuration) {
                isShootingTimer++;
                strokeWidth = minStrokeWidth + ((maxStrokeWidth - minStrokeWidth) * isShootingTimer / isShootingDuration);
            } else {
                this.isShooting = false;
                isShootingTimer = 0;
                strokeWidth = minStrokeWidth;
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        AffineTransform at = AffineTransform.getTranslateInstance(this.enemyX - image.getWidth() / 2.0, this.enemyY - image.getHeight() / 2.0);
        rotate(image, at);

        if(!this.isShooting){
            playerX = (int) (-this.getLocationOnScreen().x + this.game.player.xLocationOnScreen);
            playerY = (int) (-this.getLocationOnScreen().y + this.game.player.yLocationOnScreen);
        }

        if (this.isShooting) {
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(strokeWidth));
            g2.drawLine(this.enemyX, this.enemyY, playerX, playerY);
        }

        g2.drawImage(this.image, at, null);
        g2.dispose();
    }
}