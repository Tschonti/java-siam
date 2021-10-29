import javax.swing.*;

public abstract class Animal extends Cell {
    private Direction dir;

    public Animal(Position p, Direction d) {
        super(p);
        dir = d;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public Direction getDir() {
        return dir;
    }

    public abstract JPanel getGUI();

    public int getStrengthForPush(Direction d) {
        if (d.equals(dir)) {
            return 1;
        } else if (dir.x + d.x == 0 && dir.y + d.y == 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
