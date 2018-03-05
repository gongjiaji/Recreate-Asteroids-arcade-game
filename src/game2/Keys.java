package game2;

import utilities.SoundManager;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keys extends KeyAdapter implements Controller {
    Action action;
    public static boolean onhold = false;
    public static boolean charge = false;
    public static int hold = 0;
    public static boolean onsheild = false;

    Keys() {
        action = new Action();
    }


    @Override
    public Action action() {
        return action;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                SoundManager.startThrust();
                action.thrust = 1;
                PlayerShip.thrusting = true;
                break;
            case KeyEvent.VK_LEFT:
                action.turn = -1;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 1;
                break;
            case KeyEvent.VK_SPACE:
                hold++;
                onhold = hold > 5;
                break;
            case KeyEvent.VK_R:
                Game.restart();
                break;
            case KeyEvent.VK_DOWN:
                action.teleport = true;
                break;
            case KeyEvent.VK_C:
                action.shield = !action.shield;
                break;
            case KeyEvent.VK_B:
                action.boom = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                action.thrust = 0;
                PlayerShip.thrusting = false;
                SoundManager.stopThrust();
                break;
            case KeyEvent.VK_LEFT:
                action.turn = 0;
                break;
            case KeyEvent.VK_RIGHT:
                action.turn = 0;
                break;
            case KeyEvent.VK_SPACE:  // release space button
                if (hold > 10) {
                    onhold = true;  // charge bullet
                    action.shoot = true;
                } else if (hold < 3) {   // normal bullet
                    onhold = false;
                    action.shoot = true;
                }
                hold = 0;
                break;
            case KeyEvent.VK_DOWN:
                action.teleport = false;
                break;

        }
    }
}
