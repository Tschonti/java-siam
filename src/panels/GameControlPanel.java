package panels;

import main.SiamController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameControlPanel extends JPanel implements ActionListener {
    private final SiamController cont;
    JButton save;

    public GameControlPanel(SiamController c) {
        cont = c;
        setName("gameControl");

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

        add(newGame);
        add(loadGame);
        add(save);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "newGame": cont.newGame(null); break;
            case "saveGame": cont.saveGame(); break;
            case "loadGame": cont.loadGame(); break;
            case "exitGame": System.exit(0);
        }
    }

    public void setSaveEnabled(boolean b) {
        save.setEnabled(b);
    }
}
