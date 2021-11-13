package main;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements GameModell, Serializable {
    private Cell[][] board;
    private ArrayList<Elephant> elephantSupply;
    private ArrayList<Rhino> rhinoSupply;
    private GUI g;

    public Board() {
        board = new Cell[5][5];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (y == 2 && x > 0 && x < 4) {
                    board[y][x] = new Stone(new Position(x, y));
                } else {
                    board[y][x] = new Cell(new Position(x, y));
                }
            }
        }

        elephantSupply = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            elephantSupply.add(new Elephant(new Position(-1, -1)));
        }
        rhinoSupply = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            rhinoSupply.add(new Rhino(new Position(-1, -1)));
        }
    }

    public void setGUI(GUI gui) { g = gui; }


    @Override
    public void moveOnBoard(Position src, Direction d) {
        Cell temp = board[src.getY() + d.y][src.getX() + d.x];
        board[src.getY() + d.y][src.getX() + d.x] = board[src.getY()][src.getX()];
        board[src.getY()][src.getX()] = temp;

        board[src.getY()][src.getX()].setPos(new Position(src.getX(), src.getY()));
        board[src.getY() + d.y][src.getX() + d.x].setPos(new Position(src.getX() + d.x, src.getY() + d.y));

        g.drawBoard();
    }

    @Override
    public void moveToBench(Position source, Player bench) {
        System.out.println("sfds");
        board[source.getY()][source.getX()].setPos(new Position(-1, -1));
        switch (bench) {
            case RHINO: rhinoSupply.add((Rhino) board[source.getY()][source.getX()]); break;
            case ELEPHANT: elephantSupply.add((Elephant) board[source.getY()][source.getX()]);
        }
        board[source.getY()][source.getX()] = new Cell(new Position(source.getX(), source.getY()));
        g.drawBoard();
        g.drawSupply(bench);
    }

    @Override
    public boolean moveFromBench(Position dest, Player bench) {
        if (!isInOuterCells(dest)) {
            return false;
        }
        Animal toMove = bench == Player.ELEPHANT ? elephantSupply.remove(0) : rhinoSupply.remove(0);
        board[dest.getY()][dest.getX()] = toMove;
        toMove.setPos(dest);
        g.drawBoard();
        g.drawSupply(bench);
        return true;
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

    public Cell getSupplyCell(Player p, int idx) {
        switch (p) {
            case RHINO: return rhinoSupply.get(idx);
            case ELEPHANT: return elephantSupply.get(idx);
            default: return null;
        }
    }

    public int getSupplySize(Player p) {
        switch (p) {
            case ELEPHANT: return elephantSupply.size();
            case RHINO: return rhinoSupply.size();
            default: return 0;
        }
    }

    private boolean isInOuterCells(Position p) {
        if ((p.getY() == 0 || p.getY() == 4) && p.getX() >= 0 && p.getX() <= 4) {
            return true;
        } else return (p.getX() == 0 || p.getX() == 4) && p.getY() >= 0 && p.getY() <= 4;
    }
}
