package panels;

import main.RoundState;
import main.SiamController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PickActionPanel extends JPanel implements ActionListener {
    private final SiamController cont;

    public PickActionPanel(SiamController c) {
        cont = c;
        setName("pickAction");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Pick which action you want to perform next!"));
        setBackground(new Color(206, 97, 1));

        JButton moveRotate = new JButton("Move and/or rotate");
        JButton push = new JButton("Push");
        JButton back = new JButton("Cancel");

        moveRotate.setActionCommand("moveRotate");
        push.setActionCommand("push");
        back.setActionCommand("back");

        moveRotate.addActionListener(this);
        push.addActionListener(this);
        back.addActionListener(this);

        add(moveRotate);
        add(push);
        add(back);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("moveRotate")) {
            cont.clickedOnMove();
        } else if (ae.getActionCommand().equals("push")) {
            cont.clickedOnPush();
        } else if (ae.getActionCommand().equals("back")) {
            cont.clickedOnCancel(RoundState.PICK_ACTION);
        }
    }
}

