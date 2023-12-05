package handlers;

import entity.Player;
import entity.Boss;
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

    public void checkCollisions(CopyOnWriteArrayList<Entity> entities, CopyOnWriteArrayList<Boss> boss, CopyOnWriteArrayList<Bullet> playerBullets) {
        player.bullets.removeIf(bullet -> !bullet.isActive);
        entities.removeIf(entity -> !entity.isActive);


        for(Entity e : entities){
            if(e instanceof Enemy){
                Enemy enemy = (Enemy)e;
                double distanceSquared = (enemy.x - player.x) * (enemy.x - player.x) + (enemy.y - player.y) * (enemy.y - player.y);
                if (distanceSquared > 200 * 200) continue;

                Area intersection = new Area(player.mask);
                intersection.intersect(enemy.mask);

                if (!intersection.isEmpty()) {
                    player.takeDamage();
                    deactivate(enemy);
                }
            }
        }

        // check for playerBullet - enemy collision
        for (Bullet bullet : playerBullets) {
            if (!bullet.isActive) continue;
            for(Entity e : entities){
                if(e instanceof Enemy){
                    Enemy enemy = (Enemy)e;
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

        // for(Bullet bullet: playerBullets){
        //     if(!bullet.isActive)continue;
        //     for (Boss b: boss) {
        //         if(b instanceof Twins){
        //             Area intersection = new Area(bullet.mask);
        //             Area intersection2 = new Area(bullet.mask);
        //             intersection.intersect(((Twins) b).twin1.mask);
        //             intersection2.intersect(((Twins) b).twin2.mask);

        //             if (!intersection.isEmpty() || !intersection2.isEmpty()) {
        //                 ((Twins) b).takeDamage();
        //                 deactivate(bullet);
        //                 break;
        //             }
        //         }
        //         // double distanceSquared = (bullet.x - enemy.x) * (bullet.x - enemy.x) + (bullet.y - enemy.y) * (bullet.y - enemy.y);
        //         // if (distanceSquared > 200 * 200) continue;
        //     }
        // }
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
                        enemy.speed+= 0.5;
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
                        enemy.speed+= 0.5;
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
                        enemy.speed+= 0.5;
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
