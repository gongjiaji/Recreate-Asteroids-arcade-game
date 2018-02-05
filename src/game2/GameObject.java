package game2;

import utilities.Vector2D;

import java.awt.*;

import static game2.Constants.DT;
import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;

abstract public class GameObject {
    public Vector2D position;
    public Vector2D velocity;
    public boolean dead;
    public double radius;

    public GameObject(Vector2D position, Vector2D velocity, double radius){
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
    }

    public void hit(){

    }

    public void update(){
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public abstract void draw(Graphics2D g);
}
