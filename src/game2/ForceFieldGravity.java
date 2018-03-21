package game2;

import utilities.Vector2D;

import java.awt.*;

public class ForceFieldGravity extends GameObject implements ForceField {
    final static Vector2D gravityForce = new Vector2D(0, 1);
    final static double maxFallSpeed = 10;


    ForceFieldGravity() {
        super(new Vector2D(400, 500), new Vector2D(0, 0), 100);
    }

    @Override
    public void update(Vector2D position, Vector2D velocity, double DT) {
        if (velocity.y < maxFallSpeed) {
            velocity.addScaled(gravityForce, DT);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i < 30; i+=2) {
            g.drawOval(300, 400, 200+i/2, 200+i/2);
        }
    }

    @Override
    public String toString() {
        return "force";
    }

    @Override
    public void hit() {
        this.dead = false;
    }
}
