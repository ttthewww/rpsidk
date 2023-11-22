package handlers;

import entity.Player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    public boolean shoot;
    public boolean leftClicked;
    Player player;
    public MouseHandler(Player player){
        this.player = player;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int code = e.getButton();
        if(code == 1){
            leftClicked = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int code = e.getButton();
        if(code == 1){
            shoot = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int code = e.getButton();
        if(code == 1){
            shoot = false;
            leftClicked = false;
        }
        if(code == 3){
            player.toggleBulletType();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
