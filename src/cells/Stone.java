package cells;

import main.Direction;
import main.Position;

import javax.swing.*;
import java.awt.*;

public class Stone extends Cell {
    public Stone(Position p) {
        super(p);
        setBackground(new Color(105, 105, 105));
    }

    public void setHighlightedForMove(boolean f) {}

    public void setHighlightedCenter(boolean f) {}

    public int getStrengthForPush(Direction d) {
        return -1;
    }
}
