package game2;

import utilities.Vector2D;

import java.awt.*;



public class Bullet extends GameObject {
    public static final int RADIUS = 8;

    public Bullet(Vector2D position, Vector2D velocity) {
        super(position, velocity, RADIUS);
    }

    @Override
    public void draw(Graphics2D g) {

    }
}
