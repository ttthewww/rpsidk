package main;

import java.awt.*;

public class FPS {
    public int targetFPS= 60;
    public int currentFPS = 0;
    public double drawInterval = 1000000000/targetFPS;
    public double delta = 0;
    public long lastTime = System.nanoTime();
    public long currentTime;
    public long timer = 0;
    public int drawCount = 0;

    public void update(){
        this.currentTime = System.nanoTime();
        this.delta += (this.currentTime - this.lastTime) / this.drawInterval;
        this.timer += (this.currentTime - this.lastTime);
        this.lastTime = this.currentTime;
    }
}

