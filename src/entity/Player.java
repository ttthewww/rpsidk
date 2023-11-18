package entity;

import main.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    int x;
    int y;

    public ArrayList<Bullet> bullets = new ArrayList<>();
    private int fireCooldown = 0;
    public int reloadTime = 30;
    private int changeBulletTypeCooldown = 60;
    private int changeBulletTypeTime = 60;
    private double acceleration = 1;
    private int width = 30;
    private int height = 30;
    public double speed_x_right = 0;
    public double speed_x_left  = 0;
    public double speed_y_up = 0;
    public double speed_y_down = 0;
    public double top_speed = 4;
    double deceleration = 0.1;
    public int bulletType = 1;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        getPlayerImage();
//        this.colRect = new Rectangle(this.x + playerImage.getWidth(), this.y - playerImage.getHeight() / 2, playerImage.getWidth(), playerImage.getHeight());
        this.colRect = new Rectangle(this.x + width, this.y - height / 2, width, height);
        setDefaultValues();
    }

    public void setDefaultValues(){
        this.x = 500;
        this.y = 500;
    }

    public void getPlayerImage() {
        try {
            playerFrames = new BufferedImage[5];

            for (int i = 0; i < 5; i++) {
                BufferedImage frame = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/player/idle" + (i + 1) + ".png")));
                if (frame != null) {
                    playerFrames[i] = frame;
                } else {
                    System.err.println("Error loading image for frame " + i);
                    // Handle the error or provide a default image
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public void toggleBulletType(){
        if(changeBulletTypeCooldown >= changeBulletTypeTime){
            bulletType++;
            if(bulletType > 3){
                bulletType = 1;
            }
            System.out.println(bulletType);
            changeBulletTypeCooldown = 0;
        }
    }
    public void shoot(int targetX, int targetY, WindowContainer window){
        if(fireCooldown >= reloadTime){
            double directionX = this.gp.absoluteMouseX - ((gp.getLocationOnScreen().x + this.x));
            double directionY = this.gp.absoluteMouseY - ((gp.getLocationOnScreen().y + this.y));
            double rotationAngleInRadians = Math.atan2(directionY, directionX);
//            System.out.println(rotationAngleInRadians * 180 / Math.PI);

            bullets.add(new PlayerBullet(this, this.gp, rotationAngleInRadians, this.bulletType, window));
            fireCooldown = 3;
        }
    }

    public void move(){
        if(keyH.rightPressed){
            this.speed_x_right += this.acceleration;
            if (this.speed_x_right > this.top_speed){
                this.speed_x_right = this.top_speed;
            }
        }else{
            this.speed_x_right -= this.deceleration;
        }

        if(keyH.leftPressed){
            this.speed_x_left -= this.acceleration;
            if (this.speed_x_left < -this.top_speed){
                this.speed_x_left = -this.top_speed;
            }
        }else{
            this.speed_x_left += this.deceleration;
        }

        if(keyH.downPressed){
            this.speed_y_down += this.acceleration;
            if (this.speed_y_down > this.top_speed){
                this.speed_y_down = this.top_speed;
            }
        }else{
            this.speed_y_down -= this.deceleration;
        }

        if(keyH.upPressed){
            this.speed_y_up -= this.acceleration;
            if (this.speed_y_up < -this.top_speed){
                this.speed_y_up = -this.top_speed;
            }
        }else{
            this.speed_y_up += this.deceleration;
        }


        if (this.speed_x_right < 0){
            this.speed_x_right = 0;
        }
        if (this.speed_x_left > 0){
            this.speed_x_left = 0;
        }
        if (this.speed_y_down < 0){
            this.speed_y_down = 0;
        }
        if (this.speed_y_up > 0){
            this.speed_y_up = 0;
        }

        this.x += this.speed_x_right;
        this.x += this.speed_x_left;
        this.y += this.speed_y_up;
        this.y += this.speed_y_down;

        if(this.x - 15 <= 0){
            this.x = 15;
        }

        if(this.x + 15 >= gp.getWidth()){
            this.x = gp.getWidth() - 15;
        }

        if(this.y - 15 <= 0){
            this.y = 15;
        }
        
        if(this.y + 15 >= gp.getHeight()){
            this.y = gp.getHeight() - 15;
        }
    }

    public void updatePlayerBullets(){
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
            bullet.checkWallCollision();
            if (!bullet.isActive()) {
                iterator.remove();
            }
        }
    }

    public void update(GamePanel gp, WindowContainer window){
        move();

        //Shooting
        fireCooldown++;
        if(fireCooldown >= reloadTime)fireCooldown = reloadTime;
        if(keyH.spacePressed){
            shoot(gp.absoluteMouseX , gp.absoluteMouseY, window);
        }
        updatePlayerBullets();

        //Toggling bullet type
        changeBulletTypeCooldown++;
        if(changeBulletTypeCooldown >= changeBulletTypeTime)changeBulletTypeCooldown = changeBulletTypeTime;
        if(keyH.tabPressed){
            toggleBulletType();
        }
        //Updating collision box
        this.colRect.x = this.x - 15;
        this.colRect.y = this.y - 15;
    }
    public void rotate(BufferedImage image, GamePanel gamePanel, AffineTransform at){
        double directionX = gamePanel.absoluteMouseX - ((gp.getLocationOnScreen().x + this.x));
        double directionY = gamePanel.absoluteMouseY - ((gp.getLocationOnScreen().y + this.y));
        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void draw(Graphics2D g2, GamePanel gamePanel) {
        BufferedImage image = playerFrames[0];
        if (playerFrames != null && playerFrames.length > 0) {
            int frameIndex = (int) ((System.currentTimeMillis() / 100) % playerFrames.length);
            image = playerFrames[frameIndex];
        }

        AffineTransform at = AffineTransform.getTranslateInstance(this.x - image.getWidth() / 2.0, this.y - image.getHeight() / 2.0);
        rotate(image, gamePanel, at);
        g2.drawImage(image, at, null);

        for (Bullet bullet : bullets) {
            bullet.draw(g2, gamePanel);
        }

        g2.drawOval(this.x, this.y, 1, 1);
        g2.draw(this.colRect);
//        g2.fill(this.colRect);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

}
