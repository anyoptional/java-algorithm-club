package com.anyoptional.collections;

import com.anyoptional.util.ReflectionUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

public class HeapTest {

    @Test
    public void testInsertAsc() {
        // [ 10, 7, 5, 2, 1 ]
        Heap<Integer> heap = new Heap<>(Comparator.comparingInt($0 -> $0));
        heap.insert(2);
        assertFalse(heap.isEmpty());
        assertEquals(1, heap.size());
        assertEquals(2, (int) heap.peek());
        heap.insert(10);
        assertEquals(10, (int) heap.peek());
        heap.insert(5);
        assertEquals(10, (int) heap.peek());
        heap.insert(7);
        assertEquals(10, (int) heap.peek());
        heap.insert(1);
        assertEquals(10, (int) heap.peek());
    }

    @Test
    public void testInsertDesc() {
        // [ 10, 7, 5, 2, 1 ]
        Heap<Integer> heap = new Heap<>(($0, $1) -> $1 - $0);
        heap.insert(2);
        assertFalse(heap.isEmpty());
        assertEquals(1, heap.size());
        assertEquals(2, (int) heap.peek());
        System.out.println(heap); // 2
        heap.insert(10);
        assertEquals(2, (int) heap.peek());
        System.out.println(heap); // 2 10
        heap.insert(5);
        assertEquals(2, (int) heap.peek());
        System.out.println(heap); // 2 10 5
        heap.insert(7);
        assertEquals(2, (int) heap.peek());
        System.out.println(heap); // 2 7 5 10
        heap.insert(1);
        assertEquals(1, (int) heap.peek());
        System.out.println(heap); // 1 2 5 10 7
    }


    boolean verifyMaxHeap(Heap<Integer> h) {
        for (int i = 0; i < h.size(); i++) {
            int left = h.leftChildIndexOf(i);
            int right = h.rightChildIndexOf(i);
            int parent = h.parentIndexOf(i);
            if (left < h.size() && getStorage(h).get(i) < getStorage(h).get(left)) {
                return false;
            }
            if (right < h.size() && getStorage(h).get(i) < getStorage(h).get(right)) {
                return false;
            }
            if (i > 0 && getStorage(h).get(parent) < getStorage(h).get(i)) {
                return false;
            }
        }
        return true;
    }

    boolean verifyMinHeap(Heap<Integer> h) {
        for (int i = 0; i < h.size(); i++) {
            int left = h.leftChildIndexOf(i);
            int right = h.rightChildIndexOf(i);
            int parent = h.parentIndexOf(i);
            if (left < h.size() && getStorage(h).get(i) > getStorage(h).get(left)) {
                return false;
            }
            if (right < h.size() && getStorage(h).get(i) > getStorage(h).get(right)) {
                return false;
            }
            if (i > 0 && getStorage(h).get(parent) > getStorage(h).get(i)) {
                return false;
            }
        }
        return true;
    }

    boolean isPermutation(List<Integer> a1, List<Integer> a2) {
        if (a1.size() != a2.size()) {
            return false;
        }
        while (a1.size() > 0) {
            int i = a2.indexOf(a1.get(0));
            if (i != -1) {
                a1.remove(0);
                a2.remove(i);
            } else {
                return false;
            }
        }
        return a2.size() == 0;
    }

    @Test
    public void testEmptyHeap() {
        Heap<Integer> heap = new Heap<>(($0, $1) -> $1 - $0);
        assertTrue(heap.isEmpty());
        assertEquals(heap.size(), 0);
        assertNull(heap.peek());
        assertNull(heap.remove());
    }

    @Test
    public void testIsEmpty() {
        Heap<Integer> heap = new Heap<>(($0, $1) -> $1 - $0);
        assertTrue(heap.isEmpty());
        heap.insert(1);
        assertFalse(heap.isEmpty());
        heap.remove();
        assertTrue(heap.isEmpty());
    }

    @Test
    public void testCount() {
        Heap<Integer> heap = new Heap<>(Comparator.comparingInt($0 -> $0));
        assertEquals(0, heap.size());
        heap.insert(1);
        assertEquals(1, heap.size());
    }

