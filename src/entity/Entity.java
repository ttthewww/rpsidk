package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Entity {
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

    public Entity(){
        try{
            this.paperBulletImage =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/paper.png"));
            this.scissorBulletImage =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/scissor.png"));
            this.rockBulletImage =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/rock.png"));
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
