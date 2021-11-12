import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JPanel boardCont;
    private JPanel elephantSupplyCont;
    private JPanel rhinoSupplyCont;
    private JPanel right;
    private Board board;

    public GUI(Board b) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 1024);
        setLayout( new BorderLayout());

        JPanel left = new JPanel(new BorderLayout());
        right = new JPanel();
        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);

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
}
