package entity;

import main.GamePanel;

import java.util.concurrent.CopyOnWriteArrayList;

public class RockEnemy extends Enemy{
    public RockEnemy(GamePanel gp){
        super(gp,1);
    }
}
