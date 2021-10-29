package figurines;

import javax.swing.*;
import java.awt.*;

public class Rhino extends Animal {
    public Rhino() {
        super(Direction.DOWN);
    }

    public JPanel getGUI() {
        JPanel p = new JPanel();
        if (focused) {
            p.setBackground(new Color(112, 5, 5));
        } else {
            p.setBackground(new Color(208, 63, 63));
        }
        return p;
    }
}
