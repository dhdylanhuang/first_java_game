package openworld.music;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BackgroundMusic {

    private Thread musicThread;

    public void playMusic(String[] locations) {
        if (locations == null || locations.length == 0) {
            System.err.println("No music files provided.");
            return;
        }

        musicThread = new Thread(() -> {
            try {
                for (String location : locations) {
                    playSong(location);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        musicThread.start();
    }

    private void playSong(String location) throws InterruptedException {
        File musicPath = new File(location);

        if (!musicPath.exists()) {
            System.err.println("File not found: " + location);
            return;
        }

        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            clip.start();

            synchronized (clip) {
                clip.wait();
            }

            clip.stop();
            clip.close();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
