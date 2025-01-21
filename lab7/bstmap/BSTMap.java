package bstmap;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Stack;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;
        private int size;

        public Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    public BSTMap() {
        root = null;
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return containsKey(key, root);
    }

    private boolean containsKey(K key, Node x) {
        if (x == null)
            return false;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return containsKey(key, x.left);
        else if (cmp > 0)
            return containsKey(key, x.right);
        else
            return true;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        Node t = get(key, root);
        if (t == null)
            return null;
        return t.value;
    }

    private Node get(K key, Node x) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return get(key, x.left);
        else if (cmp > 0)
            return get(key, x.right);
        else
            return x;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        return x.size;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        root = put(key, value, root);
    }

    private Node put(K key, V value, Node x) {
        if (x == null)
            return new Node(key, value, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(key, value, x.left);
        else if (cmp > 0)
            x.right = put(key, value, x.right);
        else {
            x.value = value;
            return x;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }



    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new TreeSet<>();
        keySet(root, keySet);
        return keySet;
    }

    private void keySet(Node x, Set<K> set) {
        if (x == null)
            return;
        keySet(x.left, set);
        set.add(x.key);
        keySet(x.right, set);
    }

    /* Removes the mapping for the specified key from this map if present.*/
    @Override
    public V remove(K key) {
        V value = get(key);
        if (value == null)
            return null;
        root = remove(key, root);
        return value;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value.
     * */
    @Override
    public V remove(K key, V value) {
        V expect = get(key);
        if (!value.equals(expect))
            return null;
        root = remove(key, root);
        return expect;
    }

    private Node remove(K key, Node x) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = remove(key, x.left);
        else if (cmp > 0)
            x.right = remove(key, x.right);
        else {
            // deletion node has one child.
            if (x.left == null)
                return x.right;
            if (x.right == null)
                return x.left;
            // deletion node has two children.
            Node t = x;
            x = min(x.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /** helper function for remove.
     *  finds the node which has the minimum key.
     */
    private Node min(Node x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }

    /** helper function for remove.
     *  deletes the node which has the minimum key.
     */
    private Node deleteMin(Node x) {
        if (x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        private Stack<Node> keyStack;

        public BSTMapIterator() {
            keyStack = new Stack<>();
            pushLeft(root);
        }

        private void pushLeft(Node x) {
            if (x.left == null)
                return;
            keyStack.push(x);
            pushLeft(x.left);
        }

        @Override
        public boolean hasNext() {
            return !keyStack.empty();
        }

        @Override
        public K next() {
            Node t = keyStack.pop();
            pushLeft(t.right);
            return t.key;
        }
    }
}
