package Util;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class Utils {


    public static Image getImage(String path) {
        path = "image/" + path;

        File sourceimage = new File( path);
        try {
            Image image = ImageIO.read(sourceimage);
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Clip getClip(String path) throws Exception {
        Clip clip = AudioSystem.getClip();
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File( path));
        clip.open(inputStream);
        return clip;
    }

    public static int getRandom(int number) {
        Random sat = new Random();
        boolean exit = false;
        int tulos = (int) (Math.abs(sat.nextInt() % number));
        return tulos;
    }

}
