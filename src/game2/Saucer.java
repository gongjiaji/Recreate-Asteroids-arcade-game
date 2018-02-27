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
    private int HP = 20;

    Saucer(Controller ctrl) {
        super(new Vector2D(FRAME_WIDTH / 2, 50), new Vector2D(0, 0), RADIUS);
        this.ctrl = ctrl;
        direction = new Vector2D(0, 1);
        dead = false;
        System.out.println("new saucer created");
    }

    public void update() {
        position.x += Math.random() * 200 - 100;
        if (ctrl.action().shoot_s) {
            mkBullet();
            ctrl.action().shoot_s = false;
        }
        super.update();
    }

    @Override
    public Bullet mkBullet() {
        bullet = new Bullet(new Vector2D(position), new Vector2D(velocity));
        bullet.tag = "s";
        bullet.radius = 2;
        bullet.position.addScaled(direction, 105);// avoid immediate collision with playerShip
        bullet.velocity.addScaled(direction, 30);
        Date iniTime = new Date();
        bullet.time1 = iniTime.getTime();
        return bullet;
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform sauTransf = new AffineTransform();
        sauTransf.translate(-sau.getWidth(null) / 2, -sau.getHeight(null) / 2);
        sauTransf.translate(position.x, position.y);
        g.drawImage(sau, sauTransf, null);
    }

    @Override
    public boolean canHit(GameObject other) {
        return other.getClass() != Asteroid.class;
    }

    public void hit() {
        SoundManager.hitship();
        System.out.println("hit");
        HP--;
        if (HP == 0){
            dead = true;
            System.out.println("S Dead");
        }
    }

    @Override
    public String toString() {
        return "Saucer";
    }
}
