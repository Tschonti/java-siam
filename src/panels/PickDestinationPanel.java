package panels;

import main.RoundState;
import main.SiamController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PickDestinationPanel extends JPanel implements ActionListener {
    private final SiamController cont;

    public PickDestinationPanel(SiamController c) {
        cont = c;
        setName("pickDestination");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Pick where you want to move your animal!"));
        setBackground(new Color(171, 16, 224));
        JButton visszaGomb = new JButton("Cancel");
        visszaGomb.setActionCommand("cancel");
        visszaGomb.addActionListener(this);
        add(visszaGomb);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("cancel")) {
            cont.clickedOnCancel(RoundState.PICK_DESTINATION);
        }
    }
}

