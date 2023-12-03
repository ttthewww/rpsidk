package object;

import main.Game;

import javax.swing.*;
import java.awt.*;

public class ObjectDrawerThread extends Thread {
    private static final int UPDATE_INTERVAL_MS = 10;
    private volatile boolean isRunning = true;
    private Game gamePanel;
    public SuperObject[] obj;
    public ObjectDrawerThread(Game gamePanel) {
        this.gamePanel = gamePanel;
        obj = new SuperObject[10];
    }

    @Override
    public void run() {
        while (isRunning) {
            SwingUtilities.invokeLater(() -> {
                gamePanel.repaint();
                updateAnimations();
            });

            try {
                Thread.sleep(UPDATE_INTERVAL_MS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stopRunning() { //PAUSE
        isRunning = false;
    }
    public void startRunning() { //PLAY
        isRunning = true;
    }
    public boolean isRunning() {
        return isRunning;
    }
    private synchronized void updateAnimations() {
        for (SuperObject o : obj) {
            if (o != null) {
                o.updateAnimation();
            }
        }
    }
    public synchronized void drawObjects(Graphics2D g2) {
        for (SuperObject o : obj) {
            if (o != null) {
                o.draw(g2, gamePanel);
            }
        }
    }
    /**
     * Gets the SuperObject at the specified index.
     *
     * @param index the index of the SuperObject to retrieve
     * @return the SuperObject at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public synchronized SuperObject getObject(int index) {
        if (index >= 0 && index < obj.length) {
            return obj[index];
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }
    /**
     * Sets the SuperObject at the specified index.
     *
     * @param index   the index at which to set the SuperObject
     * @param sObject the SuperObject to set
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public synchronized void setObject(int index, SuperObject sObject) {
        if (index >= 0 && index < obj.length) {
            obj[index] = sObject;
        } else {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds");
        }
    }
}
