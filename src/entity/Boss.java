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

    public void update(){

    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.RED);
        if(!this.isShooting){
            playerX = this.gp.player.x;
            playerY = this.gp.player.y;
        }

        if (this.isShooting) {
            if (isShootingTimer < isShootingDuration) {
//                double startX = -this.gp.window.getLocationOnScreen().x + this.frameEnemy.xLocationOnScreen;
//                double startY = -this.gp.window.getLocationOnScreen().y + this.frameEnemy.yLocationOnScreen;
                double startX = -this.gp.window.getLocationOnScreen().x + this.frameEnemy.xLocationOnScreen;
                double startY = -this.gp.window.getLocationOnScreen().y + this.frameEnemy.yLocationOnScreen;

                double endX = playerX;
                double endY = playerY;

                // Limit the end point to the window boundaries
                Point limitedEndPoint = limitEndPointToWindow(startX, startY, endX, endY);
                double adjustedEndX = limitedEndPoint.x + playerX;
                double adjustedEndY = limitedEndPoint.y + playerY;
                this.line = new Line2D.Double(startX, startY, adjustedEndX, adjustedEndY);
                isShootingTimer++;
                if(isShootingTimer > 50){
                    strokeWidth = minStrokeWidth + ((maxStrokeWidth - minStrokeWidth) * isShootingTimer / isShootingDuration);
//                    strokeWidth = maxStrokeWidth;
                }
            } else {
                this.isShooting = false;
                isShootingTimer = 0;
                strokeWidth = minStrokeWidth;
            }
        }

        if (this.isShooting) {
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(strokeWidth));
            g2.draw(this.line);

            BasicStroke stroke = new BasicStroke(strokeWidth);
            Shape lineShape = stroke.createStrokedShape(line);

            Area lineArea = new Area(lineShape);
            lineArea.intersect(this.gp.player.mask);
            if(isShootingTimer > 60){
                if(!lineArea.isEmpty()){
                    this.gp.player.health--;
                }
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
