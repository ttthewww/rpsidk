package object;

import main.Game;
import main.UtilityTool;

import java.awt.image.BufferedImage;
import handlers.ImageHandler;


public class OBJ_Orb extends SuperObject implements UtilityTool {
    public OBJ_Orb(Game game) {
        super(game);
        images = new BufferedImage[96];
        super.name = "Orb";
        for (int i = 0; i < images.length; i++) {
            images[i] = ImageHandler.orbImages[i];
            images[i] = applyOpacity(images[i], 0.1f);
            images[i] = scaleImage(images[i], 576,576);
        }
    }

    @Override
    public BufferedImage[] getImages() {
        return images;
    }

    @Override
    public CloneableImageObject cloneObject() {
        return super.cloneObject();
    }
}
