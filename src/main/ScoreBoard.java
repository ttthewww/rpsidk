package main;

import java.io.*;
import java.util.*;

public class ScoreBoard {
    private static final String SCORE_FILE = "src/resource/Scores.txt";
    void addScore(String date, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE, true))) {
            writer.write(score + " | " + date);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTopScores() {
        ArrayList<String> topScoresList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            ArrayList<String> scores = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                scores.add(line);
            }

            Collections.sort(scores, (s1, s2) -> {
                int score1 = Integer.parseInt(s1.split(" | ")[0]);
                int score2 = Integer.parseInt(s2.split(" | ")[0]);
                return Integer.compare(score2, score1);
            });
            if (scores.size() > 5) {
                scores.subList(5, scores.size()).clear();
            }

            topScoresList.addAll(scores);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return topScoresList;
    }
}
