package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cell {
    protected boolean focused;
    protected Position pos;
    static protected SiamController controller;

    public Cell(Position p) {
        focused = false;
        pos = p;
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
    public void setFocused(boolean f) {
        focused = f;
    }

    public JPanel getGUI() {
        JPanel p = new JPanel();
        p.addMouseListener(new CellClickListener());
        if (focused) {
            p.setBackground(new Color(128, 145, 180));
        } else {
            p.setBackground(new Color(157, 157, 157));
        }
        p.setPreferredSize(new Dimension(120, 120));
        return p;
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
