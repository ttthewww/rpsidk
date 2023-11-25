package entity;

import main.Game;

public class PlayerBullet extends Bullet{
    public PlayerBullet(Game gp, double angle, Player bulletType){
        super(gp, angle, bulletType);
    }

    public void checkWallCollision(){
//        if(this.x < 0){
//            window.setSize(window.getWidth() + 20, window.getHeight());
//            window.setLocation(window.getLocation().x - 20, window.getLocation().y);
//            isActive = false;
//        }
    }
}
