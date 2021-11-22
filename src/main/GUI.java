package main;

import panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class GUI extends JFrame {
    private final JPanel boardCont;
    private final JPanel elephantSupplyCont;
    private final JPanel rhinoSupplyCont;
    private final JPanel rightActions;
    private Board board;
    private final PlayerOnTurnPanel potp;
    private final GameControlPanel gcp;
    static private HashMap<RoundState, String> stateToPanel;

    public GUI(Board b, SiamController c) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Siam");
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
        JPanel right = new JPanel(new BorderLayout());
        add(right, BorderLayout.EAST);

        gcp = new GameControlPanel(c);
        right.add(gcp, BorderLayout.WEST);

        CardLayout cl = new CardLayout(20, 20);
        rightActions = new JPanel();
        rightActions.setLayout(cl);

        potp = new PlayerOnTurnPanel();
        right.add(potp, BorderLayout.EAST);
        right.add(rightActions, BorderLayout.SOUTH);

        PickFigurinePanel pfp = new PickFigurinePanel();
        PickActionPanel pap = new PickActionPanel(c);
        PickDestinationPanel pdp = new PickDestinationPanel(c);
        PickDirectionPanel pdirp = new PickDirectionPanel(c);

        rightActions.add(pfp, pfp.getName());
        rightActions.add(pap, pap.getName());
        rightActions.add(pdp, pdp.getName());
        rightActions.add(pdirp, pdirp.getName());

        cl.show(rightActions, pfp.getName());

        stateToPanel = new HashMap<>();
        stateToPanel.put(RoundState.PICK_FIGURINE, pfp.getName());
        stateToPanel.put(RoundState.PICK_ACTION, pap.getName());
        stateToPanel.put(RoundState.PICK_DESTINATION, pdp.getName());
        stateToPanel.put(RoundState.PICK_DIRECTION, pdirp.getName());

        rightActions.setPreferredSize(new Dimension(640, 640));

        potp.setVisible(false);
        rightActions.setVisible(false);
        board = b;
        drawBoard();
        drawSupply(Player.ELEPHANT);
        drawSupply(Player.RHINO);
        setVisible(true);

    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void drawBoard() {
        boardCont.removeAll();
        for(int y = 0; y < 5; y++) {
            for(int x = 0; x <5; x++) {
                boardCont.add(board.getCell(x, y));
            }
        }
        boardCont.updateUI();
    }

    public void drawSupply(Player p) {
        switch (p) {
            case RHINO:
                rhinoSupplyCont.removeAll();
                for (int i = 0; i < board.getSupplySize(p); i++) {
                    rhinoSupplyCont.add(board.getSupplyCell(p, i));
                }
                rhinoSupplyCont.updateUI();
                break;
            case ELEPHANT:
                elephantSupplyCont.removeAll();
                for (int i = 0; i < board.getSupplySize(p); i++) {
                    elephantSupplyCont.add(board.getSupplyCell(p, i));
                }
                elephantSupplyCont.updateUI();
                break;
        }
    }

    public void roundStateSwitch(RoundState rs, Player onTurn) {
        CardLayout cl = (CardLayout) rightActions.getLayout();
        cl.show(rightActions, stateToPanel.get(rs));
        potp.switchPlayer(onTurn);
    }

    public void gameStateSwitch(GameState gs, Player winner) {
        switch (gs) {
            case NOT_STARTED:
                gcp.setLabelText("");
            case STARTED:
                potp.setVisible(true);
                rightActions.setVisible(true);
                gcp.setSaveEnabled(true);
                break;
            case GAME_OVER:
                potp.setVisible(false);
                rightActions.setVisible(false);
                switch (winner) {
                    case ELEPHANT: gcp.setLabelText("Elephant is the winner!"); break;
                    case RHINO: gcp.setLabelText("Rhino is the winner!");
                }
                gcp.setSaveEnabled(false);
        }
    }

}
