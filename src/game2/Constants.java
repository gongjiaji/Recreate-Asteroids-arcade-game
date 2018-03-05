package game2;

import utilities.ImageManager;

import java.awt.*;
import java.io.IOException;

public class Constants {
    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 750;
    public static final int DRAWING_SCALE = 1;
    public static final int DELAY = 20; //0.02s
    public static final double DT = DELAY / 1000.0;
    public static final Dimension FRAME_SIZE = new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

    public static Image ASTEROID1, MILKYWAY1, MILKYWAY2, PLAYERSHIP, SAUCER1, SAUCER2, SAUCER3, SAUCER4,
            PLAYERBULLET, SAUCERBULLET, HOLDBULLET, BLOOD, BULLET_ITEM, STAR;

    static {
        try {
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            MILKYWAY1 = ImageManager.loadImage("universe");
            MILKYWAY2 = ImageManager.loadImage("milkyway2");
            PLAYERSHIP = ImageManager.loadImage("feiji");
            SAUCER1 = ImageManager.loadImage("alien1");
            SAUCER2 = ImageManager.loadImage("alien2");
            SAUCER3 = ImageManager.loadImage("alien3");
            SAUCER4 = ImageManager.loadImage("alien4");
            PLAYERBULLET = ImageManager.loadImage("playerBullet");
            SAUCERBULLET = ImageManager.loadImage("hold_bullet");
            HOLDBULLET = ImageManager.loadImage("enemy_beam");
            BLOOD = ImageManager.loadImage("bloodpack");
            BULLET_ITEM = ImageManager.loadImage("shield");
            STAR = ImageManager.loadImage("Star");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

