package game2;

import java.util.Date;

import static game2.Constants.DT;

public class Action {
    public int thrust = 0; //0:0ff  1:on
    public int turn; // -1 left 0 no 1 right
    public boolean shoot;
    public boolean teleport;
    public static boolean shield;
    public boolean boom;
    public int charge = 0;

    // control saucers
    public boolean shoot_s;
    public int move_x;
    public int move_y;
}
