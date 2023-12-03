package object;

import entity.Entity;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SuperObject implements CloneableImageObject{
    private static int FRAME_DELAY_MILLIS = 100; // Delay between frames in milliseconds
    private static final int DEFAULT_SOLID_AREA_WIDTH = 48;
    private static final int DEFAULT_SOLID_AREA_HEIGHT = 48;
    public BufferedImage[] images; // Modified to use an array of images
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,DEFAULT_SOLID_AREA_WIDTH,DEFAULT_SOLID_AREA_HEIGHT);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private final Object imagesLock = new Object();
    public void draw(Graphics2D g2) {
        try {
            draw(g2, null);
        } catch (Exception e) {
            // Handle the exception
            e.printStackTrace(); // or log the exception
        }
    }
    public void draw(Graphics2D g2, Game gp) {
        try {
            if (gp == null) {
            } else {
//                double screenX = worldX - gp.player.x + gp.player.xLocationOnScreen;
//                double screenY = worldY - gp.player.y + gp.player.yLocationOnScreen;
                // Use synchronized block to ensure atomic access to the images array
                synchronized (imagesLock) {
                    g2.drawImage(getImages()[currentFrame], worldX, worldY, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateAnimation() {
        long currentTime = System.currentTimeMillis();
        if(images.length == 4) /** NOOB MANIPULATION, TO BE REVISED **/
            FRAME_DELAY_MILLIS = 2000;
        else  FRAME_DELAY_MILLIS = 100;
        if (currentTime - lastFrameTime > FRAME_DELAY_MILLIS) {
            synchronized (imagesLock) {
                currentFrame = (currentFrame + 1) % images.length;
                lastFrameTime = currentTime;
            }
        }
    }

    public abstract BufferedImage[] getImages();

    /** DEEP CLONE COPY **/
    @Override
    public CloneableImageObject cloneObject() {
        try {
            SuperObject clonedObject = (SuperObject) super.clone();
            clonedObject.images = new BufferedImage[images.length];

            for (int i = 0; i < images.length; i++) {
                BufferedImage originalImage = images[i];
                int width = originalImage.getWidth();
                int height = originalImage.getHeight();

                clonedObject.images[i] = new BufferedImage(width, height, originalImage.getType());
                clonedObject.images[i].getGraphics().drawImage(originalImage.getSubimage(0, 0, width, height), 0, 0, null);
            }

            return clonedObject;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

}
