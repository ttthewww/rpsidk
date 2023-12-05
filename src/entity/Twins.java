package entity;
import java.awt.Graphics2D;
import main.Game;

public class Twins implements Boss{
    public Twin twin1;
    public Twin twin2;

    private TwinFrame twin1Frame;
    private TwinFrame twin2Frame;
    public boolean isActive = true;

    private int health;
    
    private int isShootingDuration = 100;
    private int isShootingTimer = 0;
    private int maxStrokeWidth = 20;
    private int minStrokeWidth = 1;

    public Twins(Game game){
        // twin1Frame = new TwinFrame(game, isShootingDuration, isShootingTimer, maxStrokeWidth, minStrokeWidth);
        // twin2Frame = new TwinFrame(game, isShootingDuration, isShootingTimer, maxStrokeWidth, minStrokeWidth);

        twin1 = new Twin(game, isShootingDuration, isShootingTimer, maxStrokeWidth, minStrokeWidth);
        twin2 = new Twin(game, isShootingDuration, isShootingTimer, maxStrokeWidth, minStrokeWidth);

        this.health = 10;
    }

    public void update(){
        // twin1Frame.update();
        // twin2Frame.update();

        twin1.update();
        twin2.update();

        if(this.health <= 0){
            this.isActive = false;
            dispose();
            return;
        }
    }

    public void dispose(){
        this.twin1Frame.window.dispose();
        this.twin2Frame.window.dispose();
    }

    public void draw(Graphics2D g2){
        // twin1Frame.repaint();
        // twin2Frame.repaint();
    
        twin1.draw(g2);
        twin2.draw(g2);
    }

    public void takeDamage(){
        this.health--;
    }
}
