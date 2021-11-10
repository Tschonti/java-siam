import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Rhino extends Animal {
    public Rhino(Position p) {
        super(p, Direction.DOWN);
    }

    public JPanel getGUI() {
        JPanel p = new JPanel();
        p.addMouseListener(new RhinoClickListener());
        if (focused) {
            p.setBackground(new Color(112, 5, 5));
        } else {
            p.setBackground(new Color(208, 63, 63));
        }
        return p;
    }

    class RhinoClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            controller.clickedOnAnimal(pos, Player.RHINO);
        }
    }
}
