package main;

public enum Direction {
    UP (0, -1),
    RIGHT (1, 0),
    DOWN (0, 1),
    LEFT (-1, 0);

    public final int x;
    public final int y;

    Direction(int xp, int yp) {
        x =xp;
        y = yp;
    }
}