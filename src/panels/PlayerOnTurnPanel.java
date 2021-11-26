package panels;
import main.*;

public class PlayerOnTurnPanel extends PanelWithText {
    private static final String elephantTurn = "It's Elephant's turn!";
    private static final String rhinoTurn = "It's Rhino's turn!";

    public PlayerOnTurnPanel() {
        title.setText(elephantTurn);
        add(title);
    }

    public void switchPlayer(Player p) {
        title.setText(p == Player.ELEPHANT ? elephantTurn : rhinoTurn);
    }
}
