package main;

import cells.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A játék tábláját és két cserepadját reprezentáló osztály.
 * Felelősségei közé tartozik állatok cserepadról táblára mozgatása és vissza,
 * cellák cseréje a táblán, cellák értesítése háttérszín változtatásról,
 * tolás érvényességének ellenőrzése valamint tolás kezdeményezése
 */
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

    /**
     * src pozícióban álló mezőt megcseréli azzal a mezővel,
     * ami moveDir irányban van, majd az eredetileg src
     * pozícióban álló cella irányát facingDir-re állítja.
     * Végül "megkéri" GUI-t, hogy rajzolja újra a táblát.
     * Ha moveDir null, akkor nem mozgat, csak forgat.
     * Feltételezi, hogy src-n egy állat áll,
     * moveDir irányban van mező, és az egy üres cella.
     * @param src A mozgatni kívánt cella pozíciója
     * @param moveDir A mozgatás iránya. Lehet null
     * @param facingDir A mozgatni kívánt cella ebbe az irányba fog nézni mozgatás után.
     */
    public void moveOnBoardAndRotate(Position src, Direction moveDir, Direction facingDir) {
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

    /**
     * Az src pozícióban álló mezőt bench cserepadra rakja.
     * Feltételezi, hogy bench állata van azon a mezőn.
     * src pozícióba egy üres cellát rak.
     * Végül "megkéri" GUI-t, hogy rajzolja újra a táblát és a változtatott cserepadot.
     * @param source A cserepadra küldendő állat pozíciója
     * @param bench az a játékos, akinek az állatát mozgatjuk
     */
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

    /**
     * bench cserepadjáról egy állatot dest pozíjóba rak és newDir irányba forgat.
     * Csak akkor végzi el, ha dest a külső 16 mező egyike.
     * Feltételezi, hogy dest-en egy üres cella van.
     * Végül "megkéri" GUI-t, hogy rajzolja újra a táblát és a változtatott cserepadot.
     * @param dest Az a pozíció, ahova mozgatni akarjuk az állatot.
     * @param bench Az a játékos, akinek az állatát mozgatjuk
     * @param newDir Ebbe az irányba fog nézni az állat
     * @return Hamis, ha dest nem a külső 16 mező egyike, egyébként igaz (sikerült a mozgatás)
     */
    public boolean moveFromBench(Position dest, Player bench, Direction newDir) {
        if (!dest.isInOuterCells()) {
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

    /**
     * p helyére egy üres cellát rak.
     * @param p A törlendő cella pozíciója.
     */
    public void removeFromBoard(Position p) {
        board[p.getY()][p.getX()] = new Cell(p, true);
    }

    /**
     * src négy szomszédos mezőjét értesíti, hogy háttérszínét állítsa be "lehetséges lépés" módba vagy vissza onnan.
     * @param src Annak a mezőnek a pozíciója, akinek a szomszédait színezni szeretnénk.
     * @param b Igaz, ha bekapcsolás
     */
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

    /**
     * src pozícióban lévő mezőjét értesíti, hogy háttérszínét állítsa be "kiválasztott mező" módba vagy vissza onnan.
     * @param src Annak a mezőnek a pozíciója, akinek a szomszédait színezni szeretnénk.
     * @param b Igaz, ha bekapcsolás
     */
    public void toggleCenterHighlights(Position src, boolean b) {
        board[src.getY()][src.getX()].setHighlightedCenter(b);
    }

    /**
     * A külső 16 mezőt értesíti, hogy hogy háttérszínét állítsa be "lehetséges lépés" módba vagy vissza onnan.
     * @param b Igaz, ha bekapcsolás
     */
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

    /**
     * Kiszámolja, hogy ha az src pozícióban álló mező tolna, akkor mennyi lenne az ereje.
     * Ha ez a mező a cserepadon van vagy a külső mezőkben és kifele néz, akkor automatikusan -1, azaz nem fog tudni tolni.
     * Egyébként a tolás irányában minden mezőtől megkérdezzük, hogy ő mennyit adna a tolásba.
     * Ha közben valamelyik null-t ad vissza, akkor ő egy üres cella volt, a tolás nem fog tovább terjedni.
     * @param src A tolás forrása
     * @return A tolás ereje
     */
    public int calculateStrength(Position src) {
        if (src.equals(Position.bench())) {
            return -1;
        }
        Direction pushDir = board[src.getY()][src.getX()].getDir();
        if (new Position(src.getX() + pushDir.x, src.getY() + pushDir.y).isOutOfBounds()) {
            return -1;
        }
        Position moving = new Position(src.getX(), src.getY());
        int sum = 0;
        while (!moving.isOutOfBounds()) {
            Integer str = board[moving.getY()][moving.getX()].getStrengthForPush(pushDir);
            if (str == null) {
                return sum;
            }
            sum += str;
            moving.move(pushDir);
        }
        return sum;
    }

    /**
     * src pozíciójában lévő cellán megkezdi a tolást.
     * @param src A tolás forrása
     */
    public void startPush(Position src) {
        board[src.getY()][src.getX()].push(board[src.getY()][src.getX()].getDir());
    }

    /**
     * Az összes mezőnek szól, hogy állítsa be újra a MouseListenereket és a képeket.
     * Mentett játék betöltése után szükséges.
     */
    public void reload() {
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                board[y][x].reAddListeners();
                board[y][x].setImage();
            }
        }
        for (Elephant elephant : elephantSupply) {
            elephant.setImage();
        }
        for (Rhino rhino : rhinoSupply) {
            rhino.setImage();
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
