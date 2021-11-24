package panels;

import javax.swing.*;

public class TextPanel extends JPanel {
    private final JLabel label;

    public TextPanel() {
        label = new JLabel(
                "<html><h1>Welcome to Siam!</h1> " +
                        "In this game, elephants and rhinos race to push one of the rocks off the board.<br>" +
                        "Players take turn performing one action on one of their animals. An action can be moving or <br>" +
                        "rotating an animal, or pushing with it.An animal always pushes in the direction it's facing, <br>" +
                        "but it's only allowed to if the number of stones and animals facing opposite don't outweigh <br>" +
                        "the number of animals facing in the direction of the push.All animals start off the board <br>" +
                        "and they can only enter the board in the outer cells. Animals can always be moved <br>" +
                        "off the board too. The winner is the player whose animal was the closest to the <br>" +
                        "first pushed off rock (on any side of the board) and was facing in the direction of the push." +
                    "</html>"
        );
        add(label);
        setName("text");
    }

    public void setLabelText(String text) {
        label.setText(text);
    }
}
