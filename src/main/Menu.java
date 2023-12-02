package main;

import handlers.MouseHandler;
import java.awt.*;
import java.util.ArrayList;

public abstract class Menu {
    Game game;
    MouseHandler mouseHandler;
    public Menu(Game game){
        this.game = game;
    }

    public abstract void update(ArrayList<Point> points, Graphics2D g2);
    public abstract void draw(Graphics2D g2);
    public Point getCenteredTextPoint(Graphics2D g2, String text, int yOffset) {
        int screenWidth = this.game.windowWidth;
        int textWidth = getTextWidth(g2, text);
        int x = screenWidth / 2 - textWidth / 2;
        int y = yOffset + getTextHeight(g2);
        return new Point(x, y);
    }

    public int getTextWidth(Graphics2D g2, String text) {
        FontMetrics fm = g2.getFontMetrics();
        return fm.stringWidth(text);
    }

    public int getTextHeight(Graphics2D g2) {
        FontMetrics fm = g2.getFontMetrics();
        return fm.getHeight();
    }
}
