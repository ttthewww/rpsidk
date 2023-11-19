package main;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class MaskCreationThread extends Thread{
    private BufferedImage image;
    private double x;
    private double y;
    private volatile Area mask;

    public MaskCreationThread(BufferedImage image, double x, double y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        try {
            mask = createMaskFromTransparency(image, x - image.getWidth() / 2.0, y - image.getHeight() / 2.0);
        } catch (Exception e) {
            e.printStackTrace(); // Or log the exception
        }

    }

    public Area getMask() {
        try {
            return mask;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Area createMaskFromTransparency(BufferedImage image, double x, double y) {
        Area mask = new Area();

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int alpha = (image.getRGB(i, j) >> 24) & 0xFF;
                if (alpha != 0) {
                    Rectangle2D.Double pixelRect = new Rectangle2D.Double(x + i, y + j, 1, 1);
                    mask.add(new Area(pixelRect));
                }
            }
        }
        return mask;
    }
}

