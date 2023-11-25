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
    public EnemyHandler(Game game){
        this.game = game;
        this.player = game.player;
        this.collisionChecker = new CollisionChecker(game.player);
        reset();
    }

    public void reset(){
        this.enemies = new CopyOnWriteArrayList<>();
    }

    public void summonEnemy(){
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
