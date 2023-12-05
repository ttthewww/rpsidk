package main;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;

public interface TextUtil {
    default Point getCenteredTextPoint(Game game, Graphics2D g2, String text, int y) {
        int screenWidth = game.getWidth();
        int textWidth = getTextWidth(g2, text);
        int x = screenWidth / 2 - textWidth / 2;
        y += getTextHeight(g2);
        return new Point(x, y);
    }

    public default int getTextWidth(Graphics2D g2, String text) {
        FontMetrics fm = g2.getFontMetrics();
        return fm.stringWidth(text);
    }

    public default int getTextHeight(Graphics2D g2) {
        FontMetrics fm = g2.getFontMetrics();
        return fm.getHeight();
    } 
}
