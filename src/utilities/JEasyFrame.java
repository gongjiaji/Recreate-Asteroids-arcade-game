package utilities;
import javax.swing.*;
import java.awt.*;

public class JEasyFrame extends JFrame{
    public Component comp;
    public JEasyFrame(Component comp, String title){
        super(title);
        this.comp = comp;
        getContentPane().add(BorderLayout.CENTER, comp); // add comp to the center of selected content pane
        pack();
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        repaint();
    }
}
