package panels;

import javax.swing.*;
import java.awt.*;

public class PickFigurinePanel extends PanelWithText {
    public PickFigurinePanel() {
        ta.setText("You can choose any of your animals, on or off the board.");
        title.setText(titlePrefix + "Pick the animal you want to perform an action with!" + titlePostfix);

        setName("pickFigurine");
        add(title);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(ta);
    }
}
