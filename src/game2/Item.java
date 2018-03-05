package game2;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Item extends GameObject {
    public int tag;
    private Image star_img = Constants.STAR;  //30 x 30 px
    private Image blood_img = Constants.BLOOD;  //30 x 30 px
    private Image bullet_img = Constants.BULLET_ITEM;  //30 x 30 px

    public Item(Vector2D position, Vector2D velocity, double radius) {
        super(position, velocity, radius);
    }

    @Override
    public void draw(Graphics2D g) {
        AffineTransform itemTrans = new AffineTransform();
        switch (tag){
            case 1: // star
                itemTrans.translate(-star_img.getWidth(null) / 2, -star_img.getHeight(null) / 2);
                itemTrans.translate(position.x, position.y);
                g.drawImage(star_img, itemTrans, null);
                break;
            case 2: // blood
                itemTrans.translate(-blood_img.getWidth(null) / 2, -blood_img.getHeight(null) / 2);
                itemTrans.translate(position.x, position.y);
                g.drawImage(blood_img, itemTrans, null);
                break;
            case 3: // bullet
                itemTrans.translate(-bullet_img.getWidth(null) / 2, -bullet_img.getHeight(null) / 2);
                itemTrans.translate(position.x, position.y);
                g.drawImage(bullet_img, itemTrans, null);
                break;
        }

    }

    @Override
    public String toString() {
        String s = "";
        switch (tag){
            case 1: // star
                s = "star";
                break;
            case 2: // blood
                s = "blood";
                break;
            case 3: // bullet
                s = "shield_item";
                break;
        }
        return s;
    }

    @Override
    public void hit() {
        dead = true;
        switch (tag){
            case 1:
                Game.score += 10;
                break;
            case 2:
                Game.life += 1;
                break;
            case 3:
                Shield.sp += 50;
        }
    }
}
