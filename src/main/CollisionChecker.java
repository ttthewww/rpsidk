package main;

import entity.Bullet;
import entity.Enemy;
import entity.Entity;
import entity.Player;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.geom.Area;
import java.io.IOException;
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
//                System.out.println(bullet.mask);
//                Area intersection = null;
//                if(bullet.mask != null){
//                    intersection = new Area(bullet.mask);
//                }
//                System.out.println(intersection);
//                System.out.println(intersection);
//                intersection.intersect(enemy.mask);
//                if(!intersection.isEmpty()){
//                    System.out.println("INTERSECTING");
//                }



                if(bullet.colRect.intersects(enemy.colRect)){
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
