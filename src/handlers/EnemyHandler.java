package handlers;

import entity.*;
import handlers.CollisionChecker;
import main.FPS;
import main.GamePanel;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class EnemyHandler implements Runnable {

    int summonCooldown = 0;
    int summonTimer = 200;
    GamePanel gp;
    public boolean running = true;
    FPS fps;
    CollisionChecker collisionChecker;
    Player player;
    double spawnChance =  0.5;

    public CopyOnWriteArrayList<Enemy> enemies;
    public EnemyHandler(GamePanel gp, Player player){
        this.enemies = new CopyOnWriteArrayList<>();
        this.gp = gp;
        this.fps = new FPS();
        this.player = player;
        this.collisionChecker = new CollisionChecker(this.player);
    }

    public void run(){
        while(running){
            if(this.gp.gameState != this.gp.pauseState){
                fps.update();
                fps.currentTime = System.nanoTime();
                fps.delta += (fps.currentTime - fps.lastTime) / fps.drawInterval;
                fps.timer += (fps.currentTime - fps.lastTime);
                fps.lastTime = fps.currentTime;
                if(fps.delta >= 1){
                    update();
                    fps.delta--;
                    fps.drawCount++;
                }
            }else{
                System.out.println("NAKAPAUSE");
            }
        }
    }

    public void summonEnemy(){
        if(Math.random() < spawnChance){
            Random rand = new Random();
            int n = rand.nextInt(3);
            if(n == 0)enemies.add(new RockEnemy(gp));
            if(n == 1)enemies.add(new PaperEnemy(gp));
            if(n == 2)enemies.add(new ScissorEnemy(gp));
        }
    }

    public void update(){
        if(fps.timer>= 1000000000){
            summonEnemy();
            fps.timer = 0;
        }
        for(Enemy e : enemies) {
            e.update();
        }
        collisionChecker.checkCollisions(this.enemies, player.bullets);
    }
    public void draw(Graphics2D g2){
        for(Enemy e : enemies){
            e.draw(g2);
        }
    }
}
