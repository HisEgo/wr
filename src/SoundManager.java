import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    private Clip backgroundMusicClip;
    private float volume = 0.5f; // Default volume

    public SoundManager(String musicFilePath) {
        try {
            File musicFile = new File(musicFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            backgroundMusicClip = (Clip) AudioSystem.getLine(info);
            backgroundMusicClip.open(audioStream);

            // Set initial volume
            setVolume(volume);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playBackgroundMusic() {
        if (backgroundMusicClip != null) {
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicClip != null && backgroundMusicClip.isRunning()) {
            backgroundMusicClip.stop();
        }
    }

    public void setVolume(float volume) {
        this.volume = volume;
        if (backgroundMusicClip != null && backgroundMusicClip.isOpen()) {
            FloatControl gainControl = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            if (gainControl != null) {
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            }
        }
    }

    public float getVolume() {
        return volume;
    }
}
