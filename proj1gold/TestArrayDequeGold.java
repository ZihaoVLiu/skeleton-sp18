import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDequeGold {
    @Test

    public void testStudentArrayDeque() {
        StudentArrayDeque<Integer> test1 = new StudentArrayDeque<Integer>();
        ArrayDequeSolution<Integer> test2 = new ArrayDequeSolution<Integer>();
        Integer studentOutput;
        Integer correctOutput;
        while (true) {
            double numberBetweenZeroAndOne = StdRandom.uniform();
            int number = StdRandom.uniform(10);
            if (numberBetweenZeroAndOne > 0.75) {
                test1.addFirst(number);
                test2.addFirst(number);
                System.out.printf("addFirst(%d)\n", number);
            } else if (numberBetweenZeroAndOne >= 0.5 && numberBetweenZeroAndOne < 0.75) {
                test1.addLast(number);
                test2.addLast(number);
                System.out.printf("addLast(%d)\n", number);
            } else if (numberBetweenZeroAndOne < 0.5 && numberBetweenZeroAndOne >= 0.25
                    && !test1.isEmpty() && !test2.isEmpty()) {
                studentOutput = test1.removeFirst();
                correctOutput = test2.removeFirst();
                System.out.printf("removeFirst()\n");
                assertEquals(correctOutput, studentOutput);
            } else if (numberBetweenZeroAndOne >= 0 && numberBetweenZeroAndOne < 0.25
                    && !test1.isEmpty() && !test2.isEmpty()) {
                studentOutput = test1.removeLast();
                correctOutput = test2.removeLast();
                System.out.printf("removeLast()\n");
                assertEquals(correctOutput, studentOutput);
            } else  {
                assertEquals(test2.isEmpty(), test1.isEmpty());
            }
        }
    }
}