package cells;

import main.Board;
import main.Direction;
import main.Position;
import main.SiamController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

public class Cell extends JPanel implements Serializable {
    protected Position pos;
    protected Direction dir;
    static protected SiamController controller;
    static protected Board b;

    public Cell(Position p) {
        pos = p;
        addMouseListener(new CellClickListener());
        setBackground(new Color(157, 157, 157));
        setPreferredSize(new Dimension(128, 128));
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

    public void finisherCell() {
        System.out.println("itt valami nem jó");
    }

    public boolean initiatePush(Direction d) {
        return false;
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

    static public void setBoard(Board board) {
        b = board;
    }

    public Integer getStrengthForPush(Direction d) {
        return null;
    }

    public void reAddListeners() {
        MouseListener[] ml = getMouseListeners();
        for(MouseListener m : ml) {
            removeMouseListener(m);
        }
        addMouseListener(new CellClickListener());
    }

    public class CellClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            controller.clickedOnCell(pos);
        }
    }
}
