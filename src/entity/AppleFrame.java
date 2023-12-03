package entity;

import main.Game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

import handlers.ImageHandler;

public class AppleFrame extends EnemyFrame{
    BufferedImage image;

    public AppleFrame (Game game, int frameWidth, int frameHeight) {
        super(game, frameWidth, frameHeight);
        this.image = ImageHandler.apple;
    }

    @Override
    public void getImage() {

    }

    @Override
    public void setWindowDefaults() {
        super.setWindowDefaults();
        this.window.setVisible(false);
        ImageIcon img = new ImageIcon("src/resource/snek.png");
        window.setIconImage(img.getImage());
    }

    @Override
    public void getNewDestination() {
        // TODO Auto-generated method stub
    }

    @Override
    public void rotate(BufferedImage image, AffineTransform at) {
        // TODO Auto-generated method stub
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        this.enemyX = this.getWidth() / 2;
        this.enemyY = this.getHeight() / 2;
        if(this.image != null){
            AffineTransform at = AffineTransform.getTranslateInstance(this.enemyX - this.image.getWidth() / 2, this.enemyY - this.image.getHeight() / 2);
            g2.drawImage(image, at, null);
        }
    }
}
