package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainMenu {
    GamePanel gp;
    public MainMenu(GamePanel gp){
        this.gp = gp;

//        this.gp.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int mouseX = e.getX();
//                int mouseY = e.getY();
//
//                FontMetrics fm = getFontMetrics();
//                int textX = getXforCenteredText(fm, "PLAY");
//                int textY = 200;
//                int textWidth = fm.stringWidth(text);
//                int textHeight = fm.getHeight();
//
//                if (mouseX >= textX && mouseX <= textX + textWidth &&
//                        mouseY >= textY && mouseY <= textY + textHeight) {
//                    JOptionPane.showMessageDialog(null, "Text 'PLAY' clicked!");
//                }
//            }
//        });
    }

    public void update(){

    }

    public void draw(Graphics2D g2){
        g2.setColor(new Color(123,45, 54));
        g2.fillRect(0, 0, this.gp.getWidth(), this.gp.getHeight());


        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
        g2.setColor(Color.GREEN);

        String text1 = "GAME";
        int text1X = getXforCenteredText(g2, text1);
        g2.drawString(text1, text1X, 150);
        drawBoundingBox(g2, text1X, 150, text1);

        g2.drawString("PLAY", getXforCenteredText(g2, "PLAY"), 200);
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
