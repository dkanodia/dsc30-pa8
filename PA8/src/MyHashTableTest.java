import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MyHashTableTest {

    MyHashTable table1;
    MyHashTable table2;
    MyHashTable table3;

    @BeforeEach
    void setup() {
        table1 = new MyHashTable(12);
        table2 = new MyHashTable();
    }

    @Test
    void capacityTest() {
        assertEquals(12, table1.capacity());
        assertEquals(19, table2.capacity());
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            table3 = new MyHashTable(3);
        });

    }

    @Test
    void insertTest() {
        assertTrue(table1.insert("DSC 10"));
        assertEquals(1, table1.size());
        assertTrue(table1.insert("DSC"));
        assertEquals(2, table1.size());
        assertTrue(table1.insert("Hello"));
        assertEquals(3, table1.size());
        assertTrue(table1.insert("I"));
        assertEquals(4, table1.size());
        assertTrue(table1.insert("am"));
        assertEquals(5, table1.size());
        assertTrue(table1.insert("Divyansh"));
        assertEquals(6, table1.size());
        assertTrue(table1.insert("How"));
        assertEquals(7, table1.size());
        assertTrue(table1.insert("DSC 20"));
        assertEquals(8, table1.size());
        assertTrue(table1.insert("DSC 30"));
        assertEquals(9, table1.size());
        assertTrue(table1.insert("Economics"));
        assertEquals(10, table1.size());
        assertTrue(table1.insert("Mathematics"));
        assertEquals(11, table1.size());
        Assertions.assertThrows(NullPointerException.class,()->{
            table1.insert(null);
        });
        assertEquals(12, table1.capacity());
        assertTrue(table1.insert("physics"));
        assertEquals(12, table1.size());
        assertTrue(table1.insert("abc"));
        assertEquals(13, table1.size());
        assertFalse(table1.insert("abc"));
        assertEquals(13, table1.size());
        assertEquals(12, table1.capacity());
        assertTrue(table1.insert("xyz"));
        assertEquals(14, table1.size());
        assertEquals(24, table1.capacity());
//        table1.printTable();
    }

    @Test
    void deleteTest() {
        table1.insert("abc");
        assertEquals(1, table1.size());
        Assertions.assertThrows(NullPointerException.class,()->{
            table1.delete(null);
        });
        Assertions.assertThrows(NullPointerException.class,()->{
            table2.insert(null);
        });
        assertFalse(table2.delete("abc"));
        assertTrue(table1.delete("abc"));
        assertEquals(0, table1.size());
        table1.insert("xyz");
        assertFalse(table1.delete("123"));
        assertTrue(table1.delete("xyz"));
    }

    @Test
    void lookupTest() {
        table1.insert("DSC 10");
        table1.insert("DSC");
        table1.insert("Hello");
        table1.insert("I");

        assertTrue(table1.lookup("DSC 10"));
        assertFalse(table1.lookup("123"));
        Assertions.assertThrows(NullPointerException.class,()->{
            table2.lookup(null);
        });
    }

    @Test
    void returnAllTest() {
        table1.insert("DSC 10");
        table1.insert("DSC");
        table1.insert("Hello");
        table1.insert("I");
        table1.insert("Mathematics");
        table1.insert("physics");
        assertArrayEquals(new String[]{"I","physics", "Hello","DSC 10", "Mathematics","DSC" },
                table1.returnAll());
    }

    @Test()
    void testgetstatslog(){
        table1.insert("DSC 10");
        table1.insert("DSC");
        table1.insert("Hello");
        table1.insert("I");
        table1.insert("am");
        table1.insert("Divyansh");
        table1.insert("How");
        table1.insert("DSC 20");
        table1.insert("DSC 30");
        table1.insert("Economics");
        table1.insert("Mathematics");
        table1.insert("physics");
        table1.insert("abc");
        table1.insert("abc");
        table1.insert("xyz");
        assertEquals("Before rehash # 1: load factor 1.08, 4 collision(s).\n",
                table1.getStatsLog());
    }

}