package handlers;

import entity.*;
import main.Game;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyHandler{
    Game game;
    double spawnChance =  0.005;
    CollisionChecker collisionChecker;
    Player player;
    public CopyOnWriteArrayList<Enemy> enemies;
    public CopyOnWriteArrayList<Boss> boss;

    public EnemyHandler(Game game){
        this.game = game;
        this.player = game.player;
        this.collisionChecker = new CollisionChecker(game.player);
        this.enemies = new CopyOnWriteArrayList<>();
        this.boss = new CopyOnWriteArrayList<>();
    }

    public void reset(){
        this.enemies = new CopyOnWriteArrayList<>();

        if (!boss.isEmpty()) {
            for (Boss b : boss) {
                b.dispose();
            }
        }
        this.boss = new CopyOnWriteArrayList<>();
    }

    public void summonEnemy(){
        if (boss.isEmpty()) {
            boss.add(new Twins(game));
            boss.add(new Snake(game));
        }

        if(Math.random() < spawnChance){
            Random rand = new Random();
            int n = rand.nextInt(3);
            if(n == 0)enemies.add(new RockEnemy(game));
            if(n == 1)enemies.add(new PaperEnemy(game));
            if(n == 2)enemies.add(new ScissorEnemy(game));
        }
    }

    public void update(){
        summonEnemy();
        for (Enemy e : enemies) {
            e.update();
        }

        for (Boss b : boss) {
            if(b instanceof Twins){
                if (!((Twins) b).isActive) {
                    boss.remove(b);
                    //todo fix lag upon removing 
                    continue;
                }
            }
            b.update();
        }
        collisionChecker.checkCollisions(this.enemies, this.boss, player.bullets);
    }

    public void draw(Graphics2D g2){
        for (Enemy e : enemies) {
            e.draw(g2);
        }

        for (Boss b : boss) {
            b.draw(g2);
        }
    }
}