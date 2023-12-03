package entity;

import handlers.ImageHandler;
import handlers.Sound;
import main.FPS;
import main.Game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

public class Twin extends Enemy implements Sound {
    TwinFrame enemyFrame;
    public boolean isShooting;

    private int isShootingDuration;
    private int isShootingTimer;
    private int maxStrokeWidth;
    private int minStrokeWidth;
    private int strokeWidth;

    private int laserShotSoundCount = 0;
    Line2D line;
    public double playerX;
    public double playerY;
    double originX;
    double originY;
    double directionX;
    double directionY;
    private final Object lock = new Object();

    public Twin(Game game, TwinFrame enemyFrame, int isShootingDuration, int isShootingTimer, int maxStrokeWidth, int minStrokeWidth) {
        super(game, 4);
        this.enemyFrame = enemyFrame;

        this.isShootingDuration = isShootingDuration;
        this.isShootingTimer = isShootingTimer;
        this.maxStrokeWidth = maxStrokeWidth;
        this.minStrokeWidth = minStrokeWidth;
        this.strokeWidth = this.minStrokeWidth;
    }

    public void getImage(){
        this.image = ImageHandler.boss1;
        this.mask = new Area(this.game.maskCreationThread.addMask(this));
    }

    public void shoot(){
        if(FPS.timer > 950000000 && !isShooting){
            if(Math.random() < 0.2){
                this.isShooting = true;
                this.enemyFrame.isShooting = true;
            }
        }
    }

    public void update(){
        synchronized (lock) {
            shoot();
            if (this.game.maskCreationThread.getMask(this) != null) {
                Area newMask = this.game.maskCreationThread.getMask(this);
                AffineTransform at = AffineTransform.getTranslateInstance(this.originX, this.originY);

                if(this.isShootingTimer < 30){
                    directionX = this.game.player.x - (this.x);
                    directionY = this.game.player.y - (this.y);
                }

                double rotationAngleInRadians = Math.atan2(directionY, directionX);
                at.rotate(rotationAngleInRadians);

                this.mask.reset();
                this.mask.add(newMask);
                this.mask.transform(at);
            }
        }
    }


    public void rotate(BufferedImage image, AffineTransform at){
        if(this.isShootingTimer < 30){
            directionX = this.game.player.x - (this.x);
            directionY = this.game.player.y - (this.y);
        }

        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void draw(Graphics2D g2){
        synchronized (lock) {
            this.x = -this.game.window.getLocationOnScreen().x + this.enemyFrame.enemyXLocationOnScreen;
            this.y = -this.game.window.getLocationOnScreen().y + this.enemyFrame.enemyYLocationOnScreen;

            originX = -this.game.window.getLocationOnScreen().x + this.enemyFrame.enemyXLocationOnScreen;
            originY = -this.game.window.getLocationOnScreen().y + this.enemyFrame.enemyYLocationOnScreen;

            AffineTransform at = AffineTransform.getTranslateInstance(originX - image.getWidth() / 2.0, originY - image.getHeight() / 2.0);
            rotate(image, at);

            g2.setColor(Color.BLUE);

            if (this.isShooting) {
                if (isShootingTimer < isShootingDuration) {
                    if(isShootingTimer < 30){
                        playerX = this.game.player.x;
                        playerY = this.game.player.y;
                    }

                    double rise = playerY - originY;
                    double run = playerX - originX;

                    this.line = new Line2D.Double(playerX + (run * 10), playerY + (rise * 10), originX, originY);
                    g2.setStroke(new BasicStroke(strokeWidth));
                    isShootingTimer++;

                    if(isShootingTimer > 60){
                        g2.setColor(Color.RED);
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
                            this.game.player.takeDamage();
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
    }
};