    @Test
    public void testMaxHeapOneElement() {
        Heap<Integer> heap = new Heap<>(Comparator.comparingInt($0 -> $0));
        heap.addAll(Collections.singleton(10));
        assertTrue(verifyMaxHeap(heap));
        assertTrue(verifyMinHeap(heap));
        assertFalse(heap.isEmpty());
        assertEquals(heap.size(), 1);
        assertEquals((int) heap.peek(), 10);
    }

    @Test
    public void testCreateMaxHeap() {
        Heap<Integer> h1 = new Heap<>(($0, $1) -> $0 - $1);
        h1.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        assertTrue(verifyMaxHeap(h1));
        assertFalse(verifyMinHeap(h1));
        assertEquals(getStorage(h1), Arrays.asList(7, 5, 6, 4, 2, 1, 3));
        assertFalse(h1.isEmpty());
        assertEquals(h1.size(), 7);
        assertEquals((int) h1.peek(), 7);

        Heap<Integer> h2 = new Heap<>(Comparator.comparingInt($0 -> $0));
        h2.addAll(Arrays.asList(7, 6, 5, 4, 3, 2, 1));
        assertTrue(verifyMaxHeap(h2));
        assertFalse(verifyMinHeap(h2));
        assertEquals(getStorage(h2), Arrays.asList(7, 6, 5, 4, 3, 2, 1));
        assertFalse(h2.isEmpty());
        assertEquals(h2.size(), 7);
        assertEquals((int) h2.peek(), 7);

        Heap<Integer> h3 = new Heap<>(Comparator.comparingInt($0 -> $0));
        h3.addAll(Arrays.asList(4, 1, 3, 2, 16, 9, 10, 14, 8, 7));
        assertTrue(verifyMaxHeap(h3));
        assertFalse(verifyMinHeap(h3));
        assertEquals(getStorage(h3), Arrays.asList(16, 14, 10, 8, 7, 9, 3, 2, 4, 1));
        assertFalse(h3.isEmpty());
        assertEquals(h3.size(), 10);
        assertEquals((int) h3.peek(), 16);

        Heap<Integer> h4 = new Heap<>(Comparator.comparingInt($0 -> $0));
        h4.addAll(Arrays.asList(27, 17, 3, 16, 13, 10, 1, 5, 7, 12, 4, 8, 9, 0));
        assertTrue(verifyMaxHeap(h4));
        assertFalse(verifyMinHeap(h4));
        assertFalse(h4.isEmpty());
        assertEquals(h4.size(), 14);
        assertEquals((int) h4.peek(), 27);
    }

    /**
     * 1
     * 2 3
     * 4
     */
    @Test
    public void testCreateMinHeap() {
        Heap<Integer> h1 = new Heap<>(($0, $1) -> $1 - $0);
        h1.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        assertFalse(verifyMaxHeap(h1));
        assertTrue(verifyMinHeap(h1));
        assertEquals(getStorage(h1), Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        assertFalse(h1.isEmpty());
        assertEquals(h1.size(), 7);
        assertEquals((int) h1.peek(), 1);

        Heap<Integer> h2 = new Heap<>(($0, $1) -> $1 - $0);
        h2.addAll(Arrays.asList(7, 6, 5, 4, 3, 2, 1));
        assertFalse(verifyMaxHeap(h2));
        assertTrue(verifyMinHeap(h2));
        assertEquals(getStorage(h2), Arrays.asList(1, 3, 2, 4, 6, 7, 5));
        assertFalse(h2.isEmpty());
        assertEquals(h2.size(), 7);
        assertEquals((int) h2.peek(), 1);

        Heap<Integer> h3 = new Heap<>(($0, $1) -> $1 - $0);
        h3.addAll(Arrays.asList(4, 1, 3, 2, 16, 9, 10, 14, 8, 7));
        assertFalse(verifyMaxHeap(h3));
        assertTrue(verifyMinHeap(h3));
        assertFalse(h3.isEmpty());
        assertEquals(h3.size(), 10);
        assertEquals((int) h3.peek(), 1);

        Heap<Integer> h4 = new Heap<>(($0, $1) -> $1 - $0);
        h4.addAll(Arrays.asList(27, 17, 3, 16, 13, 10, 1, 5, 7, 12, 4, 8, 9, 0));
        assertFalse(verifyMaxHeap(h4));
        assertTrue(verifyMinHeap(h4));
        assertFalse(h4.isEmpty());
        assertEquals(h4.size(), 14);
        assertEquals((int) h4.peek(), 0);
    }

    @Test
    public void testCreateMaxHeapEqualnodes() {
        Heap<Integer> heap = new Heap<>(($0, $1) -> $0 - $1);
        heap.addAll(Arrays.asList(1, 1, 1, 1, 1));
        assertTrue(verifyMaxHeap(heap));
        assertTrue(verifyMinHeap(heap));
        assertEquals(getStorage(heap), Arrays.asList(1, 1, 1, 1, 1));
    }

    @Test
    public void testCreateMinHeapEqualnodes() {
        Heap<Integer> heap = new Heap<>(($0, $1) -> $1 - $0);
        heap.addAll(Arrays.asList(1, 1, 1, 1, 1));
        assertTrue(verifyMaxHeap(heap));
        assertTrue(verifyMinHeap(heap));
        assertEquals(getStorage(heap), Arrays.asList(1, 1, 1, 1, 1));
    }

    public List<Integer> randomArray(int n) {
        Random random = new Random();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(random.nextInt(10000000));
        }
        return result;
    }

