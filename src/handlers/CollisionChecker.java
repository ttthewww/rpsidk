package handlers;

import entity.Player;
import entity.Bullet;
import entity.Enemy;
import entity.Entity;

import java.awt.geom.Area;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollisionChecker implements Sound{
    Player player;
    public CollisionChecker(Player player){
        this.player = player;
    }
    public void checkCollisions(CopyOnWriteArrayList<Enemy> enemies, CopyOnWriteArrayList<Bullet> playerBullets) {
        player.bullets.removeIf(bullet -> !bullet.isActive);
        enemies.removeIf(enemy -> !enemy.isActive);

        //check for player-enemy collision
        for (Enemy enemy : enemies) {
            double distanceSquared = (enemy.x - player.x) * (enemy.x - player.x) + (enemy.y - player.y) * (enemy.y - player.y);
            if (distanceSquared > 200 * 200) continue;
            Area intersection = new Area(player.mask);
            intersection.intersect(enemy.mask);
            if (!intersection.isEmpty() && enemy.attackCooldown >= enemy.attackTimer) {
                player.health--;
                enemy.attackCooldown = 0;
            }
        }

        // check for player bullet collision
        for (Bullet bullet : playerBullets) {
            if (!bullet.isActive) continue;
            for (Enemy enemy : enemies) {
                if (!enemy.isActive) continue;
                double distanceSquared = (bullet.x - enemy.x) * (bullet.x - enemy.x) + (bullet.y - enemy.y) * (bullet.y - enemy.y);
                if (distanceSquared > 200 * 200) continue;
                Area intersection = new Area(bullet.mask);
                intersection.intersect(enemy.mask);
                if (!intersection.isEmpty()) {
                    handleCollision(enemy, bullet);
                    break;
                }
            }
        }
    }

    private void handleCollision(Enemy enemy, Bullet bullet) {
        int bulletType = bullet.bulletType;
        int enemyType = enemy.enemyType;
        try {
            switch (bulletType){
                case 1:
                    if(enemyType == 3){
                        playSE(3);
                        deactivate(enemy);
                        deactivate(bullet);
                        return;
                    }
                    if(enemyType == 2){
                        playSE(2);
                        deactivate(bullet);
                        return;
                    }
                    break;
                case 2:
                    if(enemyType == 1){
                        playSE(1);
                        deactivate(enemy);
                        deactivate(bullet);
                        return;
                    }
                    if(enemyType == 3){
                        playSE(3);
                        deactivate(bullet);
                        return;
                    }
                    break;
                case 3:
                    if(enemyType == 2){
                        playSE(2);
                        deactivate(enemy);
                        deactivate(bullet);
                        return;
                    }
                    if(enemyType == 1){
                        playSE(1);
                        deactivate(bullet);
                        return;
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deactivate(Entity e) {
        e.isActive = false;
        if (e instanceof Enemy) {
            player.score++;
        }
    }
}
