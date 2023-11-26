package entity;

import handlers.ImageHandler;
import main.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import static main.Game.maskCreationThread;

public class Enemy extends Entity implements Rotate{
    Game gp;
    public double x;
    public double y;
    public int enemyType;
    public int attackTimer = 150;
    public int attackCooldown = 0;
    private BufferedImage aura;
    public Enemy(Game gp, int enemyType){
        this.speed = 1;
        this.enemyType = enemyType;
        this.gp = gp;
//        this.x = 200;
//        this.y = 20;
        Point spawn = validSpawnPoint();
        this.x = spawn.x;
        this.y = spawn.y;
        getImage();

        this.colRect = this.mask.getBounds();

//        if(this.enemyType == 1){
//            this.enemyWindowContainer= new EnemyWindowContainer(
//                    100,
//                    100,
//                    100,
//                    100,
//                    this,
//                    gp
//            );
//            Thread enemyWindowThread = new Thread(this.enemyWindowContainer);
//            enemyWindowThread.start();
//        }
    }

    public void getImage(){
        if(this.enemyType == 1){
            this.image = ImageHandler.enemyRockImage;
            this.aura = ImageHandler.enemyRockAura;
        }else if(this.enemyType == 2){
            this.image = ImageHandler.enemyPaperImage;
            this.aura = ImageHandler.enemyPaperAura;
        }else{
            this.image = ImageHandler.enemyScissorImage;
            this.aura = ImageHandler.enemyScissorsAura;
        }

        this.mask = new Area(maskCreationThread.addMask(this));
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

            return new Point(x, y);
        }
        return null;
    }

//    private boolean checkCollisionWithEnemies(int x, int y) {
//        if (this.enemies != null && !this.enemies.isEmpty()) {
//            for (Enemy otherEnemy : this.enemies) {
//                if (otherEnemy != this && this.colRect.intersects(otherEnemy.colRect)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public void update() {
        double directionX = this.gp.player.x - ((this.x));
        double directionY = this.gp.player.y - ((this.y));

        double angle = Math.atan2(directionY, directionX);
        double dx = Math.cos(angle) * this.speed;
        double dy = Math.sin(angle) * this.speed;

        this.x += dx;
        this.y += dy;

        this.x -= this.gp.player.speed_x_right * 0.5;
        this.x -= this.gp.player.speed_x_left * 0.5;
        this.y -= this.gp.player.speed_y_up * 0.5;
        this.y -= this.gp.player.speed_y_down * 0.5;

        if (maskCreationThread.getMask(this) != null) {
            Area newMask = maskCreationThread.getMask(this);
            AffineTransform at = AffineTransform.getTranslateInstance(this.x, this.y);
            directionX = Player.x - (this.x + (double) this.image.getWidth() / 2);
            directionY = Player.y - (this.y + (double) this.image.getHeight() / 2);
            double rotationAngleInRadians = Math.atan2(directionY, directionX);

            at.rotate(rotationAngleInRadians);
            this.mask.reset();
            this.mask.add(newMask);
            this.mask.transform(at);
        }
        this.colRect.setLocation((int) (this.x - this.image.getWidth() / 2.0), (int) (this.y - this.image.getHeight() / 2.0));
        this.attackCooldown++;
        if(attackCooldown >= attackTimer){
            attackCooldown = attackTimer;
        }
    }
    public void rotate(BufferedImage image, AffineTransform at){
        double directionX = Player.x - (this.x + (double)image.getWidth() / 2);
        double directionY = Player.y - (this.y + (double)image.getHeight() / 2);
        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void draw(Graphics2D g2) {
        AffineTransform at = AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        AffineTransform auraAt = AffineTransform.getTranslateInstance(this.x - this.aura.getWidth() / 2.0, this.y - this.aura.getHeight() / 2.0);

        rotate(this.image, at);
        rotate(this.aura, auraAt);

        g2.setColor(Color.RED);

        g2.drawImage(this.aura, auraAt, null);
        g2.drawImage(this.image, at, null);

//        g2.draw(this.mask);
//        g2.draw(this.colRect);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

}