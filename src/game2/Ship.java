package game2;

import utilities.Vector2D;

import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;
import static game2.Constants.DT;
import static game2.Constants.DRAWING_SCALE;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Ship {
    public static final int RADIUS = 8;
    public static final double STEER_RATE = 2 * Math.PI;  // rotation velocity in radians per second
    public static final double MAG_ACC = 200; // acceleration when thrust is applied
    public static final double DRAG = 0.01;  // constant speed loss factor
    public static final Color COLOR = Color.cyan;

    public Vector2D position; // on frame
    public Vector2D velocity; // per sec
    public Vector2D direction;// unit vector with angle. pointing out the way that the ship face to

    private Controller ctrl; //provides an Action object in each frame


    public Ship(Controller ctrl) {
        this.ctrl = ctrl;
        position = new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
        velocity = new Vector2D(0, 0);
        direction = new Vector2D(0, -1);
    }

    public void update() {
        direction = direction.rotate(ctrl.action().turn * DT * STEER_RATE);
        velocity = velocity.addScaled(direction, ctrl.action().thrust * MAG_ACC * DT);
        velocity.mult(1 - DRAG);
        position.addScaled(velocity, DT).wrap(FRAME_WIDTH, FRAME_HEIGHT);
        System.out.println("velocity " + velocity);
        System.out.println("direction " + direction);
        System.out.println("position " + position + "\n");
    }

    public void draw(Graphics2D g) {
        int[] XP = {0, -5, 0, 5};
        int[] YP = {-10, 5, 0, 5};
        int[] XPTHRUST = {0, -4, 0, 4};
        int[] YPTHRUST = {-12, 4, 0, 4};

        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);
        if (ctrl.action().thrust == 1){
            g.setColor(Color.red);
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        g.setTransform(at);
    }
}
