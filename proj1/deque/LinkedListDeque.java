package deque;

/** Implement linked list deque.
 * @author Mr.T
 * */
public class LinkedListDeque<T>  {
    /** Implement the class of node in LinkedListDeque. */
    public class DequeNode{
        T item;
        DequeNode previous;
        DequeNode next;

        public DequeNode(T i, DequeNode p, DequeNode n) {
            item = i;
            previous = p;
            next = n;
        }
    }

    DequeNode sentinel;
    int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new DequeNode(null, null, null);
        sentinel.previous = sentinel;
        sentinel.next = sentinel;
    }
    public LinkedListDeque(T item) {
        size = 1;
        sentinel = new DequeNode(null, null, null);
        sentinel.next = new DequeNode(item, sentinel, sentinel);
        sentinel.previous = sentinel.next;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    /** Adds an item of type T to the front of the deque.
     * @param item the value of the node. item is never null.
     * */
    public void addFirst(T item) {
        size += 1;
        DequeNode newNode = new DequeNode(item, sentinel, sentinel.next);
        sentinel.next.previous = newNode;
        sentinel.next = newNode;
    }

    /** Adds an item of type T to the back of the deque.
     * @param item the value of the node. item is never null.
     * */
    public void addLast(T item) {
        size += 1;
        DequeNode newNode = new DequeNode(item, sentinel.previous, sentinel);
        sentinel.previous.next = newNode;
        sentinel.previous = newNode;
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null. */
    public T removeFirst(){
        if(size == 0) {
            return null;
        }
        DequeNode removed = sentinel.next;
        sentinel.next = removed.next;
        removed.next.previous = sentinel;
        size -= 1;
        return removed.item;
    }

    /** Removes and returns the item at the back of the deque.
     *  If no such item exists, returns null. */
    public T removeLast() {
        if(size == 0) {
            return null;
        }
        DequeNode removed = sentinel.previous;
        sentinel.previous = removed.previous;
        removed.previous.next = sentinel;
        size -= 1;
        return removed.item;
    }

    /** Prints the items in the deque from first to last, separated by a space.
     *  Once all the items have been printed, print out a new line.
     *  */
    public void printDeque() {
        DequeNode current = sentinel.next;
        for(int c = 0; c < size; c += 1) {
            System.out.print(current.item + " ");
            current = current.next;
        }
        System.out.println();
    }

    /** Gets the item at the given index,
     * where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     */
    public T get(int index) {
        if(index >= size) {
            return null;
        }
        DequeNode current = sentinel.next;
        for(int c = 0; c < size; c += 1) {
            current = current.next;
        }
        return current.item;
    }
}