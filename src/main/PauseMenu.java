package main;

import handlers.MouseHandler;
import java.awt.*;

public class  PauseMenu{
    GamePanel gp;
    MouseHandler mouseHandler;
    public PauseMenu(GamePanel gp, MouseHandler mouseHandler){
        this.gp = gp;
        this.mouseHandler = mouseHandler;
    }
    public void update(Point playPoint, Graphics2D g2){
        int playStringLength = getTextWidth(g2, "RESUME");
        int playStringHeight = getTextHeight(g2);

        if(gp.mouseX > playPoint.x &&
                gp.mouseX < playPoint.x + playStringLength &&
                gp.mouseY > playPoint.y - playStringHeight &&
                gp.mouseY < playPoint.y)
        {
            if(mouseHandler.leftClicked){
                gp.gameState = gp.mainGameState;
            }
        }
    }

    public void draw(Graphics2D g2){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
        g2.setColor(Color.GREEN);

        Point titlePoint = new Point(getXforCenteredText(g2, "PAUSED"),100);
        g2.drawString("PAUSED", titlePoint.x, titlePoint.y);

        Point playPoint = new Point(getXforCenteredText(g2, "RESUME"), 200);
        g2.drawString("RESUME", playPoint.x, playPoint.y);
        this.update(playPoint, g2);
    }

    public int getTextWidth(Graphics2D g2, String text) {
        FontMetrics fm = g2.getFontMetrics();
        return fm.stringWidth(text);
    }
    public int getTextHeight(Graphics2D g2) {
        FontMetrics fm = g2.getFontMetrics();
        return fm.getHeight();
    }
    public int getXforCenteredText(Graphics2D g2, String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = this.gp.screenWidth / 2 - length / 2;
        return x;
    }
}
