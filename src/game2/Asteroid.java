package game2;

import utilities.Vector2D;

import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;
import static java.lang.Math.random;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Asteroid extends GameObject {
    public static final int RADIUS = 20;
    public static final int RADIUS1 = 15;
    public static final int RADIUS2 = 10;
    public static List<Asteroid> splits = new ArrayList<>();
    public static final double MAX_SPEED = 100;


    public Asteroid(Vector2D position, Vector2D velocity) {
        super(position, velocity, RADIUS);
    }

    public static Asteroid makeRandomAsteroid() {
        return new Asteroid(new Vector2D(random() * FRAME_HEIGHT, random() * FRAME_WIDTH),
                new Vector2D(random() * MAX_SPEED, random() * MAX_SPEED));
    }

    public void update() {
        super.update();
    }

    public void draw(Graphics2D g) {
        if (this.radius == RADIUS) {
            g.setColor(Color.red);
            g.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
        } else if (this.radius == RADIUS1) {
            g.setColor(Color.blue);
            g.fillOval((int) (position.x - this.radius), (int) (position.y - this.radius), (int) (2 * this.radius), (int) (2 * this.radius));
        } else if (this.radius == RADIUS2) {
            g.setColor(Color.yellow);
            g.fillOval((int) (position.x - this.radius), (int) (position.y - this.radius), (int) (2 * this.radius), (int) (2 * this.radius));
        }
    }

    public void hit() {
        this.dead = true;
        System.out.println(this.toString());
        if (this.radius > RADIUS2) {
            split();
        }
        Game.addScore();
    }

    public void split() {
        Asteroid split1 = new Asteroid(new Vector2D(this.position), new Vector2D(this.velocity).rotate(random() * -90));
        Asteroid split2 = new Asteroid(new Vector2D(this.position), new Vector2D(this.velocity).rotate(random() * -90));
        split1.radius = this.radius - 5;
        split2.radius = this.radius - 5;
        splits.add(split1);
        splits.add(split2);
    }
}
