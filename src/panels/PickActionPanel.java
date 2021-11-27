package panels;

import main.RoundState;
import main.SiamController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Az a panel, ahol a felhasználó kiválaszthatja, hogy melyik akciót szeretné végrehajtani a kiválasztott állatán.
 * Értesíti a controllert, hogy melyiket választotta.
 */
public class PickActionPanel extends TextPanel implements ActionListener, ComponentListener {
    private final SiamController cont;
    JButton push;

    public PickActionPanel(SiamController c) {
        cont = c;
        setName("pickAction");
        title.setText(titlePrefix + "Pick which action you want to perform next!" + titlePostfix);
        title.setFont(bigFont);

        JButton moveRotate = new JButton("Move and/or rotate");
        push = new JButton("Push");
        JButton back = new JButton("Cancel animal choice");

        moveRotate.setFont(smallFont);
        push.setFont(smallFont);
        back.setFont(smallFont);

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

        importantButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(importantButtons);
        add(Box.createRigidArea(new Dimension(0,5)));
        add(back);
        add(Box.createRigidArea(new Dimension(0,20)));

        ta.setText("After choosing Move and/or Rotate, you can move the chosen animal. " +
                "If it isn't on the board, you can put it in one of the outer cells. " +
                "If it's on the board, it can be moved to one of the free adjacent cells or off the table. " +
                "You can leave it in the same cell as well. After choosing the destination, you can rotate the animal. " +
                "If you choose Push, your animal will push in the direction it's facing. " +
                "If there's nothing there, it'll just move to the next cell. " +
                "If there are other animals or rocks next to your animal, it'll push each of them by one cell, " +
                "but only if it has the strength to do so. Every animal that will be affected by the push and " +
                "is facing in the direction of the push adds one to the strength of the push, while every animal " +
                "facing opposite and every rock subtracts one. Animals facing sideways add nothing. " +
                "If the overall strength of the push is at least zero, the animals can handle the push.");
        add(ta);
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

    /**
     * Amikor megjelenik a panel, lekéri a controllertől, hogy a jelenleg kiválasztott állatnak a tolás milyen erősségű.
     * Ha legalább nulla, akkor engedélyezi a tolás gombot, egyébként letiltja
     * @param e ComponentEvent objektum
     */
    @Override
    public void componentShown(ComponentEvent e) {
        push.setEnabled(cont.getStrength() >= 0);
    }

    @Override
    public void componentHidden(ComponentEvent e) {}
}

