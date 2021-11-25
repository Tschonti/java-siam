package cells;

import main.Direction;
import main.Player;
import main.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Rhino extends Animal {
    public Rhino(Position p) {
        super(p, Direction.DOWN);
        addMouseListener(new RhinoClickListener());
        setBackground(new Color(208, 63, 63));

        try {
            image = ImageIO.read(new File("images/rhino.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHighlightedForMove(boolean f) {    }

    public void setHighlightedCenter(boolean f) {
        if (f) {
            setBackground(new Color(112, 5, 5));
        } else {
            setBackground(new Color(208, 63, 63));
        }
    }

    public boolean initiatePush(Direction d) {
        Position next = new Position(pos.getX() + d.x, pos.getY() + d.y);
        if (Position.isOutOfBounds(next)) {
            b.moveToBench(pos, Player.RHINO);
            return false;
        }
        boolean result =  b.getCell(next).initiatePush(d);
        b.moveOnBoard(pos, d, dir);
        return result;
    }

    public void finisherCell() {
        controller.gameOver(Player.RHINO);
    }

    public void reAddListeners() {
        MouseListener[] ml = getMouseListeners();
        for(MouseListener m : ml) {
            removeMouseListener(m);
        }
        addMouseListener(new RhinoClickListener());
    }

    class RhinoClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            controller.clickedOnAnimal(pos, Player.RHINO);
        }
    }
}
