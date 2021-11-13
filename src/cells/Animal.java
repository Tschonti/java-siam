package cells;

import main.Direction;
import main.Position;

import javax.swing.*;

public abstract class Animal extends Cell {
    protected JLabel dirLabel;

    public Animal(Position p, Direction d) {
        super(p);
        dir = d;
    }

    public void setDir(Direction d) {
        dir = d;
        switch (d) {
            case UP: dirLabel.setText("^"); break;
            case RIGHT: dirLabel.setText(">"); break;
            case LEFT: dirLabel.setText("<"); break;
            case DOWN: dirLabel.setText("ˇ");
        }
    }

    public int getStrengthForPush(Direction d) {
        if (d.equals(dir)) {
            return 1;
        } else if (dir.x + d.x == 0 && dir.y + d.y == 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
