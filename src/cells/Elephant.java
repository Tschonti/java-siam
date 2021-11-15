package cells;

import main.Direction;
import main.Player;
import main.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Elephant extends Animal {


    public Elephant(Position p) {
        super(p, Direction.UP);
        addMouseListener(new ElephantClickListener());
        setBackground(new Color(62, 183, 68));
        dirLabel = new JLabel("^");
        add(dirLabel);
    }

    public void setHighlightedForMove(boolean f) {}

    public void setHighlightedCenter(boolean f) {
        if (f) {
            setBackground(new Color(35, 117, 14));
        } else {
            setBackground(new Color(62, 183, 68));
        }
    }

    class ElephantClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            controller.clickedOnAnimal(pos, Player.ELEPHANT);
        }
    }
}
