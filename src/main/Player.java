package main;

import java.awt.*;
import java.io.File;

public enum Player {
    ELEPHANT (new Color(49, 0, 114), new File("images/elephant.png")),
    RHINO (new Color(108, 4, 30), new File("images/rhino.png"));

    public final Color activeColor;
    public final File image;

    Player(Color c, File i) {
        activeColor = c;
        image = i;
    }

    public Player swap() {
        if (this == Player.ELEPHANT) {
            return Player.RHINO;
        }
        return Player.ELEPHANT;
    }
}
