package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ScissorEnemy extends Enemy{
    public ScissorEnemy(GamePanel gp, ArrayList<Enemy> enemies) {
        super(gp, enemies);
        this.enemyType = 3;
        getEnemyImage();
        colRect = new Rectangle(this.x, this.y, this.image.getWidth(), this.image.getHeight());
    }

    public void getEnemyImage(){
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("../resource/enemies/scissors.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
