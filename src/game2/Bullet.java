package game2;

import utilities.Vector2D;

import java.awt.*;
import java.util.Date;

public class Bullet extends GameObject {
    public static final int RADIUS = 2;
    public Date curTime;
    public long time1; // ini
    public long time2; // cur

    public Bullet(Vector2D position, Vector2D velocity) {
        super(position, velocity, RADIUS);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.fillOval((int)position.x - RADIUS , (int)position.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
    }

    public void update(){
        super.update();
        curTime = new Date();
        time2 = curTime.getTime();
        for (GameObject b : Game.objects){
            if (b instanceof Bullet){
                ((Bullet) b).time2 = curTime.getTime();
                if (((Bullet) b).time2 - ((Bullet) b).time1 > 5000){
                    b.dead = true;
                }
            }
        }
    }

    public void hit(){
//        System.out.println("call bul hit");
        this.dead = true;
    }
}