package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ScissorEnemy extends Enemy{
    public ScissorEnemy(GamePanel gp, ArrayList<Enemy> enemies) {
        super(gp, enemies, 3);
//        getEnemyImage();
    }
}
