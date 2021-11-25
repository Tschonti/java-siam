package panels;

import main.RoundState;
import main.SiamController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class PickActionPanel extends JPanel implements ActionListener, ComponentListener {
    private final SiamController cont;
    JButton push;

    public PickActionPanel(SiamController c) {
        cont = c;
        setName("pickAction");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("<html><h1>Pick which action you want to perform next!</h1></html>"));

        JButton moveRotate = new JButton("Move and/or rotate");
        push = new JButton("Push");
        JButton back = new JButton("Cancel");

        moveRotate.setActionCommand("moveRotate");
        push.setActionCommand("push");
        back.setActionCommand("back");

        moveRotate.addActionListener(this);
        push.addActionListener(this);
        back.addActionListener(this);
        addComponentListener(this);

        JPanel importantButtons = new JPanel();
        importantButtons.add(moveRotate);
        importantButtons.add(push);

        add(importantButtons);
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

    @Override
    public void componentResized(ComponentEvent e) {}

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {
        push.setEnabled(cont.getStrength() >= 0);
    }

    @Override
    public void componentHidden(ComponentEvent e) {}
}

