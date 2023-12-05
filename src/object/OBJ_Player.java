package object;

import main.Game;
import main.UtilityTool;

import java.awt.image.BufferedImage;
import handlers.ImageHandler;
import handlers.KeyHandler;


public class OBJ_Player extends SuperObject implements UtilityTool {
    private int health;
    public int score;
    public int fireCooldown = 0;
    public int reloadTime = 30;
    public int bulletType = 1;

    private int invunerableTime = 0;
    private int maxInvulnerableTime = 100;

    public double absoluteX;
    public double absoluteY;
    public double xLocationOnScreen;
    public double yLocationOnScreen;
    public double acceleration = 0.1;
    public double speed_x_right = 0;
    public double speed_x_left = 0;
    public double speed_y_up = 0;
    public double speed_y_down = 0;
    public double top_speed = 3;
    double deceleration = 0.1;
    public OBJ_Player (Game game) {
        super(game);
        images = new BufferedImage[5];

        super.name = "Player";
        for (int i = 0; i < images.length; i++) {
            images[i] = ImageHandler.playerFrames[i];
        }
    }

    public void move(){
        if (KeyHandler.rightPressed) {
            this.speed_x_right += this.acceleration;
            if (this.speed_x_right > this.top_speed) {
                this.speed_x_right = this.top_speed;
            }
        } else {
            this.speed_x_right -= this.deceleration;
        }

        if (KeyHandler.leftPressed) {
            this.speed_x_left -= this.acceleration;
            if (this.speed_x_left < -this.top_speed) {
                this.speed_x_left = -this.top_speed;
            }
        } else {
            this.speed_x_left += this.deceleration;
        }

        if (KeyHandler.downPressed) {
            this.speed_y_down += this.acceleration;
            if (this.speed_y_down > this.top_speed) {
                this.speed_y_down = this.top_speed;
            }
        } else {
            this.speed_y_down -= this.deceleration;
        }

        if (KeyHandler.upPressed) {
            this.speed_y_up -= this.acceleration;
            if (this.speed_y_up < -this.top_speed) {
                this.speed_y_up = -this.top_speed;
            }
        } else {
            this.speed_y_up += this.deceleration;
        }


        if (this.speed_x_right < 0) {
            this.speed_x_right = 0;
        }
        if (this.speed_x_left > 0) {
            this.speed_x_left = 0;
        }
        if (this.speed_y_down < 0) {
            this.speed_y_down = 0;
        }
        if (this.speed_y_up > 0) {
            this.speed_y_up = 0;
        }

        if(!KeyHandler.shiftPressed){
            this.x += (int) this.speed_x_right;
            this.x += (int) this.speed_x_left;
            this.y += (int) this.speed_y_up;
            this.y += (int) this.speed_y_down;
        }

        this.absoluteX += this.speed_x_right;
        this.absoluteX += this.speed_x_left;
        this.absoluteY += this.speed_y_up;
        this.absoluteY += this.speed_y_down;


        if (this.x - 15 <= 0) {
            this.x = 15;
        }

        if (this.x + 15 >= game.getWidth()) {
            this.x = game.getWidth() - 15;
        }

        if (this.y - 15 <= 0) {
            this.y = 15;
        }

        if (this.y + 15 >= game.getHeight()) {
            this.y = game.getHeight() - 15;
        }

        this.xLocationOnScreen = this.game.getLocationOnScreen().x + this.x;
        this.yLocationOnScreen = this.game.getLocationOnScreen().y + this.y;

        if(KeyHandler.shiftPressed){
           this.game.windowPosX += (int) this.speed_x_right;
           this.game.windowPosX += (int) this.speed_x_left;
           this.game.windowPosY += (int) this.speed_y_up;
           this.game.windowPosY += (int) this.speed_y_down;

           this.game.window.setLocation((int) this.game.windowPosX, (int) this.game.windowPosY);
        }
    }



    public void updateAnimation() {
        super.updateAnimation();
        move();

    }

    @Override
    public BufferedImage[] getImages() {
        return images;
    }

    @Override
    public CloneableImageObject cloneObject() {
        return super.cloneObject();
    }
}