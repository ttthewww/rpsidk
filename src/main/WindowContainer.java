package main;

import javax.swing.JFrame;

public class WindowContainer extends JFrame {
    double windowHeight;
    double windowWidth;
    double windowPosX;
    double windowPosY;
    final double windowMin;
    public WindowContainer(double windowWidth, double windowHeight, int windowMin, int windowPosX, int windowPosY) {
        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;
        this.windowMin = windowMin;
        this.windowPosX = windowPosX;
        this.windowPosY = windowPosY;
        this.setLocation(windowPosX, windowPosY);
        this.setSize((int) windowHeight, (int) windowWidth);
        this.setVisible(true);
    }
    public void shrink() {
        if (windowHeight > windowMin) {
            windowPosY += 2;
            windowHeight -= 4;
        } else {
            windowHeight = windowMin;
        }

        if (windowWidth > windowMin) {
            windowPosX += 2;
            windowWidth -= 4;
        } else {
            windowWidth = windowMin;
        }

        setLocation((int) windowPosX, (int) windowPosY);
        setSize((int) windowWidth, (int) windowHeight);
    }


}