package panels;

public class PickFigurinePanel extends PanelWithText {
    public PickFigurinePanel() {
        ta.setText("You can choose any of your animals, on or off the board.");
        title.setText("Pick the animal you want to perform an action with!");

        setName("pickFigurine");
        add(title);
        add(ta);
    }
}
