package cells;

import main.Direction;
import main.Player;
import main.Position;

import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Rhino extends Animal {
    public Rhino(Position p) {
        super(p, Direction.DOWN);
        activeBackground = Player.RHINO.activeColor;
        addMouseListener(new RhinoClickListener());
        addImage();
    }

    public void addImage() {
        try {
            image = ImageIO.read(Player.RHINO.image);
        } catch (IOException e) {
            e.printStackTrace();
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
