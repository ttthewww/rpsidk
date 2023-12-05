package main;

import entity.EntityWindow;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import handlers.EntityHandler;
import handlers.ImageHandler;

public class TransparentWindow extends EntityWindow implements UtilityTool{
    private BufferedImage[] images;
    private BufferedImage image;
    Graphics2D g2;
    EntityHandler entityHandler;
    public TransparentWindow(Game game, int windowWidth, int windowHeight, EntityHandler entityHandler) {
        super(game, windowWidth, windowHeight, 1);
        this.entityHandler = entityHandler;
        this.window.setAlwaysOnTop(true);
    }

    @Override
    public void getImage() {
        // this.image = ImageHandler.spaceRock;
        int scaleFactor = 5;
        images = new BufferedImage[35];
        for (int i = 0; i < images.length; i++) {
            images[i] = ImageHandler.gems[i];
            images[i] = scaleImage(images[i], this.images[i].getWidth() / scaleFactor, this.images[i].getHeight() / scaleFactor);
            // images[i] = applyOpacity(images[i], 0.5f);
        }
    }

    @Override
    public void update() {

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g2 = (Graphics2D)g;
        if (this.images != null) {
            int frameIndex = (int) ((System.currentTimeMillis() / 100) % this.images.length);
            image = this.images[frameIndex];
            AffineTransform at = AffineTransform.getTranslateInstance(this.window.getWidth() / 2 - image.getWidth() / 2.0, this.window.getHeight() / 2 - image.getHeight() / 2.0);

            g2.drawImage(image, at, null);
        }

        if(this.entityHandler != null){
            entityHandler.draw(g2);
        }

        g2.dispose();
    }
}