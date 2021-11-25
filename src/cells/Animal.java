package cells;

import main.Direction;
import main.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public abstract class Animal extends Cell {
    protected BufferedImage image;

    public Animal(Position p, Direction d) {
        super(p);
        MouseListener[] ml = getMouseListeners();
        for(MouseListener m : ml) {
            removeMouseListener(m);
        }
        dir = d;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        double rotation = 0;
        switch (dir) {
            case UP: rotation = Math.PI; break;
            case RIGHT: rotation = Math.PI/2; break;
            case LEFT: rotation = -Math.PI/2;
        }
        g2.rotate(rotation, image.getWidth() / 2, image.getHeight() / 2);
        g2.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
    }


    public void setDir(Direction d) {
        dir = d;
    }

    public Integer getStrengthForPush(Direction d) {
        if (d.equals(dir)) {
            return 1;
        } else if (dir.x + d.x == 0 && dir.y + d.y == 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
