package main;

import javax.swing.JFrame;

public class WindowContainer extends JFrame {
    public double windowHeight;
    public double windowWidth;
    public double windowPosX;
    public double windowPosY;
    public WindowContainer(double windowWidth, double windowHeight, int windowPosX, int windowPosY) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        this.windowPosX = windowPosX;
        this.windowPosY = windowPosY;
        this.setLocation(windowPosX, windowPosY);
        this.setSize((int) windowHeight, (int) windowWidth);
        // this.setVisible(true);
    }
}