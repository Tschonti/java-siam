package figurines;

import javax.swing.*;
import java.awt.*;

public class Elephant extends Animal {
    public Elephant() {
        super(Direction.UP);
    }

    public JPanel getGUI() {
        JPanel p = new JPanel();
        if (focused) {
            p.setBackground(new Color(35, 117, 14));
        } else {
            p.setBackground(new Color(62, 183, 68));
        }
        return p;
    }
}
