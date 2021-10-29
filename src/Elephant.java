import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Elephant extends Animal {
    public Elephant(Position p) {
        super(p, Direction.UP);
    }

    public JPanel getGUI() {
        JPanel p = new JPanel();
        p.addMouseListener(new ElephantClickListener());
        if (focused) {
            p.setBackground(new Color(35, 117, 14));
        } else {
            p.setBackground(new Color(62, 183, 68));
        }
        return p;
    }

    class ElephantClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            controller.clickedOnElephant(pos);
        }
    }
}
