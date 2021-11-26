package main;

import cells.*;

import java.io.Serializable;
import java.util.ArrayList;

import static main.Position.isInOuterCells;

public class Board implements Serializable {
    private final Cell[][] board;
    private final ArrayList<Elephant> elephantSupply;
    private final ArrayList<Rhino> rhinoSupply;
    private transient GUI g;

    public Board() {
        board = new Cell[5][5];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                if (y == 2 && x > 0 && x < 4) {
                    board[y][x] = new Stone(new Position(x, y));
                } else {
                    board[y][x] = new Cell(new Position(x, y), true);
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

    public void moveOnBoard(Position src, Direction moveDir, Direction facingDir) {
        Position dest = new Position(src.getX(), src.getY());
        if (moveDir != null) {
            dest = new Position(src.getX() + moveDir.x, src.getY() + moveDir.y);
            Cell temp = board[dest.getY()][dest.getX()];
            board[dest.getY()][dest.getX()] = board[src.getY()][src.getX()];
            board[src.getY()][src.getX()] = temp;

            board[src.getY()][src.getX()].setPos(new Position(src.getX(), src.getY()));
            board[dest.getY()][dest.getX()].setPos(new Position(dest.getX(), dest.getY()));
        }
        board[dest.getY()][dest.getX()].setDir(facingDir);

        if (g != null) {
            g.drawBoard();
        }
    }

    public void moveToBench(Position source, Player bench) {
        board[source.getY()][source.getX()].setPos(new Position(-1, -1));
        board[source.getY()][source.getX()].toggleBackground(false);
        switch (bench) {
            case RHINO:
                rhinoSupply.add((Rhino) board[source.getY()][source.getX()]);
                board[source.getY()][source.getX()].setDir(Direction.DOWN);
                break;
            case ELEPHANT:
                elephantSupply.add((Elephant) board[source.getY()][source.getX()]);
                board[source.getY()][source.getX()].setDir(Direction.UP);
        }
        board[source.getY()][source.getX()] = new Cell(source, true);

        if (g != null) {
            g.drawBoard();
            g.drawSupply(bench);
        }
    }

    public boolean moveFromBench(Position dest, Player bench, Direction newDir) {
        if (!isInOuterCells(dest)) {
            return false;
        }
        Animal toMove = bench == Player.ELEPHANT ? elephantSupply.remove(0) : rhinoSupply.remove(0);
        toMove.toggleBackground(true);
        board[dest.getY()][dest.getX()] = toMove;
        toMove.setPos(dest);
        toMove.setDir(newDir);

        if (g != null) {
            g.drawBoard();
            g.drawSupply(bench);
        }
        return true;
    }

    public void removeFromBoard(Position p) {
        board[p.getY()][p.getX()] = new Cell(p, true);
    }

    public void toggleMoveHighlights(Position src, boolean b) {
        for(int dx = -1; dx < 2; dx += 2) {
            if (src.getX() + dx >= 0 && src.getX() + dx < 5) {
                board[src.getY()][src.getX() + dx].setHighlightedForMove(b);
            }
        }

        for(int dy = -1; dy < 2; dy += 2) {
            if (src.getY() + dy >= 0 && src.getY() + dy < 5) {
                board[src.getY() + dy][src.getX()].setHighlightedForMove(b);
            }
        }
    }

    public void toggleCenterHighlights(Position src, boolean b) {
        board[src.getY()][src.getX()].setHighlightedCenter(b);
    }

    public void toggleOuterHighlights(boolean b) {
        int y = 0;
        int x = 0;
        for(; x < 4; x++) {
            board[y][x].setHighlightedForMove(b);
        }
        for(; y < 4; y++) {
            board[y][x].setHighlightedForMove(b);
        }
        for(; x > 0; x--) {
            board[y][x].setHighlightedForMove(b);
        }
        for(; y > 0; y--) {
            board[y][x].setHighlightedForMove(b);
        }
    }

    public int calculateStrength(Position src) {
        if (src.equals(Position.bench())) {
            return -1;
        }
        Direction pushDir = board[src.getY()][src.getX()].getDir();
        if (Position.isOutOfBounds(new Position(src.getX() + pushDir.x, src.getY() + pushDir.y))) {
            return -1;
        }
        Position moving = new Position(src.getX(), src.getY());
        int sum = 0;
        while (!Position.isOutOfBounds(moving)) {
            Integer str = board[moving.getY()][moving.getX()].getStrengthForPush(pushDir);
            if (str == null) {
                return sum;
            }
            sum += str;
            moving.move(pushDir);
        }
        return sum;
    }

    public void push(Position src) {
        board[src.getY()][src.getX()].initiatePush(board[src.getY()][src.getX()].getDir());
    }

    public void reload() {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                board[y][x].reAddListeners();
                board[y][x].addImage();
            }
        }
        for (Elephant elephant : elephantSupply) {
            elephant.addImage();
        }
        for (Rhino rhino : rhinoSupply) {
            rhino.addImage();
        }
    }

    public Cell getCell(int x, int y) {
        return board[y][x];
    }
    public Cell getCell(Position p) {
        return board[p.getY()][p.getX()];
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
}
