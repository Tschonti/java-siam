import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private JPanel left;
    private JPanel right;
    private Board board;

    public GUI(Board b) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout( new BorderLayout());

        left = new JPanel();
        right = new JPanel();

        add(left, BorderLayout.WEST);
        add(right, BorderLayout.EAST);

        left.setLayout(new GridLayout(5, 5, 5, 5));
        board = b;
        drawLeft();
        setVisible(true);

    }

    public void drawLeft() {
        left.removeAll();
        for(int y = 0; y < 5; y++) {
            for(int x = 0; x <5; x++) {
                left.add(board.getCell(x, y).getGUI());
            }
        }
        left.updateUI();
    }
}
