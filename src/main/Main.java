package main;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Game gamePanel = new Game();
        gamePanel.startGameThread();
        System.out.print("Hello World");
    }
}