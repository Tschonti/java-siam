package panels;

import main.Direction;
import main.RoundState;
import main.SiamController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PickDirectionPanel extends JPanel implements ActionListener {
    private final SiamController cont;

    public PickDirectionPanel(SiamController c) {
        cont = c;
        setName("pickDirection");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("<html><h1>Pick the direction you want your animal to be facing!</h1></html>"));

        Icon upIcon = new ImageIcon("images/up.png");
        JButton up = new JButton(upIcon);

        Icon rightIcon = new ImageIcon("images/right.png");
        JButton right = new JButton(rightIcon);

        Icon downIcon = new ImageIcon("images/down.png");
        JButton down = new JButton(downIcon);

        Icon leftIcon = new ImageIcon("images/left.png");
        JButton left = new JButton(leftIcon);

        JButton back = new JButton("Cancel");

        up.setActionCommand("up");
        right.setActionCommand("right");
        down.setActionCommand("down");
        left.setActionCommand("left");
        back.setActionCommand("back");

        up.addActionListener(this);
        right.addActionListener(this);
        down.addActionListener(this);
        left.addActionListener(this);
        back.addActionListener(this);

        add(up);

        JPanel sideButtons = new JPanel();
        sideButtons.add(left);
        sideButtons.add(right);

        add(sideButtons);

        add(down);
        add(back);

        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("up")) {
            cont.clickedOnDirection(Direction.UP);
        } else if (ae.getActionCommand().equals("right")) {
            cont.clickedOnDirection(Direction.RIGHT);
        } else if (ae.getActionCommand().equals("down")) {
            cont.clickedOnDirection(Direction.DOWN);
        } else if (ae.getActionCommand().equals("left")) {
            cont.clickedOnDirection(Direction.LEFT);
        } else if (ae.getActionCommand().equals("back")) {
            cont.clickedOnCancel(RoundState.PICK_DIRECTION);
        }
    }
}

