package game2;


import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class View extends JComponent {
    // background color
    public static final Color BG_COLOR = Color.black;
    private Game game;
    Image im = Constants.MILKYWAY1;
    AffineTransform bgTransf;

    public View(Game game) {
        this.game = game;
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretch_x = (imWidth > Constants.FRAME_WIDTH ? 1 : Constants.FRAME_WIDTH / imWidth);
        double stretch_y = (imHeight > Constants.FRAME_HEIGHT ? 1 : Constants.FRAME_HEIGHT / imHeight);
        bgTransf = new AffineTransform();
        bgTransf.scale(stretch_x, stretch_y);
    }

    @Override
    public void paintComponent(Graphics g0) {
        if (Game.over) { // game over
            Graphics2D g = (Graphics2D) g0;
            g.setColor(BG_COLOR);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.white);

            g.setColor(Color.red);
            g.setFont(new Font("Monaca", Font.BOLD, 50));
            g.drawString(Game.over(), 190, 100);

            g.setColor(Color.white);
            g.setFont(new Font("Monaca", Font.ITALIC, 30));
            g.drawString("Press 'R' to restart", 300, 650);
            g.drawString("Top 10", 400, 160);
            int y = 180;
            for (String line : HighScore.top10().split("\n"))
                g.drawString(line, 350, y += g.getFontMetrics().getHeight());

        } else { // alive
            Graphics2D g = (Graphics2D) g0;
            g.drawImage(im, bgTransf, null);
            g.setColor(Color.white);
            g.drawString("Score: " + Game.score, 20, 20);
            // rare situation
            if (Game.life < 0) {
                Game.life = 0;
            }
            g.drawString("Life: " + Game.life, 100, 20);
            g.drawString("Level: " + Game.level, 180, 20);
            g.drawString("Charge bullet: Hold SPACE", 380, 20);
            g.drawString("Shield: C", 560, 20);
            g.drawString("Teleport: ↓", 650, 20);
            g.drawString("BOMB: B", 750, 20);

            if (Game.bossFight) { // DRAW HP BAR
                g.setColor(Color.WHITE);
                g.drawString("Boss Life: " + Saucer.HP, 260, 20);
                g.drawRect(100, 700, 800, 20);
                g.setColor(Color.red);
                switch (Game.level) {
                    case 1:
                        g.fillRect(100, 700, Saucer.HP * 40, 20);
                        break;
                    case 2:
                        g.fillRect(100, 700, Saucer.HP * 20, 20);
                        break;
                    case 3:
                        g.fillRect(100, 700, Saucer.HP * 16, 20);
                        break;
                    case 4:
                        g.fillRect(100, 700, Saucer.HP * 10, 20);
                        break;

                }
            }

            synchronized (Game.class) {
                for (GameObject object : Game.objects) {
                    object.draw(g);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}
