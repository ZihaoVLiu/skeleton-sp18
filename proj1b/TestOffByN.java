import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    CharacterComparator offBy3 = new OffByN(3);
    CharacterComparator offBy5 = new OffByN(5);

    @Test
    public void testOffBy3() {
        //OffByOne obo = new OffByOne();
        CharacterComparator obo = new OffByOne();
        assertTrue(offBy3.equalChars('a', 'd'));
        assertTrue(offBy3.equalChars('c', 'f'));
        assertTrue(offBy3.equalChars('a', 'd'));
    }

    @Test
    public void testOffBy5() {
        assertTrue(offBy5.equalChars('f', 'a'));
        assertFalse(offBy5.equalChars('z', 'x'));
    }
}
