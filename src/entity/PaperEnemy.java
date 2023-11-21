package entity;

import main.GamePanel;
import java.util.ArrayList;

public class PaperEnemy extends Enemy{
    public PaperEnemy(GamePanel gp, ArrayList<Enemy> enemies) {
        super(gp, enemies, 2);
    }
}
