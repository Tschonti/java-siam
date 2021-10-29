package figurines;

import javax.swing.*;
import java.awt.*;

public class Stone extends Cell {
    public JPanel getGUI() {
        JPanel p = new JPanel();
        p.setBackground(new Color(105, 105, 105));
        return p;
    }
}
