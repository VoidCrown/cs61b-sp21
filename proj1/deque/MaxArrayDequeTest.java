package deque;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Comparator;

public class MaxArrayDequeTest {
    private static class integerComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return a.compareTo(b);
        }
    }

    private static class minimumIntegerComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return b.compareTo(a);
        }
    }

    private static class stringComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }

    @Test
    public void testIntegerMax() {
        MaxArrayDeque<Integer> test = new MaxArrayDeque<>(new integerComparator());
        Comparator<Integer> mic = new minimumIntegerComparator();
        for(int t = 0; t < 8; t++) {
            test.addLast(t);
        }
        assertEquals( 7, (long) test.max());
        assertEquals(0, (long) test.max(mic));
    }

    @Test
    public void testStringMax() {
        MaxArrayDeque<String> test = new MaxArrayDeque<>(new stringComparator());

        test.addLast("apple");
        test.addLast("bad");
        test.addLast("lucky");
        test.addLast("five");

        assertEquals( "lucky", test.max());
    }
}
