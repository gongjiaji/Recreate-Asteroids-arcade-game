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
    private int boomRadius = 40;
    private static final double STEER_RATE = 2 * Math.PI;  // rotation velocity in radians per second
    private static final double MAG_ACC = 200; // acceleration when thrust is applied
    private static final double DRAG = 0.01;  // constant speed loss factor
    private Controller ctrl; //provides an Action object in each frame
    public static boolean thrusting = false;
    public static Bullet bullet = null;
    public static Shield shield = null;
    public static long timeLastShot;
    private Image pship = Constants.PLAYERSHIP;  //50 x 50 px
    public static Vector2D p = new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
    public static Vector2D direction;
    public static Vector2D velocity = new Vector2D(0, 0);
    public static int booms = 3; // the amount of booms holding
    private final double SHIELD_REGEN = 0.5;

    public PlayerShip(Controller ctrl) {
        super(p, velocity, RADIUS);
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
        if (ctrl.action().shield) {
            Game.shield.dead = false;
            Shield.sp--;
            if (Shield.sp <= 0) {
                Shield.sp = -1;
                Action.shield = false;
            }
        } else if (!ctrl.action().shield) {
            Game.shield.dead = true;
            Shield.sp += SHIELD_REGEN;   //regeneration rate
            if (Shield.sp >= 100) {
                Shield.sp = 100;
            }
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
            // charge bar
            if (Keys.hold > 0) {
                int w = Keys.hold * 5;
                w = w > 50 ? 50 : w;
                if (w > 10) {
                    g.setColor(Color.WHITE);
                    g.fillRect((int) (position.x - pship.getWidth(null) / 2), (int) (position.y + pship.getHeight(null) / 2 + 5), 52, 5);
                    g.setColor(Color.RED);
                    g.fillRect((int) (position.x - pship.getWidth(null) / 2 + 1), (int) (position.y + pship.getHeight(null) / 2 + 6), w, 3);
                }
            }
            // shield bar
            if (Shield.sp > 0) {
                double s = Shield.sp > 100 ? 100 : Shield.sp;
                g.setColor(Color.WHITE);
                g.fillRect((int) (position.x - pship.getWidth(null) / 2), (int) (position.y + pship.getHeight(null) / 2 + 15), 52, 5);
                g.setColor(Color.GREEN);
                g.fillRect((int) (position.x - pship.getWidth(null) / 2 + 1), (int) (position.y + pship.getHeight(null) / 2 + 16), (int) (s / 2), 3);
            }

            // boom
            if (booms > 0 && Game.bossFight) {
                if (ctrl.action().boom) {
                    g.setColor(Color.MAGENTA);
                    g.drawOval((int) (position.x - boomRadius), (int) (position.y - boomRadius), 2 * boomRadius, 2 * boomRadius);
                    boomRadius += 40;
                    if (boomRadius > 600) {
                        boomRadius = 0;
                        booms--;
                        ctrl.action().boom = false;
                    }
                }
            }
        }

        // boom bar
        if (Game.bossFight) {
            g.setColor(Color.YELLOW);
            switch (booms) {
                case 3:
                    g.fillRect((int) (position.x - pship.getWidth(null) / 2 + 37), (int) (position.y + pship.getHeight(null) / 2 + 25), 15, 5);
                case 2:
                    g.fillRect((int) (position.x - pship.getWidth(null) / 2 + 18.5), (int) (position.y + pship.getHeight(null) / 2 + 25), 15, 5);
                case 1:
                    g.fillRect((int) (position.x - pship.getWidth(null) / 2), (int) (position.y + pship.getHeight(null) / 2 + 25), 15, 5);
                    break;
            }
        }

    }

    public Bullet mkBullet() {
        bullet = new Bullet(new Vector2D(position), new Vector2D(velocity));
        if (Keys.onhold) {
            bullet.tag = "pc";
            bullet.position.addScaled(direction, 35);// avoid immediate collision with playerShip
            bullet.velocity.addScaled(direction, 1000);
        } else {
            bullet.tag = "p";
            bullet.position.addScaled(direction, 35);// avoid immediate collision with playerShip
            bullet.velocity.addScaled(direction, 100);
        }

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
