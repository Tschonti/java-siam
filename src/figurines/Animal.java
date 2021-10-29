package figurines;

import javax.swing.*;

public abstract class Animal extends Cell {
    private Direction dir;

    public Animal(Direction d) {
        super();
        dir = d;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public Direction getDir() {
        return dir;
    }

    public abstract JPanel getGUI();
}
