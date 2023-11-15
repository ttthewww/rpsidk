package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    int x;
    int y;

    public ArrayList<Bullet> bullets = new ArrayList<>();
    private int fireCooldown = 0;
    public int reloadTime = 30;
    private double velocity = 0.0001;
    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        getPlayerImage();
        this.colRect = new Rectangle(this.x + playerImage.getWidth(), this.y - playerImage.getHeight() / 2, playerImage.getWidth(), playerImage.getHeight());
        setDefaultValues();
    }

    public void setDefaultValues(){
        this.x = 500;
        this.y = 500;
        speed = 4;
    }

    public void getPlayerImage(){
        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream("/player/scissor.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void shoot(int targetX, int targetY){
        targetX -= gp.getLocationOnScreen().x;
        targetY -= gp.getLocationOnScreen().y;
//        System.out.println(targetX + " " + targetY);
//        System.out.println(gp.mouseX + " " + gp.mouseY);
        bullets.add(new Bullet(this, targetX, targetY, gp));
        fireCooldown = 3;
    }
    public Point getAbsolutePosition() {
        int absoluteX = gp.windowX + this.x;
        int absoluteY = gp.windowY + this.y;
        return new Point(absoluteX, absoluteY);
    }
    public void update(JFrame gameWindow, GamePanel gp){
        if(keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed){
            velocity++;
            if(velocity >= speed){
                velocity = speed;
            }
        }else {
            velocity--;
            if(velocity <= 0){
                velocity = 0;
            }
        }

        if(keyH.upPressed && !keyH.downPressed){
            if(keyH.shiftPressed){
                gp.windowY -= (int) (velocity + gp.windowSpeed);
                if (y < 0) y = 0;
                if (gp.windowY < 0) gp.windowY = 0;
                gameWindow.setLocation(gp.windowX, gp.windowY);
            }else{
                this.y -= (int) this.velocity;
                if(this.y < 0)this.y = gp.getHeight();
            }
        }

        if(keyH.downPressed && !keyH.upPressed){
            if(keyH.shiftPressed){
                gp.windowY += (int) (velocity + gp.windowSpeed);

                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                if (gp.windowY + gameWindow.getHeight() > screenSize.height) {
                    gp.windowY = screenSize.height - gameWindow.getHeight();
                }

                gameWindow.setLocation(gp.windowX, gp.windowY);
            }else{
                this.y += (int) this.velocity;
                if(this.y > gp.getHeight())this.y = 0;
            }
        }

        if(keyH.rightPressed && !keyH.leftPressed){
            if(keyH.shiftPressed){
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                gp.windowX += (int) (velocity + gp.windowSpeed);

                if (gp.windowX + gameWindow.getHeight() > screenSize.width) {
                    gp.windowX = screenSize.width - gameWindow.getHeight();
                }
                gameWindow.setLocation(gp.windowX, gp.windowY);
            }else{
                this.x += (int) this.velocity;
                if(this.x > gp.getWidth())this.x = 0;
            }

        }

        if(keyH.leftPressed && !keyH.rightPressed){
            if(keyH.shiftPressed){
                gp.windowX -= (int) (velocity + gp.windowSpeed);
                if(gp.windowX < 0)gp.windowX = 0;
                gameWindow.setLocation(gp.windowX, gp.windowY);
            }else{
                this.x -= (int) this.velocity;
                if(this.x < 0)this.x = gp.getWidth();
            }
        }

        fireCooldown++;
        if(fireCooldown >= reloadTime)fireCooldown = reloadTime;

        if(keyH.spacePressed){
            if(fireCooldown >= reloadTime){
                shoot(gp.absoluteMouseX , gp.absoluteMouseY);
            }
        }

        Iterator<Bullet> iterator = bullets.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.update();
            if (!bullet.isActive()) {
                iterator.remove();
            }
        }

        this.colRect.x = this.x - playerImage.getWidth() / 2;
        this.colRect.y = this.y - playerImage.getHeight() / 2;
    }

    public void draw(Graphics2D g2, GamePanel gameWindow) {
        BufferedImage image = playerImage;

        double directionX = gameWindow.absoluteMouseX - (gp.windowX + this.x + (double) playerImage.getWidth() / 2);
        double directionY = gameWindow.absoluteMouseY - (gp.windowY + this.y + (double) playerImage.getHeight() / 2);

        double rotationAngleInRadians = Math.atan2(directionY, directionX);

        AffineTransform at = AffineTransform.getTranslateInstance(this.x - playerImage.getWidth() / 2.0, this.y - playerImage.getHeight() / 2.0);
        at.rotate(rotationAngleInRadians, playerImage.getWidth() / 2.0, playerImage.getHeight() / 2.0);

        g2.drawImage(image, at, null);
        for(Bullet bullet : bullets){
            bullet.draw(g2, gameWindow);
        }

//        g2.setColor(Color.RED);
//        g2.draw(this.colRect);
    }
}
