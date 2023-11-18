package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
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

    public Enemy(GamePanel gp, ArrayList<Enemy> enemies){
        this.gp = gp;
        Point spawn = validSpawnPoint();
        this.x = spawn.x;
        this.y = spawn.y;
        this.enemies = enemies;
    }

    public abstract void getEnemyImage();
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

    public void update(GamePanel gameWindow) {
        int speed = 1;
        int margin = 10; // Adjust the margin to your preference

//        if (this.x > gameWindow.getWidth() - margin) this.x = margin;
//        if (this.x < margin) this.x = gameWindow.getWidth() - margin;
//        if (this.y > gameWindow.getHeight() - margin) this.y = margin;
//        if (this.y < margin) this.y = gameWindow.getHeight() - margin;

        for (Enemy otherEnemy : enemies) {
            if (otherEnemy != this) {
                if (this.colRect.intersects(otherEnemy.colRect)) {
                    int dx = this.x - otherEnemy.x;
                    int dy = this.y - otherEnemy.y;
                    double angle = Math.atan2(dy, dx);
                    this.x += speed * Math.cos(angle);
                    this.y += speed * Math.sin(angle);
                }
            }
        }

//        if (gameWindow.getWidth() - this.x + gameWindow.player.x < this.x - gameWindow.player.x) {
//            this.x++;
//        } else if (this.x + gameWindow.getWidth() - gameWindow.player.x < gameWindow.player.x - this.x) {
//            this.x--;
//        } else {
            if (this.x < gameWindow.player.x) {
                this.x += speed;
            } else if (this.x > gameWindow.player.x) {
                this.x -= speed;
            }
//        }

//        if (gameWindow.getHeight() - this.y + gameWindow.player.y < this.y - gameWindow.player.y) {
//            this.y++;
//        } else if (this.y + gameWindow.getHeight() - gameWindow.player.y < gameWindow.player.y - this.y) {
//            this.y--;
//        } else {
            if (this.y < gameWindow.player.y) {
                this.y += speed;
            } else if (this.y > gameWindow.player.y) {
                this.y -= speed;
            }
//        }

        this.colRect.x = this.x;
        this.colRect.y = this.y;
    }

    public void draw(Graphics2D g2, GamePanel gameWindow) {
        BufferedImage image = this.image;

        double directionX = gameWindow.player.x - (this.x + (double) this.image.getWidth() / 2);
        double directionY = gameWindow.player.y - (this.y + (double) this.image.getHeight() / 2);

        double rotationAngleInRadians = Math.atan2(directionY, directionX);

        AffineTransform at = AffineTransform.getTranslateInstance(this.x, this.y);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);

        g2.drawOval(this.x + this.image.getWidth() / 2, this.y + this.image.getHeight() / 2, 1, 1);
        g2.draw(this.colRect);
        g2.drawImage(image, at, null);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}