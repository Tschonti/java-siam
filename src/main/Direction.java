package main;

/**
 * Egy irányt reprezentáló enumeráció.
 * Négy irányba (fel, jobbra, le, balra) lehet mozogni a táblán,
 * ezért ez a négy lehetséges értéke.
 */
public enum Direction {
    UP (0, -1),
    RIGHT (1, 0),
    DOWN (0, 1),
    LEFT (-1, 0);

    /**
     * Ha ebbe az irányba akarunk mozogni, akkor ennyit kell hozzáadni az x koordinátához.
     */
    public final int x;
    /**
     * Ha ebbe az irányba akarunk mozogni, akkor ennyit kell hozzáadni az y koordinátához.
     */
    public final int y;

    Direction(int xp, int yp) {
        x =xp;
        y = yp;
    }
}
