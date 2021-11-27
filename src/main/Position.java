package main;

import java.io.Serializable;

/**
 * A tábla egy pozícióját reprezentáló osztály.
 */
public class Position implements Serializable {
    private int x;
    private int y;

    public Position(int xp, int yp) {
        x = xp;
        y = yp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(Position p) {
        return x == p.x && y == p.y;
    }

    /**
     * Elmozdítja a pozíciót d irányba
     * @param d mozgás iránya
     */
    public void move(Direction d) {
        x += d.x;
        y += d.y;
    }

    /**
     * Ellenőrzi, hogy a pozíció a táblán kívűl van-e
     * @return Igaz, ha a táblán kívül van
     */
    public boolean isOutOfBounds() {
        return x < 0 || y < 0 || x > 4 || y > 4;
    }

    /**
     * Ellenőrzi, hogy a pozíció a tábla külső 16 mezőjében van-e
     * @return Igaz, ha a tábla külső mezőiben van
     */
    public boolean isInOuterCells() {
        if ((y == 0 || y == 4) && x >= 0 && x <= 4) {
            return true;
        } else return (x == 0 || x == 4) && y >= 0 && y <= 4;
    }

    /**
     * Melyik irányba kell lépni, ha src-ből dest-be akarunk lépni.
     * Csak akokr ad vissza érvényes irányt, ha lépés megengedett a játékban,
     * azaz csak egyet kell lépni a négy irány valamelyikébe.
     * Ha többet vagy nullát kell lépni, akkor null-t ad vissza.
     * Nem ellenőrzi, hogy a pozíciók a táblán belül vannak-e.
     * @param src A lépés forrása
     * @param dest A lépés célja
     * @return Az irány, amibe lépni kell, hogy src-ből dest-be jussunk.
     */
    public static Direction whichWayToStep(Position src, Position dest) {
        int dx = dest.x - src.x;
        int dy = dest.y - src.y;
        if ((dx != 0 && dy != 0) || (dx == 0 && dy == 0) || dx > 1 || dy > 1 || dx < -1 || dy < -1) {
            return null;
        }
        if ( dx == 0) {
            if ( dy == 1) {
                return Direction.DOWN;
            }
            return Direction.UP;
        } else {
            if ( dx == 1 ) {
                return Direction.RIGHT;
            }
            return Direction.LEFT;
        }

    }

    /**
     * Visszaad egy olyan pozíciót, aminek mindkét koordinátája -1,
     * ami az implementációban a cserepad pozícióját jelenti.
     * @return Position(-1, -1)
     */
    public static Position bench() {
        return new Position(-1, -1);
    }
}
