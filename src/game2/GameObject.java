package game2;

import utilities.Vector2D;

import java.awt.*;

import static game2.Constants.DT;
import static game2.Constants.FRAME_HEIGHT;
import static game2.Constants.FRAME_WIDTH;
import static java.lang.Math.random;

abstract public class GameObject {
    public Vector2D position;
    public Vector2D velocity;
    public Vector2D direction;

    public boolean dead;
    public double radius;

    GameObject(Vector2D position, Vector2D velocity, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
    }

    public void hit() {
    }

    public void update() {
        if (this.toString().equals("pc")) {
            if (Keys.onhold) {
                position = PlayerShip.p.addScaled(new Vector2D(0,-1), 30);
            }
        } else {
            position.addScaled(velocity, DT);
            position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
        }

    }

    public abstract void draw(Graphics2D g);

    public abstract String toString();

    private boolean overlap(GameObject other) {
        return this.position.dist(other.position) < (this.radius + other.radius);
    }

    public void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass() && this.overlap(other)) {
            if (this.toString().equals("bullet_s") && other.toString().equals("asteroid") ||
                    this.toString().equals("Saucer") && other.toString().equals("asteroid") ||
                    this.toString().equals("asteroid") && other.toString().equals("Saucer") ||
                    this.toString().equals("bullet_s") && other.toString().equals("Saucer") ||
                    this.toString().equals("Player") && other.toString().equals("Saucer") ||
                    this.toString().equals("shield") && other.toString().equals("Player") ||
                    this.toString().equals("Player") && other.toString().equals("shield")||
                    this.toString().equals("shield") && other.toString().equals("bullet_p") ||
                    this.toString().equals("bullet_p") && other.toString().equals("shield")||
                    this.toString().equals("shield") && other.toString().equals("bullet_pc") ||
                    this.toString().equals("shield") && other.toString().equals("bullet_pc1") ||
                    this.toString().equals("shield") && other.toString().equals("asteroid")

                    ) {
            } else {
                if (this.toString().equals("asteroid") && other.toString().equals("shield")){
                    this.position.addScaled(PlayerShip.direction, 100);
                }
                other.hit();
            }
        }
    }
}
