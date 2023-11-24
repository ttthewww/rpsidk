package entity;

import main.GamePanel;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public interface Rotate {
    void rotate(BufferedImage image, AffineTransform at);
}
