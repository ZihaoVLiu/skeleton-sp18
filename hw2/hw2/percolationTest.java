package hw2;

import org.junit.Test;
import static org.junit.Assert.*;

public class percolationTest {
    public static Percolation test = new Percolation(5);

    @Test
    public void testIsFull() {
//        test.open(3,4);
//        test.open(2,4);
//        test.open(2,3);
//        test.open(2,2);
//        test.open(0,2);
//        test.open(1,2);
        test.open(3,4);
        test.open(4,4);
        //assertEquals(test.isFull(2, 3), test.wqu.connected(test.size * test.size, 13));
        //assertTrue(test.isFull(0,2));
        //assertFalse(test.isFull(0,3));
        //assertEquals(test.numberOfOpenSites(), 7);
        assertFalse(test.percolates());
        assertFalse(test.isFull(3,4));
        assertFalse(test.isOpen(3,3));
    }

    public static void main(String[] args) {
        test.open(3,4);
        test.open(2,4);
        test.open(2,3);
        test.open(2,2);
        test.open(0,2);
        test.open(1,2);
        //System.out.println(test.wqu.count());
        //System.out.println(test.wqu.connected(test.size * test.size, 19));

        PercolationFactory pf = new PercolationFactory();
        PercolationStats testStats = new PercolationStats(1000, 30, pf);
        System.out.println();
        System.out.println("Mean is " + testStats.mean());
        //System.out.println(testStats.confidenceLow());
    }
}
