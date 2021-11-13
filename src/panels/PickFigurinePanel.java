package panels;

import main.Player;
import javax.swing.*;

public class PickFigurinePanel extends JPanel {
    private final String name = "pickFigurine";

    public PickFigurinePanel(PlayerOnTurnPanel potp) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(potp);
        add(new JLabel("Pick the animals you want to perform an action with!"));
    }
}
