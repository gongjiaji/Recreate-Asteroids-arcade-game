package game2;

import utilities.Vector2D;
import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;
import static game2.Constants.DT;
import static java.lang.Math.random;
import java.awt.Color;
import java.awt.Graphics2D;

public class Asteroid {
    public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;

    private Vector2D p; // position
    private Vector2D v; // velocity

    public Asteroid(Vector2D p, Vector2D v){
        this.p = p;
        this.v = v;
    }

    public static Asteroid makeRandomAsteroid(){
        return new Asteroid(new Vector2D(random() * FRAME_HEIGHT, random() * FRAME_WIDTH) ,
                new Vector2D(random() * MAX_SPEED, random() * MAX_SPEED));
    }

    public void update(){
        p.x += v.x * DT;
        p.y += v.y * DT;
        p.x = (p.x + FRAME_WIDTH) % FRAME_WIDTH;
        p.y = (p.y + FRAME_HEIGHT) % FRAME_HEIGHT;
    }

    public void draw(Graphics2D g){
        g.setColor(Color.red);
        g.fillOval((int)p.x - RADIUS , (int)p.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }
}
