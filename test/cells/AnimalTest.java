package cells;

import main.Direction;
import main.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnimalTest {
    Animal a1;
    Animal a2;

    @Before
    public void setUp() {
        a1 = new Elephant(new Position(2, 1));
        a2 = new Rhino(new Position(3, 4));
    }

    @Test
    public void getStrengthForPush() {
        assertEquals(a1.getDir(), Direction.UP);
        assertEquals(a2.getDir(), Direction.DOWN);

        assertEquals(a1.getStrengthForPush(Direction.UP), Integer.valueOf(1));
        assertEquals(a2.getStrengthForPush(Direction.UP), Integer.valueOf(-1));

        assertEquals(a1.getStrengthForPush(Direction.LEFT), Integer.valueOf(0));
        assertEquals(a2.getStrengthForPush(Direction.RIGHT), Integer.valueOf(0));

        a1.setDir(Direction.RIGHT);
        a2.setDir(Direction.LEFT);

        assertEquals(a1.getStrengthForPush(Direction.UP), Integer.valueOf(0));
        assertEquals(a2.getStrengthForPush(Direction.UP), Integer.valueOf(0));

        assertEquals(a1.getStrengthForPush(Direction.LEFT), Integer.valueOf(-1));
        assertEquals(a2.getStrengthForPush(Direction.RIGHT), Integer.valueOf(-1));
    }
}