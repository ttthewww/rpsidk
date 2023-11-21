package main;

import entity.Enemy;
import entity.Entity;

import javax.swing.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class MaskCreationThread implements Runnable{
    private BufferedImage image;
    private double x;
    private double y;
    private volatile Area mask;

    HashMap<Entity, Area> masks;

//    public MaskCreationThread(BufferedImage image, double x, double y) {
//        this.image = image;
//        this.x = x;
//        this.y = y;
//        this.masks = new HashMap<Entity, Area>();
//    }

    public MaskCreationThread() {
        this.masks = new HashMap<Entity, Area>();
    }

    @Override
    public void run() {
        try{
            System.out.println("HELLO");
        }catch (Exception e){

        }
    }

    public Area addMask(Entity entity){
        Area mask = createMaskFromTransparency(entity.image, entity.x - entity.image.getWidth() / 2, entity.y - entity.image.getHeight() / 2);
        masks.put(entity, mask);
        return mask;
    }

    public Area getMask(Entity entity) {
        try {
            for (Map.Entry<Entity, Area> entry : masks.entrySet()) {
                if(entry.getKey() == entity){
                    return entry.getValue();
                }
            }
            return this.mask;
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