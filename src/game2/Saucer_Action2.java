package game2;

public class Saucer_Action2 implements Controller {
    private Action action = new Action();
    private static int counter = 0;

    @Override
    public Action action() {
        counter++;
        action.move_x = 2;
        int hp = 40;
        if (Saucer.HP >= 32) {
            action.shoot_s = counter % 50 == 0;
        } else if (Saucer.HP < hp * 0.25) {  // almost dead
            action.shoot_s = counter % 50 == 0;
            if (action.shoot_s) {
                action.move_x = (int) (Math.random() * 1000 - 500);
                action.move_y = 1;
            }
        } else if (Saucer.HP < hp * 0.5) {  // half life
            action.shoot_s = counter % 35 == 0;
            action.move_x = 6;
            action.move_y = 1;
        } else if (Saucer.HP < hp * 0.8) {  // fighting
            action.shoot_s = counter % 40 == 0;
            action.move_x = 3;
        }
        return action;
    }
}
