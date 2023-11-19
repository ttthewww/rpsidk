package entity;

import main.GamePanel;
import main.WindowContainer;

public class PlayerBullet extends Bullet{
    private final WindowContainer window;
    public PlayerBullet(GamePanel gp, double angle, int bulletType, WindowContainer window){
        super(gp, angle, bulletType);
        this.window = window;
    }

    public void checkWallCollision(){
//        if(this.x < 0){
//            window.setSize(window.getWidth() + 20, window.getHeight());
//            window.setLocation(window.getLocation().x - 20, window.getLocation().y);
//            isActive = false;
//        }
    }
}
