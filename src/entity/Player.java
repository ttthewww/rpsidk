package entity;

import handlers.ImageHandler;
import handlers.KeyHandler;
import handlers.MouseHandler;
import main.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import static main.GamePanel.maskCreationThread;

public class Player extends Entity implements Rotate{
    public int health = 3;
    public int score = 0;
    public static double x;
    public static double y;
    GamePanel gp;
    KeyHandler keyH;
    MouseHandler mouseH;
    public BufferedImage[] playerFrames;
    public CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>();
    private int fireCooldown = 0;
    public int reloadTime = 30;
    private double acceleration = 1;
    public double speed_x_right = 0;
    public double speed_x_left = 0;
    public double speed_y_up = 0;
    public double speed_y_down = 0;
    public double top_speed = 4;
    double deceleration = 0.1;
    public int bulletType = 1;
    private BufferedImage bulletTypeImage = ImageHandler.rockBulletImage;

    public Player(GamePanel gp) {
        this.gp = gp;
        getImage();
        setDefaultValues();
        this.colRect = this.mask.getBounds();
    }

    public void setDefaultValues() {
        x = this.gp.window.getWidth() / 2;
        y = this.gp.window.getHeight() / 2;
    }

    public void setHandlers(KeyHandler keyH, MouseHandler mouseH){
        this.mouseH = mouseH;
        this.keyH = keyH;
    }

    public void getImage() {
        try {
            playerFrames = new BufferedImage[5];

            for (int i = 0; i < 5; i++) {
                BufferedImage frame = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/player/idle" + (i + 1) + ".png")));
                if (frame != null) {
                    playerFrames[i] = frame;
                } else {
                    System.err.println("Error loading image for frame " + i);
                }
            }
            this.image = playerFrames[0];
            this.mask = new Area(maskCreationThread.addMask(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        draw(g2, null);
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

    public void shoot(int targetX, int targetY) {
        if (fireCooldown >= reloadTime) {
            double directionX = this.gp.absoluteMouseX - ((gp.getLocationOnScreen().x + this.x));
            double directionY = this.gp.absoluteMouseY - ((gp.getLocationOnScreen().y + this.y));
            double rotationAngleInRadians = Math.atan2(directionY, directionX);
//            System.out.println(rotationAngleInRadians * 180 / Math.PI);

            bullets.add(new PlayerBullet(this.gp, rotationAngleInRadians, this));
            fireCooldown = 3;
        }
    }

    public void move() {
        if (keyH.rightPressed) {
            this.speed_x_right += this.acceleration;
            if (this.speed_x_right > this.top_speed) {
                this.speed_x_right = this.top_speed;
            }
        } else {
            this.speed_x_right -= this.deceleration;
        }

        if (keyH.leftPressed) {
            this.speed_x_left -= this.acceleration;
            if (this.speed_x_left < -this.top_speed) {
                this.speed_x_left = -this.top_speed;
            }
        } else {
            this.speed_x_left += this.deceleration;
        }

        if (keyH.downPressed) {
            this.speed_y_down += this.acceleration;
            if (this.speed_y_down > this.top_speed) {
                this.speed_y_down = this.top_speed;
            }
        } else {
            this.speed_y_down -= this.deceleration;
        }

        if (keyH.upPressed) {
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

        this.x += (int) this.speed_x_right;
        this.x += (int) this.speed_x_left;
        this.y += (int) this.speed_y_up;
        this.y += (int) this.speed_y_down;

        if (this.x - 15 <= 0) {
            this.x = 15;
        }

        if (this.x + 15 >= gp.getWidth()) {
            this.x = gp.getWidth() - 15;
        }

        if (this.y - 15 <= 0) {
            this.y = 15;
        }

        if (this.y + 15 >= gp.getHeight()) {
            this.y = gp.getHeight() - 15;
        }


    }

    public void updatePlayerBullets(){
        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
        }
    }

    public void update(GamePanel gp, WindowContainer window){
        move();
        //Shooting
        fireCooldown++;
        if (fireCooldown >= reloadTime) fireCooldown = reloadTime;
        if (keyH.spacePressed || mouseH.shoot) {
            shoot(gp.absoluteMouseX, gp.absoluteMouseY);
        }
        updatePlayerBullets();

        //Updating collision box
        this.colRect.setLocation((int) (this.x - this.image.getWidth() / 2), (int) (this.y - this.image.getHeight() / 2));
    }


    @Override
    public void rotate(BufferedImage image, AffineTransform at) {
        rotate(image, null, at);
    }
    public void rotate(BufferedImage image, GamePanel gamePanel, AffineTransform at) {
        double directionX = gamePanel.absoluteMouseX - ((gp.getLocationOnScreen().x + this.x));
        double directionY = gamePanel.absoluteMouseY - ((gp.getLocationOnScreen().y + this.y));
        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);

        if (maskCreationThread.getMask(this) != null) {
            Area newMask = maskCreationThread.getMask(this);
            at = AffineTransform.getTranslateInstance(this.x, this.y);

            directionX = this.gp.absoluteMouseX - ((gp.getLocationOnScreen().x + this.x));
            directionY = this.gp.absoluteMouseY - ((gp.getLocationOnScreen().y + this.y));
            rotationAngleInRadians = Math.atan2(directionY, directionX);

            at.rotate(rotationAngleInRadians);
            this.mask.reset();
            this.mask.add(newMask);
            this.mask.transform(at);
        }
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
        g2.drawImage(this.bulletTypeImage, AffineTransform.getTranslateInstance(this.gp.getWidth() / 2.0 - this.bulletTypeImage.getWidth() / 2,  10), null);

        g2.setColor(Color.RED);
//        g2.draw(this.colRect);
        g2.draw(this.mask);

        g2.setColor(Color.BLUE);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

}