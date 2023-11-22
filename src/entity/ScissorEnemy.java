package entity;

import main.GamePanel;

import java.util.concurrent.CopyOnWriteArrayList;

public class ScissorEnemy extends Enemy{
    public ScissorEnemy(GamePanel gp) {
        super(gp,3);
    }
}
