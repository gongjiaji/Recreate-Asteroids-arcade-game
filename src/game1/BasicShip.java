package game1;

import utilities.Vector2D;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;
import static game1.Constants.DT;
import java.awt.*;

public class BasicShip {
    public static final int RADIUS = 8;
    public static final double STEER_RATE = 2 * Math.PI;  // rotation velocity in radians per second
    public static final double MAG_ACC = 200; // acceleration when thrust is applied
    public static final double DRAG = 0.01;  // constant speed loss factor
    public static final Color COLOR = Color.cyan;

    public Vector2D position; // on frame
    public Vector2D velocity; // per sec
    public Vector2D direction;// unit vector with angle. pointing out the way that the ship face to

    private BasicController ctrl; //provides an Action object in each frame


    public BasicShip(BasicController ctrl) {
        this.ctrl = ctrl;
        position = new Vector2D(FRAME_WIDTH / 2,FRAME_HEIGHT / 2);
        velocity = new Vector2D(0,0);
        direction = new Vector2D(1, 0.5*Math.PI);
    }

    public void update(){
//        direction = direction.rotate(ctrl.action().turn * DT * STEER_RATE);
        direction = direction.rotate(1);
        System.out.println("direction " + direction);
//        velocity = velocity.add(direction.mult(MAG_ACC * DT * ctrl.action().thrust));
        velocity = velocity.add(0.01,0.01);

        System.out.println("velocity " + velocity);
//        velocity.subtract(DRAG,DRAG);
        position = position.add(velocity).wrap(FRAME_WIDTH, FRAME_HEIGHT);
        System.out.println("position " + position + "\n");
    }

    public void draw(Graphics2D g){
        g.setColor(COLOR);
        g.fillOval((int)position.x - RADIUS , (int)position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }
}
