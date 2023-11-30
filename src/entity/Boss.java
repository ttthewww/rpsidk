package entity;

import handlers.ImageHandler;
import handlers.Sound;
import main.Game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class Boss extends Enemy implements Sound {
    FrameEnemy frameEnemy;

    public boolean isShooting;
    private int isShootingDuration = 100;
    private int isShootingTimer = 0;
    private int maxStrokeWidth = 20;
    private int minStrokeWidth = 1;
    private int laserShotSoundCount = 0;
    private int strokeWidth = minStrokeWidth;
    Line2D line;
    public double playerX;
    public double playerY;
    double originX;
    double originY;
    double directionX;
    double directionY;

    public Boss(Game game, FrameEnemy frameEnemy) {
        super(game, 4);
        this.frameEnemy = frameEnemy;
    }

    public void getImage(){
        this.image = ImageHandler.boss1;
        this.mask = new Area(this.game.maskCreationThread.addMask(this));
    }

//    public void rotate(BufferedImage image, AffineTransform at){
//        if(!this.isShooting){
//            directionX = this.game.player.xLocationOnScreen - (this.xLocationOnScreen + (double) image.getWidth() / 2);
//            directionY = this.game.player.yLocationOnScreen - (this.yLocationOnScreen + (double) image.getHeight() / 2);
//        }
//
//        double rotationAngleInRadians = Math.atan2(directionY, directionX);
//        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
//    }

    public void rotate(BufferedImage image, AffineTransform at){
        if(this.frameEnemy.isShootingTimer < 30){
            directionX = this.game.player.x - (this.x);
            directionY = this.game.player.y - (this.y);
        }


        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void draw(Graphics2D g2){
        this.x = -this.game.window.getLocationOnScreen().x + this.frameEnemy.enemyXLocationOnScreen;
        this.y = -this.game.window.getLocationOnScreen().y + this.frameEnemy.enemyYLocationOnScreen;

        originX = -this.game.window.getLocationOnScreen().x + this.frameEnemy.enemyXLocationOnScreen;
        originY = -this.game.window.getLocationOnScreen().y + this.frameEnemy.enemyYLocationOnScreen;

        AffineTransform at = AffineTransform.getTranslateInstance(originX - image.getWidth() / 2.0, originY - image.getHeight() / 2.0);
        rotate(image, at);

        g2.setColor(Color.RED);

        if (this.isShooting) {
            if (isShootingTimer < isShootingDuration) {
                if(isShootingTimer < 30){
                    playerX = this.game.player.x;
                    playerY = this.game.player.y;
                }
//                double originX = -this.game.window.getLocationOnScreen().x + this.frameEnemy.xLocationOnScreen;
//                double originY = -this.game.window.getLocationOnScreen().y + this.frameEnemy.yLocationOnScreen;

                double rise = playerY - originY;
                double run = playerX - originX;

                this.line = new Line2D.Double(playerX + (run * 10), playerY + (rise * 10), originX, originY);
                g2.setStroke(new BasicStroke(strokeWidth));
                isShootingTimer++;

                if(isShootingTimer > 60){
                    g2.setColor(Color.BLUE);
                    strokeWidth = maxStrokeWidth;
                    if(laserShotSoundCount < 1){
                        playSE(4);
                        laserShotSoundCount++;
                    }
                    //setup collision
                    BasicStroke stroke = new BasicStroke(strokeWidth);
                    Shape lineShape = stroke.createStrokedShape(line);
                    Area lineArea = new Area(lineShape);
                    lineArea.intersect(this.game.player.mask);
                    if(!lineArea.isEmpty()){
//                        this.game.player.health--;
                    }
                }else{
                    strokeWidth = minStrokeWidth + ((maxStrokeWidth - minStrokeWidth) * isShootingTimer / isShootingDuration);
                }
                g2.draw(this.line);
            } else {
                this.isShooting = false;
                isShootingTimer = 0;
                strokeWidth = minStrokeWidth;
                laserShotSoundCount = 0;
            }
        }
        g2.drawImage(this.image, at, null);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
};
