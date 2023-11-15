package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
public class Entity {
    public int x, y;
    public int speed;

    public Rectangle colRect;
    public boolean isActive;

    public BufferedImage playerImage;
    public BufferedImage enemyImage;
    public BufferedImage bulletImage;
    public Point getAbsolutePosition(GamePanel gp) {
        int absoluteX = gp.windowX + this.x;
        int absoluteY = gp.windowY + this.y;
        return new Point(absoluteX, absoluteY);
    }
}
