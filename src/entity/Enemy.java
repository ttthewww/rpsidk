package entity;

import main.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static main.GamePanel.maskCreationThread;

public abstract class Enemy extends Entity{

    GamePanel gp;
    public int x;
    public int y;

    ArrayList<Enemy> enemies;
    public int enemyType;
    MaskCreationThread maskThread;
    public int attackTimer = 150;
    public int attackCooldown = 0;
    public EnemyWindowContainer enemyWindowContainer;
    private BufferedImage aura;
    public Enemy(GamePanel gp, ArrayList<Enemy> enemies, int enemyType){
        this.enemyType = enemyType;
        this.gp = gp;

//        this.x = 200;
//        this.y = 20;

        Point spawn = validSpawnPoint();
        this.x = spawn.x;
        this.y = spawn.y;
        getEnemyImage();

        this.enemies = enemies;
        this.colRect = this.mask.getBounds();

//        if(this.enemyType == 1){
//            this.enemyWindowContainer= new EnemyWindowContainer(
//                    100,
//                    100,
//                    100,
//                    100,
//                    this,
//                    gp
//            );
//            Thread enemyWindowThread = new Thread(this.enemyWindowContainer);
//            enemyWindowThread.start();
//        }
    }

    public void getEnemyImage(){
        if(this.enemyType == 1){
            this.image = ImageHandler.enemyRockImage;
            this.aura = ImageHandler.enemyRockAura;
        }else if(this.enemyType == 2){
            this.image = ImageHandler.enemyPaperImage;
            this.aura = ImageHandler.enemyPaperAura;
        }else{
            this.image = ImageHandler.enemyScissorImage;
            this.aura = ImageHandler.enemyScissorsAura;
        }

        this.mask = new Area(maskCreationThread.addMask(this));
    }

    public Point validSpawnPoint() {
        int maxAttempts = 100;
        int x;
        int y;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            if (Math.random() < 0.5) {
                x = (int) (Math.random() * -200);
            } else {
                x = (int) (Math.random() * gp.getWidth() + 200) + gp.getWidth();
            }

            if (Math.random() < 0.5) {
                y = (int) (Math.random() * gp.getHeight() - 200);
            } else {
                y = (int) (Math.random() * gp.getHeight() + 200) + gp.getHeight();
            }

            int distanceX = Math.abs(x - gp.player.x);
            int distanceY = Math.abs(y - gp.player.y);

            if (!(distanceX < 200 || distanceY < 200 || checkCollisionWithEnemies(x, y))) {
                return new Point(x, y);
            }
        }
        return null;
    }

    private boolean checkCollisionWithEnemies(int x, int y) {
        if (this.enemies != null && !this.enemies.isEmpty()) {
            for (Enemy otherEnemy : this.enemies) {
                if (otherEnemy != this && this.colRect.intersects(otherEnemy.colRect)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void update() {
        int speed = 1;

        if(this.x > Player.x)this.x--;
        if(this.x < Player.x)this.x++;
        if(this.y > Player.y)this.y--;
        if(this.y < Player.y)this.y++;

        for (Enemy otherEnemy : enemies) {
            if (otherEnemy != this) {
                if (this.colRect.intersects(otherEnemy.colRect)) {
                    int dx = this.x - otherEnemy.x;
                    int dy = this.y - otherEnemy.y;
                    double angle = Math.atan2(dy, dx);
                    this.x += (int) (speed * Math.cos(angle));
                    this.y += (int) (speed * Math.sin(angle));
                }
            }
        }

        if (maskCreationThread.getMask(this) != null) {
            Area newMask = maskCreationThread.getMask(this);
            AffineTransform at = AffineTransform.getTranslateInstance(this.x, this.y);
            double directionX = Player.x - (this.x + (double) this.image.getWidth() / 2);
            double directionY = Player.y - (this.y + (double) this.image.getHeight() / 2);
            double rotationAngleInRadians = Math.atan2(directionY, directionX);

            at.rotate(rotationAngleInRadians);
            this.mask.reset();
            this.mask.add(newMask);
            this.mask.transform(at);

//            System.out.println(this.x + " " + this.y);
//            System.out.println(this.mask.getBounds().getX() + " " + this.mask.getBounds().getY());
        }
        this.colRect.setLocation((int) (this.x - this.image.getWidth() / 2.0), (int) (this.y - this.image.getHeight() / 2.0));
        this.attackCooldown++;
        if(attackCooldown >= attackTimer){
            attackCooldown = attackTimer;
        }
    }
    public void rotate(AffineTransform at, BufferedImage image){
        double directionX = Player.x - (this.x + (double)image.getWidth() / 2);
        double directionY = Player.y - (this.y + (double)image.getHeight() / 2);
        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void draw(Graphics2D g2) {
        AffineTransform at = AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        AffineTransform auraAt = AffineTransform.getTranslateInstance(this.x - this.aura.getWidth() / 2.0, this.y - this.aura.getHeight() / 2.0);

        rotate(at, this.image);
        rotate(auraAt, this.aura);

        g2.setColor(Color.RED);

        g2.drawImage(this.aura, auraAt, null);
        g2.drawImage(this.image, at, null);

//        g2.draw(this.mask);
//        g2.draw(this.colRect);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}