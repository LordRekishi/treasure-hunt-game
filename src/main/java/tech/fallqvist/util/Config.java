package tech.fallqvist.util;

import tech.fallqvist.GamePanel;

import java.io.*;

public class Config {

    private final GamePanel gamePanel;

    public Config(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void saveConfig() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("config.txt"));

            // FULLSCREEN
            if (gamePanel.isFullScreenOn()) {
                bufferedWriter.write("On");
            } else {
                bufferedWriter.write("Off");
            }
            bufferedWriter.newLine();

            // MUSIC VOLUME
            bufferedWriter.write(String.valueOf(gamePanel.getMusic().getVolumeScale()));
            bufferedWriter.newLine();

            // SOUND EFFECT VOLUME
            bufferedWriter.write(String.valueOf(gamePanel.getSoundEffect().getVolumeScale()));
            bufferedWriter.newLine();

            bufferedWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("config.txt"));

            String s = bufferedReader.readLine();

            // FULLSCREEN
            if (s.equals("On")) {
                gamePanel.setFullScreenOn(true);
            } else {
                gamePanel.setFullScreenOn(false);
            }

            // MUSIC VOLUME
            s = bufferedReader.readLine();
            gamePanel.getMusic().setVolumeScale(Integer.parseInt(s));

            // MUSIC VOLUME
            s = bufferedReader.readLine();
            gamePanel.getSoundEffect().setVolumeScale(Integer.parseInt(s));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
