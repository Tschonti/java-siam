package cells;

import main.Direction;
import main.Player;
import main.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Elephant extends Animal {


    public Elephant(Position p) {
        super(p, Direction.UP);
        addMouseListener(new ElephantClickListener());
        setBackground(new Color(62, 183, 68));

        try {
            image = ImageIO.read(new File("images/elephant.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHighlightedForMove(boolean f) {}

    public void setHighlightedCenter(boolean f) {
        if (f) {
            setBackground(new Color(35, 117, 14));
        } else {
            setBackground(new Color(62, 183, 68));
        }
    }

    public boolean initiatePush(Direction d) {
        Position next = new Position(pos.getX() + d.x, pos.getY() + d.y);
        if (Position.isOutOfBounds(next)) {
            b.moveToBench(pos, Player.ELEPHANT);
            return false;
        }
        boolean result =  b.getCell(next).initiatePush(d);
        b.moveOnBoard(pos, d, dir);
        return result;
    }

    public void finisherCell() {
        controller.gameOver(Player.ELEPHANT);
    }

    public void reAddListeners() {
        MouseListener[] ml = getMouseListeners();
        for(MouseListener m : ml) {
            removeMouseListener(m);
        }
        addMouseListener(new ElephantClickListener());
    }

    class ElephantClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            controller.clickedOnAnimal(pos, Player.ELEPHANT);
        }
    }
}
