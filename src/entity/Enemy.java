package entity;

import main.GamePanel;
import main.MaskCreationThread;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public abstract class Enemy extends Entity{

    GamePanel gp;
    public int x;
    public int y;

    ArrayList<Enemy> enemies;
    public int enemyType;

    MaskCreationThread maskThread;
    public Enemy(GamePanel gp, ArrayList<Enemy> enemies){
        this.gp = gp;
//        Point spawn = validSpawnPoint();
//        this.x = spawn.x;
//        this.y = spawn.y;
        this.x = 100;
        this.y = 100;
        this.enemies = enemies;
    }

    public void getEnemyImage(){
        if(this.enemyType == 1){
            this.image = rockBulletImage;
        }else if(this.enemyType == 2){
            this.image = paperBulletImage;
        }else{
            this.image = scissorBulletImage;
        }
        this.maskThread = new MaskCreationThread(this.image, 0, 0);
        maskThread.start();
    }

    public Point validSpawnPoint() {
        int maxAttempts = 100;
        int x;
        int y;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            if (Math.random() < 0.5) {
                x = (int) (Math.random() * -200);
            } else {
                x = (int) (Math.random() * gp.getWidth() + 200) + gp.getWidth();
            }

            if (Math.random() < 0.5) {
                y = (int) (Math.random() * gp.getHeight() - 200);
            } else {
                y = (int) (Math.random() * gp.getHeight() + 200) + gp.getHeight();
            }

            int distanceX = Math.abs(x - gp.player.x);
            int distanceY = Math.abs(y - gp.player.y);

            if (!(distanceX < 200 || distanceY < 200 || checkCollisionWithEnemies(x, y))) {
                return new Point(x, y);
            }
        }
        return null;
    }

    private boolean checkCollisionWithEnemies(int x, int y) {
        if (this.enemies != null && !this.enemies.isEmpty()) {
            for (Enemy otherEnemy : this.enemies) {
                if (otherEnemy != this && this.colRect.intersects(otherEnemy.colRect)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void update() {
        int speed = 1;

        for (Enemy otherEnemy : enemies) {
            if (otherEnemy != this) {
                if (this.colRect.intersects(otherEnemy.colRect)) {
                    int dx = this.x - otherEnemy.x;
                    int dy = this.y - otherEnemy.y;
                    double angle = Math.atan2(dy, dx);
                    this.x += (int) (speed * Math.cos(angle));
                    this.y += (int) (speed * Math.sin(angle));
                }
            }
        }

//        if (this.x < Player.x) {
//            this.x += speed;
//        } else if (this.x > Player.x) {
//            this.x -= speed;
//        }
//
//        if (this.y < Player.y) {
//            this.y += speed;
//        } else if (this.y > Player.y) {
//            this.y -= speed;
//        }

        this.colRect.x = this.x;
        this.colRect.y = this.y;
    }
    public void rotate(AffineTransform at, double rotationAngleInRadians){
        at.rotate(rotationAngleInRadians, this.image.getWidth() / 2.0, this.image.getHeight() / 2.0);
    }
    public void draw(Graphics2D g2) {
        double directionX = Player.x - (this.x + (double) this.image.getWidth() / 2);
        double directionY = Player.y - (this.y + (double) this.image.getHeight() / 2);
        double rotationAngleInRadians = Math.atan2(directionY, directionX);

        AffineTransform at = AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        rotate(at, rotationAngleInRadians);

        g2.drawImage(this.image, at, null);

//        if (maskThread.isAlive()) {
//            try {
//                maskThread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        Area mask = this.maskThread.getMask();
        if (mask != null) {
            this.mask = new Area(mask);
            this.mask.transform(AffineTransform.getRotateInstance(rotationAngleInRadians));
            this.mask.transform(AffineTransform.getTranslateInstance(this.x, this.y));

            g2.setColor(Color.BLUE);
            g2.draw(this.mask);

            g2.setClip(null);
        }

        g2.drawOval(this.x, this.y, 3, 3);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

}