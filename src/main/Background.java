package main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Background {
    private static final double WIDTH = 960;
    private static final double HEIGHT = 540;
    private static final double X = 0;  // Assuming X is always 0 for the background
    private static final int NUM_BACKGROUNDS = 4;

    // Fields
    private double Y;
    private double speed = 1;
    private BufferedImage[] backgrounds;
    private int currentBackgroundIndex = 0;

    /**
     * Constructor
     *
     * Initializes a Background object with the specified initial Y coordinate.
     *
     * @param Y Initial Y coordinate of the background.
     * @throws IOException Exception may occur during the image loading process.
     */
    public Background(double Y) throws IOException {
        this.Y = Y;

        // Load background images
        try{
            backgrounds = new BufferedImage[NUM_BACKGROUNDS];

            for (int i = 1; i <= NUM_BACKGROUNDS; i++) {
                backgrounds[i - 1] = ImageIO.read(Objects.requireNonNull(getClass().getResource("../resource/background/bac" + i + ".png")));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void update(double delta, JPanel panel) {
        Y += speed * delta;

        if (Y >= panel.getWidth() - 30) {
            Y = - panel.getHeight();
            // Move to the next background image
            currentBackgroundIndex = (currentBackgroundIndex + 1) % backgrounds.length;
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(backgrounds[currentBackgroundIndex], (int)this.X, (int)this.Y, (int)this.WIDTH, (int) this.HEIGHT, null);
    }}