    @Test
    public void testCreateRandomMaxHeap() {
        for (int i = 1; i <= 40; i++) {
            List<Integer> a = randomArray(i);
            Heap<Integer> h = new Heap<>(($0, $1) -> $0 - $1);
            h.addAll(a);
            assertTrue(verifyMaxHeap(h));
            assertFalse(h.isEmpty());
            assertEquals(h.size(), i);
            assertTrue(isPermutation(a, getStorage(h)));
        }
    }

    //
    @Test
    public void testCreateRandomMinHeap() {
        for (int i = 1; i <= 40; i++) {
            List<Integer> a = randomArray(i);
            Heap<Integer> h = new Heap<>(($0, $1) -> $1 - $0);
            h.addAll(a);
            assertTrue(verifyMinHeap(h));
            assertFalse(h.isEmpty());
            assertEquals(h.size(), i);
            assertTrue(isPermutation(a, getStorage(h)));
        }
    }


    @Test
    public void testRemoving() {
        Heap<Integer> h = new Heap<>();
        h.addAll(Arrays.asList(100, 50, 70, 10, 20, 60, 65));
        assertTrue(verifyMaxHeap(h));
        assertEquals(Arrays.asList(100, 50, 70, 10, 20, 60, 65), getStorage(h));

        //test index out of bounds
        Integer v = h.remove(10);
        assertNull(v);
        assertTrue(verifyMaxHeap(h));
        assertEquals(Arrays.asList(100, 50, 70, 10, 20, 60, 65), getStorage(h));

        int v1 = h.remove(5);
        assertEquals(v1, 60);
        assertTrue(verifyMaxHeap(h));
        assertEquals(Arrays.asList(100, 50, 70, 10, 20, 65), getStorage(h));

        int v2 = h.remove(4);
        assertEquals(v2, 20);
        assertTrue(verifyMaxHeap(h));
        assertEquals(Arrays.asList(100, 65, 70, 10, 50), getStorage(h));


        int v3 = h.remove(4);
        assertEquals(v3, 50);
        assertTrue(verifyMaxHeap(h));
        assertEquals(Arrays.asList(100, 65, 70, 10), getStorage(h));


        int v4 = h.remove(0);
        assertEquals(v4, 100);
        assertTrue(verifyMaxHeap(h));
        assertEquals(Arrays.asList(70, 65, 10), getStorage(h));


        assertEquals((int) h.peek(), 70);
        int v5 = h.remove();
        assertEquals(v5, 70);
        assertTrue(verifyMaxHeap(h));
        assertEquals(Arrays.asList(65, 10), getStorage(h));

        assertEquals((int) h.peek(), 65);
        int v6 = h.remove();
        assertEquals(v6, 65);
        assertTrue(verifyMaxHeap(h));
        assertEquals(Collections.singletonList(10), getStorage(h));

        assertEquals((int) h.peek(), 10);
        int v7 = h.remove();
        assertEquals(v7, 10);
        assertTrue(verifyMaxHeap(h));
        assertTrue(getStorage(h).isEmpty());

        assertNull(h.peek());
    }

