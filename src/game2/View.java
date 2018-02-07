package game2;

import javax.swing.*;
import java.awt.*;

public class View extends JComponent {
    // background color
    public static final Color BG_COLOR = Color.black;
    private Game game;


    public View(Game game) {
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);
        g.drawString("Score: " + Game.score, 20, 20);
        // rare situation
        if (Game.life < 0) { Game.life = 0; }
        g.drawString("Life: " + Game.life, 100, 20);
        synchronized (Game.class) {
            for (GameObject object : Game.objects) {
                object.draw(g);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}
