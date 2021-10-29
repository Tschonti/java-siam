package figurines;

import javax.swing.*;
import java.awt.*;

public class Cell {
    protected boolean focused;

    public Cell() {
        focused = false;
    }
    public void setFocused(boolean f) {
        focused = f;
    }

    public JPanel getGUI() {
        JPanel p = new JPanel();
        if (focused) {
            p.setBackground(new Color(128, 145, 180));
        } else {
            p.setBackground(new Color(157, 157, 157));
        }
        return p;
    }
}
