package entity;

import main.GamePanel;
import main.MaskCreationThread;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public abstract class Entity {
    public double x, y;
    public int speed;

    public Rectangle colRect;
    public boolean isActive = true;

    public BufferedImage playerImage;
    public BufferedImage enemyScissorImage;
    public BufferedImage enemyRockImage;
    public BufferedImage enemyPaperImage;

    public BufferedImage image;
    public BufferedImage bulletImage;

    public BufferedImage[] playerFrames;

    public BufferedImage rockBulletImage;
    public BufferedImage scissorBulletImage;
    public BufferedImage paperBulletImage;


    public BufferedImage enemyRockAura;
    public BufferedImage enemyPaperAura;
    public BufferedImage enemyScissorsAura;
    public Area mask;

    public Entity(){
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

        }catch(IOException e){
            e.printStackTrace();
        }
    }

//    public Point getAbsolutePosition(GamePanel gp) {
//        int absoluteX = gp.windowX + this.x;
//        int absoluteY = gp.windowY + this.y;
//        return new Point(absoluteX, absoluteY);
//    }
}
