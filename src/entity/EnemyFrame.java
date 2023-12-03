package entity;

import javax.swing.*;

import main.Game;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public abstract class EnemyFrame extends JPanel {
    public Game game;
    public JFrame window;

    public int windowPosX, windowPosY;

    public BufferedImage image;
    public Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public Point destination;

    public int enemyX, enemyY;
    public int enemyXLocationOnScreen, enemyYLocationOnScreen;

    public int windowWidth;
    public int windowHeight;

    public int playerX;
    public int playerY;

    public int speed = 2;

    public EnemyFrame(Game game, int windowWidth, int windowHeight) {
        getImage();
        this.game = game;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        setWindowDefaults();
        this.setDoubleBuffered(true);
        this.setSize(window.getWidth(), window.getHeight());
        this.setFocusTraversalKeysEnabled(false);
        this.window.setVisible(true);
    }


    public EnemyFrame(Game game, int windowWidth, int windowHeight, boolean isVisible){
        this.game = game;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        setWindowDefaults();
        this.setDoubleBuffered(true);
        this.setSize(window.getWidth(), window.getHeight());
        this.setFocusTraversalKeysEnabled(false);
        if(isVisible){
            this.window.setVisible(true);
        }
    }

    public abstract void getImage();

    public void setWindowDefaults(){
        this.window = new JFrame();
        this.window.setSize(windowWidth, windowHeight);

        this.window.setFocusTraversalKeysEnabled(false);
        this.window.setFocusableWindowState(false);
        this.window.setResizable(false);
        this.window.setDefaultCloseOperation(0);
        this.window.add(this);

        this.setSize(this.window.getWidth(), this.window.getHeight());

        this.window.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                window.setExtendedState(Frame.NORMAL);
            }
        }); 
    }

    public abstract void getNewDestination();

    public abstract void rotate(BufferedImage image, AffineTransform at);

    public abstract void move();

    public abstract void update();

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}