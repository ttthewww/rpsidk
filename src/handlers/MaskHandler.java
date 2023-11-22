package handlers;

import javax.imageio.ImageIO;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MaskHandler {

    public static Area rockBulletMask;
    public static Area paperBulletMask;
    public static Area scissorsBulletMask;
    public static Area rockEnemyMask;
    public static Area paperEnemyMask;
    public static Area scissorsEnemyMask;

    public MaskHandler() throws IOException {
        rockBulletMask = createMaskFromTransparency(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/bullets/rock.png"))),10000,10000);
        paperBulletMask = createMaskFromTransparency(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/bullets/paper.png"))), 10000, 10000);
        scissorsBulletMask = createMaskFromTransparency(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/bullets/scissors.png"))),10000, 10000);
        rockEnemyMask = createMaskFromTransparency(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/rock.png"))),10000, 10000);
        paperEnemyMask = createMaskFromTransparency(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/paper.png"))),10000, 10000);
        scissorsEnemyMask = createMaskFromTransparency(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/scissors.png"))),10000, 10000);
    }

    public static Area createMaskFromTransparency(BufferedImage image, double x, double y) {
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
