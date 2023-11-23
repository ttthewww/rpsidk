package handlers;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class SoundHandler {
    private static Clip clip;

    public static void playSound(String filePath, boolean loop) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        try (InputStream audioStream = SoundHandler.class.getResourceAsStream(filePath)) {
            if (audioStream == null) {
                throw new IOException("Audio file not found: " + filePath);
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioStream);

            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            if (loop) {
                // Set up a listener to detect when the music ends
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        // Music has stopped, rewind and play again for looping
                        clip.setMicrosecondPosition(0);
                        clip.start();
                    }
                });
            }

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
