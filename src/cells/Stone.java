package cells;

import main.Direction;
import main.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class Stone extends Cell {
    public Stone(Position p) {
        super(p);
        MouseListener[] ml = getMouseListeners();
        for(MouseListener m : ml) {
            removeMouseListener(m);
        }
        setBackground(new Color(105, 105, 105));
    }

    public boolean initiatePush(Direction d) {
        Position next = new Position(pos.getX() + d.x, pos.getY() + d.y);
        if (Position.isOutOfBounds(next)) {
            b.removeFromBoard(pos);
            return true;
        }
        boolean result =  b.getCell(next).initiatePush(d);
        b.moveOnBoard(pos, d, dir);
        return result;
    }

    public void setHighlightedForMove(boolean f) {}

    public void setHighlightedCenter(boolean f) {}

    public Integer getStrengthForPush(Direction d) {
        return -1;
    }
}
