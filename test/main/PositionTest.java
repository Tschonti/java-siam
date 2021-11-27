package main;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * A Position osztály metódusainak tesztjei paraméterekkel.
 */
@RunWith(Parameterized.class)
public class PositionTest {
    Position source;
    Position destination;
    Direction expectedDir;
    boolean outer;
    boolean outOfBounds;

    public PositionTest(Position p1, Position p2, Direction d, boolean b1, boolean b2) {
        source = p1;
        destination = p2;
        expectedDir = d;
        outer = b1;
        outOfBounds = b2;
    }

    @Parameters
    static public List<Object[]> parameters() {
        List<Object[]> params = new ArrayList<>();
        params.add(new Object[]{new Position(1, 2), new Position(2, 2), Direction.RIGHT, false, false});
        params.add(new Object[]{new Position(1, 2), new Position(1, 1), Direction.UP, false, false});
        params.add(new Object[]{new Position(1, 2), new Position(2, 3), null, false, false});
        params.add(new Object[]{new Position(1, 2), new Position(2, 1), null, false, false});
        params.add(new Object[]{new Position(1, 2), new Position(1, 2), null, false, false});
        params.add(new Object[]{new Position(0, 1), new Position(-1, 1), Direction.LEFT, true, false});
        params.add(new Object[]{new Position(4, 4), new Position(4, 5), Direction.DOWN, true, false});
        params.add(new Object[]{new Position(5, 5), new Position(4, 5), Direction.LEFT, false, true});
        params.add(new Object[]{new Position(-1, 0), new Position(0, -1), null, false, true});
        return params;
    }

    /**
     * Az Position osztály ugyanilyen nevű metódusának tesztje.
     * Ellenőrzi, hogy különböző forrás- és célpozíciókra a várt eredményt adja-e
     */
    @Test
    public void whichWayToStep() {
        assertEquals(Position.whichWayToStep(source, destination), expectedDir);
    }

    /**
     * Az Position osztály ugyanilyen nevű metódusának tesztje.
     * Ellenőrzi, hogy különböző forrásokra a várt eredményt adja-e
     */
    @Test
    public void isOutOfBounds() {
        assertEquals(source.isOutOfBounds(), outOfBounds);
    }

    /**
     * Az Position osztály ugyanilyen nevű metódusának tesztje.
     * Ellenőrzi, hogy különböző forrásokra a várt eredményt adja-e
     */
    @Test
    public void isInOuterCells() {
        assertEquals(source.isInOuterCells(), outer);
    }
}