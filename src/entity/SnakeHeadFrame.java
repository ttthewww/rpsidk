package entity;

import main.Game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.ImageIcon;

public class SnakeHeadFrame extends EntityWindow{
    private int xHeadPoly[] = {};
    private int yHeadPoly[] = {};

    public Polygon polygon;

    char direction = 'R';

    public SnakeHeadFrame(Game game, int UNIT_SIZE) {
        super(game, UNIT_SIZE, UNIT_SIZE, 0);
        this.polygon = new Polygon(xHeadPoly, yHeadPoly, xHeadPoly.length);
    }

    @Override
    public void getImage() {

    }

    @Override
    public void setWindowDefaults() {
        super.setWindowDefaults();
        ImageIcon img = new ImageIcon("src/resource/snek.png");
        window.setIconImage(img.getImage());
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    public void updateDirection(char direction) {
        switch (direction) {
            case 'R':
                this.xHeadPoly = new int[]{10, this.getWidth() - 10, 10}; 
                this.yHeadPoly = new int[]{10, this.getHeight() / 2 - 10, this.getHeight() - 10};
                break;
            case 'L':
                this.xHeadPoly = new int[]{this.getWidth() - 10, this.getWidth() - 10, 0}; 
                this.yHeadPoly = new int[]{10, this.getHeight() - 10, this.getHeight() / 2};
                break;

            case 'U':
                this.xHeadPoly = new int[]{this.getWidth() / 2, 10, this.getWidth() - 10}; 
                this.yHeadPoly = new int[]{10, this.getHeight() - 10, this.getHeight() - 10};

                break;
            case 'D':
                this.xHeadPoly = new int[]{this.getWidth() / 2, this.getWidth() - 10, 10}; 
                this.yHeadPoly = new int[]{this.getHeight() - 10, 10, 10};
                break;
            default:
                break;
        } 
    }    

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.GREEN);
        g2.fill(this.polygon);
        this.polygon = new Polygon(xHeadPoly, yHeadPoly, xHeadPoly.length);
        
        g2.setStroke(new BasicStroke(4));

        g2.drawPolygon(polygon);
    }
}