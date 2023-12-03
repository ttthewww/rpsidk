package object;

import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class OBJ_Orb extends SuperObject implements UtilityTool {
    private BufferedImage[] imgs;

    public OBJ_Orb() {
        imgs = new BufferedImage[96];
        images = new BufferedImage[96];
        super.name = "Orb";
        try {
            for (int i = 0; i < images.length; i++) {
                images[i] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/resource/background/orbBuff/" + (i + 1) + ".png")));
                imgs[i] = applyOpacity(images[i], 0.1f); // Adjust the opacity value as needed
                imgs[i] = scaleImage(imgs[i], 576,576);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage applyOpacity(BufferedImage image, float opacity) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();

        // Apply opacity to each pixel
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(image.getRGB(x, y), true);
                color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (opacity * color.getAlpha()));
                result.setRGB(x, y, color.getRGB());
            }
        }

        g.dispose();
        return result;
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
