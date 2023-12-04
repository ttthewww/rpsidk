package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class GameOverMenu extends Menu{
    public boolean mainMenuHovered;
    public boolean retryHovered;
    public boolean quitHovered;
    public GameOverMenu(Game game) {
        super(game);
    }
    @Override
    public void update(ArrayList<Point> points, Graphics2D g2) {
        mainMenuHovered = false;
        retryHovered = false;
        quitHovered = false;

        int mainMenuStringLength = getTextWidth(g2, "Main Menu");
        int mainMenuStringHeight = getTextHeight(g2);

        if(game.mouseX > points.get(1).x &&
                game.mouseX < points.get(2).x + mainMenuStringLength &&
                game.mouseY > points.get(2).y - mainMenuStringHeight + 12 &&
                game.mouseY < points.get(2).y)
        {
            mainMenuHovered = true;
        }

        int retryStringLength = getTextWidth(g2, "Retry");
        int retryStringHeight = getTextHeight(g2);
        if(game.mouseX > points.get(1).x &&
                game.mouseX < points.get(3).x + retryStringLength &&
                game.mouseY > points.get(3).y - retryStringHeight + 12 &&
                game.mouseY < points.get(3).y)
        {
            retryHovered = true;
        }

        int quitStringLength = getTextWidth(g2, "Quit");
        int quitStringHeight = getTextHeight(g2);
        if(game.mouseX > points.get(1).x &&
                game.mouseX < points.get(4).x + quitStringLength &&
                game.mouseY > points.get(4).y - quitStringHeight + 12 &&
                game.mouseY < points.get(4).y)
        {
            quitHovered = true;
        }


    }

    @Override
    public void draw(Graphics2D g2) {
        ArrayList<Point> points = new ArrayList<>();
        Font font = g2.getFont().deriveFont(Font.BOLD, 28F);
        g2.setFont(font);
        g2.setColor(Color.GREEN);

        int score = this.game.player.score;

        String[] menuItems  = {"Game Over", "You Scored: " + score, "Main Menu", "Retry", "Quit"};
        int[] menuItemsOffest = {100, 135, 200, 275, 350, 425};

        for(int i = 0; i < menuItems.length; i++){
            Point point = getCenteredTextPoint(g2, menuItems[i], menuItemsOffest[i]);
            g2.drawString(menuItems[i], point.x, point.y);
            points.add(point);
        }

        update(points, g2);
    }
}
