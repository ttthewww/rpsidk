package entity;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class EntityMask {
    public Area mask;

    public double x;
    public double y;

    public EntityMask(BufferedImage image, double x, double y){
        this.x = x;
        this.y = y;
        this.mask = createMaskFromTransparency(image, x, y);
    }

    public Area createMaskFromTransparency(BufferedImage image, double x, double y) {
        Area mask = new Area();
        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixel = new int[4]; // ARGB
        Raster raster = image.getRaster();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                raster.getPixel(i, j, pixel);

                // Check alpha channel (transparency)
                int alpha = pixel[3];
                if (alpha != 0) {
                    Rectangle2D.Double pixelRect = new Rectangle2D.Double(x + i, y + j, 1, 1);
                    mask.add(new Area(pixelRect));
                }
            }
        }
        return mask;
    }

    public void update(){

    }
    public void draw(){

    }

}
