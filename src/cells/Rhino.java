package cells;

import main.Direction;
import main.Player;
import main.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Rhino extends Animal {
    public Rhino(Position p) {
        super(p, Direction.DOWN);
        addMouseListener(new RhinoClickListener());
        setBackground(new Color(208, 63, 63));
        dirLabel = new JLabel("ˇ");
        add(dirLabel);
    }

    public void setHighlighted(boolean f) {
        if (f) {
            setBackground(new Color(112, 5, 5));
        } else {
            setBackground(new Color(208, 63, 63));
        }
    }

    class RhinoClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            controller.clickedOnAnimal(pos, Player.RHINO);
        }
    }
}
