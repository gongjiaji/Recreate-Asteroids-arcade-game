package game2;

import utilities.SoundManager;
import utilities.Vector2D;

import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;
import static game2.Constants.DT;
import static java.lang.Math.random;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Date;

public class PlayerShip extends Ship {
    private static final int RADIUS = 25;
    private static final double STEER_RATE = 2 * Math.PI;  // rotation velocity in radians per second
    private static final double MAG_ACC = 200; // acceleration when thrust is applied
    private static final double DRAG = 0.01;  // constant speed loss factor
    private Controller ctrl; //provides an Action object in each frame
    public static boolean thrusting = false;
    public static Bullet bullet = null;
    public static long timeLastShot;
    private Image pship = Constants.PLAYERSHIP;  //50 x 50 px

    public PlayerShip(Controller ctrl) {
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
        if (Game.life > 0) {
            AffineTransform psTransf = new AffineTransform();
            double rot = direction.angle() + Math.PI / 2;
            psTransf.rotate(rot, position.x, position.y);
            psTransf.translate(-pship.getWidth(null) / 2, -pship.getHeight(null) / 2);
            psTransf.translate(position.x, position.y);
            g.drawImage(pship, psTransf, null);
        }
    }

    public Bullet mkBullet() {
        bullet = new Bullet(new Vector2D(position), new Vector2D(velocity));
        bullet.tag = "p";
        bullet.position.addScaled(direction, 35);// avoid immediate collision with playerShip
        bullet.velocity.addScaled(direction, 100);
        Date iniTime = new Date();
        bullet.time1 = iniTime.getTime();
        SoundManager.fire();
        return bullet;
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

    public boolean canHit(GameObject other) {
        return true;
    }

    @Override
    public String toString() {
        return "Player";
    }
}
