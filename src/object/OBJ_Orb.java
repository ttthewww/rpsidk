package object;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Orb extends SuperObject  {
    BufferedImage[] imgs;
    public OBJ_Orb() {
        imgs = new BufferedImage[96];
        images = new BufferedImage[96];
        super.name = "Orb";
        try {
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resource/background/orbBuff/" + (i) + ".png")));
                imgs = images;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BufferedImage[] getImages() {
        return imgs;
    }

    @Override
    public CloneableImageObject cloneObject() {
        return (OBJ_Orb) super.cloneObject();
    }
}