package cells;

import main.Direction;
import main.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Stone extends Cell {
    private BufferedImage image;

    public Stone(Position p) {
        super(p);
        MouseListener[] ml = getMouseListeners();
        for(MouseListener m : ml) {
            removeMouseListener(m);
        }
        setBackground(new Color(105, 105, 105));

        try {
            image = ImageIO.read(new File("images/stone.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
    }

    public boolean initiatePush(Direction d) {
        Position next = new Position(pos.getX() + d.x, pos.getY() + d.y);
        if (Position.isOutOfBounds(next)) {
            b.removeFromBoard(pos);
            return true;
        }
        boolean result =  b.getCell(next).initiatePush(d);
        b.moveOnBoard(pos, d, dir);
        return result;
    }

    public void setHighlightedForMove(boolean f) {}

    public void setHighlightedCenter(boolean f) {}

    public Integer getStrengthForPush(Direction d) {
        return -1;
    }
}
