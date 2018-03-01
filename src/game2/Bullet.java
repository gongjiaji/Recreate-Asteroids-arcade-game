package game2;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Date;

public class Bullet extends GameObject {
    public static final int RADIUS = 5;
    public static final int RADIUS_S = 5;
    public Date curTime;
    public long time1; // ini
    public long time2; // cur
    public static int FLYINGTIME_P = 5000; // bullet flying time
    public static int FLYINGTIME_S = 10000; // bullet flying time
    public String tag;
    private Image pb = Constants.PLAYERBULLET;  //10 x 10 px
    private Image sb = Constants.SAUCERBULLET;  //10 x 24 px



    public Bullet(Vector2D position, Vector2D velocity) {
        super(position, velocity, RADIUS);
    }

    @Override
    public void draw(Graphics2D g) {
        if (tag.equals("s")){
            AffineTransform sbTransf = new AffineTransform();
            sbTransf.translate(-sb.getWidth(null) / 2, -sb.getHeight(null) / 4);
            sbTransf.translate(position.x, position.y);
            g.drawImage(sb, sbTransf, null);

        }
        if (tag.equals("p")){
            AffineTransform pbTransf = new AffineTransform();
            pbTransf.translate(-pb.getWidth(null) / 2, -pb.getHeight(null) / 2);
            pbTransf.translate(position.x, position.y);
            g.drawImage(pb, pbTransf, null);
        }
    }

    public void update(){
        super.update();
        curTime = new Date();
        time2 = curTime.getTime();
        for (GameObject b : Game.objects){
            if (b instanceof Bullet){
                ((Bullet) b).time2 = curTime.getTime();
                if (((Bullet) b).tag.equals("p") &&((Bullet) b).time2 - ((Bullet) b).time1 > FLYINGTIME_P){
                    b.dead = true;
                }else if (((Bullet) b).tag.equals("p") &&((Bullet) b).time2 - ((Bullet) b).time1 > FLYINGTIME_P){
                    b.dead = true;
                }
            }
        }
    }

    public void hit(){
        this.dead = true;
    }

    @Override
    public String toString() {
        String s = "";
        if (tag.equals("p")){
            s = "bullet_p";
        }
        if (tag.equals("s")){
            s = "bullet_s";
        }
        return s;
    }
}
