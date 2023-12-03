package object;

import main.Game;
import main.UtilityTool;
import handlers.ImageHandler;

import java.awt.image.BufferedImage;

public class OBJ_Background extends SuperObject implements UtilityTool {
    private double background_speed = 0.3;
    public OBJ_Background(Game game){
        super(game);
        images = new BufferedImage[1];
        images [0] = ImageHandler.background;
    }


    @Override
    public BufferedImage[] getImages() {
        return images;
    }

    public void updateAnimation() {
        this.worldX = -images[0].getWidth() / 2 + this.game.window.getWidth() / 2 + (int) (-this.game.player.absoluteX * background_speed);
        this.worldY = -images[0].getHeight() / 2 + this.game.window.getHeight() / 2 + (int) (-this.game.player.absoluteY * background_speed);
    }

    @Override
    public CloneableImageObject cloneObject() {
        return super.cloneObject();
    }
}