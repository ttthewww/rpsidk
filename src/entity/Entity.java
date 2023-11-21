package entity;

import main.GamePanel;
import main.MaskCreationThread;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {
    public int x, y;
    public int speed;
    public Rectangle colRect;
    public boolean isActive = true;
    public BufferedImage image;
    public BufferedImage[] playerFrames;
    public Area mask;
}
