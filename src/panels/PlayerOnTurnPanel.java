package panels;
import main.*;

import javax.swing.*;
import java.awt.*;

public class PlayerOnTurnPanel extends JPanel {
    private JLabel label;
    private static final String elephantTurn = "<html><h1>It's Elephant's turn!</h1></html>";
    private static final String rhinoTurn = "<html><h1>It's Rhino's turn!</h1></html>";

    public PlayerOnTurnPanel() {
        label = new JLabel(elephantTurn);
        add(label);
        setPreferredSize(new Dimension(100, 50));

    }

    public void switchPlayer(Player p) {
        label.setText(p == Player.ELEPHANT ? elephantTurn : rhinoTurn);
    }
}
