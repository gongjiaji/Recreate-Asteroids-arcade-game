package game1;

import utilities.JEasyFrame;


public class BasicGame {
    public static void main(String[] args) {
        BasicGame game = new BasicGame();
        BasicView view = new BasicView(game);
        new JEasyFrame(view, "Basic game");
    }
}
