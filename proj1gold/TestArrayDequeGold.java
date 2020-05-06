import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {

    public static StudentArrayDeque<Integer> dequeGenerator() {
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<Integer>();
        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            if (numberBetweenZeroAndOne < 0.5) {
                sad1.addLast(i);
            } else {
                sad1.addFirst(i);
            }
        }
        return sad1;
    }

    @Test
    public void testAddLast() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<Integer>();
        StudentArrayDeque<Integer> sds = new StudentArrayDeque<Integer>();
        for (int i = 0; i < 10; i++) {
            int random = StdRandom.uniform(100);
            ads.addLast(random);
            sds.addLast(random);
        }
        for (int i = 0; i < 10; i += 1) {
            int expected = ads.get(i);
            int actual = sds.get(i);
            assertEquals("Oh noooo!\nThis is bad in addLast():\n   Random number " + actual
                    + " not equal to " + expected + "!",
                    expected, actual);
        }
    }

    @Test
    public void testAddFirst() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<Integer>();
        StudentArrayDeque<Integer> sds = new StudentArrayDeque<Integer>();
        for (int i = 0; i < 10; i++) {
            int random = StdRandom.uniform(100);
            ads.addFirst(random);
            sds.addFirst(random);
        }
        for (int i = 0; i < 10; i += 1) {
            int expected = ads.get(i);
            int actual = sds.get(i);
            assertEquals("Oh noooo!\nThis is bad in addFirst():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }
    }

    @Test
    public void testRemoveFirst() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<Integer>();
        StudentArrayDeque<Integer> sds = new StudentArrayDeque<Integer>();
        for (int i = 0; i < 10; i++) {
            int random = StdRandom.uniform(100);
            ads.addFirst(random);
            sds.addFirst(random);
        }
        for (int i = 0; i < 10; i += 1) {
            int expected = ads.removeFirst();
            int actual = sds.removeFirst();
            assertEquals("Oh noooo!\nThis is bad in removeFirst():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }
    }

    @Test
    public void testRemoveLast() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<Integer>();
        StudentArrayDeque<Integer> sds = new StudentArrayDeque<Integer>();
        for (int i = 0; i < 10; i++) {
            int random = StdRandom.uniform(100);
            ads.addFirst(random);
            sds.addFirst(random);
        }
        for (int i = 0; i < 10; i += 1) {
            int expected = ads.removeLast();
            int actual = sds.removeLast();
            assertEquals("Oh noooo!\nThis is bad in removeLast():\n   Random number " + actual
                            + " not equal to " + expected + "!",
                    expected, actual);
        }
    }

    @Test
    public void testArratDeque2() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<Integer>();
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<Integer>();
        int random = StdRandom.uniform(100);
        ads.addFirst(random);
        sad.addFirst(random);
        assertEquals("addFirst("+random+")", ads.get(0), sad.get(0));
        System.out.println("addFirst("+random+")");

        random = StdRandom.uniform(100);
        ads.addLast(random);
        sad.addLast(random);
        assertEquals("addLast("+random+")", ads.get(1), sad.get(1));
        System.out.println("addLast("+random+")");

        int actual = ads.removeFirst();
        int expected = ads.removeFirst();
        assertEquals("removeFirst()", actual, expected);
        System.out.println("removeFirst()");

        actual = ads.removeLast();
        expected = sad.removeLast();
        assertEquals("removeLast()", actual, expected);
        System.out.println("removeLast()");
    }
}
