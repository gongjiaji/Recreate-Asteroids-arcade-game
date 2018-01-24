package game1;

import utilities.JEasyFrame;
import java.util.ArrayList;
import java.util.List;
import static game1.Constants.DELAY;

public class BasicGame {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public static List<BasicAsteroid> asteroids;

    public BasicGame() {
        asteroids = new ArrayList<>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            asteroids.add(BasicAsteroid.makeRandomAsteroid());
        }
    }

    public void update(){
        for (BasicAsteroid a : asteroids){
            a.update();
        }
    }

    public static void main(String[] args) throws Exception {
        BasicGame game = new BasicGame();
        BasicView view = new BasicView(game);
        new JEasyFrame(view, "Basic game");

        while(true){
            game.update();
            view.repaint();
            Thread.sleep(DELAY);
        }
    }
}
