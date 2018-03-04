package game2;

import utilities.Vector2D;

import java.awt.*;

public class Shield extends GameObject {
    public static double sp = 100; // energy point of shield. 0 = broken

    Shield() {
        super(PlayerShip.p, new Vector2D(0, 0), 36);
        this.dead = true;
        direction = PlayerShip.direction;
    }

    @Override
    public void update() {
        if (sp <= 0) {
            Action.shield = false;
        }
        this.dead = !Action.shield;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color((int) (255 * Math.random()), (int) (255 * Math.random()), 255));
        g.drawOval((int) position.x - 35, (int) position.y - 33, 70, 70);
        g.drawOval((int) position.x - 36, (int) position.y - 34, 72, 72);
    }

    @Override
    public String toString() {
        return "shield";
    }

    @Override
    public void hit() {
        Action.shield = false;
    }
}
