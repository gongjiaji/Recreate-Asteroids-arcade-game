package game2;

import utilities.SoundManager;
import utilities.Vector2D;

import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;
import static game2.Constants.DT;
import static game2.Constants.DRAWING_SCALE;
import static java.lang.Math.random;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Date;

public class Ship extends GameObject {
    public static final int RADIUS = 8;
    public static final double STEER_RATE = 2 * Math.PI;  // rotation velocity in radians per second
    public static final double MAG_ACC = 200; // acceleration when thrust is applied
    public static final double DRAG = 0.01;  // constant speed loss factor
    public static final Color COLOR = Color.cyan;
    public static Vector2D direction;// unit vector with angle. pointing out the way that the ship face to
    private Controller ctrl; //provides an Action object in each frame
    public boolean thrusting = false;
    public static Bullet bullet = null;
    public static Date iniTime;


    public Ship(Controller ctrl) {
        super(new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2), new Vector2D(0, 0), RADIUS);
        this.ctrl = ctrl;
        direction = new Vector2D(0, -1);
    }

    public void update() {
        direction = direction.rotate(ctrl.action().turn * DT * STEER_RATE);
        velocity = velocity.addScaled(direction, ctrl.action().thrust * MAG_ACC * DT);
        velocity.mult(1 - DRAG);
        super.update();
        if (ctrl.action().shoot) {
            mkBullet();
            ctrl.action().shoot = false;
        }
        if (Game.life == 0) {
            this.dead = true;
            Game.over();
        }
    }

    public void draw(Graphics2D g) {
        int[] XP = {0, -5, 0, 5};
        int[] YP = {-10, 5, 0, 5};
        int[] XPTHRUST = {0, -4, 0, 4};
        int[] YPTHRUST = {-12, 4, 0, 4};

        if (Game.life > 0) {
            AffineTransform at = g.getTransform();
            g.translate(position.x, position.y);
            double rot = direction.angle() + Math.PI / 2;
            g.rotate(rot);
            g.scale(DRAWING_SCALE, DRAWING_SCALE);
            g.setColor(COLOR);
            g.fillPolygon(XP, YP, XP.length);
            if (ctrl.action().thrust == 1) {
                g.setColor(Color.red);
                g.setColor(new Color((int)(255 * Math.random()), (int)(255 * Math.random()),(int)(255 * Math.random())));
                g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
            }
            g.setTransform(at);
        }
    }

    private void mkBullet() {
        bullet = new Bullet(new Vector2D(position), new Vector2D(velocity));
        bullet.position.addScaled(direction, 20);// avoid immediate collision with ship
        bullet.velocity.addScaled(direction, 100);
        iniTime = new Date();
        bullet.time1 = iniTime.getTime();
        SoundManager.fire();
    }

    public void hit() {
        SoundManager.hitship();
        Game.life--;
        position.set(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
        direction = new Vector2D(0, -1);
    }

    public void teleport() {
        position = new Vector2D(random() * FRAME_HEIGHT, random() * FRAME_WIDTH);
        ctrl.action().teleport = false;
    }
}
