package com.anyoptional.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class BTreeTest {

    @Test
    public void testEmpty() {
        BTree<Integer, Integer> bTree = new BTree<>();
        assertTrue(bTree.isEmpty());
        assertEquals(0, bTree.size());
        assertNull(bTree._root);
    }

    @Test
    public void testInsert() {
        BTree<Integer, Integer> bTree = new BTree<>();
        bTree.insert(3, null);
        bTree.insert(2, null);
        bTree.insert(1, null);
        bTree.insert(7, null);
        bTree.insert(5, null);
        bTree.insert(10, null);
        bTree.insert(9, null);
        assertFalse(bTree.isEmpty());
        assertEquals(7, bTree.size());
        assertNotNull(bTree._root);

        assertEquals(1, bTree._root.entries.size());
        assertEquals(2, bTree._root.children.size());
        assertEquals(5, (int)bTree._root.entries.get(0).getKey());

        BTree.Node<Integer, Integer> node5 =  bTree.searchNode(5).first;
        assertEquals(bTree._root, node5);
        assertNotNull(node5);
        assertNull(node5.parent);
        assertEquals(node5.entries.size(), 1);
        assertEquals((int)node5.entries.get(0).getKey(), 5);
        assertEquals(node5.children.size(), 2);
        assertNotNull(node5.children.get(0));
        assertEquals(2, (int)node5.children.get(0).entries.get(0).getKey());
        assertNotNull(node5.children.get(1));
        assertEquals(9, (int)node5.children.get(1).entries.get(0).getKey());

        BTree.Node<Integer, Integer> node2 =  bTree.searchNode(2).first;
        assertNotNull(node2);
        assertEquals(node2.parent, node5);
        assertEquals(node2.entries.size(), 1);
        assertEquals((int)node2.entries.get(0).getKey(), 2);
        assertEquals(node2.children.size(), 2);
        assertNotNull(node2.children.get(0));
        assertEquals(1, (int)node2.children.get(0).entries.get(0).getKey());
        assertNotNull(node2.children.get(1));
        assertEquals(3, (int)node2.children.get(1).entries.get(0).getKey());

        BTree.Node<Integer, Integer> node9 =  bTree.searchNode(9).first;
        assertNotNull(node9);
        assertEquals(node9.parent, node5);
        assertEquals(node9.entries.size(), 1);
        assertEquals((int)node9.entries.get(0).getKey(), 9);
        assertEquals(node9.children.size(), 2);
        assertNotNull(node9.children.get(0));
        assertEquals(7, (int)node9.children.get(0).entries.get(0).getKey());
        assertNotNull(node9.children.get(1));
        assertEquals(10, (int)node9.children.get(1).entries.get(0).getKey());

        BTree.Node<Integer, Integer> node1 =  bTree.searchNode(1).first;
        assertNotNull(node1);
        assertEquals(node1, node2.children.get(0));
        assertEquals(node1.parent, node2);
        assertEquals(node1.entries.size(), 1);
        assertEquals((int)node1.entries.get(0).getKey(), 1);
        assertEquals(node1.children.size(), 2);
        assertNull(node1.children.get(0));
        assertNull(node1.children.get(1));

        BTree.Node<Integer, Integer> node3 =  bTree.searchNode(3).first;
        assertNotNull(node3);
        assertEquals(node3, node2.children.get(1));
        assertEquals(node3.parent, node2);
        assertEquals(node3.entries.size(), 1);
        assertEquals((int)node3.entries.get(0).getKey(), 3);
        assertEquals(node3.children.size(), 2);
        assertNull(node3.children.get(0));
        assertNull(node3.children.get(1));

        BTree.Node<Integer, Integer> node7 =  bTree.searchNode(7).first;
        assertNotNull(node7);
        assertEquals(node7, node9.children.get(0));
        assertEquals(node7.parent, node9);
        assertEquals(node7.entries.size(), 1);
        assertEquals((int)node7.entries.get(0).getKey(), 7);
        assertEquals(node7.children.size(), 2);
        assertNull(node7.children.get(0));
        assertNull(node7.children.get(1));

        BTree.Node<Integer, Integer> node10 =  bTree.searchNode(10).first;
        assertNotNull(node10);
        assertEquals(node10, node9.children.get(1));
        assertEquals(node10.parent, node9);
        assertEquals(node10.entries.size(), 1);
        assertEquals((int)node10.entries.get(0).getKey(), 10);
        assertEquals(node10.children.size(), 2);
        assertNull(node10.children.get(0));
        assertNull(node10.children.get(1));
    }

    @Test
    public void testRandomInsert() {
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            BTree<Integer, Integer> bTree = new BTree<>(3 + random.nextInt(5));
            for (int j = 0; j < 1000; j++) {
                bTree.insert(random.nextInt(100), j);
                List<Integer> keys = new ArrayList<>();
                bTree.traverseInOrder(new Consumer<Entry<Integer, Integer>>() {
                    @Override
                    public void accept(Entry<Integer, Integer> integerIntegerEntry) {
                        keys.add(integerIntegerEntry.getKey());
                    }
                });
                assertEquals(bTree.size(), j + 1);
                assertEquals(keys.size(), j + 1);
                assertSorted(keys);
            }
        }
    }

    @Test
    public void testRemoveRoot() {
        BTree<Integer, Integer> bTree = new BTree<>();
        bTree.insert(3, null);
        bTree.insert(2, null);
        bTree.insert(1, null);
        bTree.insert(7, null);
        bTree.insert(5, null);
        bTree.insert(10, null);
        bTree.insert(9, null);
        assertEquals(bTree.size(), 7);
        assertFalse(bTree.isEmpty());

        BTree.Node<Integer, Integer> node5 =  bTree.searchNode(5).first;
        assertEquals(bTree._root, node5);
        assertNotNull(node5);

        Entry<Integer, Integer> remove5 = bTree.remove(5);
        assertEquals(bTree.size(), 6);
        assertFalse(bTree.isEmpty());
        assertEquals(5, (int)remove5.getKey());
        assertNull(remove5.getValue());

        BTree.Node<Integer, Integer> node2 =  bTree.searchNode(2).first;
        assertEquals(bTree._root, node2);
        assertNotNull(node2);

        BTree.Node<Integer, Integer> node7 =  bTree.searchNode(7).first;
        assertEquals(node2, node7);
        assertEquals(bTree._root, node7);
        assertNotNull(node7);
        assertEquals(node7.entries.size(), 2);
        assertEquals(node7.children.size(), 3);

        Entry<Integer, Integer> remove2 = bTree.remove(2);
        assertEquals(bTree.size(), 5);
        assertFalse(bTree.isEmpty());
        assertEquals(2, (int)remove2.getKey());

        node7 = bTree.searchNode(7).first;
        BTree.Node<Integer, Integer> node3 =  bTree.searchNode(3).first;
        BTree.Node<Integer, Integer> node9 =  bTree.searchNode(9).first;
        assertNotEquals(bTree._root, node7);
        assertEquals(bTree._root, node3);
        assertNotNull(node3);
        assertEquals(node3, node9);
        assertEquals(bTree._root, node9);
        assertNotNull(node9);
        assertEquals(node9.entries.size(), 2);
        assertEquals(node9.children.size(), 3);

        Entry<Integer, Integer> remove9 = bTree.remove(9);
        assertEquals(bTree.size(), 4);
        assertFalse(bTree.isEmpty());
        assertEquals(9, (int)remove9.getKey());

        node3 = bTree.searchNode(3).first;
        assertEquals(bTree._root, node3);
        assertEquals(node3.entries.size(), 1);
        assertEquals(node3.children.size(), 2);

        Entry<Integer, Integer> remove3 = bTree.remove(3);
        assertEquals(bTree.size(), 3);
        assertFalse(bTree.isEmpty());
        assertEquals(3, (int)remove3.getKey());

        node7 = bTree.searchNode(7).first;
        assertEquals(bTree._root, node7);
        assertEquals(node7.entries.size(), 1);
        assertEquals(node7.children.size(), 2);

        Entry<Integer, Integer> remove7 = bTree.remove(7);
        assertEquals(bTree.size(), 2);
        assertFalse(bTree.isEmpty());
        assertEquals(7, (int)remove7.getKey());

        BTree.Node<Integer, Integer> node1 = bTree.searchNode(1).first;
        assertEquals(bTree._root, node1);
        assertEquals(node1.entries.size(), 2);
        assertEquals(node1.children.size(), 3);
        BTree.Node<Integer, Integer> node10 = bTree.searchNode(10).first;
        assertEquals(bTree._root, node10);
        assertEquals(node1, node10);
        assertEquals(node10.entries.size(), 2);
        assertEquals(node10.children.size(), 3);

        Entry<Integer, Integer> remove1 = bTree.remove(1);
        assertEquals(bTree.size(), 1);
        assertFalse(bTree.isEmpty());
        assertEquals(1, (int)remove1.getKey());

        node10 = bTree.searchNode(10).first;
        assertEquals(bTree._root, node10);
        assertEquals(node10.entries.size(), 1);
        assertEquals(node10.children.size(), 2);

        Entry<Integer, Integer> remove10 = bTree.remove(10);
        assertEquals(bTree.size(), 0);
        assertTrue(bTree.isEmpty());
        assertEquals(10, (int)remove10.getKey());
        assertNull(bTree._root);
    }

    @Test
    public void testRandomRemove() {
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            BTree<Integer, Integer> bTree = new BTree<>(3 + random.nextInt(5));
            for (int j = 0; j < 1000; j++) {
                bTree.insert(random.nextInt(100), j);
            }
            int size = bTree.size();
            for (int j = 0; j < 10000; j++) {
                Entry<Integer, Integer> remove = bTree.remove(random.nextInt(100));
                if (remove != null) {
                    size -= 1;
                    assertEquals(bTree.size(), size);
                    assertEquals(size == 0, bTree.isEmpty());
                    List<Integer> keys = new ArrayList<>();
                    bTree.traverseInOrder(new Consumer<Entry<Integer, Integer>>() {
                        @Override
                        public void accept(Entry<Integer, Integer> integerIntegerEntry) {
                            keys.add(integerIntegerEntry.getKey());
                        }
                    });
                    assertEquals(keys.size(), size);
                    assertSorted(keys);
                }
            }
        }
    }

    private <K extends Comparable<K>> void assertSorted(List<K> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1).compareTo(list.get(i)) > 0) {
                System.out.println(list.get(i-1));
                System.out.println(list.get(i));
            }
             assertTrue(list.get(i - 1).compareTo(list.get(i)) <= 0);
        }
    }
}