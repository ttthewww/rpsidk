package main;

import entity.Enemy;

import javax.swing.*;

public class EnemyWindowContainer extends JFrame implements Runnable {
    int windowPosX;
    int windowPosY;
    int windowHeight;
    int windowWidth;
    Enemy enemy;
    GamePanel gp;

    private volatile boolean running = true;

    public EnemyWindowContainer(int windowHeight, int windowWidth, int windowPosX, int windowPosY, Enemy enemy, GamePanel gp) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowPosX = windowPosX;
        this.windowPosY = windowPosY;
        this.enemy = enemy;
        this.gp = gp;

        this.setFocusableWindowState(false);
        this.setLocation((int) (this.enemy.x + this.gp.getLocationOnScreen().x), (int) (this.enemy.y + this.gp.getLocationOnScreen().y));
        this.setSize(windowHeight, windowWidth);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
    }

    @Override
    public void run() {
        while (running) {
            update();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dispose();
        System.gc();
    }

    public void draw() {
        System.out.println("TEST");
    }

    public void update() {
        int newWindowPosX = this.enemy.x + gp.getLocationOnScreen().x;
        int newWindowPosY = this.enemy.y + gp.getLocationOnScreen().y;

        if (checkOverlap(newWindowPosX, newWindowPosY)) {
            System.out.println("Window position overlaps with GamePanel position!");
            running = false;
        }

        this.windowPosX = newWindowPosX;
        this.windowPosY = newWindowPosY;

        this.setLocation((int) this.windowPosX, (int) this.windowPosY);
    }

    private boolean checkOverlap(int posX, int posY) {
        int gpPos_X = gp.getLocationOnScreen().x;
        int gpPos_Y = gp.getLocationOnScreen().y;

        int gpWidth = gp.getWidth();
        int gpHeight = gp.getHeight();

        return (posX < gpPos_X + gpWidth && posX + windowWidth > gpPos_X &&
                posY < gpPos_Y + gpHeight && posY + windowHeight > gpPos_Y);
    }

}
