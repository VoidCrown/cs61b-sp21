package deque;

import afu.org.checkerframework.checker.oigj.qual.O;

import java.util.Iterator;

/** Implement linked list deque.
 * @author Mr.T
 * */
public class LinkedListDeque<T> implements Deque<T>, Iterable<T>  {
    /** Implement the class of node in LinkedListDeque. */
    public class DequeNode{
        private T item;
        private DequeNode previous;
        private DequeNode next;

        public DequeNode(T i, DequeNode p, DequeNode n) {
            item = i;
            previous = p;
            next = n;
        }
    }

    private DequeNode sentinel;
    private int size;

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
//    public boolean isEmpty() {
//        return size == 0;
//    }

    /** Returns the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /** Adds an item of type T to the front of the deque.
     * @param item the value of the node. item is never null.
     * */
    @Override
    public void addFirst(T item) {
        size += 1;
        DequeNode newNode = new DequeNode(item, sentinel, sentinel.next);
        sentinel.next.previous = newNode;
        sentinel.next = newNode;
    }

    /** Adds an item of type T to the back of the deque.
     * @param item the value of the node. item is never null.
     * */
    @Override
    public void addLast(T item) {
        size += 1;
        DequeNode newNode = new DequeNode(item, sentinel.previous, sentinel);
        sentinel.previous.next = newNode;
        sentinel.previous = newNode;
    }

    /** Removes and returns the item at the front of the deque.
     *  If no such item exists, returns null. */
    @Override
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
    @Override
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
    @Override
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
    @Override
    public T get(int index) {
        if(index >= size) {
            return null;
        }
        DequeNode current = sentinel.next;
        for(int c = 0; c < index; c += 1) {
            current = current.next;
        }
        return current.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new LLDequeIterator();
    }

    private class LLDequeIterator implements Iterator<T> {
        private DequeNode current;

        public LLDequeIterator() {
            current = sentinel;
        }
        @Override
        public boolean hasNext() {
            return current.next != sentinel;
        }

        @Override
        public T next() {
            T returnItem = current.next.item;
            current = current.next;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        LinkedListDeque<T> o = (LinkedListDeque<T>) other;
        if (o.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if(!get(i).equals(o.get(i))) {
                return false;
            }
        }
        return true;
    }
}
