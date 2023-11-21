package entity;

import main.GamePanel;
import java.util.ArrayList;

public class RockEnemy extends Enemy{
    public RockEnemy(GamePanel gp, ArrayList<Enemy> enemies) {
        super(gp, enemies, 1);
    }
}
