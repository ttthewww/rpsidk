package object;

import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class OBJ_Map extends SuperObject implements UtilityTool {
    BufferedImage[] imgs;
    public OBJ_Map(){
        imgs = new BufferedImage[4];
        images = new BufferedImage[4];
        try{
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resource/background/bac" + ( i + 1 ) + ".png")));
                imgs[i] = scaleImage(images[i], 1920, 1080);
            }
            imgs = Arrays.copyOf(images, images.length);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public BufferedImage[] getImages() {
        return imgs;
    }

    @Override
    public CloneableImageObject cloneObject() {
        return super.cloneObject();
    }
}