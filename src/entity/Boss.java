package entity;

import main.Game;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Line2D;

public class Boss extends Enemy{
    FrameEnemy frameEnemy;

    public boolean isShooting;
    private int isShootingDuration = 100;
    private int isShootingTimer = 0;
    private int maxStrokeWidth = 20;
    private int minStrokeWidth = 1;
    private int strokeWidth = minStrokeWidth;
    Line2D line;
    public double playerX;
    public double playerY;

    public Boss(Game gp, FrameEnemy frameEnemy) {
        super(gp, 4);
        this.frameEnemy = frameEnemy;
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.RED);
        if(!this.isShooting){
            playerX = this.gp.player.x;
            playerY = this.gp.player.y;
        }

        if (this.isShooting) {
            if (isShootingTimer < isShootingDuration) {
                double originX = -this.gp.window.getLocationOnScreen().x + this.frameEnemy.xLocationOnScreen;
                double originY = -this.gp.window.getLocationOnScreen().y + this.frameEnemy.yLocationOnScreen;

                double rise = playerY - originY;
                double run = playerX - originX;

                this.line = new Line2D.Double(playerX + (run * 10), playerY + (rise * 10), originX, originY);
                g2.setStroke(new BasicStroke(strokeWidth));
                isShootingTimer++;

                if(isShootingTimer > 60){
                    g2.setColor(Color.BLUE);
                    strokeWidth = maxStrokeWidth;

                    //setup collision
                    BasicStroke stroke = new BasicStroke(strokeWidth);
                    Shape lineShape = stroke.createStrokedShape(line);
                    Area lineArea = new Area(lineShape);
                    lineArea.intersect(this.gp.player.mask);
                    if(!lineArea.isEmpty()){
                        this.gp.player.health--;
                    }
                }else{
                    strokeWidth = minStrokeWidth + ((maxStrokeWidth - minStrokeWidth) * isShootingTimer / isShootingDuration);
                }
                g2.draw(this.line);
            } else {
                this.isShooting = false;
                isShootingTimer = 0;
                strokeWidth = minStrokeWidth;
            }
        }
    }
    private Point limitEndPointToWindow(double startX, double startY, double endX, double endY) {
        Rectangle windowBounds = this.gp.window.getBounds();

        double limitedEndX = Math.min(windowBounds.getMaxX(), Math.max(windowBounds.getMinX(), endX));
        double limitedEndY = Math.min(windowBounds.getMaxY(), Math.max(windowBounds.getMinY(), endY));

        return new Point((int) limitedEndX, (int) limitedEndY);
    }
};
