package byog.lab6;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class TestMemoryGame {
    int seed = 10;
    @Test
    public void generateRandomStringTest() {
        MemoryGame mg = new MemoryGame(40, 40, seed);
        String actual = mg.generateRandomString(3);
        mg.drawFrame(actual);
        assertEquals(3, actual.length());
    }
}
