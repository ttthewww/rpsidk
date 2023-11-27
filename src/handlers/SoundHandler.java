package handlers;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class SoundHandler {
    Clip clip;
    URL soundURL[] = new URL[30];
    public SoundHandler(){
        soundURL[0] = getClass().getResource("/resource/sounds/bgm.wav");
        soundURL[1] = getClass().getResource("/resource/sounds/rock.wav");
        soundURL[2] = getClass().getResource("/resource/sounds/paper.wav");
        soundURL[3] = getClass().getResource("/resource/sounds/scissor.wav");
    }
    public void setFile (int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            // Adjust volume (example: 0.5f is 50% volume)
//            setVolume(0.5f);
        }catch (Exception e){

        }
    }

    public void setVolume(float volume) {
        if (clip != null) {
            try {
                FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float max = control.getMaximum();
                float min = control.getMinimum();

                // Ensure that the requested volume is within the valid range
                float adjustedVolume = Math.min(max, Math.max(min, volume));

                control.setValue(adjustedVolume);
            } catch (IllegalArgumentException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }
    }
    public void play (){
        clip.start();
    }
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop (){
        clip.stop();
    }
}
