package cells;

import main.Direction;
import main.Player;
import main.Position;

import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Elephant extends Animal {

    public Elephant(Position p) {
        super(p, Direction.UP);
        activeBackground = Player.ELEPHANT.activeColor;
        addMouseListener(new ElephantClickListener());
        addImage();
    }

    public void addImage() {
        try {
            image = ImageIO.read(Player.ELEPHANT.image);
        } catch (IOException e) {
            e.printStackTrace();
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
        if (result && d == dir) {
            controller.gameOver(Player.ELEPHANT);
        }
        return result;
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
