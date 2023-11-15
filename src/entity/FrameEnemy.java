package entity;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class FrameEnemy extends JFrame {
    int screenWidth = 576;
    int screenHeight = 576;
    int x;
    int y;
    double moveChance = 0.001 * 0.16;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int xRange = (int) screenSize.getWidth();
    int yRange = (int) screenSize.getHeight();
    String type;
    public FrameEnemy(){
        this.x = (int) (Math.random() * xRange);
        this.y = (int) (Math.random() * yRange);

        this.setLocation(this.x,this.y);
        this.setSize(100,100);
        this.setFocusableWindowState(false);

//        this.setUndecorated(true);
        this.setVisible(true);
    }
    public FrameEnemy(int x, int y, Player player){
        this.type = "frame";
        this.setLocation(this.x,this.y);
        this.setSize(100,100);
        this.setFocusableWindowState(false);
//        this.setUndecorated(true);
        this.setVisible(true);
    }
    public void update(Player player) {
        if(Math.random() < moveChance){
            this.setState(JFrame.ICONIFIED);
            this.x = (int) (Math.random() * xRange);
            this.y = (int) (Math.random() * yRange);
            this.setState(JFrame.NORMAL);
        }

        if(Objects.equals(this.type, "frame")){
//            if(this.x > player.getAbsolutePosition().x)this.x--;
//            if(this.x < player.getAbsolutePosition().x)this.x++;
//            if(this.y > player.getAbsolutePosition().y)this.y--;
//            if(this.y < player.getAbsolutePosition().y)this.y++;
        }
        this.setLocation(this.x, this.y);
    }
}
