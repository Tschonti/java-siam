package cells;

import main.Direction;
import main.Position;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public abstract class Animal extends Cell {
    transient protected BufferedImage image;

    public Animal(Position p, Direction d) {
        super(p);
        MouseListener[] ml = getMouseListeners();
        for(MouseListener m : ml) {
            removeMouseListener(m);
        }
        dir = d;
    }

    public void setHighlightedForMove(boolean f) {}

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        double rotation = 0;
        switch (dir) {
            case UP: rotation = Math.PI; break;
            case RIGHT: rotation = -Math.PI/2; break;
            case LEFT: rotation = Math.PI/2;
        }
        if (image != null) {
            g2.rotate(rotation, (double) image.getWidth() / 2, (double) image.getHeight() / 2);
            switch (dir) {
                case UP: g2.translate(120*Math.PI, 120*Math.PI); break;
                case RIGHT: g2.translate(120*Math.PI, 0); break;
                case LEFT: g2.translate(0, 120*Math.PI);
            }
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
        }
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
