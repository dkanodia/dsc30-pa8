/*
 * Name: Divyansh Kanodia
 * PID: A17922611
 */

/**
 * TODO
 *
 * @author Divyansh Kanodia
 * @since May 30
 */

public class MyBloomFilter implements KeyedSet {

    private static final int DEFAULT_M = (int) 1e7;

    boolean[] bits;

    /**
     * Initialize MyBloomFilter with the default number of bits
     */
    public MyBloomFilter() {
        bits = new boolean[DEFAULT_M];
    }

    /**
     * Insert the string key into the bloom filter.
     *
     * @param key key to insert
     * @throws NullPointerException if value is null
     * @return true if the key was inserted, false if the key was already
     *         present
     */
    public boolean insert(String key) {
        if (key == null){
            throw new NullPointerException();
        }
        bits[hashFuncA(key)] = true;
        bits[hashFuncB(key)] = true;
        bits[hashFuncC(key)] = true;
        return true;
    }

    /**
     * Check if the given key is present in the bloom filter.
     * @param key key to look up
     * @throws NullPointerException if value is null
     * @return true if the key was found, false if the key was not found
     */
    public boolean lookup(String key) {
        if (key == null){
            throw new NullPointerException();
        }
        return bits[hashFuncA(key)] & bits[hashFuncB(key)] & bits[hashFuncC(key)];
    }

    /**
     * First hash function to be used by MyBloomFilter
     * @param value The input string
     * @return A hashcode for the string
     */
    private int hashFuncA(String value) {
        int hashvalue = 0;
        for(int i = 0; i < value.length(); i++){
            int leftShiftedValue  = hashvalue << 5;
            int rightShiftedValue  = hashvalue >>> 27;

            hashvalue = (leftShiftedValue | rightShiftedValue) ^ value.charAt(i);
        }
        return  Math.abs(hashvalue) % DEFAULT_M;
    }

    /**
     * Second hash function to be used by MyBloomFilter
     * @param value The input string
     * @return A hashcode for the string
     */
    private int hashFuncB(String value) {
        int hashval = 0;
        for(int i = 0; i < value.length(); i++){
            int letter = value.charAt(i);
            hashval = (hashval * 27 + letter) % DEFAULT_M;
        }
        return hashval;
    }

    /**
     * Third hash function to be used by MyBloomFilter
     * @param value The input string
     * @return A hashcode for the string
     */
    private int hashFuncC(String value) {
        int hashVal = 0;
        for (int j = 0; j < value.length(); j++) {
            int letter = value.charAt(j);
            hashVal = ((hashVal << 8) + letter) % bits.length;
        }
        return Math.abs(hashVal % bits.length);
    }
}
