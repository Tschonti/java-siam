package main;

import java.awt.*;

/**
 * A két játékos reprezentáló enumeráció
 */
public enum Player {
    ELEPHANT (new Color(49, 0, 114), "/images/elephant.png"),
    RHINO (new Color(108, 4, 30), "/images/rhino.png");

    public final Color activeColor;     // A játékoshoz tartozó szín.
    public final String imagePath;            // Az a képfájl, ami a játékos cellájára kerül.

    Player(Color c, String i) {
        activeColor = c;
        imagePath = i;
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
