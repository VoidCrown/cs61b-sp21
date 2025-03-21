package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private final Comparator<T> comparator;

    /** creates a MaxArrayDeque with the given Comparator */
    public MaxArrayDeque(Comparator<T> c) {
        comparator = c;
    }

    /** returns the maximum element in the deque as governed by the previously given Comparator.
     * If the MaxArrayDeque is empty, simply return null */
    public T max() {
        return max(comparator);
    }

    /** returns the maximum element in the deque as governed by the parameter Comparator c.
     * If the MaxArrayDeque is empty, simply return null. */
    public T max(Comparator<T> c) {
        if(isEmpty()) {
            return null;
        }
        T maxVal = get(0);
        for(int i = 0; i < size(); i++) {
            T current = get(i);
            if(c.compare(maxVal, current) < 0) {
                maxVal = current;
            }
        }
        return maxVal;
    }
}
