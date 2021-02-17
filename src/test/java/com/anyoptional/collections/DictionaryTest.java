package com.anyoptional.collections;

import org.junit.Test;

import static org.junit.Assert.*;

public class DictionaryTest {

    @Test
    public void testPut() {
        Dictionary<String, String> dictionary = new Dictionary<>();
        assertTrue(dictionary.isEmpty());
        assertEquals(0, dictionary.size());

        dictionary.put("a", "a");
        assertFalse(dictionary.isEmpty());
        assertEquals(1, dictionary.size());
        assertTrue(dictionary.containsKey("a"));
        assertEquals(dictionary.get("a"), "a");

        dictionary.put("b", "b");
        assertFalse(dictionary.isEmpty());
        assertEquals(2, dictionary.size());
        assertTrue(dictionary.containsKey("b"));
        assertEquals(dictionary.get("b"), "b");

        dictionary.put("c", "c");
        assertFalse(dictionary.isEmpty());
        assertEquals(3, dictionary.size());
        assertTrue(dictionary.containsKey("c"));
        assertEquals(dictionary.get("c"), "c");

        dictionary.put("d", "d");
        assertFalse(dictionary.isEmpty());
        assertEquals(4, dictionary.size());
        assertTrue(dictionary.containsKey("d"));
        assertEquals(dictionary.get("d"), "d");

        dictionary.put("e", "e");
        assertFalse(dictionary.isEmpty());
        assertEquals(5, dictionary.size());
        assertTrue(dictionary.containsKey("e"));
        assertEquals(dictionary.get("e"), "e");
    }

    @Test
    public void testPutDuplicate() {
        Dictionary<String, String> dictionary = new Dictionary<>();
        dictionary.put("a", "a");
        assertFalse(dictionary.isEmpty());
        assertEquals(1, dictionary.size());
        assertTrue(dictionary.containsKey("a"));
        assertEquals(dictionary.get("a"), "a");

        dictionary.put("a", "b");
        assertFalse(dictionary.isEmpty());
        assertEquals(1, dictionary.size());
        assertTrue(dictionary.containsKey("a"));
        assertEquals(dictionary.get("a"), "b");

        dictionary.put("a", "c");
        assertFalse(dictionary.isEmpty());
        assertEquals(1, dictionary.size());
        assertTrue(dictionary.containsKey("a"));
        assertEquals(dictionary.get("a"), "c");
    }

    @Test
    public void testRemove() {
        Dictionary<String, String> dictionary = new Dictionary<>();
        dictionary.put("a", "a");
        dictionary.put("b", "b");
        dictionary.put("c", "c");
        dictionary.put("d", "d");
        dictionary.put("e", "e");
        assertFalse(dictionary.isEmpty());
        assertEquals(5, dictionary.size());

        assertNull(dictionary.remove("f"));
        assertFalse(dictionary.containsKey("f"));
        assertFalse(dictionary.isEmpty());
        assertEquals(5, dictionary.size());

        assertNull(dictionary.remove("g"));
        assertFalse(dictionary.containsKey("g"));
        assertFalse(dictionary.isEmpty());
        assertEquals(5, dictionary.size());

        assertNull(dictionary.remove("h"));
        assertFalse(dictionary.containsKey("h"));
        assertFalse(dictionary.isEmpty());
        assertEquals(5, dictionary.size());

        assertNull(dictionary.remove("i"));
        assertFalse(dictionary.containsKey("i"));
        assertFalse(dictionary.isEmpty());
        assertEquals(5, dictionary.size());

        assertNull(dictionary.remove("j"));
        assertFalse(dictionary.containsKey("j"));
        assertFalse(dictionary.isEmpty());
        assertEquals(5, dictionary.size());

        assertNull(dictionary.remove("k"));
        assertFalse(dictionary.containsKey("k"));
        assertFalse(dictionary.isEmpty());
        assertEquals(5, dictionary.size());

        assertTrue(dictionary.containsKey("a"));
        assertEquals("a", dictionary.remove("a"));
        assertFalse(dictionary.containsKey("a"));
        assertFalse(dictionary.isEmpty());
        assertEquals(4, dictionary.size());

        assertNull(dictionary.remove("a"));
        assertFalse(dictionary.isEmpty());
        assertEquals(4, dictionary.size());

        assertTrue(dictionary.containsKey("b"));
        assertEquals("b", dictionary.remove("b"));
        assertFalse(dictionary.containsKey("b"));
        assertFalse(dictionary.isEmpty());
        assertEquals(3, dictionary.size());

        assertNull(dictionary.remove("b"));
        assertFalse(dictionary.isEmpty());
        assertEquals(3, dictionary.size());

        assertTrue(dictionary.containsKey("c"));
        assertEquals("c", dictionary.remove("c"));
        assertFalse(dictionary.containsKey("c"));
        assertFalse(dictionary.isEmpty());
        assertEquals(2, dictionary.size());

        assertNull(dictionary.remove("c"));
        assertFalse(dictionary.isEmpty());
        assertEquals(2, dictionary.size());

        assertTrue(dictionary.containsKey("d"));
        assertEquals("d", dictionary.remove("d"));
        assertFalse(dictionary.containsKey("d"));
        assertFalse(dictionary.isEmpty());
        assertEquals(1, dictionary.size());

        assertNull(dictionary.remove("d"));
        assertFalse(dictionary.isEmpty());
        assertEquals(1, dictionary.size());

        assertTrue(dictionary.containsKey("e"));
        assertEquals("e", dictionary.remove("e"));
        assertFalse(dictionary.containsKey("e"));
        assertTrue(dictionary.isEmpty());
        assertEquals(0, dictionary.size());

        assertNull(dictionary.remove("e"));
        assertTrue(dictionary.isEmpty());
        assertEquals(0, dictionary.size());
    }

    @Test
    public void testRemoveDuplicate() {
        Dictionary<String, String> dictionary = new Dictionary<>();
        dictionary.put("a", "a");
        dictionary.put("a", "b");
        dictionary.put("a", "c");
        assertFalse(dictionary.isEmpty());
        assertEquals(1, dictionary.size());
        assertTrue(dictionary.containsKey("a"));
        assertEquals(dictionary.get("a"), "c");

        assertEquals("c", dictionary.remove("a"));
        assertFalse(dictionary.containsKey("a"));
        assertTrue(dictionary.isEmpty());
        assertEquals(0, dictionary.size());
    }

    @Test
    public void testNull() {
        Dictionary<String, String> dictionary = new Dictionary<>();
        dictionary.put("a", null);
        dictionary.put("b", null);
        dictionary.put("c", null);
        dictionary.put("d", null);
        dictionary.put("e", null);
        System.out.println(dictionary);
        assertTrue(dictionary.containsValue(null));
        assertNull(dictionary.remove("a"));
        assertTrue(dictionary.containsValue(null));
        assertNull(dictionary.remove("b"));
        assertTrue(dictionary.containsValue(null));
        assertNull(dictionary.remove("c"));
        assertTrue(dictionary.containsValue(null));
        assertNull(dictionary.remove("d"));
        assertTrue(dictionary.containsValue(null));
        assertNull(dictionary.remove("e"));
        assertFalse(dictionary.containsValue(null));
        assertTrue(dictionary.isEmpty());
        assertEquals(0, dictionary.size());
    }

}