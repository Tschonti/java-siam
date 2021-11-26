package panels;

public class TextPanel extends PanelWithText {
    public TextPanel() {
        String text = "In this game, elephants and rhinos race to push one of the rocks off the board. " +
                "Players take turn performing one action on one of their animals. An action can be moving or " +
                "rotating an animal, or pushing with it. An animal always pushes in the direction it's facing, " +
                "but it's only allowed to if the number of stones and animals facing opposite don't outweigh " +
                "the number of animals facing in the direction of the push. All animals start off the board " +
                "and they can only enter the board in the outer cells. Animals can always be moved " +
                "off the board too. The winner is the player whose animal was the closest to the " +
                "first pushed off rock (on any side of the board) and was facing in the direction of the push. Good luck and have fun!";
        ta.setText(text);
        title.setText("Welcome to Siam!");
        title.setFont(hugeFont);

        add(title);
        add(ta);
        setName("text");
    }

    public void setLabelText(String text) {
        title.setText(text);
    }
}
