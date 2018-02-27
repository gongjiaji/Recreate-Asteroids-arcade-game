package game2;

import utilities.Vector2D;

import java.awt.*;

public abstract class Ship extends GameObject{
    Color color;
    Controller controller;
    long timeLastShot;
    Bullet bullet;
    Vector2D direction;


    public Ship(Vector2D position, Vector2D velocity, double radius) {
        super(position, velocity, radius);
    }


    public void update(){
        super.update();
    }
    public abstract Bullet mkBullet();
    public abstract void draw(Graphics2D g);
    public abstract boolean canHit(GameObject other);

}
