package panels;

import main.RoundState;
import main.SiamController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Az a panel, ami elmagyarázza a felhasználónak, hogy melyik mezőt választhatja a mozgatás céljának.
 */
public class PickDestinationPanel extends PanelWithText implements ActionListener {
    private final SiamController cont;

    public PickDestinationPanel(SiamController c) {
        cont = c;
        setName("pickDestination");
        title.setText(titlePrefix + "Pick where you want to move your animal!" + titlePostfix);

        JButton backButton = new JButton("Cancel action choice");
        backButton.setFont(smallFont);
        backButton.setActionCommand("cancel");
        backButton.addActionListener(this);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        ta.setText("If the chosen animal isn't on the board, you can put it in one of the outer cells." +
                "If it's on the board, it can be moved to one of the free adjacent cells or off the table." +
                "You can leave it in the same cell as well. After choosing the destination, you can rotate the animal.");

        add(title);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(backButton);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(ta);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("cancel")) {
            cont.clickedOnCancel(RoundState.PICK_DESTINATION);
        }
    }
}

