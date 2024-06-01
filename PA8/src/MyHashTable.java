/*
 * Name: Divyansh Kanodia
 * PID: A17922611
 */

import java.util.LinkedList;

/**
 * TODO
 * 
 * @author Divyansh Kanodia
 * @since May 28
 */

public class MyHashTable implements KeyedSet {

    /* instance variables */
    private int size; // number of elements stored
    private LinkedList<String>[] table; // data table

    private int capacity;

    private static final int DEFAULTCAPACITY = 19;

    private static final int CAPACITYCHECK = 7;

    private String stats = "";
    private int rehashcount;
    private int numcollisions;

    private float loadfactor;

    /**
     * Initialize hash table with the default capacity
     */
    public MyHashTable() {
        this(DEFAULTCAPACITY);
    }

    /**
     * Initialize hash table with a given capacity
     * @param capacity capacity for the hash table
     * @throws IllegalArgumentException when capacity is less than 7
     */
    @SuppressWarnings("unchecked")
    public MyHashTable(int capacity) {
        if (capacity < CAPACITYCHECK){
            throw new IllegalArgumentException();
        }
        this.capacity = capacity;
        numcollisions = 0;
        rehashcount = 1;
        table = new LinkedList[capacity];
        for(int i = 0; i < capacity; i++){
            table[i] = new LinkedList<>();
        }
        this.size = 0;
    }

    /**
     * Insert value in the hash table
     * @param value value to be added in the hash table
     * @throws NullPointerException if value is null
     * @return true if the value is inserted else false
     */
    public boolean insert(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        loadfactor = (float) this.size /  this.capacity;

        int index = this.hashString(value);
        if (this.table[index].contains(value)){
            return false;
        }

        if (loadfactor > 1.0f) {
            this.stats += String.format("Before rehash # %d: load factor %.2f, %d collision(s). " +
                            "\n",
                    this.rehashcount, loadfactor, this.numcollisions);

            numcollisions = 0;
            this.rehash();
            this.rehashcount ++;
        }

        index = this.hashString(value);
        if( !this.table[index].isEmpty()){
            numcollisions++;
        }
        this.table[index].add(value);
        this.size++;
        return true;

    }

    /**
     * delete value from the hash table
     * @param value value to be deleted from the hash table
     * @throws NullPointerException if value is null
     * @return true if the value is deleted else false
     */
    public boolean delete(String value) {
        if (value == null) {
            throw new NullPointerException();
        }

        int index = hashString(value);
        if (!lookup(value)){
            return false;
        }
        else{
            this.table[index].remove(value);
            this.size --;
            return true;
        }
    }

    /**
     * find the value from the hash table
     * @param value value to be searched in the hash table
     * @throws NullPointerException if value is null
     * @return true if the value is found else false
     */
    public boolean lookup(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return this.table[hashString(value)].contains(value);
    }

    /**
     * Returns an array of all elements stored in the hashtable
     * @return array of all elements stored in the hashtable
     */
    public String[] returnAll() {
        int outputIndex = 0;
        String[] output = new String[this.size()];
        for(int i = 0; i < table.length; i++){
            for(int j = 0; j < table[i].size(); j++){
                if (table[i].get(j) != null){
                    output[outputIndex] = table[i].get(j);
                    outputIndex++;
                }
            }
        }
        return output;
    }

    /**
     * Returns the number of elements of the hash table
     * @return the size of the array
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns the capacity of the hash table
     * @return the capacity of the array
     */
    public int capacity() {
        return this.capacity;
    }

    /**
     *Report the following information for the hash table.
     * The number of times you had to rehash the table.
     * The load factor of the table (before rehashing), rounded to 2 decimal places.
     * The number of collisions detected during insertions (before rehashing).
     * @return the statistics log of the HashTable
     */
    public String getStatsLog() {
        return stats;
    }

    /**
     * Utility function provided to help with debugging
     */
    public void printTable() {
        String s = "";
        for (int i = 0; i < table.length; i++) {
            s = s + i + ":";
            if (!table[i].isEmpty()) {
                for (String t : table[i])
                    s = s + " " + t + ",";
                // remove trailing comma
                s = s.substring(0, s.length() - 1);
            }
            s = s + "\n";
        }
        // remove trailing newline
        s = s.substring(0, s.length() - 1);
        System.out.println(s);
    }

    /**
     * Rehash the table by (1) doubling the capacity, and (2) iterating through the old table and
     * re-inserting every valid element to the new table.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        LinkedList<String>[] tmp = new LinkedList[this.capacity * 2];
        for(int i = 0; i < tmp.length; i++){
            tmp[i] = new LinkedList<>();
        }
        this.capacity = this.capacity * 2;
        for(int i = 0; i < table.length; i++){
            if(!table[i].isEmpty()){
                for(int j = 0; j < table[i].size(); j++){
                    int index = hashString(table[i].get(j));
                    tmp[index].add(table[i].get(j));
                }
            }
        }
        this.table = tmp;
    }

    /**
     * Uses CRC hash function
     * @param value value to be hashed
     * @return the hash value of the given string
     */
    private int hashString(String value) {
        int hashvalue = 0;
        for(int i = 0; i < value.length(); i++){
            int leftShiftedValue  = hashvalue << 5;
            int rightShiftedValue  = hashvalue >>> 27;

            hashvalue = (leftShiftedValue | rightShiftedValue) ^ value.charAt(i);
        }
        return  Math.abs(hashvalue) % this.capacity();
    }
}
