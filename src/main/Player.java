package main;

import java.awt.*;
import java.io.File;

/**
 * A két játékos reprezentáló enumeráció
 */
public enum Player {
    ELEPHANT (new Color(49, 0, 114), new File("images/elephant.png")),
    RHINO (new Color(108, 4, 30), new File("images/rhino.png"));

    public final Color activeColor;     // A játékoshoz tartozó szín.
    public final File image;            // Az a képfájl, ami a játékos cellájára kerül.

    Player(Color c, File i) {
        activeColor = c;
        image = i;
    }

    /**
     * Egy adott játékoson hívva a másik játékost adja vissza.
     * @return  Player.ELEPHANT ha RHINO-n van hívva, és fordítva
     */
    public Player swap() {
        if (this == Player.ELEPHANT) {
            return Player.RHINO;
        }
        return Player.ELEPHANT;
    }
}
