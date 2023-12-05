package main;

import handlers.MouseHandler;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public abstract class Menu implements TextUtil{
    Game game;
    MouseHandler mouseHandler;
    public Menu(Game game){
        this.game = game;
    }

    public abstract void update(ArrayList<Point> points, Graphics2D g2);
    public abstract void draw(Graphics2D g2);
}
