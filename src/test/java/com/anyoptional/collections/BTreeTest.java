package com.anyoptional.collections;

import org.junit.Test;

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
    public void testInsertDuplicate() {
        BTree<Integer, Integer> bTree = new BTree<>();
        bTree.insert(1, 11);
        bTree.insert(1, 22);
        bTree.insert(1, 33);
        bTree.insert(1, 44);
        bTree.insert(1, 55);
        bTree.insert(1, 66);
        bTree.insert(1, 77);

        assertFalse(bTree.isEmpty());
        assertEquals(7, bTree.size());
        assertNotNull(bTree._root);

        assertEquals(1, bTree._root.entries.size());
        assertEquals(2, bTree._root.children.size());
        assertEquals(77, (int)bTree._root.entries.get(0).getValue());

        BTree.Node<Integer, Integer> node7 =  bTree._root;
        assertEquals(bTree._root, node7);
        assertNotNull(node7);
        assertNull(node7.parent);
        assertEquals(node7.entries.size(), 1);
        assertEquals((int)node7.entries.get(0).getValue(), 77);
        assertEquals(node7.children.size(), 2);
        assertNotNull(node7.children.get(0));
        assertEquals(33, (int)node7.children.get(0).entries.get(0).getValue());
        assertNotNull(node7.children.get(1));
        assertEquals(55, (int)node7.children.get(1).entries.get(0).getValue());

        BTree.Node<Integer, Integer> node3 =  node7.children.get(0);
        assertNotNull(node3);
        assertEquals(node3.parent, node7);
        assertEquals(node3.entries.size(), 1);
        assertEquals((int)node3.entries.get(0).getValue(), 33);
        assertEquals(node3.children.size(), 2);
        assertNotNull(node3.children.get(0));
        assertEquals(11, (int)node3.children.get(0).entries.get(0).getValue());
        assertNotNull(node3.children.get(1));
        assertEquals(22, (int)node3.children.get(1).entries.get(0).getValue());

        BTree.Node<Integer, Integer> node5 =  node7.children.get(1);
        assertNotNull(node5);
        assertEquals(node5.parent, node7);
        assertEquals(node5.entries.size(), 1);
        assertEquals((int)node5.entries.get(0).getValue(), 55);
        assertEquals(node5.children.size(), 2);
        assertNotNull(node5.children.get(0));
        assertEquals(66, (int)node5.children.get(0).entries.get(0).getValue());
        assertNotNull(node5.children.get(1));
        assertEquals(44, (int)node5.children.get(1).entries.get(0).getValue());

        BTree.Node<Integer, Integer> node1 = node3.children.get(0);
        assertNotNull(node1);
        assertEquals(node1.parent, node3);
        assertEquals(node1.entries.size(), 1);
        assertEquals((int)node1.entries.get(0).getValue(), 11);
        assertEquals(node1.children.size(), 2);
        assertNull(node1.children.get(0));
        assertNull(node1.children.get(1));

        BTree.Node<Integer, Integer> node2 =  node3.children.get(1);
        assertNotNull(node2);
        assertEquals(node2.parent, node3);
        assertEquals(node2.entries.size(), 1);
        assertEquals((int)node2.entries.get(0).getValue(), 22);
        assertEquals(node2.children.size(), 2);
        assertNull(node2.children.get(0));
        assertNull(node2.children.get(1));

        BTree.Node<Integer, Integer> node6 =  node5.children.get(0);
        assertNotNull(node6);
        assertEquals(node6.parent, node5);
        assertEquals(node6.entries.size(), 1);
        assertEquals((int)node6.entries.get(0).getValue(), 66);
        assertEquals(node6.children.size(), 2);
        assertNull(node6.children.get(0));
        assertNull(node6.children.get(1));

        BTree.Node<Integer, Integer> node4 =  node5.children.get(1);
        assertNotNull(node4);
        assertEquals(node4.parent, node5);
        assertEquals(node4.entries.size(), 1);
        assertEquals((int)node4.entries.get(0).getValue(), 44);
        assertEquals(node4.children.size(), 2);
        assertNull(node4.children.get(0));
        assertNull(node4.children.get(1));
    }

    @Test
    public void testSearch() {

    }

}