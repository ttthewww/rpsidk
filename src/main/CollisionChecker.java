package main;

import entity.Bullet;
import entity.Enemy;
import entity.Entity;
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

    public void deactivate(Entity e){
        e.isActive = false;
    }
    public void checkPlayerBulletCollision(ArrayList<Bullet> bullets, ArrayList<Enemy> enemies) {
        for (Bullet bullet : bullets) {
            for (Enemy enemy : enemies) {
                if(bullet.colRect.intersects(enemy.colRect)){
                    switch (bullet.bulletType){
                        case 1:
                            if(enemy.enemyType == 3){
                                deactivate(enemy);
                                deactivate(bullet);
                                return;
                            }
                            if(enemy.enemyType == 2){
                                deactivate(bullet);
                                return;
                            }
                            break;
                        case 2:
                            if(enemy.enemyType == 1){
                                deactivate(enemy);
                                deactivate(bullet);
                                return;
                            }
                            if(enemy.enemyType == 3){
                                deactivate(bullet);
                                return;
                            }
                            break;
                        case 3:
                            if(enemy.enemyType == 2){
                                deactivate(enemy);
                                deactivate(bullet);
                                return;
                            }
                            if(enemy.enemyType == 1){
                                deactivate(bullet);
                                return;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
