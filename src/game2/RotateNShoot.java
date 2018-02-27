package game2;

public class RotateNShoot implements Controller {
    private Action action = new Action();

    @Override
    public Action action() {
        action.shoot_s = true;
        action.turn_s = 0;
        return action;
    }
}
