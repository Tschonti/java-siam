package main;

import cells.Cell;
import panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;

public class GUI extends JFrame {
    private final JPanel boardCont = new JPanel();
    private final JPanel elephantSupplyCont = new JPanel();
    private final JPanel rhinoSupplyCont = new JPanel();
    private final JPanel rightActions = new JPanel();
    private Board board;
    private final SiamController cont;
    private final PlayerOnTurnPanel potp = new PlayerOnTurnPanel();
    private final GameControlPanel gcp;
    static private HashMap<RoundState, String> stateToPanel;
    private final JMenuItem saveGameMenu = new JMenuItem("Save Game");
    private final TextPanel tp = new TextPanel();

    public GUI(Board b, SiamController c) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Siam");
        setSize(1280, 960);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        board = b;
        cont = c;

        // left side
        JPanel left = new JPanel(new BorderLayout(0, 8));
        add(left, BorderLayout.WEST);
        initializeLeftSide(left);

        //right side
        JPanel right = new JPanel();
        add(right, BorderLayout.EAST);
        gcp = new GameControlPanel(cont);
        initializeRightSide(right);

        initializeMenu();

        potp.setVisible(false);
        drawBoard();
        drawSupply(Player.ELEPHANT);
        drawSupply(Player.RHINO);
        setVisible(true);
    }

    public void initializeLeftSide(JPanel leftCont) {
        leftCont.setPreferredSize(new Dimension(630, 960));

        boardCont.setLayout(new GridLayout(5, 5, 5, 5));
        boardCont.setMaximumSize(new Dimension(620, 620));

        elephantSupplyCont.setPreferredSize(new Dimension(620, 130));
        rhinoSupplyCont.setPreferredSize(new Dimension(620, 130));

        elephantSupplyCont.addMouseListener(new ElephantSupplyClickListener());
        rhinoSupplyCont.addMouseListener(new RhinoSupplyClickListener());

        leftCont.add(rhinoSupplyCont, BorderLayout.NORTH);
        leftCont.add(boardCont, BorderLayout.CENTER);
        leftCont.add(elephantSupplyCont, BorderLayout.SOUTH);
    }

    public void initializeRightSide(JPanel rightCont) {
        rightCont.setLayout(new BoxLayout(rightCont, BoxLayout.Y_AXIS));
        rightCont.setPreferredSize(new Dimension(590, 960));

        rightCont.add(gcp);

        CardLayout cl = new CardLayout(20, 20);
        rightActions.setLayout(cl);

        rightCont.add(potp);
        rightCont.add(rightActions);

        PickFigurinePanel pfp = new PickFigurinePanel();
        PickActionPanel pap = new PickActionPanel(cont);
        PickDestinationPanel pdp = new PickDestinationPanel(cont);
        PickDirectionPanel pdirp = new PickDirectionPanel(cont);

        rightActions.add(pfp, pfp.getName());
        rightActions.add(pap, pap.getName());
        rightActions.add(pdp, pdp.getName());
        rightActions.add(pdirp, pdirp.getName());
        rightActions.add(tp, tp.getName());

        cl.show(rightActions, tp.getName());

        stateToPanel = new HashMap<>();
        stateToPanel.put(RoundState.PICK_FIGURINE, pfp.getName());
        stateToPanel.put(RoundState.PICK_ACTION, pap.getName());
        stateToPanel.put(RoundState.PICK_DESTINATION, pdp.getName());
        stateToPanel.put(RoundState.PICK_DIRECTION, pdirp.getName());

        rightActions.setPreferredSize(new Dimension(590, 640));
    }

    public void initializeMenu() {
        JMenuItem newGameMenu = new JMenuItem("New Game");
        newGameMenu.setActionCommand("newGame");
        newGameMenu.addActionListener(gcp);

        JMenuItem loadGameMenu = new JMenuItem("Load Game");
        loadGameMenu.setActionCommand("loadGame");
        loadGameMenu.addActionListener(gcp);

        saveGameMenu.setActionCommand("saveGame");
        saveGameMenu.addActionListener(gcp);
        saveGameMenu.setEnabled(false);

        JMenuItem exitGameMenu = new JMenuItem("Exit Game");
        exitGameMenu.setActionCommand("exitGame");
        exitGameMenu.addActionListener(gcp);

        JMenu menu = new JMenu("Game");
        menu.add(newGameMenu);
        menu.add(loadGameMenu);
        menu.add(saveGameMenu);
        menu.add(exitGameMenu);

        JMenuBar mb = new JMenuBar();
        mb.add(menu);
        setJMenuBar(mb);
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

    public void toggleSupplyHighlight(Player p, boolean highlight, boolean center) {
        Color c = UIManager.getColor ( "Panel.background" );
        if (highlight && center) {
            c = p.activeColor;
        } else if (highlight) {
            c = Cell.getForMoveBackground();
        }
        switch (p) {
            case RHINO:
                rhinoSupplyCont.setBackground(c);
                break;
            case ELEPHANT:
                elephantSupplyCont.setBackground(c);
        }
    }

    public void roundStateSwitch(RoundState rs, Player onTurn) {
        CardLayout cl = (CardLayout) rightActions.getLayout();
        cl.show(rightActions, stateToPanel.get(rs));
        potp.switchPlayer(onTurn);
    }

    public void gameStateSwitch(GameState gs, Player winner) {
        switch (gs) {
            case STARTED:
                potp.setVisible(true);
                gcp.setSaveEnabled(true);
                saveGameMenu.setEnabled(true);
                break;
            case GAME_OVER:
                potp.setVisible(false);
                CardLayout cl = (CardLayout) rightActions.getLayout();
                cl.show(rightActions, tp.getName());
                switch (winner) {
                    case ELEPHANT: tp.setLabelText("Elephant is the winner!"); break;
                    case RHINO: tp.setLabelText("Rhino is the winner!");
                }
                gcp.setSaveEnabled(false);
                saveGameMenu.setEnabled(false);
        }
    }

    public File showFileChooser(String buttonText) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int val = chooser.showDialog(this, buttonText);
        if (val == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    public boolean confirmNewGame() {
        JOptionPane opt = new JOptionPane("Are you sure you want to abandon this game? Any unsaven progress will be lost!",
                JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog jd = opt.createDialog("Abandon Game");
        jd.setVisible(true);
        return !(opt.getValue() == null || opt.getValue().equals(JOptionPane.CANCEL_OPTION));
    }

    public void errorMessage(String msg) {
        JOptionPane opt = new JOptionPane(msg,
                JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog jd = opt.createDialog("Error");
        jd.setVisible(true);
    }

    public void successMessage(String msg) {
        JOptionPane opt = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog jd = opt.createDialog("Success");
        jd.setVisible(true);
    }

    class ElephantSupplyClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            cont.clickedOnAnimal(Position.bench(), Player.ELEPHANT);
        }
    }

    class RhinoSupplyClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            cont.clickedOnAnimal(Position.bench(), Player.RHINO);
        }
    }
}
