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
    public static int N_INITIAL_ASTEROIDS = 5;
    public static Ship ship;
    public static Keys ctrl;
    public static List<GameObject> objects;
    public static int score = 0;
    public static int life = 3;
    public static int bonus = 0;
    public static int award_threshold = 9;
    public static int level = 1;
    public static boolean over = false; // 0 playing 1 dead 2 restart


    public Game() {
        objects = new ArrayList<>(); //store all game objects
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }
        ctrl = new Keys();
        ship = new Ship(ctrl);
        objects.add(ship);
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
            if (Ship.bullet != null) {
                alive.add(Ship.bullet);
                Ship.bullet = null;
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
            }
        }
        if (i == 0 && !over) {
            levelup();
        }

        if (ctrl.action.teleport){
            ship.teleport();
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

    public void levelup() {
        objects.clear();
        level++;
        N_INITIAL_ASTEROIDS += 2;
        award_threshold += 10;
        Bullet.FLYINGTIME += 500;
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }
        objects.add(ship);
    }

    // end of game
    public static String over() {
        over = true;
        for (GameObject o : objects) {
            o.dead = true;
        }
        return " Game Over ! \r Score: " + score;
    }

    public static void restart() {
        N_INITIAL_ASTEROIDS = 5;
        life = 3;
        score = 0;
        level = 1;
        award_threshold = 9;
        Bullet.FLYINGTIME = 5000;

        ship.position = new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
        ship.velocity = new Vector2D(0, 0);
        Ship.direction = new Vector2D(0, -1);

        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }
        ship.dead = false;
        objects.add(ship);
        over = false;
    }

}
