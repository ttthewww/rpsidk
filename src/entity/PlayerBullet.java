package entity;

import main.Game;

public class PlayerBullet extends Bullet{
    public PlayerBullet(Game game, double angle, int bulletType){
        super(game, angle);
        this.bulletType = bulletType;
    }
}
