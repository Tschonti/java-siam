package panels;

import javax.swing.*;
import java.awt.*;

public class PickFigurinePanel extends JPanel {
    public PickFigurinePanel() {
        setName("pickFigurine");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Pick the animals you want to perform an action with!"));
        setBackground(new Color(16, 224, 162));
    }
}
