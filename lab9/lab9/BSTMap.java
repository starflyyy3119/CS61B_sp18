package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name Starfly
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) { return null; }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        } else if (cmp > 0) {
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size = size + 1;
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else { // cmp == 0
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
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
        Set<K> keys = new HashSet<>();
        addKey(keys, root);
        return keys;
    }

    private void addKey(Set<K> keys, Node p) {
        if (p == null) return;
        keys.add(p.key);
        addKey(keys, p.left);
        addKey(keys, p.right);
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    /*
        问题： princeton 给出的是删除 key 节点的方式，但是不能够返回 value 值
        解决方法： remove(K key, Node p) 仅需要完成自己的删除节点、重新构建树的工作即可，在 remove(K key) 中
        通过 get 得到 value 即可。
     */
    @Override
    public V remove(K key) {
        V value = get(key);
        root = remove(key, root);
        return value;
    }

    /**
     * remove the key from the node p tree
     * @param key needs to be matched
     * @param p present node
     * @return present node(specific key has been deleted)
     */
    private Node remove(K key, Node p) {
        if (p == null) { return null; }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = remove(key, p.left);
        } else if (cmp > 0) {
            p.right = remove(key, p.right);
        } else { // cmp == 0
            if (p.left == null) return p.right;
            if (p.right == null) return p.left;

            Node t = p;
            p = min(t.right);   // find successor
            p.right = deleteMin(t.right);
            p.left = t.left;
            size = size - 1;
        }
        return p;
    }

    private Node deleteMin(Node p) {
        // don't need to check, because p.left isn't null
        if (p.left == null) return p.right;
        p.left = deleteMin(p.left);
        return p;
    }

    private Node min(Node p) {
        if (p.left == null) return p;
        else return min(p.left);
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V val = get(key);
        if (!value.equals(val)) { return null; }
        remove(key, root);
        return val;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
