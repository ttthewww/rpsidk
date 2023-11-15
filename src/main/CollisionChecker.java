package main;

import entity.Bullet;
import entity.Enemy;
import entity.Player;

import java.util.ArrayList;

public class CollisionChecker {

    public boolean check(Player player, ArrayList<Enemy> enemies){
        for(Enemy e : enemies){
            if(e.colRect.intersects(player.colRect)){
                return true;
            }
        }
        return false;
    }
    public boolean checkPlayerBulletCollision(ArrayList<Bullet> bullets, ArrayList<Enemy> enemies) {
        for (Bullet bullet : bullets) {
            for (Enemy enemy : enemies) {
                if (bullet.colRect.intersects(enemy.colRect)) {
                    bullet.isActive = false;
                    enemy.isActive = false;
                    return true;
                }
            }
        }
        return false;
    }
}
