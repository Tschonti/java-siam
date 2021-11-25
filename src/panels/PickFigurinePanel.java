package panels;

import javax.swing.*;

public class PickFigurinePanel extends JPanel {
    public PickFigurinePanel() {
        setName("pickFigurine");
        add(new JLabel("<html><h1>Pick the animal you want <br> to perform an action with!</h1>" +
                "You can choose any of your animals, on or off the board." +
                "</html>"));
    }
}
