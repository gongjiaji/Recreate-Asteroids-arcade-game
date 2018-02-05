package game2;

import utilities.JEasyFrame;
import java.util.ArrayList;
import java.util.List;
import static game2.Constants.DELAY;

public class Game {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public static Ship ship;
    public static Keys ctrl;
    public List<GameObject> objects;

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

        new JEasyFrame(view, "Basic game").addKeyListener(game.ctrl);

        while(true){
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
        }
    }

    public void update(){
        List<GameObject> alive = new ArrayList<>();
        for (GameObject o : objects){
            o.update();
            if (!o.dead){
                alive.add(o);
            }
            if (Ship.bullet == null){
                alive.add(Ship.bullet);
                Ship.bullet = null;
            }
            objects.clear();
            objects.addAll(alive);
        }
    }
}
