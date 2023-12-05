package handlers;

import main.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    public boolean shoot;
    public boolean leftClicked;

    Game game;
    public MouseHandler(Game game){
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int code = e.getButton();
        if(code == 1){
            if(this.game.gameState == game.mainMenuState){
                if(this.game.mainMenu.playHovered){
                    this.game.reset();
                }
                if(this.game.mainMenu.highScoresHovered){
                    this.game.mainMenu.state = 1;
                }
                if(this.game.mainMenu.quitHovered){
                    System.exit(0);
                }
                if(this.game.mainMenu.backHovered){
                    this.game.mainMenu.state = 0;
                }
            }

            if(this.game.paused){
                if(this.game.pauseMenu.resumeHovered){
                    KeyHandler.escToggled = false;
                    this.game.paused = false;
                }
                if(this.game.pauseMenu.mainMenuHovered){
                    KeyHandler.escToggled = false;
                    this.game.paused = false;
                    this.game.gameState = game.mainMenuState;
                }
                if(this.game.pauseMenu.quitHovered){
                    System.exit(0);
                }
            }

            if(this.game.gameState == this.game.gameOverState){
                if(this.game.gameOverMenu.mainMenuHovered){
                    this.game.gameState = 0;
                }
                if(this.game.gameOverMenu.retryHovered){
                    this.game.reset();
                }
                if(this.game.gameOverMenu.quitHovered){
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int code = e.getButton();
        if(code == 1){
            shoot = true;
            leftClicked = true;
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
            this.game.player.toggleBulletType();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
