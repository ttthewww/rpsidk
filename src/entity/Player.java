package entity;

import handlers.ImageHandler;
import handlers.KeyHandler;
import handlers.MouseHandler;
import main.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;


public class Player extends Entity implements Rotate{
    private int health;
    public int score;
    public int fireCooldown = 0;
    public int reloadTime = 30;
    public int bulletType = 1;

    private int invunerableTime = 0;
    private int maxInvulnerableTime = 100;

    public double absoluteX;
    public double absoluteY;
    public double xLocationOnScreen;
    public double yLocationOnScreen;
    public double acceleration = 0.1;
    public double speed_x_right = 0;
    public double speed_x_left = 0;
    public double speed_y_up = 0;
    public double speed_y_down = 0;
    public double top_speed = 3;
    double deceleration = 0.1;

    KeyHandler keyH;
    MouseHandler mouseH;
    public BufferedImage[] playerFrames;
    public CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>();
    private BufferedImage bulletTypeImage = ImageHandler.rockBulletImage;
    PlayerBullet playerBullet;

    public Player(Game game) {
        this.game = game;
        playerBullet = new PlayerBullet(this.game, 0F, 0, 0);
        getImage();
        reset();
    }


    public void reset() {
        this.x = 0;
        this.y = 0;
        this.mask = new Area(this.game.maskHandler.addMask(this));
        this.x = this.game.window.getWidth() / 2;
        this.y = this.game.window.getHeight() / 2;
        this.health = 999;
        this.score = 0;

    }

    public void setHandlers(KeyHandler keyH, MouseHandler mouseH){
        this.mouseH = mouseH;
        this.keyH = keyH;
    }

    public void getImage() {
        this.image = ImageHandler.playerFrames[0];
    }

    public void takeDamage(){
        if(invunerableTime <= 0){
            this.health--;
            invunerableTime = maxInvulnerableTime;
        }
    }

    public int getHealth() {
        return this.health;
    }

    public void toggleBulletType() {
        bulletType++;
        if (bulletType > 3) {
            bulletType = 1;
        }
        if(this.bulletType == 1) this.bulletTypeImage = ImageHandler.rockBulletImage;
        if(this.bulletType == 2) this.bulletTypeImage = ImageHandler.paperBulletImage;
        if(this.bulletType == 3) this.bulletTypeImage = ImageHandler.scissorBulletImage;
    }

    public void shoot() {
        if (fireCooldown >= reloadTime) {
            double targetX = this.game.absoluteMouseX - ((game.getLocationOnScreen().x + this.x));
            double targetY = this.game.absoluteMouseY - ((game.getLocationOnScreen().y + this.y));
            double rotationAngleInRadians = Math.atan2(targetY, targetX);

            CloneableEntity newBullet = playerBullet.clone();
            PlayerBullet newPlayerBullet = (PlayerBullet)newBullet;
            newPlayerBullet.game = this.game;
            newPlayerBullet.angle = rotationAngleInRadians;
            newPlayerBullet.bulletType = this.bulletType;
            newPlayerBullet.getImage();
            newPlayerBullet.x = this.x;
            newPlayerBullet.y = this.y;
            bullets.add(newPlayerBullet);
            // bullets.add(new PlayerBullet(game, this.x, this.y, rotationAngleInRadians));
            fireCooldown = 3;
        }
    }

