package game2;

import utilities.SoundManager;
import utilities.Vector2D;

import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;
import static java.lang.Math.random;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Asteroid extends GameObject {
    private static final int RADIUS = 26;
    private static final int RADIUS1 = 20;
    private static final int RADIUS2 = 14;
    public static List<Asteroid> splits = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    private static final double MAX_SPEED = 100;
    private Image as = Constants.ASTEROID1;  // 26 x 26
    private static int angle = 0;



    private Asteroid(Vector2D position, Vector2D velocity) {
        super(position, velocity, RADIUS);
    }

    public static Asteroid makeRandomAsteroid() {
        Vector2D p = new Vector2D(random() * FRAME_HEIGHT, random() * FRAME_WIDTH);
        Vector2D v = new Vector2D(random() * MAX_SPEED, random() * MAX_SPEED);

        if (p.dist(new Vector2D(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT)) < 50) {
            System.out.println(p.toString());
            p = new Vector2D(random() * FRAME_HEIGHT, random() * FRAME_WIDTH);
        }
        return new Asteroid(p, v);
    }

    public void update() {
        super.update();
    }

    public void draw(Graphics2D g) {
        AffineTransform asTransf = new AffineTransform();
        asTransf.translate(-as.getWidth(null) / 2, -as.getHeight(null) / 2);
        asTransf.translate(position.x, position.y);
        if (radius == RADIUS) {
            asTransf.rotate(angle += 0.01);
            asTransf.scale(2, 2);
        } else if (radius == RADIUS1) {
            asTransf.rotate(angle += 0.01);

            asTransf.scale(1.5, 1.5);
        } else if (radius == RADIUS2) {
            asTransf.rotate(angle += 0.01);

            asTransf.scale(1, 1);
        }
        g.drawImage(as, asTransf, null);
    }

    public void hit() {

        // sound
        switch ((int) this.radius) {
            case RADIUS:
                SoundManager.play(SoundManager.bangLarge);
                break;
            case RADIUS1:
                SoundManager.play(SoundManager.bangMedium);
                break;
            case RADIUS2:
                SoundManager.play(SoundManager.bangSmall);
                break;
        }

        this.dead = true;
        if (this.radius > RADIUS2) {
            split();
        }
        Game.addScore();
        loot();
    }

    private void split() {
        Asteroid split1 = new Asteroid(new Vector2D(this.position), new Vector2D(this.velocity).rotate(random() * -90));
        Asteroid split2 = new Asteroid(new Vector2D(this.position), new Vector2D(this.velocity).rotate(random() * -90));
        split1.radius = this.radius - 6;
        split2.radius = this.radius - 6;
        splits.add(split1);
        splits.add(split2);
    }

    @Override
    public String toString() {
        String s = "";
        switch ((int) radius) {
            case RADIUS:
                s = "asteroid_big";
                break;
            case RADIUS1:
                s = "asteroid_med";
                break;
            case RADIUS2:
                s = "asteroid_sma";
                break;
        }
        return s;
    }

    public void loot(){
        if(radius == RADIUS2){
            Item item = new Item(position, velocity, 15);
            int n = (int)(Math.random() * 10);
            if (n == 1 || n == 2 || n == 0 || n == 9){
                item.tag = 1;
            }else if (n == 3 || n == 4 || n == 5){
                item.tag = 2;
            }else if (n == 6 || n == 7 || n == 8){
                item.tag = 3;
            }
            items.add(item);
        }
    }
}
