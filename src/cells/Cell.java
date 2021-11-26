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
    protected static final Color defaultBackground = new Color(157, 157, 157);
    private static final Color forMoveBackground = new Color(128, 145, 180);
    protected Color activeBackground;

    public Cell(Position p, boolean b) {
        pos = p;
        activeBackground = new Color(68, 111, 173);
        addMouseListener(new CellClickListener());
        if (b) {
            setBackground(defaultBackground);
        } else {
            setOpaque(false);
        }
        setPreferredSize(new Dimension(120, 120));
        setMaximumSize(new Dimension(120, 120));
    }

    public void setHighlightedForMove(boolean f) {
        if (f) {
            setBackground(forMoveBackground);
        } else {
            setBackground(defaultBackground);
        }
    }

    public void setHighlightedCenter(boolean f) {
        if (f) {
            setBackground(activeBackground);
        } else {
            setBackground(defaultBackground);
        }
    }

    public void toggleBackground(boolean b) {
        if (b) {
            setBackground(defaultBackground);
            setOpaque(true);
        } else {
            setBackground(UIManager.getColor ( "Panel.background" ));
            setOpaque(false);
        }
    }

    public void addImage() {}

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

    public static Color getForMoveBackground() {
        return forMoveBackground;
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
            System.out.println(pos.getY()  + " " + pos.getX() + " " + getWidth()+ " " +getHeight());
            controller.clickedOnCell(pos);
        }
    }
}
