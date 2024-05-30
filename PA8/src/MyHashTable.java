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

    private String stats = "";
    private int rehashcount;
    private int numcollisions;

    private float loadfactor;


    public MyHashTable() {
        this(19);
    }

    @SuppressWarnings("unchecked")
    public MyHashTable(int capacity) {
        if (capacity < 7){
            throw new  IllegalArgumentException();
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
            this.stats += String.format("Before rehash #%d : load factor %.2f, %d collision(s). \n",
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

    public boolean lookup(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return this.table[hashString(value)].contains(value);
    }

    public String[] returnAll() {
        int output_index = 0;
        String[] output = new String[this.size()];
        for(int i = 0; i < table.length; i++){
            for(int j = 0; j < table[i].size(); j++){
                if (table[i].get(j) != null){
                    output[output_index] = table[i].get(j);
                    output_index++;
                }
            }
        }
        return output;
    }

    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.capacity;
    }

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
