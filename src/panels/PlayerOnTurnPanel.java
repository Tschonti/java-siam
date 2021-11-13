package panels;
import main.*;

import javax.swing.*;
import java.awt.*;

public class PlayerOnTurnPanel extends JPanel {
    private JLabel label;
    private static final String elephantTurn = "It's Elephant's turn!";
    private static final String rhinoTurn = "It's Rhino's turn!";

    public PlayerOnTurnPanel() {
        label = new JLabel(elephantTurn);
        add(label);
        setPreferredSize(new Dimension(100, 20));
        setBackground(new Color(222, 234, 9));
    }

    public void switchPlayer(Player p) {
        label.setText(p == Player.ELEPHANT ? elephantTurn : rhinoTurn);
    }
}
