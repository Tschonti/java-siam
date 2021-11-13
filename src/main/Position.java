package main;

public class Position {
    private int x;
    private int y;

    public Position(int xp, int yp) {
        x = xp;
        y = yp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Position p) {
        return x == p.x && y == p.y;
    }

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
    public static Position bench() {
        return new Position(-1, -1);
    }
}
