package main;

import javax.swing.*;
import java.awt.*;

public class Stone extends Cell {
    public Stone(Position p) {
        super(p);
    }

    public JPanel getGUI() {
        JPanel p = new JPanel();
        p.setBackground(new Color(105, 105, 105));
        return p;
    }

    public int getStrengthForPush(Direction d) {
        return -1;
    }
}
