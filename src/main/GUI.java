package main;

import cells.Cell;
import panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;

/**
 * A játék megjelenésért felelős osztály.
 * Ez jeleníti meg a táblát a cserepadokat,
 * a jobb oldali menürendszert és a felső menüsávot.
 */
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

        add(initializeLeftSide(), BorderLayout.WEST);
        gcp = new GameControlPanel(cont);
        add(initializeRightSide(), BorderLayout.EAST);
        initializeMenu();

        potp.setVisible(false);
        drawBoard();
        drawSupply(Player.ELEPHANT);
        drawSupply(Player.RHINO);
        setVisible(true);
    }

    /**
     * A tábla és a cserepadok megjelenését állítja a kezdőállapotba.
     * @return A bal oldal elemeit tartalmazó JPanel
     */
    private JPanel initializeLeftSide() {
        JPanel leftCont = new JPanel(new BorderLayout(0, 8));
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
        return leftCont;
    }

    /**
     * A jobboldali menürendszer megjelenését állítja a kezdőállapotba.
     * @return A jobb oldal elemeit tartalmazó JPanel
     */
    private JPanel initializeRightSide() {
        JPanel rightCont = new JPanel();
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
        return rightCont;
    }

    /**
     * A felső menüsáv megjelenését állítja a kezdőállapotba.
     */
    private void initializeMenu() {
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

    /**
     * A tábla celláit kiveszi boardCOnt-ból, majd újra berakja.
     * Így frissíti a táblát, cellák cseréje így jelenik meg.
     */
    public void drawBoard() {
        boardCont.removeAll();
        for(int y = 0; y < 5; y++) {
            for(int x = 0; x <5; x++) {
                boardCont.add(board.getCell(x, y));
            }
        }
        boardCont.updateUI();
    }

    /**
     * A cserebad celláit kiveszi az egyik SupplyCont-ból, majd újra berakja,
     * így frissíti a cserepadot.
     * @param p Melyik játékos cserepadját kell frisíteni
     */
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

    /**
     * Az egyik cserepad háttérszínét az adott módba kapcsolja, vagy onnan ki.
     * @param p Melyik játékos cserepadját kell frisíteni
     * @param highlight igaz, ha bekapcsolás
     * @param center igaz, ha "kiválasztott mező" módba kapcsolunk, hamis ha "leheséges lépés" módba
     */
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

    /**
     * A controller ezzel a metódussal értesíti a GUI-t, hogy változott a kör állapota.
     * A stateToPanel HashMap segítségével az új állapothoz tartozó panel kerül megjelenítésre a rightActions CardLayout-ban.
     * A potp-n frissíti a soron lévő játékost.
     * @param rs Az új RoundState
     * @param onTurn A soron lévő játékos.
     */
    public void roundStateSwitch(RoundState rs, Player onTurn) {
        CardLayout cl = (CardLayout) rightActions.getLayout();
        cl.show(rightActions, stateToPanel.get(rs));
        potp.switchPlayer(onTurn);
    }

    /**
     * A controller ezzel a metódussal értesíti a GUI-t, hogy változott a játék állapota.
     * Ha most indul, akkor megjeleníti a potp-t és engedélyezi a mentés gombokat.
     * Ha most lett vége, akkor eltünteti a potp-t és letiltja a mentés gombokat, valamint kiírja a győztest.
     * @param gs Az új GameState
     * @param winner A győztes játékos, ha gs == GameOver, egyébként mindegy
     */
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
                    case ELEPHANT: tp.setLabelText(String.format("<html><span style='color: rgb(%d, %d, %d);'>Elephant</span> is the winner!</html>", Player.ELEPHANT.activeColor.getRed(), Player.ELEPHANT.activeColor.getGreen(), Player.ELEPHANT.activeColor.getBlue())); break;
                    case RHINO: tp.setLabelText(String.format("<html><span style='color: rgb(%d, %d, %d);'>Rhino</span> is the winner!</html>", Player.RHINO.activeColor.getRed(), Player.RHINO.activeColor.getGreen(), Player.RHINO.activeColor.getBlue()));
                }
                gcp.setSaveEnabled(false);
                saveGameMenu.setEnabled(false);
        }
    }

    /**
     * Megjelenít egy JFileChoosert a projetk forrásmappájánál.
     * @param buttonText A FileChooser oké gomjának szövege
     * @return null, ha a felhasználó nem választott fájlt, egyébként a választott fájl
     */
    public File showFileChooser(String buttonText) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int val = chooser.showDialog(this, buttonText);
        if (val == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        return null;
    }

    /**
     * Felhívja a felhasználó figyelmét, hogy az adott akcióval elveszti a jelenlegi játék előrehaladását.
     * @return Igaz, ha a játékos továbbra is szeretné elvégezni az akciót
     */
    public boolean confirmNewGame() {
        JOptionPane opt = new JOptionPane("Are you sure you want to abandon this game? Any unsaven progress will be lost!",
                JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog jd = opt.createDialog("Abandon Game");
        jd.setVisible(true);
        return !(opt.getValue() == null || opt.getValue().equals(JOptionPane.CANCEL_OPTION));
    }

    /**
     * Hibaüzenetet mutat a felhasználónak
     * @param msg hibaüzenet
     */
    public void errorMessage(String msg) {
        JOptionPane opt = new JOptionPane(msg,
                JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog jd = opt.createDialog("Error");
        jd.setVisible(true);
    }

    /**
     * Siker üzenetet mutat a felhasználónak
     * @param msg szöveg
     */
    public void successMessage(String msg) {
        JOptionPane opt = new JOptionPane(msg, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog jd = opt.createDialog("Success");
        jd.setVisible(true);
    }

    /**
     * MouseListener osztály, ami értesíti a controllert, ha az elefánt cserepadjára kattintott a felhasználó.
     */
    class ElephantSupplyClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            cont.clickedOnAnimal(Position.bench(), Player.ELEPHANT);
        }
    }

    /**
     * MouseListener osztály, ami értesíti a controllert, ha az orrszarvú cserepadjára kattintott a felhasználó.
     */
    class RhinoSupplyClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            cont.clickedOnAnimal(Position.bench(), Player.RHINO);
        }
    }
}
