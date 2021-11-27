package cells;

import main.Direction;
import main.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Sziklát reprezentáló osztály.
 * JPanel-ből származik, ezért ezek az objktumok kerülnek a táblára.
 * Nincsen MouseListener-e, ezért rajta nincs semmi hatása a kattintásnak.
 */
public class Stone extends Cell {
    transient private BufferedImage image;

    public Stone(Position p) {
        super(p, true);
        MouseListener[] ml = getMouseListeners();
        for(MouseListener m : ml) {
            removeMouseListener(m);
        }
        setBackground(new Color(105, 105, 105));
        setImage();
    }

    /**
     * A Component paintComponent metódusának felülírása, ami először meghívja az ős ugyanezen metódusát,
     * majd beállítja a cella képét.
     * @param g Graphics objektum
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
    }

    /**
     * Ennek a metódusnak a meghívása azt jelenti, hogy ezt a cellát tolják.
     * Ha a tolás hatására ez a szikla leesne a tábláról, akkor leveszi magát a tábláról,
     * és igazzal tér vissza, azaz a játéknak vége.
     * Egyébként meghívja a tolás irányában a következő cellán ugyanezt a függvényt,
     * majd elmozdítja magát a tolás irányában, és visszatér a következő mezőtől kapott értékkel.
     * @param d A tolás iránya
     * @return igaz, ha leesett egy szikla (nem feltétlen ez a szikla), azaz vége a játéknak
     */
    public boolean push(Direction d) {
        Position next = new Position(pos.getX() + d.x, pos.getY() + d.y);
        if (next.isOutOfBounds()) {
            b.removeFromBoard(pos);
            return true;
        }
        boolean result =  b.getCell(next).push(d);
        b.moveOnBoardAndRotate(pos, d, dir);
        return result;
    }

    public void setImage() {
        try {
            image = ImageIO.read(new File("images/stone.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lehetséges lépést jelző háttérszín be- vagy kikapcsolása.
     * A szikla háttére sosem változik, ezért üres metódus.
     * @param f igaz, ha bekapcsolás
     */
    public void setHighlightedForMove(boolean f) {}

    /**
     * Kiválasztott mezőt jelző háttérszínbe- vagy kikapcsolása.
     * A szikla háttére sosem változik, ezért üres metódus.
     * @param f igaz, ha bekapcsolás
     */
    public void setHighlightedCenter(boolean f) {}

    /**
     * Visszaadja, hogy az adott irányú tolásba mennyi erőt ad az adott cella.
     * Szikla esetében ez mindig -1
     * @param d a tolás iránya
     * @return -1
     */
    public Integer getStrengthForPush(Direction d) {
        return -1;
    }
}
