package game2;

import utilities.JEasyFrame;

import java.util.ArrayList;
import java.util.List;

import static game2.Constants.DELAY;

public class Game {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public static Ship ship;
    public static Keys ctrl;
    public static List<GameObject> objects;
    public static int score = 0;
    public static int life = 3;
    public static int bonus = 0;


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

        while (true) {
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    public void update() {
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
    }

    public static void addScore() {
        score ++;
        bonus ++; // additional life will be given for every x scores
        if (bonus > 9){ // 10 scores +1 life
            life++;
            bonus = 0;
        }
//        System.out.println(score + "/" +bouns + "/" + life);
    }


}
