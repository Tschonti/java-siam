package panels;

import main.Player;
import javax.swing.*;

public class PickDestinationPanel extends JPanel {
    private final String name = "pickDestination";

    public PickDestinationPanel(PlayerOnTurnPanel potp) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(potp);
        add(new JLabel("Pick where you want to move your animal!"));
    }

}

