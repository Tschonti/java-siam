package cells;

import main.Direction;
import main.Position;
import main.SiamController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell extends JPanel {
    protected Position pos;
    protected Direction dir;
    static protected SiamController controller;

    public Cell(Position p) {
        pos = p;
        addMouseListener(new CellClickListener());
        setBackground(new Color(157, 157, 157));
        setPreferredSize(new Dimension(120, 120));
    }

    public void setHighlightedForMove(boolean f) {
        if (f) {
            setBackground(new Color(128, 145, 180));
        } else {
            setBackground(new Color(157, 157, 157));
        }
    }

    public void setHighlightedCenter(boolean f) {
        if (f) {
            setBackground(new Color(68, 112, 203));
        } else {
            setBackground(new Color(157, 157, 157));
        }
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public Direction getDir() {
        return dir;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    static public void setController(SiamController c) {
        controller = c;
    }

    public int getStrengthForPush(Direction d) {
        return 0;
    }

    class CellClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            controller.clickedOnCell(pos);
        }
    }
}
