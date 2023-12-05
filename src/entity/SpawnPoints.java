package entity;

import main.Game;

import java.awt.geom.Point2D;

public interface SpawnPoints {

    public default Point2D.Double validSpawnPoint(Game gp) {
        double x;
        double y;

        if (Math.random() < 0.5) {
            x = (Math.random() * 201) - 200;
        } else {
            x = (Math.random() * 201) + gp.getWidth();
        }

        if (Math.random() < 0.5) {
            y = (Math.random() * 201) - 200;
        } else {
            y = (Math.random() * 201) + gp.getHeight();
        }
        Point2D.Double spawn = new Point2D.Double(x, y);
        return spawn;
    }
}
