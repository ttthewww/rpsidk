package entity;

import javax.swing.*;

import main.Game;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public abstract class EntityWindow extends JPanel {
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

    public EntityWindow(Game game, int windowWidth, int windowHeight, int state) {
        getImage();
        this.game = game;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        setWindowDefaults();

        if(state == 1){
            this.window.setUndecorated(true);
            this.window.setBackground(new Color(0, 0, 0, 0));
            this.setPreferredSize(new Dimension(windowWidth, windowHeight));
            this.window.pack();
            this.setOpaque(false);
        }

        this.setDoubleBuffered(true);
        this.setSize(window.getWidth(), window.getHeight());
        this.setFocusTraversalKeysEnabled(false);
        this.window.setVisible(true);
    }

    public EntityWindow(Game game, int windowWidth, int windowHeight, boolean isVisible){
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

    public void setWindowDefaults(){
        this.window = new JFrame();

        this.window.setSize(windowWidth, windowHeight);
        this.window.setFocusTraversalKeysEnabled(false);
        this.window.setFocusableWindowState(false);

        this.window.setResizable(false);
        this.window.setDefaultCloseOperation(0);
        this.window.add(this);
        
        this.window.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                window.setExtendedState(Frame.NORMAL);
            }
        }); 
    }

    public abstract void getImage();

    public abstract void update();

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}