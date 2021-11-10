public interface GameModell {
    void moveOnBoard(Position src, Direction d);
    void moveToBench(Position source, Player bench);
    void moveFromBench(Position dest, Player bench);
    void showMoveOptions(Position src);
    int calculateStrength(Position src);
    void push(Position src);
    Cell getCell(int x, int y);
}
