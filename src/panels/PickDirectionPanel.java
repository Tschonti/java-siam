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
        setLayout(new BorderLayout());
        add(new JLabel("Pick the direction you want your animal to be facing!"));
        setBackground(new Color(119, 11, 227));

        JButton up = new JButton("^");
        JButton right = new JButton(">");
        JButton down = new JButton("ˇ");
        JButton left = new JButton("<");
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

        add(up, BorderLayout.NORTH);
        add(right, BorderLayout.EAST);
        add(down, BorderLayout.SOUTH);
        add(left, BorderLayout.WEST);
        add(back, BorderLayout.CENTER);
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

