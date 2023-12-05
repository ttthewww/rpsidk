package entity;

import main.Game;
import handlers.ImageHandler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public class Bullet extends Entity implements CloneableEntity{
    public int bulletType;
    double angle;
    double dx;
    double dy;

    public Bullet(Game game, double x, double y, double angle){
        this.speed = 8;
        this.bulletType = 0;
        this.game = game;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void getImage(){
        if(this.bulletType == 1){
            this.image = ImageHandler.rockBulletImage;
        }else if(this.bulletType == 2){
            this.image = ImageHandler.paperBulletImage;
        }else{
            this.image = ImageHandler.scissorBulletImage;
        }

        this.mask = new Area(this.game.maskHandler.addMask(this));
    }

    public void rotate(BufferedImage image , AffineTransform at){
        at.rotate(this.angle, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void update(){
        this.dx = Math.cos(angle) * this.speed;
        this.dy = Math.sin(angle) * this.speed;

        this.x +=  this.dx;
        this.y +=  this.dy;

        //todo should hit frame enemies
        if(this.x > game.getWidth() || this.x < 0 || this.y > game.getHeight() || this.y < 0){
            this.isActive = false;
        }

        Area newMask = this.game.maskHandler.getMask(this);
        AffineTransform at = AffineTransform.getTranslateInstance(this.x, this.y);
        at.rotate(this.angle);
        this.mask.reset();
        this.mask.add(newMask);
        this.mask.transform(at);
    }

    public void draw(Graphics2D g2){
        AffineTransform at =  AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        rotate(this.image, at);
        g2.drawImage(image, at, null);
        g2.setColor(Color.RED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    public CloneableEntity clone() {
        try {
            CloneableEntity clonedObject = (Bullet) super.clone();
            return clonedObject;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}