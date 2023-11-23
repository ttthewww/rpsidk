package entity;


import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public double x, y;
    public double speed;
    public Rectangle colRect;
    public boolean isActive = true;
    public BufferedImage image;
    public Area mask;
}
