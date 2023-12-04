package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test extends JFrame {
    JPanel panel = new ImagePanel();

    public Test() {
        super("Transparent Window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(500, 500));

        add(panel);
        pack();
        setVisible(true);
    }

    private class ImagePanel extends JPanel {
        private BufferedImage paperBulletImage;

        public ImagePanel() {
            try {
                paperBulletImage = ImageIO.read(getClass().getResourceAsStream("../resource/bullets/paper.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            if (paperBulletImage != null) {
                int x = (getWidth() - paperBulletImage.getWidth()) / 2;
                int y = (getHeight() - paperBulletImage.getHeight()) / 2;
                g2d.drawImage(paperBulletImage, x, y, this);
            } else {
                g2d.setColor(Color.RED);
                g2d.drawString("Failed to load image", 50, 50);
            }

            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        Test window = new Test();
        window.setLocation(0, 0); // Set the frame location to (0, 0)
        boolean start = true;

        int x =0;
        int y = 0;
        while(start){
            x++;
            window.setLocation(x, y);
        }
    }
}
