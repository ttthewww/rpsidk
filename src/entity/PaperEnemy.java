package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class PaperEnemy extends Enemy{
    public PaperEnemy(GamePanel gp, ArrayList<Enemy> enemies) {
        super(gp, enemies);
        this.enemyType = 2;
        getEnemyImage();
        colRect = new Rectangle(this.x, this.y, this.image.getWidth(), this.image.getHeight());
    }

    public void getEnemyImage(){
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("../resource/enemies/paper.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
