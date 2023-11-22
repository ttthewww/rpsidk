package main;

import handlers.ImageHandler;
import handlers.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class MainMenu {
    GamePanel gp;
    MouseHandler mouseHandler;
    public MainMenu(GamePanel gp, MouseHandler mouseHandler){
        this.gp = gp;
        this.mouseHandler = mouseHandler;
    }

    public void update(Point playPoint, Graphics2D g2){
        int playStringLength = getTextWidth(g2, "PLAY");
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
        BufferedImage backgroundImage = ImageHandler.mainMenuBackgroundImage;
        g2.drawImage(backgroundImage, 0,0, backgroundImage.getWidth(), backgroundImage.getHeight(), null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
        g2.setColor(Color.GREEN);

        Point titlePoint = new Point(getXforCenteredText(g2, "GAME"),100);
        g2.drawString("GAME", titlePoint.x, titlePoint.y);

        Point playPoint = new Point(getXforCenteredText(g2, "PLAY"), 200);
        g2.drawString("PLAY", playPoint.x, playPoint.y);
        update(playPoint, g2);
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

    private void drawBoundingBox(Graphics2D g2, int x, int y, String text) {
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        g2.setColor(Color.WHITE);
        g2.drawRect(x - 2, y - fm.getAscent() + 4, textWidth, textHeight - 4);
    }
}