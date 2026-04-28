package main;

import java.io.*;

public class Config {

    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }
    public void saveConfig() {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // SAVE FULL SCREEN
            if(gp.fullScreenOn) {
                bw.write("On");
            }
            if(!gp.fullScreenOn){
                bw.write("Off");
            }
            bw.newLine();

            // SAVE MUSIC

            bw.write(String.valueOf(gp.music.volumeScale));
            bw.newLine();

            // SAVE SE
            bw.write(String.valueOf(gp.sE.volumeScale));
            bw.newLine();

            bw.close();

        } catch(IOException e) {
             e.printStackTrace();
        }
    }
    public void loadConfig() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String str = br.readLine();

            // FULL SCREEN
            if(str.equals("On")) {
                gp.fullScreenOn = true;
            }
            if(str.equals("Off")){
                gp.fullScreenOn = false;
            }

            // MUSIC VOLUME
            str = br.readLine();
            gp.music.volumeScale = Integer.parseInt(str);

            // SE VOLUME
            str = br.readLine();
            gp.sE.volumeScale = Integer.parseInt(str);

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
