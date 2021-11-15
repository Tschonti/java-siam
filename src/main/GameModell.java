package main;

import cells.Cell;

public interface GameModell {
    void moveOnBoard(Position src, Direction moveDir, Direction facingDir);
    void moveToBench(Position source, Player bench);
    boolean moveFromBench(Position dest, Player bench, Direction newDir);
    void toggleHighlights(Position src, boolean b);
    int calculateStrength(Position src);
    void push(Position src);
    Cell getCell(int x, int y);
}
