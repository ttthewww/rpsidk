package main;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GamePanel gamePanel = new GamePanel();
        gamePanel.startGameThread();
        System.out.print("Hello World");
    }
}