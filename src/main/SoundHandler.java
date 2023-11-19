package main;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class SoundHandler {
    static Clip clip;

    public static void playSound(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        try (InputStream audioStream = SoundHandler.class.getResourceAsStream(filePath)) {
            if (audioStream == null) {
                throw new IOException("Audio file not found: " + filePath);
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);

            // Use the class-level clip variable instead of declaring a new local variable
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.setMicrosecondPosition(0);
            clip.start();
        }
    }

    public static void setVolume(float volume) {
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
}