    public void move() {
        if (KeyHandler.rightPressed) {
            this.speed_x_right += this.acceleration;
            if (this.speed_x_right > this.top_speed) {
                this.speed_x_right = this.top_speed;
            }
        } else {
            this.speed_x_right -= this.deceleration;
        }

        if (KeyHandler.leftPressed) {
            this.speed_x_left -= this.acceleration;
            if (this.speed_x_left < -this.top_speed) {
                this.speed_x_left = -this.top_speed;
            }
        } else {
            this.speed_x_left += this.deceleration;
        }

        if (KeyHandler.downPressed) {
            this.speed_y_down += this.acceleration;
            if (this.speed_y_down > this.top_speed) {
                this.speed_y_down = this.top_speed;
            }
        } else {
            this.speed_y_down -= this.deceleration;
        }

        if (KeyHandler.upPressed) {
            this.speed_y_up -= this.acceleration;
            if (this.speed_y_up < -this.top_speed) {
                this.speed_y_up = -this.top_speed;
            }
        } else {
            this.speed_y_up += this.deceleration;
        }


        if (this.speed_x_right < 0) {
            this.speed_x_right = 0;
        }
        if (this.speed_x_left > 0) {
            this.speed_x_left = 0;
        }
        if (this.speed_y_down < 0) {
            this.speed_y_down = 0;
        }
        if (this.speed_y_up > 0) {
            this.speed_y_up = 0;
        }

        if(!KeyHandler.shiftPressed){
            this.x += (int) this.speed_x_right;
            this.x += (int) this.speed_x_left;
            this.y += (int) this.speed_y_up;
            this.y += (int) this.speed_y_down;
        }

        this.absoluteX += this.speed_x_right;
        this.absoluteX += this.speed_x_left;
        this.absoluteY += this.speed_y_up;
        this.absoluteY += this.speed_y_down;


        if (this.x - 15 <= 0) {
            this.x = 15;
        }

        if (this.x + 15 >= game.getWidth()) {
            this.x = game.getWidth() - 15;
        }

        if (this.y - 15 <= 0) {
            this.y = 15;
        }

        if (this.y + 15 >= game.getHeight()) {
            this.y = game.getHeight() - 15;
        }

        this.xLocationOnScreen = this.game.getLocationOnScreen().x + this.x;
        this.yLocationOnScreen = this.game.getLocationOnScreen().y + this.y;
    }

    public void updatePlayerBullets(){
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
        }
    }

    public void rotate(BufferedImage image, AffineTransform at) {
        double directionX = this.game.absoluteMouseX - ((game.getLocationOnScreen().x + this.x));
        double directionY = this.game.absoluteMouseY - ((game.getLocationOnScreen().y + this.y));
        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);

        if (this.game.maskHandler.getMask(this) != null) {
            Area newMask = this.game.maskHandler.getMask(this);
            at = AffineTransform.getTranslateInstance(this.x, this.y);

            directionX = this.game.absoluteMouseX - ((game.getLocationOnScreen().x + this.x));
            directionY = this.game.absoluteMouseY - ((game.getLocationOnScreen().y + this.y));
            rotationAngleInRadians = Math.atan2(directionY, directionX);

            at.rotate(rotationAngleInRadians);
            this.mask.reset();
            this.mask.add(newMask);
            this.mask.transform(at);
        }
    }

    public void update(){
        move();
        invunerableTime--;
        //Shooting
        fireCooldown++;
        if (fireCooldown >= reloadTime) fireCooldown = reloadTime;
        if (KeyHandler.spacePressed || mouseH.shoot) {
            shoot();
        }
        updatePlayerBullets();
    }

    public void draw(Graphics2D g2) {
        if (ImageHandler.playerFrames != null && ImageHandler.playerFrames.length > 0) {
            int frameIndex = (int) ((System.currentTimeMillis() / 100) % ImageHandler.playerFrames.length);
            image = ImageHandler.playerFrames[frameIndex];
        }

        AffineTransform at = AffineTransform.getTranslateInstance(this.x - image.getWidth() / 2.0, this.y - image.getHeight() / 2.0);
        rotate(image, at);
        g2.drawImage(image, at, null);
        g2.drawImage(this.bulletTypeImage, AffineTransform.getTranslateInstance(this.game.getWidth() / 2.0 - this.bulletTypeImage.getWidth() / 2.0,  10), null);

        g2.setColor(Color.RED);

        // g2.draw(this.mask);

        g2.setColor(Color.BLUE);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}