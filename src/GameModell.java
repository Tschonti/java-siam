import figurines.Cell;
import figurines.Direction;

public interface GameModell {
    void moveOnBoard(Position src, Direction d);
    void moveToBench(Position source);
    void moveFromBench(Position dest);
    void showMoveOptions(Position src);
    int calculateStrength(Position src);
    void push(Position src);
    Cell getCell(int x, int y);
}
