package entity;

import main.GamePanel;
import main.MaskCreationThread;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.concurrent.*;


public abstract class Bullet extends Entity{
    double angle;
    double dx;
    double dy;
    int targetX;
    int targetY;
    public int bulletType;
    GamePanel gp;
    double directionX;
    double directionY;

    MaskCreationThread maskThread;

    double rotationAngleInRadians;
    public EntityMask entityMask;

    private static ExecutorService threadPool = Executors.newSingleThreadExecutor(); // Using a single thread executor for this example

    public Bullet(GamePanel gp, double angle, int bulletType){
        this.bulletType = bulletType;
        getBulletImage();
        this.gp = gp;
        this.speed = 8;
        this.x = Player.x;
        this.y = Player.y;

        directionX = this.gp.absoluteMouseX - ((gp.getLocationOnScreen().x + this.x));
        directionY = this.gp.absoluteMouseY - ((gp.getLocationOnScreen().y + this.y));
        this.angle = Math.atan2(directionY, directionX);
        this.rotationAngleInRadians = Math.atan2(directionY, directionX);
        this.dx = Math.cos(angle) * this.speed;
        this.dy = Math.sin(angle) * this.speed;

        this.isActive = true;
//        this.colRect = mask.getBounds();
        colRect = new Rectangle((int) this.x, (int) this.y, this.image.getWidth(), this.image.getHeight());
    }

    public void getBulletImage(){
        try{
            if(this.bulletType == 1){
                this.image = rockBulletImage;
                this.mask = new Area(MaskHandler.rockBulletMask);
            }else if(this.bulletType == 2){
                this.image = paperBulletImage;
                this.mask = new Area(MaskHandler.paperBulletMask);
            }else{
                this.image = scissorBulletImage;
                this.mask = new Area(MaskHandler.scissorsBulletMask);
            }
            this.maskThread = new MaskCreationThread(this.image, this.x, this.y);
            this.maskThread.start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void rotate(BufferedImage image , AffineTransform at){
        double rotationAngleInRadians = Math.atan2(this.directionY, this.directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void update(){
        if(this.x >= this.targetX - 30 && this.y >= this.targetY - 30) {
            this.dx = Math.cos(angle) * this.speed;
            this.dy = Math.sin(angle) * this.speed;
        }

        this.x +=  this.dx;
        this.y +=  this.dy;


        if(this.x > gp.getWidth() || this.x < 0 || this.y > gp.getHeight() || this.y < 0){
            this.isActive = false;
        }

        if (this.maskThread.getMask() != null) {
            Area newMask = this.maskThread.getMask();
            if (this.mask != null) {
                AffineTransform at = AffineTransform.getTranslateInstance(this.x, this.y);
                at.rotate(rotationAngleInRadians);
                this.mask.reset();
                this.mask.add(newMask);
                this.mask.transform(at);
            }
        }

        System.out.println(this.mask);

        this.colRect.setLocation((int) this.x, (int) this.y);
    }


    public void draw(Graphics2D g2, GamePanel gamePanel){
        AffineTransform at =  AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        rotate(this.image, at);
        g2.drawImage(image, at, null);
        g2.setColor(Color.RED);
//        g2.draw(this.colRect);
        if(this.mask != null){
            g2.draw(this.mask);
        }
        g2.drawOval((int) this.x, (int) this.y, 2, 2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    public void checkWallCollision(){

    }
}
