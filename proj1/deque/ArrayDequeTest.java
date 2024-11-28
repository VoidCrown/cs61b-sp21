package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    /** Test add items when the deque is full. */
    @Test
    public void addLargerTest() {
        ArrayDeque<Integer> test = new ArrayDeque<>();
        for(int t = 0; t < 8; t++) {
            test.addLast(t);
        }
        test.addLast(1);
        assertEquals(9, test.size());
    }

    /** Test remove items when the deque need to be smaller. */
    @Test
    public void addSmallerTest() {
        ArrayDeque<Integer> test = new ArrayDeque<>();
        for(int t = 15; t >= 0; t--) {
            test.addFirst(t);
        }
        test.addFirst(1);
        for(int t = 0; t < 17; t++) {
            test.removeFirst();
        }
        assertEquals(0, test.size());
    }

    @Test
    public void testThreeAddThreeRemove() {
        LinkedListDeque<Integer> correct = new LinkedListDeque<>();
        ArrayDeque<Integer> test = new ArrayDeque<>();

        correct.addLast(4);
        correct.addLast(5);
        correct.addLast(6);
        test.addLast(4);
        test.addLast(5);
        test.addLast(6);

        assertEquals(correct.size(), test.size());
        assertEquals(correct.removeLast(), test.removeLast());
        assertEquals(correct.removeLast(), test.removeLast());
        assertEquals(correct.removeLast(), test.removeLast());
    }

    @Test
    public void randomizedTest() {
        LinkedListDeque<Integer> correct = new LinkedListDeque<>();
        ArrayDeque<Integer> test = new ArrayDeque<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 5);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                test.addLast(randVal);
                //System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // addFirst
                int randVal = StdRandom.uniform(0, 100);
                correct.addFirst(randVal);
                test.addFirst(randVal);
            } else if (operationNumber == 2) {
                //size
                assertEquals(correct.size(), test.size());
            } else if (operationNumber == 3) {
                //removeLast
                assertEquals(correct.removeLast(), test.removeLast());
            } else if (operationNumber == 4) {
                //removeFirst
                assertEquals(correct.removeFirst(), test.removeFirst());
            } else if (operationNumber == 5) {
                int randIndex = StdRandom.uniform(0, correct.size());
                assertEquals(correct.get(randIndex), test.get(randIndex));
            }
        }
    }
}
