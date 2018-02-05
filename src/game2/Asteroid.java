package game2;

import utilities.Vector2D;
import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;
import static java.lang.Math.random;
import java.awt.Color;
import java.awt.Graphics2D;

public class Asteroid extends GameObject{
    public static final int RADIUS = 10;
    public static final double MAX_SPEED = 100;

    public Asteroid(Vector2D position, Vector2D velocity){
        super(position, velocity, RADIUS);
    }

    public static Asteroid makeRandomAsteroid(){
        return new Asteroid(new Vector2D(random() * FRAME_HEIGHT, random() * FRAME_WIDTH) ,
                new Vector2D(random() * MAX_SPEED, random() * MAX_SPEED));
    }

    public void update(){
        super.update();
    }

    public void draw(Graphics2D g){
        g.setColor(Color.red);
        g.fillOval((int)position.x - RADIUS , (int)position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }
}
