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

    public int getStrengthForPush(Direction d) {
        return -1;
    }
}
