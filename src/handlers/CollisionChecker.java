package handlers;

import entity.Bullet;
import entity.Enemy;
import entity.Entity;
import entity.Player;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.geom.Area;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollisionChecker {
    Player player;
    public CollisionChecker(Player player){
        this.player = player;
    }
    public void deactivate(Entity e){
        e.isActive = false;
        if(e instanceof Enemy){
            this.player.score++;
        }
    }
    public void checkCollisions(CopyOnWriteArrayList<Enemy> enemies, CopyOnWriteArrayList<Bullet> playerbullets){
        this.player.bullets.removeIf(bullet ->! bullet.isActive);
        enemies.removeIf(enemy -> !enemy.isActive);

        for (Enemy e : enemies) {
            for (Enemy otherEnemy : enemies) {
                if (e != otherEnemy) {
                    Area intersection = new Area(e.mask);
                    intersection.intersect(otherEnemy.mask);
                    if (!(intersection.isEmpty())) {
                        double dx = e.x - otherEnemy.x;
                        double dy = e.y - otherEnemy.y;
                        double angle = Math.atan2(dy, dx);
                        e.x += e.speed * Math.cos(angle);
                        e.y += e.speed * Math.sin(angle);
                    }
                }
            }
        }

        //check for player-enemyCollision
        for (Enemy e : enemies) {
            Area intersection = new Area(this.player.mask);
            intersection.intersect(e.mask);
            if (!(intersection.isEmpty()) && e.attackCooldown >= e.attackTimer) {
                player.health--;
                e.attackCooldown = 0;
            }
        }

        //check for player bullet collision
        for (Bullet bullet : playerbullets) {
            for (Enemy enemy : enemies) {
                Area intersection = null;
                intersection = new Area(bullet.mask);
                intersection.intersect(enemy.mask);

                if(!intersection.isEmpty()){
                    switch (bullet.bulletType){
                        case 1:
                            if(enemy.enemyType == 3){
                                try {
                                    SoundHandler.playSound("../resource/sounds/scissor.wav");
                                } catch (UnsupportedAudioFileException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (LineUnavailableException e) {
                                    throw new RuntimeException(e);
                                }
                                SoundHandler.setVolume(0.7f);
                                deactivate(enemy);
                                deactivate(bullet);
                                return;
                            }
                            if(enemy.enemyType == 2){
                                try {
                                    SoundHandler.playSound("../resource/sounds/paper.wav");
                                } catch (UnsupportedAudioFileException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (LineUnavailableException e) {
                                    throw new RuntimeException(e);
                                }
                                SoundHandler.setVolume(0.8f);
                                deactivate(bullet);
                                return;
                            }
                            break;
                        case 2:
                            if(enemy.enemyType == 1){
                                try {
                                    SoundHandler.playSound("../resource/sounds/rock.wav");
                                } catch (UnsupportedAudioFileException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (LineUnavailableException e) {
                                    throw new RuntimeException(e);
                                }
                                SoundHandler.setVolume(0.7f);
                                deactivate(enemy);
                                deactivate(bullet);
                                return;
                            }
                            if(enemy.enemyType == 3){
                                try {
                                    SoundHandler.playSound("../resource/sounds/scissor.wav");
                                } catch (UnsupportedAudioFileException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (LineUnavailableException e) {
                                    throw new RuntimeException(e);
                                }
                                SoundHandler.setVolume(0.7f);
                                deactivate(bullet);
                                return;
                            }
                            break;
                        case 3:
                            if(enemy.enemyType == 2){
                                try {
                                    SoundHandler.playSound("../resource/sounds/paper.wav");
                                } catch (UnsupportedAudioFileException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (LineUnavailableException e) {
                                    throw new RuntimeException(e);
                                }
                                SoundHandler.setVolume(0.8f);
                                deactivate(enemy);
                                deactivate(bullet);
                                return;
                            }
                            if(enemy.enemyType == 1){
                                try {
                                    SoundHandler.playSound("../resource/sounds/rock.wav");
                                } catch (UnsupportedAudioFileException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                } catch (LineUnavailableException e) {
                                    throw new RuntimeException(e);
                                }
                                SoundHandler.setVolume(0.7f);
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
