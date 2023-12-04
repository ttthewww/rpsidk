package entity;


import main.Game;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public double x, y;
    Game game;
    public double speed;
    public boolean isActive = true;
    public BufferedImage image;
    public Area mask;
    public abstract void getImage();
    public abstract void draw(Graphics2D g2);
}
