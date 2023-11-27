package entity;

import main.Game;
import handlers.ImageHandler;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public class Bullet extends Entity{
    public int bulletType;
    double angle;
    double dx;
    double dy;
    int targetX;
    int targetY;
    double directionX;
    double directionY;
    double rotationAngleInRadians;

    public Bullet(Game game, double angle){
        this.speed = 8;
        this.bulletType = game.player.bulletType;
        this.game = game;
        getImage();
        this.x = game.player.x;
        this.y = game.player.y;

        directionX = this.game.absoluteMouseX - ((game.getLocationOnScreen().x + this.x));
        directionY = this.game.absoluteMouseY - ((game.getLocationOnScreen().y + this.y));
        this.angle = Math.atan2(directionY, directionX);
        this.rotationAngleInRadians = Math.atan2(directionY, directionX);
        this.dx = Math.cos(angle) * this.speed;
        this.dy = Math.sin(angle) * this.speed;

        this.isActive = true;
        this.colRect = mask.getBounds();
        this.colRect.x = (int) (this.x - this.image.getWidth() / 2.0);
        this.colRect.y = (int) (this.y - this.image.getHeight() / 2.0);
    }

    public void getImage(){
        if(this.bulletType == 1){
            this.image = ImageHandler.rockBulletImage;
        }else if(this.bulletType == 2){
            this.image = ImageHandler.paperBulletImage;
        }else{
            this.image = ImageHandler.scissorBulletImage;
        }

        this.mask = new Area(this.game.maskCreationThread.addMask(this));
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

        //todo should hit frame enemies
        if(this.x > game.getWidth() || this.x < 0 || this.y > game.getHeight() || this.y < 0){
            this.isActive = false;
        }

        Area newMask = this.game.maskCreationThread.getMask(this);
        AffineTransform at = AffineTransform.getTranslateInstance(this.x, this.y);
        at.rotate(rotationAngleInRadians);
        this.mask.reset();
        this.mask.add(newMask);
        this.mask.transform(at);
        this.colRect.setLocation((int) (this.x - this.image.getWidth() / 2.0), (int) (this.y - this.image.getHeight() / 2.0));
    }


    public void draw(Graphics2D g2){
        AffineTransform at =  AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        rotate(this.image, at);
        g2.drawImage(image, at, null);
        g2.setColor(Color.RED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
