package panels;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {
    private final JLabel label;

    public TextPanel() {
        label = new JLabel(
                "<html><h1>Welcome to Siam!</h1> " +
                    " In this game, elephants and rhinos race to push one of the rocks off the board." +
                    "" +
                    "</html>"
        );
        add(label);
        setName("text");
        setBackground(new Color(72, 121, 74));
    }

    public void setLabelText(String text) {
        label.setText(text);
    }
}
