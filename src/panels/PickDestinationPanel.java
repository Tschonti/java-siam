package panels;

import main.RoundState;
import main.SiamController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PickDestinationPanel extends PanelWithText implements ActionListener {
    private final SiamController cont;

    public PickDestinationPanel(SiamController c) {
        cont = c;
        setName("pickDestination");
        title.setText("Pick where you want to move your animal!");

        JButton backButton = new JButton("Cancel");
        backButton.setFont(smallFont);
        backButton.setActionCommand("cancel");
        backButton.addActionListener(this);

        ta.setText("You can move anywhere you want to xd");

        add(title);
        add(ta);
        add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("cancel")) {
            cont.clickedOnCancel(RoundState.PICK_DESTINATION);
        }
    }
}

