package cells;

import main.Direction;
import main.Position;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

/**
 * A tábla egy állatát (bábuját) reprezentáló absztrakt osztály.
 * JPanel-ből származik, ezért ezek az objktumok kerülnek a táblára.
 * Egyszerre bábu és cella.
 */
public abstract class Animal extends Cell {
    transient protected BufferedImage image;

    public Animal(Position p, Direction d) {
        super(p, false);
        MouseListener[] ml = getMouseListeners();
        for(MouseListener m : ml) {
            removeMouseListener(m);
        }
        dir = d;
    }

    /**
     * Lehetséges lépést jelző háttérszín be- vagy kikapcsolása.
     * Sosem lehet egy másik állat helyére lépni, ezért üres metódus.
     * @param f igaz, ha bekapcsolás
     */
    public void setHighlightedForMove(boolean f) {}

    /**
     * A Component paintComponent metódusának felülírása, ami először meghívja az ős ugyanezen metódusát,
     * majd beállítja a cella képét, az irány alapján elforgatva.
     * @param g Graphics objektum
     */
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
            g2.rotate(rotation, (double) getWidth() / 2, (double) getHeight() / 2);
            g2.drawImage(image, 5, 5, getWidth() - 5, getHeight() - 5, 0, 0, image.getWidth(), image.getHeight(), null);
        }
    }


    public void setDir(Direction d) {
        dir = d;
    }

    /**
     * Visszaadja, hogy az adott irányú tolásba mennyi erőt ad az adott cella.
     * @param d a tolás iránya
     * @return 1, ha a tolás irányába néz az állat, -1 ha azzal szembe. Egyébként 0.
     */
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
