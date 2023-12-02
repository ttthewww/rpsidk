package entity;

import handlers.*;
import main.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class FrameEnemy extends JPanel implements Rotate{
    public JFrame window;
    public int windowPosX, windowPosY;
    public int enemyX, enemyY;
    public int enemyXLocationOnScreen, enemyYLocationOnScreen;
    public FPS fps = new FPS();
    public Thread frameEnemyThread;
    Game game;
    BufferedImage image;
    private int windowWidth = 200;
    private int windowHeight = 200;

    public boolean isShooting;
    public int isShootingDuration = 100;
    public int isShootingTimer = 0;
    private int maxStrokeWidth = 20;
    private int minStrokeWidth = 1;
    private int strokeWidth = minStrokeWidth;

    private int playerX;
    private int playerY;
    public Point destination;
    public int speed = 2;
    double directionX;
    double directionY;
    Line2D line;
    public boolean isRunning = true;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    Point mainWindowPos;

    public FrameEnemy(Game game){
        getImage();
        this.game = game;
        setWindowDefaults();
        /** BACKGROUND TO DO **/
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setSize(window.getWidth(), window.getHeight());
        this.setFocusTraversalKeysEnabled(false);
        this.enemyX = this.window.getWidth() / 2;
        this.enemyY = this.window.getHeight() / 2;
        this.mainWindowPos = this.game.getLocationOnScreen();
    }

    public void getImage(){
        this.image = ImageHandler.boss1;
    }

    public void setWindowDefaults(){
        this.window = new JFrame();
        this.window.setSize(windowWidth, windowHeight);
        Point spawn = setValidSpawnPoint();
        this.destination = spawn;
        this.windowPosX = spawn.x;
        this.windowPosY = spawn.y;

        this.window.setLocation(spawn.x, spawn.y);
        ImageIcon img = new ImageIcon("src/resource/pepe.png");
        window.setIconImage(img.getImage());

        this.window.setFocusTraversalKeysEnabled(false);
        this.window.setFocusableWindowState(false);
        this.window.setResizable(false);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.add(this);
        this.window.setVisible(true);
    }

    private Point setValidSpawnPoint(){
        Random random = new Random();
        int posX;
        int posY;

        posY = random.nextInt(dim.height - this.window.getHeight());

        if(random.nextInt(2) == 1){
            posX = random.nextInt(Math.abs(this.game.getLocationOnScreen().x - this.window.getWidth()));
        }else{
            int pos = Math.abs(dim.width - (this.game.getLocationOnScreen().x + this.game.getWidth()));
            posX = random.nextInt(pos) + this.game.getLocationOnScreen().x + this.game.getWidth();
        }
        return new Point(posX, posY);
    }

    public void getNewDestination(){
        this.destination = setValidSpawnPoint();
    }

    public void rotate(BufferedImage image, AffineTransform at){
        if(this.isShootingTimer < 30){
            directionX = this.game.player.xLocationOnScreen - (this.getLocationOnScreen().x +   image.getWidth() / 2.0);
            directionY = this.game.player.yLocationOnScreen - (this.getLocationOnScreen().y +   image.getHeight() / 2.0);
        }
        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void move(){
        if(FPS.timer > 950000000){
            if(Math.random() < 0.5){
                getNewDestination();
            }
        }

        if(!isShooting){
            Point destinationdirection = new Point(this.destination.x - this.getLocationOnScreen().x, this.destination.y - this.getLocationOnScreen().y);
            double angle = Math.atan2(destinationdirection.y, destinationdirection.x);
            double dx = Math.cos(angle) * this.speed;
            double dy = Math.sin(angle) * this.speed;

            if(Math.abs(this.windowPosX - this.destination.x) > 100){
                this.windowPosX += (int) dx;
            }

            if(Math.abs(this.windowPosY - this.destination.y) > 100){
                this.windowPosY += (int) dy;
            }

            this.window.setLocation(windowPosX, windowPosY);
        }
    }

    public void shootAnimation(Graphics2D g2){
        if(!this.isShooting)return;
        g2.setColor(Color.BLUE);
        if (isShootingTimer < isShootingDuration){
            if(isShootingTimer < 30){
                playerX = (int) (-this.getLocationOnScreen().x + this.game.player.xLocationOnScreen);
                playerY = (int) (-this.getLocationOnScreen().y + this.game.player.yLocationOnScreen);
            }

            // if(mainWindowPos.x != game.getLocationOnScreen().x && mainWindowPos.y != game.getLocationOnScreen().y){
            //     playerX = (int) (-this.getLocationOnScreen().x + this.game.player.xLocationOnScreen);
            //     playerY = (int) (-this.getLocationOnScreen().x + this.game.player.yLocationOnScreen);
            // }

            this.line = new Line2D.Double(this.enemyX, this.enemyY, playerX, playerY);
            g2.setStroke(new BasicStroke(strokeWidth));
            isShootingTimer++;

            mainWindowPos = game.getLocationOnScreen();

            if(isShootingTimer > 60){
                g2.setColor(Color.RED);
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

    public void update(){
        this.enemyXLocationOnScreen = this.enemyX + this.window.getLocationOnScreen().x;
        this.enemyYLocationOnScreen = this.enemyY + this.window.getLocationOnScreen().y;
        // move();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        this.enemyX = this.getWidth() / 2;
        this.enemyY = this.getHeight() / 2;

        AffineTransform at = AffineTransform.getTranslateInstance(this.enemyX - this.image.getWidth() / 2, this.enemyY - this.image.getHeight() / 2);
        rotate(image, at);

        shootAnimation(g2); 

        g2.drawImage(this.image, at, null);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.dispose();
    }
}