package main;

import java.io.*;

public class ScoreBoard {
    private static final String SCORE_FILE = "src/resource/Scores.txt";
    static void addScore(String playerName, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            // Append the new score to the file
            writer.write(playerName + "," + score);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayScoreBoard() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            System.out.println("Score Board:");

            String line;
            while ((line = reader.readLine()) != null) {
                // Split the line into player name and score
                String[] parts = line.split(",");
                String playerName = parts[0];
                int score = Integer.parseInt(parts[1]);

                System.out.println(playerName + ": " + score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
