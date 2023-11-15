package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bullet extends Entity{
    double angle;
    double dx;
    double dy;
    int targetX;
    int targetY;
    public boolean isActive;
    GamePanel gp;
    public Bullet(Player player, int targetX, int targetY, GamePanel gp){
        //FIX BULLET PROJECTILE;
        getBulletImage();
        this.gp = gp;
        this.speed = 5;
        this.targetX = targetX;
        this.targetY = targetY;
        this.angle = Math.atan2(targetY - this.y, targetX - this.x);

        this.dx = Math.cos(angle) * 8.0;
        this.dy = Math.sin(angle) * 8.0;

        this.x = player.x; //change this to attacker
        this.y = player.y;
        this.isActive = true;

        colRect = new Rectangle(this.x, this.y, bulletImage.getWidth(), bulletImage.getHeight());
    }
    public void getBulletImage(){
        try {
            bulletImage = ImageIO.read(getClass().getResourceAsStream("/bullets/scissor.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public boolean isActive() {
        return isActive;
    }
    public void update(){
        this.angle = Math.atan2(targetY - this.y, targetX - this.x);
        this.dx = Math.cos(angle) * 8.0;
        this.dy = Math.sin(angle) * 8.0;

        this.x += (int) this.dx;
        this.y += (int) this.dy;

        if(this.x >= this.targetX - 30 && this.y >= this.targetY - 30) {
            this.targetX += (int) this.dx * 10;
            this.targetY += (int) this.dy * 10;
        }
//        if(getAbsolutePosition(this.gp).x > 0)this.x--;
//        if(getAbsolutePosition(this.gp).x< 0)this.x++;
//        if(getAbsolutePosition(this.gp).y > 0)this.y--;
//        if(getAbsolutePosition(this.gp).y < 0)this.y++;

        this.colRect.x = this.x;
        this.colRect.y = this.y;


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if(this.x > screenSize.getWidth() || this.x < 0 || this.y > screenSize.getHeight() || this.y < 0){
            this.isActive = false;
        }
    }

    public void draw(Graphics2D g2, GamePanel gameWindow){
        BufferedImage image = bulletImage;

//        double directionX = this.targetX - (gameWindow.windowX + this.x + (double) bulletImage.getWidth() / 2);
//        double directionY = this.targetY - (gameWindow.windowY + this.y + (double) bulletImage.getHeight() / 2);
//
//        double rotationAngleInRadians = Math.atan2(directionY, directionX);
//
//        AffineTransform at = AffineTransform.getTranslateInstance(this.x - bulletImage.getWidth() / 2.0, this.y - bulletImage.getHeight() / 2.0);
//
//        at.rotate(rotationAngleInRadians, bulletImage.getWidth() / 2.0, bulletImage.getHeight() / 2.0);
//
//        g2.drawImage(image, this.x, this.y, null);
        g2.setColor(Color.RED);
        g2.drawOval(this.x - 8, this.y - 8, 16, 16);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

//        g2.drawLine(this.x,this.y,targetX, targetY);
    }
}
