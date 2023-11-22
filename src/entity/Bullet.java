package entity;

import main.GamePanel;
import handlers.ImageHandler;
import main.MaskCreationThread;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

import static main.GamePanel.maskCreationThread;

public abstract class Bullet extends Entity{
    double angle;
    double dx;
    double dy;
    int targetX;
    int targetY;
    public int bulletType;
    GamePanel gp;
    double directionX;
    double directionY;

    MaskCreationThread maskThread;

    double rotationAngleInRadians;

    public Bullet(GamePanel gp, double angle, int bulletType){
        this.bulletType = bulletType;
        getBulletImage();
        this.gp = gp;
        this.speed = 8;
        this.x = Player.x;
        this.y = Player.y;

        directionX = this.gp.absoluteMouseX - ((gp.getLocationOnScreen().x + this.x));
        directionY = this.gp.absoluteMouseY - ((gp.getLocationOnScreen().y + this.y));
        this.angle = Math.atan2(directionY, directionX);
        this.rotationAngleInRadians = Math.atan2(directionY, directionX);
        this.dx = Math.cos(angle) * this.speed;
        this.dy = Math.sin(angle) * this.speed;

        this.isActive = true;
        this.colRect = mask.getBounds();
        this.colRect.x = (int) (this.x - this.image.getWidth() / 2.0);
        this.colRect.y = (int) (this.y - this.image.getHeight() / 2.0);
    }

    public void getBulletImage(){
        try{
            if(this.bulletType == 1){
                this.image = ImageHandler.rockBulletImage;
            }else if(this.bulletType == 2){
                this.image = ImageHandler.paperBulletImage;
            }else{
                this.image = ImageHandler.scissorBulletImage;
            }

            this.mask = new Area(maskCreationThread.addMask(this));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void rotate(BufferedImage image , AffineTransform at){
        double rotationAngleInRadians = Math.atan2(this.directionY, this.directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void update(){
        if(this.x >= this.targetX - 30 && this.y >= this.targetY - 30) {
            this.dx = Math.cos(angle) * this.speed;
            this.dy = Math.sin(angle) * this.speed;
        }

        this.x +=  this.dx;
        this.y +=  this.dy;


        if(this.x > gp.getWidth() || this.x < 0 || this.y > gp.getHeight() || this.y < 0){
            this.isActive = false;
        }

        Area newMask = maskCreationThread.getMask(this);
        AffineTransform at = AffineTransform.getTranslateInstance(this.x, this.y);
        at.rotate(rotationAngleInRadians);
        this.mask.reset();
        this.mask.add(newMask);
        this.mask.transform(at);

        this.colRect.setLocation((int) (this.x - this.image.getWidth() / 2.0), (int) (this.y - this.image.getHeight() / 2.0));
    }


    public void draw(Graphics2D g2, GamePanel gamePanel){
        AffineTransform at =  AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        rotate(this.image, at);
        g2.drawImage(image, at, null);
        g2.setColor(Color.RED);

//        g2.draw(this.mask);
//        g2.draw(this.colRect);

        g2.drawOval((int) this.x, (int) this.y, 2, 2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
