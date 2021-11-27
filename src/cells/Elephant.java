package cells;

import main.Direction;
import main.Player;
import main.Position;

import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * A tábla egy elefánt bábuját reprezentáló osztály.
 * JPanel-ből származik, ezért ezek az objktumok kerülnek a táblára.
 * Egyszerre bábu és cella.
 */
public class Elephant extends Animal {

    public Elephant(Position p) {
        super(p, Direction.UP);
        activeBackground = Player.ELEPHANT.activeColor;
        addMouseListener(new ElephantClickListener());
        setImage();
    }

    public void setImage() {
        try {
            image = ImageIO.read(Player.ELEPHANT.image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ennek a metódusnak a meghívása azt jelenti, hogy ezt a cellát tolják.
     * Ha a tolás hatására ez az elefánt leesne a tábláról, akkor az elefántok kispadjára kerül,
     * a táblán kívülre, és hamissal tér vissza, azaz nincs vége a játéknak, nem esett le szikla.
     * Egyébként meghívja a tolás irányában a következő cellán ugyanezt a függvényt,
     * majd elmozdítja magát a tolás irányában, és visszatér a következő mezőtől kapott értékkel.
     * @param d A tolás iránya
     * @return igaz, ha leesett egy szikla, azaz vége a játéknak
     */
    public boolean push(Direction d) {
        Position next = new Position(pos.getX() + d.x, pos.getY() + d.y);
        if (Position.isOutOfBounds(next)) {
            b.moveToBench(pos, Player.ELEPHANT);
            return false;
        }
        boolean result =  b.getCell(next).push(d);
        b.moveOnBoardAndRotate(pos, d, dir);
        if (result && d == dir) {
            controller.gameOver(Player.ELEPHANT);
        }
        return result;
    }

    /**
     * A cellához tartozó játékos visszaadása.
     * Kizátólag tesztelésre van használva!
     * @return Player.ELEPHANT
     */
    public Player getPlayer() { return Player.ELEPHANT; }

    /**
     * Törli az összes aktív MouseListenert, majd egy újat hozzáad.
     * Új játék betöltése után szükséges.
     */
    public void reAddListeners() {
        MouseListener[] ml = getMouseListeners();
        for(MouseListener m : ml) {
            removeMouseListener(m);
        }
        addMouseListener(new ElephantClickListener());
    }

    /**
     * Olyan MouseListener osztály, ami értesíti a controllert, hogy erre az elefántra kattintottak.
     */
    class ElephantClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            controller.clickedOnAnimal(pos, Player.ELEPHANT);
        }
    }
}
