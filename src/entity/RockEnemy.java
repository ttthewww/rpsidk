package entity;

import main.GamePanel;
import java.awt.*;
import java.util.ArrayList;

public class RockEnemy extends Enemy{
    public RockEnemy(GamePanel gp, ArrayList<Enemy> enemies) {
        super(gp, enemies);
        this.enemyType = 1;
        getEnemyImage();
        colRect = new Rectangle(this.x, this.y, this.image.getWidth(), this.image.getHeight());
    }
}
