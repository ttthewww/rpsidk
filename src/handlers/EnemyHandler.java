package handlers;

import entity.*;
import main.FPS;
import main.Game;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyHandler{
    Game game;
    double spawnChance =  0.01;
    CollisionChecker collisionChecker;
    Player player;
    public CopyOnWriteArrayList<Enemy> enemies;
    public CopyOnWriteArrayList<FrameEnemy> frameEnemies;
    public CopyOnWriteArrayList<Boss> boss;
    public double shootChance;
    public double chance = 0.05;

    public EnemyHandler(Game game){
        this.game = game;
        this.player = game.player;
        this.collisionChecker = new CollisionChecker(game.player);
        reset();
    }

    public void reset(){
        this.enemies = new CopyOnWriteArrayList<>();
        this.frameEnemies = new CopyOnWriteArrayList<>();
        this.boss = new CopyOnWriteArrayList<>();
    }

    public void summonEnemy(){
        if(frameEnemies.isEmpty()) {
            FrameEnemy frameEnemy = new FrameEnemy(this.game);
            frameEnemies.add(frameEnemy);
            frameEnemies.get(0).startFrameEnemyThread();
            boss.add(new Boss(this.game,frameEnemy));


            FrameEnemy frameEnemy2 = new FrameEnemy(this.game);
            frameEnemies.add(frameEnemy2);
            frameEnemies.get(1).startFrameEnemyThread();
            boss.add(new Boss(this.game,frameEnemy2));
        }

//        if(Math.random() < spawnChance){
//            Random rand = new Random();
//            int n = rand.nextInt(3);
//            if(n == 0)enemies.add(new RockEnemy(game));
//            if(n == 1)enemies.add(new PaperEnemy(game));
//            if(n == 2)enemies.add(new ScissorEnemy(game));
//        }
    }

    public void update(){
        summonEnemy();
        for(Enemy e : enemies) {
            e.update();
        }

        shootChance = Math.random();
        collisionChecker.checkCollisions(this.enemies, player.bullets);
    }
    public void draw(Graphics2D g2){
        for(Enemy e : enemies){
            e.draw(g2);
        }


        for (Boss b : boss) {
            b.draw(g2);
        }

        if(shootChance < chance){
            for(FrameEnemy e: frameEnemies){
                e.update();
                if(shootChance < chance){
                    e.isShooting = true;
                }
            }

            for (Boss b : boss) {
                b.isShooting = true;
            }
        }
    }
}