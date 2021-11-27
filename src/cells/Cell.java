package cells;

import main.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

/**
 * A tábla egy üres celláját reprezentáló osztály.
 * JPanel-ből származik, ezért ezek az objktumok kerülnek a táblára.
 * Saját hátterét állítja, és értesíti a controllert, ha rákattintanak.
 */
public class Cell extends JPanel implements Serializable {
    protected Position pos;
    protected Direction dir;
    static protected SiamController controller;
    static protected Board b;
    protected static final Color defaultBackground = new Color(157, 157, 157);
    private static final Color forMoveBackground = new Color(128, 145, 180);
    protected Color activeBackground;

    public Cell(Position p, boolean b) {
        pos = p;
        activeBackground = new Color(68, 111, 173);
        addMouseListener(new CellClickListener());
        if (b) {
            setBackground(defaultBackground);
        } else {
            setOpaque(false);
        }
        setPreferredSize(new Dimension(120, 120));
        setMaximumSize(new Dimension(120, 120));
    }

    /**
     * Lehetséges lépést jelző háttérszín be- vagy kikapcsolása.
     * @param f igaz, ha bekapcsolás
     */
    public void setHighlightedForMove(boolean f) {
        if (f) {
            setBackground(forMoveBackground);
        } else {
            setBackground(defaultBackground);
        }
    }

    /**
     * Kiválasztott mezőt jelző háttérszínbe- vagy kikapcsolása.
    * @param f igaz, ha bekapcsolás
     */
    public void setHighlightedCenter(boolean f) {
        if (f) {
            setBackground(activeBackground);
        } else {
            setBackground(defaultBackground);
        }
    }

    /**
     * Háttérszín be- vagy kikapcsolása (kikapcsolásnál átlátszó lesz)
     * @param b igaz, ha bekapcsolás
     */
    public void toggleBackground(boolean b) {
        if (b) {
            setBackground(defaultBackground);
            setOpaque(true);
        } else {
            setBackground(UIManager.getColor ( "Panel.background" ));
            setOpaque(false);
        }
    }

    /**
     * Kép hozzáadása a cellához. Mivel az üres cellákon nincs kép, itt üres metódus.
     */
    public void setImage() {}

    /**
     * Ennek a metódusnak a meghívása azt jelenti, hogy ezt a cellát tolják.
     * Üres cella esetében a válasz egy automatikus false,
     * azaz ennél a cellánál nem terjedhet tovább a tolás (ezért return),
     * és nem esett le szikla a tábláról (ezért false).
     * @param d A tolás iránya
     * @return igaz, ha leesett egy szikla, azaz vége a játéknak
     */
    public boolean push(Direction d) {
        return false;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public Direction getDir() {
        return dir;
    }

    public Position getPos() {
        return pos;
    }

    public static Color getForMoveBackground() {
        return forMoveBackground;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    static public void setController(SiamController c) {
        controller = c;
    }

    static public void setBoard(Board board) {
        b = board;
    }

    /**
     * Visszaadja, hogy az adott irányú tolásba mennyi erőt ad az adott cella.
     * @param d a tolás iránya
     * @return null, ami egyben azt is jelenti, hogy itt végetér a tolás
     */
    public Integer getStrengthForPush(Direction d) {
        return null;
    }

    /**
     * A cellához tartozó játékos visszaadása.
     * Mivel ez egy üres cella, ezért null.
     * Kizátólag tesztelésre van használva!
     * @return null
     */
    public Player getPlayer() { return null; }

    /**
     * Törli az összes aktív MouseListenert, majd egy újat hozzáad.
     * Új játék betöltése után szükséges.
     */
    public void reAddListeners() {
        MouseListener[] ml = getMouseListeners();
        for(MouseListener m : ml) {
            removeMouseListener(m);
        }
        addMouseListener(new CellClickListener());
    }

    /**
     * Olyan MouseListener osztály, ami értesíti a controllert, hogy erre a cellára kattintottak.
     */
    public class CellClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            controller.clickedOnCell(pos);
        }
    }
}
