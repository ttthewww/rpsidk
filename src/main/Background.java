package main;

import handlers.ImageHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Background {
    public BufferedImage image;
    public double x;
    public double y;
    public Background(double x, double  y){
        this.x = x;
        this.y = y;
        Random rand = new Random();
        int n = rand.nextInt(4);
        if(n == 0){
           this.image = ImageHandler.background1;
        }
        if(n == 1){
            this.image = ImageHandler.background2;
        }
        if(n == 2){
            this.image = ImageHandler.background3;
        }
        if(n == 3){
            this.image = ImageHandler.background4;
        }
    }
    public void update() {

    }

    public void draw(Graphics2D g) {
        g.drawImage(this.image, (int)this.x, (int)this.y, 960, 540, null);
    }}
