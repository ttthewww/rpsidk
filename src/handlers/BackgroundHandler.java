package handlers;

import entity.Player;
import main.Background;
import main.Game;

import java.awt.*;

public class BackgroundHandler {
    Player player;
    Game game;
    Background background1;
    Background background2;
    Background background3;
    Background activeBackground;
    double backgroundSpeed = 0.3;
    public BackgroundHandler(Game game){
        this.game = game;
        this.player = game.player;
        this.background1 = new Background(player.x, player.y);
        this.background2 = new Background(player.x, player.y);
        this.background3 = new Background(player.x, player.y);
    }

    public void update(){
        this.background1.x = -player.absoluteX * backgroundSpeed;
        this.background1.y = -player.absoluteY * backgroundSpeed;

//        this.background2.x = -player.absoluteX * backgroundSpeed;
//        this.background2.y = -player.absoluteY * backgroundSpeed;

//        System.out.println(player.absoluteX + " " + player.absoluteY);
    }
    /** BACKGROUND TO DO **/
    public void draw(Graphics2D g2){
//        this.activeBackground = background1;
//        if(player.absoluteY < 0){
//            this.background2.y = -this.game.window.getHeight() + 40 - player.absoluteY * backgroundSpeed;
//            this.background2.draw(g2);
//            activeBackground = background2;
//        }

//        if(player.absoluteY > 0){
//            this.background2.y = this.game.window.getHeight() - 40 - player.absoluteY * backgroundSpeed;
//            this.background2.draw(g2);
//        }
        this.background1.draw(g2);
//        this.activeBackground.draw(g2);


//        this.background3.draw(g2);
    }
    /** BACKGROUND TO DO **/
}
