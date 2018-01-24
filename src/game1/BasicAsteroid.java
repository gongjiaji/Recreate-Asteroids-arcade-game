package game1;

import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;
import static game1.Constants.DT;
import static java.lang.Math.random;
import java.awt.Color;
import java.awt.Graphics2D;

public class BasicAsteroid {
    public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;

    private double x, y;
    private double vx, vy;

    public BasicAsteroid(double x, double y, double vx, double vy){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public static BasicAsteroid makeRandomAsteroid(){
        return new BasicAsteroid(random() * FRAME_HEIGHT, random() * FRAME_WIDTH,
                random() * MAX_SPEED, random() * MAX_SPEED);
    }

    public void update(){
        x += vx * DT;
        y += vy * DT;
        x = (x + FRAME_WIDTH) % FRAME_WIDTH;
        y = (y + FRAME_HEIGHT) % FRAME_HEIGHT;
    }

    public void draw(Graphics2D g){
        g.setColor(Color.red);
        g.fillOval((int)x - RADIUS , (int)y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }
}
