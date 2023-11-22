package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionHandler implements MouseMotionListener {
    private int mouseX;
    private int mouseY;
    private boolean mouseMoved;
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        mouseMoved = true;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public boolean hasMouseMoved() {
        if (mouseMoved) {
            mouseMoved = false;
            return true;
        }
        return false;
    }
}
