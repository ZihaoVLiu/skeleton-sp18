package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Zihao Liu
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

    public MyHashMap(int resize) {
        buckets = new ArrayMap[resize];
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
        if (buckets[hash(key)].containsKey(key)) {
            return buckets[hash(key)].get(key);
        }
        return null;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (!buckets[hash(key)].containsKey(key)) {
            size += 1;
        }
        buckets[hash(key)].put(key, value);
        if (loadFactor() > MAX_LF) {
            resize();
        }
    }

//    private void resize() {
//        MyHashMap<K, V> temp = new MyHashMap<>(buckets.length * 2);
//        for (K key : this.keySet()) {
//            temp.put(key, this.get(key));
//        }
//        this.size = temp.size;
//        this.buckets = temp.buckets;
//    }

    private void resize() {
        ArrayMap<K, V>[] temp = this.buckets;
        int tempSize = this.size;
        this.buckets = new ArrayMap[this.buckets.length * 2];
        this.clear();
        for (int i = 0; i < temp.length; i++) {
            for (K tempKey : temp[i].keySet()) {
                V tempValue = temp[i].get(tempKey);
                int tempHash = hash(tempKey);
                this.buckets[tempHash].put(tempKey, tempValue);
            }
        }
        this.size = tempSize;
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
        if (size == 0) {
            return null;
        }
        HashSet<K> newSet = new HashSet<>();
        for (int i = 0; i < buckets.length; i++) {
            newSet.addAll(buckets[i].keySet());
        }
        return newSet;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (buckets[hash(key)].containsKey(key)) {
            return buckets[hash(key)].remove(key);
        }
        return null;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (buckets[hash(key)].containsKey(key)) {
            return buckets[hash(key)].remove(key, value);
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        MyHashMap mhm = new MyHashMap();
        mhm.put("hello", 5);
        mhm.put("cat", 10);
        mhm.put("fish", 22);
        mhm.put("zebra", 90);
        mhm.put("zebra", 50);
        mhm.remove("fish");
        Iterator<String> a = mhm.iterator();
        while(a.hasNext()){
            System.out.println(mhm.get(a.next()));
        }
    }
}
