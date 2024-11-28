package deque;

/** Invariants:
 *  1. The position of the next item to be inserted (using addLast) is always size.
 *  2. The number of items in the AList is always size.
 *  3. The position of the last item in the list is always size - 1.
 */
public class ArrayDeque<T> implements Deque<T> {
    private int size;
    private T[] items;
    private int nextFirst, nextLast;
    public ArrayDeque() {
        size = 0;
        items = (T []) new Object[8];
        nextFirst = 7;
        nextLast = 0;
    }

    /** Resize the deque to size of given capacity. */
    private void resize(int capacity) {
        T[] newItems = (T []) new Object[capacity];
        int temp;
        //System.arraycopy(items, 0, newItems, 0, size);
        for(int i = 0; i < size; i++) {
            temp = (nextFirst + 1 + i) % items.length;
            newItems[i] = items[temp];
        }
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }


    /** Adds an item of type T to the front of the deque. */
    @Override
    public void addFirst(T item){
        if(size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        size++;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
    }

    /** Adds an item of type T to the back of the deque. */
    @Override
    public void addLast(T item) {
        if(size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        size++;
        nextLast = (nextLast + 1) % items.length;
    }

    /** Returns true if deque is empty, false otherwise. */
//    public boolean isEmpty() {
//        return size == 0;
//    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /** Prints the items in the deque from first to last, separated by a space. */
    @Override
    public void printDeque() {
        for(int i = 0; i < size; i++) {
            System.out.print(items[(nextFirst + i) % items.length] + " ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    @Override
    public T removeFirst() {
        if(size == 0) {
            return null;
        }
        if((items.length > 16) && (size < items.length / 4)) {
            resize(items.length / 4);
        }
        int removedIndex = (nextFirst + 1) % items.length;
        T removed = items[removedIndex];
        items[removedIndex] = null;
        nextFirst = removedIndex;
        size--;
        return removed;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    @Override
    public T removeLast() {
        if(size == 0) {
            return null;
        }
        if((items.length > 16) && (size < items.length / 4)) {
            resize(items.length / 4);
        }
        int removedIndex = (nextLast - 1 + items.length) % items.length;
        T removed = items[removedIndex];
        items[removedIndex] = null;
        nextLast = removedIndex;
        size--;
        return removed;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. */
    @Override
    public T get(int index) {
        if(index >= size) {
            return null;
        }
        return items[(nextFirst + 1 + index) % items.length];
    }
}
