package entity;

import handlers.ImageHandler;
import main.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public class Enemy extends Entity implements Rotate{
    public double x;
    public double y;
    public int enemyType;
    public int attackTimer = 150;
    public int attackCooldown = 0;
    private BufferedImage aura;
    public Enemy(Game game, int enemyType){
        this.speed = 1;
        this.enemyType = enemyType;
        this.game = game;
        Point spawn = validSpawnPoint();
        this.x = spawn.x;
        this.y = spawn.y;
        getImage();

        this.colRect = this.mask.getBounds();
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
        this.mask = new Area(this.game.maskCreationThread.addMask(this));
    }

    public Point validSpawnPoint() {
        int maxAttempts = 100;
        int x;
        int y;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            if (Math.random() < 0.5) {
                x = (int) (Math.random() * -200);
            } else {
                x = (int) (Math.random() * game.getWidth() + 200) + game.getWidth();
            }

            if (Math.random() < 0.5) {
                y = (int) (Math.random() * game.getHeight() - 200);
            } else {
                y = (int) (Math.random() * game.getHeight() + 200) + game.getHeight();
            }

            return new Point(x, y);
        }
        return null;
    }
    public void update() {
        double directionX = this.game.player.x - ((this.x));
        double directionY = this.game.player.y - ((this.y));

        double angle = Math.atan2(directionY, directionX);
        double dx = Math.cos(angle) * this.speed;
        double dy = Math.sin(angle) * this.speed;

        this.x += dx;
        this.y += dy;

        this.x -= this.game.player.speed_x_right * 0.5;
        this.x -= this.game.player.speed_x_left * 0.5;
        this.y -= this.game.player.speed_y_up * 0.5;
        this.y -= this.game.player.speed_y_down * 0.5;

        if (this.game.maskCreationThread.getMask(this) != null) {
            Area newMask = this.game.maskCreationThread.getMask(this);
            AffineTransform at = AffineTransform.getTranslateInstance(this.x, this.y);
            directionX = this.game.player.x - (this.x + (double) this.image.getWidth() / 2);
            directionY = this.game.player.y - (this.y + (double) this.image.getHeight() / 2);
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
        double directionX = this.game.player.x - (this.x + (double)image.getWidth() / 2);
        double directionY = this.game.player.y - (this.y + (double)image.getHeight() / 2);
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

        g2.draw(this.mask);
//        g2.draw(this.colRect);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

}