    @Test
    public void testRemoveEmpty() {
        Heap<Integer> heap = new Heap<>();

        Integer removed = heap.remove();
        assertNull(removed);
    }

    @Test
    public void testRemoveRoot() {
        Heap<Integer> h = new Heap<>();
        h.addAll(Arrays.asList(15, 13, 9, 5, 12, 8, 7, 4, 0, 6, 2, 1));

        assertTrue(verifyMaxHeap(h));
        assertEquals(Arrays.asList(15, 13, 9, 5, 12, 8, 7, 4, 0, 6, 2, 1), getStorage(h));
        assertEquals((int) h.peek(), 15);
        int v = h.remove();
        assertEquals(v, 15);
        assertTrue(verifyMaxHeap(h));
        assertEquals(Arrays.asList(13, 12, 9, 5, 6, 8, 7, 4, 0, 1, 2), getStorage(h));

    }

    @Test
    public void testInsert() {
        Heap<Integer> h = new Heap<>();
        h.addAll(Arrays.asList(15, 13, 9, 5, 12, 8, 7, 4, 0, 6, 2, 1));
        assertTrue(verifyMaxHeap(h));
        assertEquals(Arrays.asList(15, 13, 9, 5, 12, 8, 7, 4, 0, 6, 2, 1), getStorage(h));

        h.insert(10);
        assertTrue(verifyMaxHeap(h));
        assertEquals(Arrays.asList(15, 13, 10, 5, 12, 9, 7, 4, 0, 6, 2, 1, 8), getStorage(h));
    }

    @Test
    public void testInsertArrayAndRemove() {
        Heap<Integer> heap = new Heap<>();
        heap.addAll(Arrays.asList(1, 3, 2, 7, 5, 9));
        assertEquals(Arrays.asList(9, 7, 2, 3, 5, 1), getStorage(heap));

        assertEquals(9, (int) heap.remove());
        assertEquals(7, (int) heap.remove());
        assertEquals(5, (int) heap.remove());
        assertEquals(3, (int) heap.remove());
        assertEquals(2, (int) heap.remove());
        assertEquals(1, (int) heap.remove());
        assertNull(heap.remove());
    }

    @Test
    public void testReplace() {
        Heap<Integer> h = new Heap<>();
        h.addAll(Arrays.asList(16, 14, 10, 8, 7, 9, 3, 2, 4, 1));
        assertTrue(verifyMaxHeap(h));

        h.replace(5, 13);
        assertTrue(verifyMaxHeap(h));

        h.replace(8, 18);
        assertTrue(verifyMaxHeap(h));

        h.replace(0, 1);
        assertTrue(verifyMaxHeap(h));

        // test replace out of range
        assertNull(h.replace(20, 1));
        assertTrue(verifyMaxHeap(h));
    }

    @Test
    public void testMergeMaxHeap() {
        Heap<Integer> h1 = new Heap<>();
        h1.addAll(Arrays.asList(1, 2, 3));

        Heap<Integer> h2 = new Heap<>();
        h2.addAll(Arrays.asList(4, 5, 6));

        h1.merge(h2);
        assertTrue(verifyMaxHeap(h1));
        assertFalse(verifyMinHeap(h1));
    }

    @Test
    public void testMergeMinHeap() {
        Heap<Integer> h1 = new Heap<>(($0, $1) -> $1 - $0);
        h1.addAll(Arrays.asList(5, 3, 1));

        Heap<Integer> h2 = new Heap<>();
        h2.addAll(Arrays.asList(6, 4, 2));

        h1.merge(h2);
        assertFalse(verifyMaxHeap(h1));
        assertTrue(verifyMinHeap(h1));
    }


    private <E> List<E> getStorage(Heap<E> heap) {
        Field field = ReflectionUtils.findField(Heap.class, "_storage");
        ReflectionUtils.makeAccessible(field);
        return (List<E>) ReflectionUtils.getField(field, heap);
    }
}