package game2;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Date;

import static game2.Constants.*;

public class Saucer extends Ship {
    private static final int RADIUS = 100;
    private Controller ctrl; //provides an Action object in each frame
    public static Bullet bullet = null;
    private Image sau = Constants.SAUCER1;  //200 x 200 px
    private Image sau2 = Constants.SAUCER2;  //200 x 200 px
    private Image sau3 = Constants.SAUCER3;  //200 x 200 px
    private Image sau4 = Constants.SAUCER4;  //200 x 200 px
    public static int HP;
    public static boolean nextLevel = false;
    private static final double STEER_RATE = 2 * Math.PI;
    int i = 0;

    Saucer(Controller ctrl) {
        super(new Vector2D(FRAME_WIDTH / 2, 50), new Vector2D(0, 0), RADIUS);
        this.ctrl = ctrl;
        direction = new Vector2D(0, 1);
        dead = false;
        System.out.println("new saucer created");
    }

    public void update() {
        position.x += ctrl.action().move_x;
        position.y += ctrl.action().move_y;
//        direction = direction.rotate(ctrl.action().turn_s * DT * STEER_RATE * 0.1);
        if (ctrl.action().shoot_s) {
            mkBullet();
            ctrl.action().shoot_s = false;
        }
        super.update();
    }

    @Override
    public Bullet mkBullet() {
        bullet = new Bullet(new Vector2D(position), new Vector2D(velocity));

        i++;
        System.out.println(i);
        bullet.tag = "s";
        bullet.radius = 5;
        bullet.position.addScaled(direction, 120);// avoid immediate collision with playerShip
        bullet.velocity.addScaled(direction, 300);
        Date iniTime = new Date();
        bullet.time1 = iniTime.getTime();
        SoundManager.play(SoundManager.saucer_fire);
        return bullet;
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform sauTransf = new AffineTransform();
        double rot = direction.angle() + Math.PI / 2;
        sauTransf.rotate(rot, position.x, position.y);
        sauTransf.translate(-sau.getWidth(null) / 2, -sau.getHeight(null) / 2);
        sauTransf.translate(position.x, position.y);
        switch (Game.level) {
            case 1:
                g.drawImage(sau, sauTransf, null);
                break;
            case 2:
                g.drawImage(sau2, sauTransf, null);
                break;
            case 3:
                g.drawImage(sau3, sauTransf, null);
                break;
            case 4:
                g.drawImage(sau4, sauTransf, null);
                break;
        }
    }

    @Override
    public boolean canHit(GameObject other) {
        return other.getClass() != Asteroid.class;
    }

    public void hit() {
        SoundManager.hitship();
        HP--;
        if (HP == 0) {
            dead = true;
            Game.bossFight = false; // leave boss fight
            nextLevel = true; // go to next level
        }
    }

    @Override
    public String toString() {
        return "Saucer";
    }
}
