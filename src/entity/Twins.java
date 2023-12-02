package entity;

import java.awt.Graphics2D;

import main.Game;

public class Twins implements Boss{
    public Twin twin1;
    public Twin twin2;

    private FrameEnemy twin1Frame;
    private FrameEnemy twin2Frame;
    private int health;
    public boolean isActive = true;
    public Twins(Game game){
        twin1Frame = new FrameEnemy(game);
        twin2Frame = new FrameEnemy(game);

        twin1 = new Twin(game, twin1Frame);
        twin2 = new Twin(game, twin2Frame);
        this.health = 10;
    }

    public void update(){
        twin1Frame.update();
        twin2Frame.update();

        twin1.update();
        twin2.update();

        if(health <= 0){
            this.isActive = false;
            twin1Frame.window.dispose();
            twin2Frame.window.dispose();
        }
    }

    public void dispose(){
        this.twin1Frame.window.dispose();
        this.twin2Frame.window.dispose();
    }

    public void draw(Graphics2D g2){
        twin1Frame.repaint();
        twin2Frame.repaint();
    
        twin1.draw(g2);
        twin2.draw(g2);
    }

    public void takeDamage(){
        this.health--;
    }
}
