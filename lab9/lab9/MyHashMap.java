package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author starfly
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    public MyHashMap(int length) {
        buckets = new ArrayMap[length];
        this.clear();

    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        ArrayMap<K, V> bucket = buckets[hash(key)];
        // if bucket == null, bucket.get(key) will return null
        return bucket.get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        ArrayMap<K, V> bucket = buckets[hash(key)];

        if (!bucket.containsKey(key)) { size = size + 1; }
        bucket.put(key, value);

        if (loadFactor() > MAX_LF) { resize(buckets.length * 2); }
    }

    /* Problem: If set up a tmp array, may face some problems, like need to use put, then resize and
       put are called by each other; and the buckets number should be changed. So set up a new object */

    /**
     * @source https://github.com/GAKKI100/cs61b-sp18-Data-Structure/blob/master/lab9/lab9/MyHashMap.java
     * @param capacity of the new container
     */
    private void resize(int capacity) {
        MyHashMap<K, V> mHM = new MyHashMap<>(capacity);

        for (K key : this.keySet()) {
            mHM.put(key, get(key));
        }

        this.size = mHM.size;
        this.buckets = mHM.buckets;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keyset = new HashSet<>();
        for (ArrayMap<K, V> bucket : buckets) {
            keyset.addAll(bucket.keySet());
        }
        return keyset;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        ArrayMap<K, V> bucket = buckets[hash(key)];
        return bucket.remove(key);
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        ArrayMap<K, V> bucket = buckets[hash(key)];
        return bucket.remove(key, value);
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
