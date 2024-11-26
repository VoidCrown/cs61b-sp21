package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> aList = new AListNoResizing<>();
        BuggyAList<Integer> bList = new BuggyAList<>();

        aList.addLast(4);
        aList.addLast(5);
        aList.addLast(6);
        bList.addLast(4);
        bList.addLast(5);
        bList.addLast(6);

        assertEquals(aList.size(), bList.size());
        assertEquals(aList.removeLast(), bList.removeLast());
        assertEquals(aList.removeLast(), bList.removeLast());
        assertEquals(aList.removeLast(), bList.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> bL = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                bL.addLast(randVal);
                //System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                //int size = L.size();
                assertEquals(L.size(), bL.size());
                //System.out.println("size: " + size);
            } else if (operationNumber == 2) {
                //removeLast
                if(L.size() != 0) {
                    int removedL = L.removeLast();
                    int removedB = bL.removeLast();
                    assertEquals(removedL, removedB);
                }
            } else if (operationNumber == 3) {
                //getLast
                if(L.size() != 0 && bL.size() != 0) {
                    assertEquals(L.getLast(), bL.getLast());
                }
            }
        }
    }
}
