package main;

import handlers.ImageHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MainMenu extends Menu{
    public int state = 0;
    ScoreBoard scores;

    public boolean playHovered;
    public boolean highScoresHovered;
    public boolean quitHovered;
    public boolean backHovered;

    public MainMenu(Game gp){
        super(gp);
        scores = new ScoreBoard();
    }

    public void update(ArrayList<Point> points, Graphics2D g2){
        playHovered = false;
        highScoresHovered = false;
        quitHovered = false;
        backHovered= false;

        if(this.state == 0){
            int playStringLength = getTextWidth(g2, "PLAY");
            int playStringHeight = getTextHeight(g2);
            if(gp.mouseX > points.get(1).x &&
                    gp.mouseX < points.get(1).x + playStringLength &&
                    gp.mouseY > points.get(1).y - playStringHeight + 12 &&
                    gp.mouseY < points.get(1).y)
            {
                playHovered = true;
            }

            int highScoresStringLength = getTextWidth(g2, "High Scores");
            int highScoresStringHeight= getTextHeight(g2);
            if(gp.mouseX > points.get(1).x &&
                    gp.mouseX < points.get(2).x + highScoresStringLength &&
                    gp.mouseY > points.get(2).y - highScoresStringHeight + 12 &&
                    gp.mouseY < points.get(2).y)
            {
                highScoresHovered = true;
            }


            int quitStringLength = getTextWidth(g2, "QUIT");
            int quitStringHeight = getTextHeight(g2);
            if(gp.mouseX > points.get(1).x &&
                    gp.mouseX < points.get(3).x + quitStringLength&&
                    gp.mouseY > points.get(3).y - quitStringHeight + 12 &&
                    gp.mouseY < points.get(3).y)
            {
                quitHovered = true;
            }
        }

        if(this.state == 1){
            int backStringLength = getTextWidth(g2, "BACK");
            int backStringHeight = getTextHeight(g2);
            if(gp.mouseX > points.get(1).x &&
                    gp.mouseX < points.get(1).x + backStringLength &&
                    gp.mouseY > points.get(1).y - backStringHeight + 12 &&
                    gp.mouseY < points.get(1).y)
            {
                backHovered = true;
            }
        }
    }

    public void draw(Graphics2D g2) {
        ArrayList<Point> points = new ArrayList<>();
        /** BACKGROUND TO DO **/
        BufferedImage backgroundImage = ImageHandler.mainMenuBackgroundImage;
        g2.drawImage(backgroundImage, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight(), null);
        /** BACKGROUND TO DO **/
        Font font = g2.getFont().deriveFont(Font.BOLD, 28F);
        g2.setFont(font);
        g2.setColor(Color.GREEN);

        String[] menuState0= {"WOW", "PLAY", "High Scores", "Quit"};
        int[] menuState0VerticalOffsets = {100, 200, 300, 400};


        ArrayList<String> topScores = scores.getTopScores();
        String[] menuState1 = {"High Scores","Back"};
        int[] menuState1VerticalOffsets = {50, 450};
        int[] highScoresOffset = {150, 200, 250, 300, 350};

        if(this.state == 0){
            for (int i = 0; i < menuState0.length; i++) {
                Point point = getCenteredTextPoint(g2, menuState0[i], menuState0VerticalOffsets[i]);
                g2.drawString(menuState0[i], point.x, point.y);
                points.add(point);
            }
        }

        if(this.state == 1){
            for (int i = 0; i < menuState1.length; i++) {
                Point point = getCenteredTextPoint(g2, menuState1[i], menuState1VerticalOffsets[i]);
                g2.drawString(menuState1[i], point.x, point.y);
                points.add(point);
            }

            for (int i = 0; i < topScores.size(); i++) {
                Point scorePoint = getCenteredTextPoint(g2, topScores.get(i), highScoresOffset[i]);
                g2.drawString(topScores.get(i), scorePoint.x, scorePoint.y);
            }
        }

        update(points, g2);
    }
}