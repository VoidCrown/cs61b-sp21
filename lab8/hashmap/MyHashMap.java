package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author Mr.T
 */
public class MyHashMap<K, V> implements Map61B<K, V> {


    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    private Set<K> keys;
    private int pairs;
    private int hashTableSize;
    private double loadFactor;
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int initialSize) {
        this(initialSize, 0.75);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        loadFactor = maxLoad;
        hashTableSize = initialSize;
        buckets = createTable(initialSize);
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = createBucket();
        }
        keys = new HashSet<>();
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new HashSet<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        return new Collection[tableSize];
    }

    /**
     * double the size of hash table when N/M larger than load factor
     */
    private void resize() {
        MyHashMap<K, V> temp = new MyHashMap<>(hashTableSize * 2, loadFactor);
        for (K key : keySet()) {
            temp.put(key, this.get(key));
        }
        this.hashTableSize = temp.hashTableSize;
        this.buckets = temp.buckets;
    }

    private int hash(K key) {
        return Math.floorMod(key.hashCode(), hashTableSize);
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        for (Collection<Node> bucket : buckets) {
            Iterator<Node> iterator = bucket.iterator();
            while (iterator.hasNext()) {
                Node node = iterator.next();
                iterator.remove();
            }
        }
        keys.clear();
        pairs = 0;
    }

    /** Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        for (Node node : buckets[hash(key)]) {
            if (node.key.equals(key))
                return node.value;
        }
        return null;
    }

    /** Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return pairs;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    @Override
    public void put(K key, V value) {
        if ((double) pairs / hashTableSize >= loadFactor) {
            resize();
        }

        int hs = hash(key);
        if (!containsKey(key)) {
            pairs += 1;
            buckets[hs].add(createNode(key, value));
            keys.add(key);
        } else {
            for (Node node : buckets[hash(key)]) {
                if (node.key.equals(key))
                    node.value = value;
            }
        }

    }

    /** Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        return new myHashMapIterator();
    }

    private class myHashMapIterator implements Iterator<K> {
        private int currentBucketIndex = 0;
        private Iterator<Node> currentIterator;
        public myHashMapIterator() {
            if (buckets.length > 0) {
                currentIterator = buckets[0].iterator();
            }
        }

        @Override
        public boolean hasNext() {
            if (currentIterator != null && currentIterator.hasNext())
                return true;

            while (currentBucketIndex < buckets.length - 1) {
                currentBucketIndex += 1;
                currentIterator = buckets[currentBucketIndex].iterator();

                if (currentIterator.hasNext()) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return currentIterator.next().key;
        }
    }

}
