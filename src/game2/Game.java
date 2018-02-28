package game2;

import utilities.JEasyFrame;
import utilities.SoundManager;
import utilities.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static game2.Constants.DELAY;
import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;

public class Game {
    private static int N_INITIAL_ASTEROIDS = 1;
    private static PlayerShip playerShip;
    private static Keys ctrl;
    public static List<GameObject> objects;
    public static List<Saucer> saucers;
    public static int score = 0;
    public static int life = 30;
    private static int bonus = 0;
    private static int award_threshold = 9;
    public static int level = 1;
    public static boolean over = false; // 0 playing 1 dead 2 restart
    private Saucer saucer_1 = new Saucer(new RotateNShoot());
    private Saucer saucer_2 = new Saucer(new RotateNShoot());
    private Saucer saucer_3 = new Saucer(new RotateNShoot());
    private Saucer saucer_4 = new Saucer(new RotateNShoot());
    public static boolean bossFight = false;

    public Game() {
        objects = new ArrayList<>(); //store all game objects
        saucers = new ArrayList<>();
        saucers.add(saucer_1);
        saucers.add(saucer_2);
        saucers.add(saucer_3);
        saucers.add(saucer_4);
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }
        ctrl = new Keys();
        playerShip = new PlayerShip(ctrl);
        objects.add(playerShip);
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        View view = new View(game);

        new JEasyFrame(view, "Basic game").addKeyListener(ctrl);

        SoundManager.play(SoundManager.bgm);

        while (true) {
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    public void update() {
        int i = 0;
        List<GameObject> alive = new ArrayList<>();
        for (GameObject o : objects) {
            o.update();
            if (!o.dead) {
                alive.add(o);
            }
            if (PlayerShip.bullet != null) {
                alive.add(PlayerShip.bullet);
                PlayerShip.bullet = null;
            }

            if (Saucer.bullet != null) {
                alive.add(Saucer.bullet);
                Saucer.bullet = null;
            }

            if (!Asteroid.splits.isEmpty()) {
                alive.addAll(Asteroid.splits);
                Asteroid.splits.clear();
            }
        }
        synchronized (Game.class) {
            objects.clear();
            objects.addAll(alive);
        }

        for (GameObject object : objects) {
            for (GameObject object1 : objects) {
                object.collisionHandling(object1);
            }
        }

        for (GameObject a : alive) {
            if (a.getClass() == Asteroid.class) {
                i++;
            } else if (a.getClass() == Saucer.class) {
                i = 1000;
            }
        }
        if (i == 0 && !over) { // clear all asteroids
            if (!bossFight) { // if not boss fight , start boss fight
                switch (level) {
                    case 1:
                        System.out.println("enter 1");
                        objects.add(saucer_1);
                        Saucer.HP = 20;
                        Game.bossFight = true; // fighting boss
                        break;
                    case 2:
                        System.out.println("enter 2");
                        objects.add(saucer_2);
                        Saucer.HP = 40;
                        Game.bossFight = true;
                        break;
                    case 3:
                        objects.add(saucer_3);
                        Game.bossFight = true;
                        Saucer.HP = 50;
                        break;
                    case 4:
                        objects.add(saucer_4);
                        Game.bossFight = true;
                        Saucer.HP = 80;
                        break;
                }
            } else {
                if (Saucer.nextLevel) { // saucer dead
                    levelup();
                    bossFight = false;
                    Saucer.nextLevel = false;
                }
            }
        }

        if (ctrl.action.teleport) {
            playerShip.teleport();
        }
    }

    public static void addScore() {
        score++;
        bonus++; // additional life will be given for every x scores
        if (bonus > award_threshold) { // 10 scores +1 life
            life++;
            SoundManager.extraShip();
            bonus = 0;
        }
    }

    private void levelup() {
        objects.clear();
        level++;
        if (level > 4){
            over = true;
        }
//        N_INITIAL_ASTEROIDS += 2;
        award_threshold += 10;
        Bullet.FLYINGTIME += 500;
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }
        objects.add(playerShip);
    }

    // end of game
    public static String over() {
        over = true;
        if (level <= 4){
            for (GameObject o : objects) {
                o.dead = true;
            }
            return " Game Over ! \r Score: " + score;
        }
        else return "牛逼! \r" + score;

    }


    public static void restart() {
        N_INITIAL_ASTEROIDS = 5;
        life = 3;
        score = 0;
        level = 1;
        award_threshold = 9;
        Bullet.FLYINGTIME = 5000;

        playerShip.position = new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
        playerShip.velocity = new Vector2D(0, 0);
        playerShip.direction = new Vector2D(0, -1);

        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }
        playerShip.dead = false;
        objects.add(playerShip);
        over = false;
    }

}
