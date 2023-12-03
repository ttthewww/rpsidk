package entity;

import javax.swing.*;

import main.Game;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

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
    }

    public abstract void getImage();

    public abstract void setWindowDefaults();

    public abstract Point setValidSpawnPoint();

    public abstract void getNewDestination();

    public abstract void rotate(BufferedImage image, AffineTransform at);

    public abstract void move();

    public abstract void update();


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}