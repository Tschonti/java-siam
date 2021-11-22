package panels;

import main.Player;
import main.SiamController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameControlPanel extends JPanel implements ActionListener {
    private final SiamController cont;
    JButton save;
    JLabel label;

    public GameControlPanel(SiamController c) {
        cont = c;
        setName("gameControl");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel buttons = new JPanel();
        label = new JLabel();
        setBackground(new Color(8, 157, 112));
        add(buttons);
        add(label);

        JButton newGame = new JButton("New Game");
        newGame.setActionCommand("newGame");
        newGame.addActionListener(this);

        save = new JButton("Save Game");
        save.setActionCommand("saveGame");
        save.addActionListener(this);
        save.setEnabled(false);

        JButton loadGame = new JButton("Load Game");
        loadGame.setActionCommand("loadGame");
        loadGame.addActionListener(this);

        buttons.add(newGame);
        buttons.add(loadGame);
        buttons.add(save);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "newGame": cont.newGame(); break;
            case "saveGame": cont.saveGame(); break;
            case "loadGame": cont.loadGame();
        }
    }

    public void setLabelText(String text) {
        label.setText(text);

    }

    public void setSaveEnabled(boolean b) {
        save.setEnabled(b);
    }
}
