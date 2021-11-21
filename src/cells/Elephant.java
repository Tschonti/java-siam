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

    public boolean initiatePush(Direction d) {
        Position next = new Position(pos.getX() + d.x, pos.getY() + d.y);
        if (Position.isOutOfBounds(next)) {
            b.moveToBench(pos, Player.ELEPHANT);
            return false;
        }
        boolean result =  b.getCell(next).initiatePush(d);
        b.moveOnBoard(pos, d, dir);
        return result;
    }

    class ElephantClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            controller.clickedOnAnimal(pos, Player.ELEPHANT);
        }
    }
}
