package main;

import panels.PickDestinationPanel;
import panels.PickFigurinePanel;
import panels.PlayerOnTurnPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GUI extends JFrame {
    private JPanel boardCont;
    private JPanel elephantSupplyCont;
    private JPanel rhinoSupplyCont;
    private JPanel right;
    private Board board;
    private PlayerOnTurnPanel potp;
    static private HashMap<RoundState, String> stateToPanel;

    public GUI(Board b, SiamController c) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 1024);
        setLayout( new BorderLayout());

        // left side
        JPanel left = new JPanel(new BorderLayout());
        add(left, BorderLayout.WEST);

        boardCont = new JPanel();
        elephantSupplyCont = new JPanel();
        rhinoSupplyCont = new JPanel();

        boardCont.setLayout(new GridLayout(5, 5, 5, 5));
        boardCont.setPreferredSize(new Dimension(640, 640));

        elephantSupplyCont.setPreferredSize(new Dimension(640, 128));
        rhinoSupplyCont.setPreferredSize(new Dimension(640, 128));

        left.add(rhinoSupplyCont, BorderLayout.NORTH);
        left.add(boardCont, BorderLayout.CENTER);
        left.add(elephantSupplyCont, BorderLayout.SOUTH);

        //right side
        CardLayout cl = new CardLayout();
        right = new JPanel(cl);
        add(right, BorderLayout.EAST);

        potp = new PlayerOnTurnPanel();
        PickDestinationPanel pdp = new PickDestinationPanel(potp);
        PickFigurinePanel pfp = new PickFigurinePanel(potp);

        right.add(pfp, pfp.getName());
        right.add(pdp, pdp.getName());

        cl.show(right, pfp.getName());

        stateToPanel = new HashMap<>();
        stateToPanel.put(RoundState.PICK_DESTINATION, pdp.getName());
        stateToPanel.put(RoundState.PICK_FIGURINE, pfp.getName());
        right.setPreferredSize(new Dimension(640, 640));


        board = b;
        drawBoard();
        drawSupply(Player.ELEPHANT);
        drawSupply(Player.RHINO);
        setVisible(true);

    }

    public void drawBoard() {
        boardCont.removeAll();
        for(int y = 0; y < 5; y++) {
            for(int x = 0; x <5; x++) {
                boardCont.add(board.getCell(x, y).getGUI());
            }
        }
        boardCont.updateUI();
    }

    public void drawSupply(Player p) {
        switch (p) {
            case RHINO:
                rhinoSupplyCont.removeAll();
                for (int i = 0; i < board.getSupplySize(p); i++) {
                    rhinoSupplyCont.add(board.getSupplyCell(p, i).getGUI());
                }
                break;
            case ELEPHANT:
                elephantSupplyCont.removeAll();
                for (int i = 0; i < board.getSupplySize(p); i++) {
                    elephantSupplyCont.add(board.getSupplyCell(p, i).getGUI());
                }
                break;
        }
    }

    public void stateSwitch(GameState gs, RoundState rs, Player onTurn) {
        if (gs == GameState.STARTED) {
            CardLayout cl = (CardLayout) right.getLayout();
            cl.show(right, stateToPanel.get(rs));
            potp.switchPlayer(onTurn);
        }
    }

}
