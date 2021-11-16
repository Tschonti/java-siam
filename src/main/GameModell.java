package main;

import cells.Cell;

public interface GameModell {
    void moveOnBoard(Position src, Direction moveDir, Direction facingDir);
    void moveToBench(Position source, Player bench);
    boolean moveFromBench(Position dest, Player bench, Direction newDir);
    void toggleMoveHighlights(Position src, boolean b);
    void toggleCenterHighlights(Position src, boolean b);
    void toggleOuterHighlights(boolean b);
    int calculateStrength(Position src);
    void push(Position src);
    Cell getCell(int x, int y);
}
