package entity;

import main.GamePanel;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class PaperEnemy extends Enemy{
    public PaperEnemy(GamePanel gp){
        super(gp,2);
    }
}
