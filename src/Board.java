import figurines.*;

import java.io.Serializable;

public class Board implements GameModell, Serializable {
    Cell[][] board;
    Cell[] elephantSupply;
    Cell[] rhinoSupply;

    public Board() {
        board = new Cell[5][5];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (y == 2 && x > 0 && x < 4) {
                    board[y][x] = new Stone();
                } else {
                    board[y][x] = new Cell();
                }
            }
        }

        elephantSupply = new Cell[5];
        for (int i = 0; i < 5; i++) {
            elephantSupply[i] = new Elephant();
        }
        rhinoSupply = new Cell[5];
        for (int i = 0; i < 5; i++) {
            rhinoSupply[i] = new Rhino();
        }
    }


    @Override
    public void moveOnBoard(Position src, Direction d) {
        Cell temp = board[src.getY() + d.y][src.getX() + d.x];
        board[src.getY() + d.y][src.getX() + d.x] = board[src.getY()][src.getX()];
        board[src.getY()][src.getX()] = temp;
    }

    @Override
    public void moveToBench(Position source) {

    }

    @Override
    public void moveFromBench(Position dest) {

    }

    @Override
    public void showMoveOptions(Position src) {

    }

    @Override
    public int calculateStrength(Position src) {
        return 0;
    }

    @Override
    public void push(Position src) {

    }

    public Cell getCell(int x, int y) {
        return board[y][x];
    }
}
