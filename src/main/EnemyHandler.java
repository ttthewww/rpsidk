package main;

import entity.*;

import java.awt.*;
import java.util.ArrayList;

public class EnemyHandler{

    ArrayList<Enemy> enemies;

    public EnemyHandler(){
        this.enemies = new ArrayList<>();
    }

    public void summonEnemy(GamePanel gp){
        enemies.add(new RockEnemy(gp, enemies));
        enemies.add(new PaperEnemy(gp, enemies));
        enemies.add(new ScissorEnemy(gp, enemies));
    }

    public void update(){
        for(Enemy e : enemies) {
            e.update();
        }
    }

    public void draw(Graphics2D g2){
        for(Enemy e : enemies){
            e.draw(g2);
        }
    }
}
