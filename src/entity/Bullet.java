package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

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
    public Bullet(Player player, GamePanel gp, double angle, int bulletType){
        this.bulletType = bulletType;
        getBulletImage();
        this.gp = gp;
        this.speed = 8;
        this.x = player.x ;
        this.y = player.y ;

        directionX = this.gp.absoluteMouseX - ((gp.getLocationOnScreen().x + this.x));
        directionY = this.gp.absoluteMouseY - ((gp.getLocationOnScreen().y + this.y));
        this.angle = Math.atan2(directionY, directionX);

        this.dx = Math.cos(angle) * this.speed;
        this.dy = Math.sin(angle) * this.speed;

        this.isActive = true;

        colRect = new Rectangle((int) this.x, (int) this.y, this.image.getWidth(), this.image.getHeight());
    }
    public void getBulletImage(){
        if(this.bulletType == 1){
            this.image = rockBulletImage;
        }else if(this.bulletType == 2){
            this.image = paperBulletImage;
        }else{
            this.image = scissorBulletImage;
        }
    }

    public boolean isActive() {
        return isActive;
    }
    public void update(){
        if(this.x >= this.targetX - 30 && this.y >= this.targetY - 30) {
            this.dx = Math.cos(angle) * this.speed;
            this.dy = Math.sin(angle) * this.speed;
        }

        this.x +=  this.dx;
        this.y +=  this.dy;

        this.colRect.x = (int) this.x;
        this.colRect.y = (int) this.y;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        System.out.println(this.x + " " + this.y);
        if(this.x > gp.getWidth() || this.x < 0 || this.y > gp.getHeight() || this.y < 0){
            this.isActive = false;
        }
    }
    public void rotate(BufferedImage image , AffineTransform at){
//        double directionX = this.gp.absoluteMouseX - ((gp.getLocationOnScreen().x + this.x));
//        double directionY = this.gp.absoluteMouseY - ((gp.getLocationOnScreen().y + this.y));
        double rotationAngleInRadians = Math.atan2(this.directionY, this.directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void draw(Graphics2D g2, GamePanel gamePanel){
        AffineTransform at =  AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        rotate(this.image, at);
        g2.drawImage(image, at, null);

        g2.setColor(Color.RED);

        g2.drawOval((int) this.x, (int) this.y, 1, 1);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.drawLine(this.x,this.y, this.targetX, this.targetY);
    }
    public void checkWallCollision(){

    }
}
