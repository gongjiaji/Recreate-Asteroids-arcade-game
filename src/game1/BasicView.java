package game1;

import javax.swing.*;
import java.awt.*;

public class BasicView extends JComponent{
    // background color
    public static final Color BG_COLOR = Color.black;

    private BasicGame game;

    public BasicView(BasicGame game){
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.setColor(BG_COLOR);
        System.out.println("Painting");
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}
