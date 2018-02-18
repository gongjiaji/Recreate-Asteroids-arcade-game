package game2;

import utilities.ImageManager;

import java.awt.*;
import java.io.IOException;

public class Constants {
    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 750;
    public static final int DRAWING_SCALE = 1;
    public static final int DELAY = 20;
    public static final double DT = DELAY / 1000.0;
    public static final Dimension FRAME_SIZE = new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

    public static Image ASTEROID1, MILKYWAY1, MILKYWAY2;

    static {
        try {
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            MILKYWAY1 = ImageManager.loadImage("milkyway1");
            MILKYWAY2 = ImageManager.loadImage("milkyway2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

