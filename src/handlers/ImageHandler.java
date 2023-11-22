package handlers;

import main.Background;

import javax.imageio.ImageIO;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageHandler {
    public BufferedImage playerImage;
    public BufferedImage[] playerFrames;
    public static BufferedImage enemyScissorImage;
    public static BufferedImage enemyRockImage;
    public static BufferedImage enemyPaperImage;
    public static BufferedImage rockBulletImage;
    public static BufferedImage scissorBulletImage;
    public static BufferedImage paperBulletImage;
    public static BufferedImage enemyRockAura;
    public static BufferedImage enemyPaperAura;
    public static BufferedImage enemyScissorsAura;
    public static BufferedImage mainMenuBackgroundImage;
    public ImageHandler(){
        try{
            this.paperBulletImage =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/paper.png"));
            this.scissorBulletImage =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/scissors.png"));
            this.rockBulletImage =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/rock.png"));

            this.enemyRockImage =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/rock.png"));
            this.enemyPaperImage =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/paper.png"));
            this.enemyScissorImage =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/scissors.png"));

            this.enemyRockAura = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/rockaura.png")));
            this.enemyPaperAura = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/paperaura.png")));
            this.enemyScissorsAura = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/scissorsaura.png")));

            this.mainMenuBackgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/background/bac1.png")));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
