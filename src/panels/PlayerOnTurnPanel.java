package panels;
import main.*;

import java.awt.*;

public class PlayerOnTurnPanel extends PanelWithText {
    private static final String elephantTurn = String.format("<html>It's <span style='color: rgb(%d, %d, %d);'>Elephant</span>'s turn!</html>", Player.ELEPHANT.activeColor.getRed(), Player.ELEPHANT.activeColor.getGreen(), Player.ELEPHANT.activeColor.getBlue());
    private static final String rhinoTurn = String.format("<html>It's <span style='color: rgb(%d, %d, %d);'>Rhino</span>'s turn!</html>", Player.RHINO.activeColor.getRed(), Player.RHINO.activeColor.getGreen(), Player.RHINO.activeColor.getBlue());

    public PlayerOnTurnPanel() {
        setLayout(new FlowLayout());
        title.setText(elephantTurn);
        add(title);
    }

    public void switchPlayer(Player p) {
        title.setText(p == Player.ELEPHANT ? elephantTurn : rhinoTurn);
    }
}
