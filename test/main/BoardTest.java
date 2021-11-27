package main;

import cells.Cell;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A Board osztály metódusainak tesztjei
 */
public class BoardTest {
    Board b;

    @Before
    public void setUp() {
        b = new Board();
    }

    /**
     * Az Board osztály ugyanilyen nevű metódusának tesztje.
     * Ellenőrzi, hogy belső mezőbe ne engdjen lépni a cserepadról
     * Ellenőrzi, hogy a cserepadról eltűnik az állat sikeres lépés után,
     * és pozíciója frissül, valamint hogy felkerül a táblára
     */
    @Test
    public void moveFromBench() {
        assertEquals(b.getSupplySize(Player.ELEPHANT), 5);
        assertEquals(b.getSupplySize(Player.RHINO), 5);

        assertFalse(b.moveFromBench(new Position(2, 2), Player.RHINO, Direction.DOWN));
        assertEquals(b.getSupplySize(Player.RHINO), 5);

        assertTrue(b.moveFromBench(new Position(0, 0), Player.RHINO, Direction.DOWN));
        assertEquals(b.getSupplySize(Player.RHINO), 4);
        assertEquals(b.getCell(0, 0).getPlayer(), Player.RHINO);
        assertEquals(b.getCell(0, 0).getDir(), Direction.DOWN);
        assertTrue(b.getCell(0, 0).getPos().equals(new Position(0, 0)));
    }

    /**
     * Az Board osztály ugyanilyen nevű metódusának tesztje.
     * Ellenőrzi, hogy felkerül a mozgatott állat a cserepadra, valamint hogy a tábláról eltűnik.
     */
    @Test
    public void moveToBench() {
        b.moveFromBench(new Position(1, 0), Player.RHINO, Direction.RIGHT);
        b.moveFromBench(new Position(4, 3), Player.ELEPHANT, Direction.LEFT);

        Cell c1 = b.getCell(1, 0);
        Cell c2 = b.getCell(4, 3);

        b.moveToBench(new Position(1, 0), Player.RHINO);
        assertEquals(b.getSupplySize(Player.RHINO), 5);
        assertTrue(c1.getPos().equals(Position.bench()));
        assertEquals(c1.getDir(), Direction.DOWN);
        assertNull(b.getCell(0, 1).getPlayer());

        b.moveToBench(new Position(4, 3), Player.ELEPHANT);
        assertEquals(b.getSupplySize(Player.ELEPHANT), 5);
        assertTrue(c2.getPos().equals(Position.bench()));
        assertEquals(c2.getDir(), Direction.UP);
        assertNull(b.getCell(4, 3).getPlayer());
    }

    /**
     * Az Board osztály ugyanilyen nevű metódusának tesztje.
     * Ellenőrzi, hogy mozgatás után a megflelő helyre került az állat,
     * hogy pozíciója és iránya be lett állítva.
     * Ellenőrzi, hogy az irány akkor is beállítódik, ha a mozgatás iránya null,
     * és hogy a pozíció ilyenkor nem változik.
     */
    @Test
    public void moveOnBoard() {
        Position p1 = new Position(1, 0);
        Position p2 = new Position(4, 3);

        b.moveFromBench(p1, Player.RHINO, Direction.RIGHT);
        b.moveFromBench(p2, Player.ELEPHANT, Direction.LEFT);

        b.moveOnBoardAndRotate(p1, Direction.RIGHT, Direction.DOWN);

        assertNull(b.getCell(p1).getPlayer());
        assertTrue(b.getCell(p1).getPos().equals(p1));
        assertEquals(b.getCell(2, 0).getPlayer(), Player.RHINO);
        assertTrue(b.getCell(2, 0).getPos().equals(new Position(2, 0)));
        assertEquals(b.getCell(2, 0).getDir(), Direction.DOWN);

        b.moveOnBoardAndRotate(p2, null, Direction.UP);

        assertEquals(b.getCell(p2).getPlayer(), Player.ELEPHANT);
        assertTrue(b.getCell(p2).getPos().equals(p2));
        assertEquals(b.getCell(p2).getDir(), Direction.UP);

    }

    /**
     * Az Board osztály ugyanilyen nevű metódusának tesztje.
     * Ellenőrzi, hogy a mező a metódus után üres.
     */
    @Test
    public void removeFromBoard() {
        Position p1 = new Position(0, 1);
        Position p2 = new Position(2, 2);

        b.moveFromBench(p1, Player.RHINO, Direction.RIGHT);

        assertEquals(b.getCell(p1).getPlayer(), Player.RHINO);
        b.removeFromBoard(p1);
        assertNull(b.getCell(p1).getPlayer());

        assertNull(b.getCell(p2).getPlayer());
        b.removeFromBoard(p2);
        assertNull(b.getCell(p2).getPlayer());
    }

    /**
     * Az Board osztály ugyanilyen nevű metódusának tesztje.
     * Sok különböző szituációban ellenőrzi, hogy a metódus a várt eredmény adja.
     */
    @Test
    public void calculateStrength() {
        assertEquals(b.calculateStrength(new Position(-1, -1)), -1);

        Position p1 = new Position(0, 1);
        b.moveFromBench(p1, Player.RHINO, Direction.LEFT);
        assertEquals(b.calculateStrength(p1), -1);

        Position p2 = new Position(1, 0);
        b.moveFromBench(p2, Player.RHINO, Direction.LEFT);
        b.moveOnBoardAndRotate(p2, Direction.DOWN, Direction.LEFT);
        Position p2_2 = new Position(1, 1);
        assertEquals(b.calculateStrength(p2_2), 2);

        b.getCell(1, 1).setDir(Direction.DOWN);
        assertEquals(b.calculateStrength(p2_2), 0);

        Position p3 = new Position(0, 3);
        b.moveFromBench(p3, Player.ELEPHANT, Direction.UP);
        b.moveOnBoardAndRotate(p3, Direction.RIGHT, Direction.UP);
        Position p3_2 = new Position(1, 3);
        assertEquals(b.calculateStrength(p2_2), -1);
        assertEquals(b.calculateStrength(p3_2), -1);

        b.getCell(p3_2).setDir(Direction.RIGHT);
        assertEquals(b.calculateStrength(p2_2), 0);
        assertEquals(b.calculateStrength(p3_2), 1);
    }

    /**
     * Az Board osztály ugyanilyen nevű metódusának tesztje.
     * Ellenőrzi, hogy az állat a megfelelő pozícióba kerül, akkor is,
     * ha a semmit tolja, és akkor is, ha egy másik állatot.
     */
    @Test
    public void push() {
        Cell.setBoard(b);
        Position p1 = new Position(0, 1);
        b.moveFromBench(p1, Player.RHINO, Direction.DOWN);
        b.startPush(p1);

        assertNull(b.getCell(p1).getPlayer());
        assertEquals(b.getCell(0, 2).getPlayer(), Player.RHINO);

        b.moveFromBench(new Position(0, 3), Player.ELEPHANT, Direction.UP);
        b.startPush(new Position(0, 3));

        assertNull(b.getCell(0, 3).getPlayer());
        assertEquals(b.getCell(0, 2).getPlayer(), Player.ELEPHANT);
        assertEquals(b.getCell(0, 1).getPlayer(), Player.RHINO);


    }
}