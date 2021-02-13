package com.anyoptional.collections;

import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {

    private <K, V> List<K> toKeyList(BinarySearchTree<K, V> tree) {
        if (tree.isEmpty()) return Collections.emptyList();
        List<K> list = new ArrayList<>();
        tree.traverseInOrder($0 -> list.add($0.getKey()));
        return list;
    }

    @Test
    public void testRootNode() {
        BinarySearchTree<Integer, String> tree = new BinarySearchTree<>();
        tree.insert(8, "A");
        assertFalse(tree.isEmpty());
        assertEquals(tree.size(), 1);
        assertEquals(tree._root.minimum().entry.getValue(), "A");
        assertEquals(tree._root.maximum().entry.getValue(), "A");
        assertEquals(tree.height(), 0);
        assertTrue(tree.containsKey(8));
        assertEquals("A", tree.searchValue(8));
        assertNull(tree.searchValue(10));
        assertEquals(Collections.singletonList(8), toKeyList(tree));
    }

    @Test
    public void testCreateFromArray() {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(8, 5, 10, 3, 12, 9, 6, 16));
        assertEquals(tree.size(), 8);
        assertEquals(toKeyList(tree), Arrays.asList(3, 5, 6, 8, 9, 10, 12, 16));

        assertTrue(tree.containsKey(9));
        assertNull(tree.searchBinaryNode(99));
        assertFalse(tree.containsKey(99));

        assertEquals((int) tree._root.minimum().entry.getKey(), 3);
        assertEquals((int) tree._root.maximum().entry.getKey(), 16);

        assertEquals(tree.height(), 3);

        BinaryNode<Integer, Integer> node1 = tree.searchBinaryNode(16);
        assertNotNull(node1);
        assertEquals(node1.height, 0);

        BinaryNode<Integer, Integer> node2 = tree.searchBinaryNode(12);
        assertNotNull(node2);
        assertEquals(node2.height, 1);

        BinaryNode<Integer, Integer> node3 = tree.searchBinaryNode(10);
        assertNotNull(node3);
        assertEquals(node3.height, 2);
    }

    @Test
    public void testInsert() {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        tree.insert(8, 8);

        tree.insert(5, 5);
        assertEquals(tree.size(), 2);
        assertEquals(tree.height(), 1);

        BinaryNode<Integer, Integer> node1 = tree.searchBinaryNode(5);
        assertNotNull(node1);
        assertEquals(node1.height, 0);

        tree.insert(10, 10);
        assertEquals(tree.size(), 3);
        assertEquals(tree.height(), 1);

        BinaryNode<Integer, Integer> node2 = tree.searchBinaryNode(10);
        assertNotNull(node2);
        assertEquals(node2.height, 0);

        tree.insert(3, 3);
        assertEquals(tree.size(), 4);
        assertEquals(tree.height(), 2);

        BinaryNode<Integer, Integer> node3 = tree.searchBinaryNode(3);
        assertNotNull(node3);
        assertEquals(node3.height, 0);
        assertEquals(node1.height, 1);

        assertEquals((int) tree._root.minimum().entry.getValue(), 3);
        assertEquals((int) tree._root.maximum().entry.getValue(), 10);
        assertEquals(Arrays.asList(3, 5, 8, 10), toKeyList(tree));
    }

    @Test
    public void testInsertDuplicates() {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(8, 5, 10));
        tree.insert(8, 8);
        tree.insert(5, 5);
        tree.insert(10, 10);
        assertEquals(tree.size(), 6);
        assertEquals(Arrays.asList(5, 5, 8, 8, 10, 10), toKeyList(tree));
    }

    @Test
    public void testTraversing() {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(8, 5, 10, 3, 12, 9, 6, 16));

        List<Integer> inOrder = new ArrayList<>();
        tree._root.traverseInOrder($0 -> inOrder.add($0.entry.getKey()));
        assertEquals(inOrder, Arrays.asList(3, 5, 6, 8, 9, 10, 12, 16));

        List<Integer> preOrder = new ArrayList<>();
        tree._root.traversePreOrder($0 -> preOrder.add($0.entry.getKey()));
        assertEquals(preOrder, Arrays.asList(8, 5, 3, 6, 10, 9, 12, 16));

        List<Integer> postOrder = new ArrayList<>();
        tree._root.traversePostOrder($0 -> postOrder.add($0.entry.getKey()));
        assertEquals(postOrder, Arrays.asList(3, 6, 5, 9, 16, 12, 10, 8));
    }

    @Test
    public void testInsertSorted() {
        Comparator<Integer> comparator = Comparator.comparingInt($0 -> $0);
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        List<Integer> integers = Arrays.asList(8, 5, 10, 3, 12, 9, 6, 16);
        Collections.sort(integers, comparator);
        tree.addAll(integers);
        assertEquals(tree.size(), 8);
        assertEquals(toKeyList(tree), Arrays.asList(3, 5, 6, 8, 9, 10, 12, 16));

        assertEquals((int) tree._root.minimum().entry.getKey(), 3);
        assertEquals((int) tree._root.maximum().entry.getKey(), 16);

        assertEquals(tree.height(), 7);

        BinaryNode<Integer, Integer> node1 = tree.searchBinaryNode(16);
        assertNotNull(node1);
        assertEquals(node1.height, 0);
    }

    @Test
    public void testRemoveLeaf() {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(8, 5, 10, 4));

        BinaryNode<Integer, Integer> node8 = tree.searchBinaryNode(8);
        assertEquals(tree._root, node8);

        BinaryNode<Integer, Integer> node10 = tree.searchBinaryNode(10);
        assertNull(node10.left);
        assertNull(node10.right);
        assertTrue(tree._root.right == node10);

        BinaryNode<Integer, Integer> node5 = tree.searchBinaryNode(5);
        assertTrue(tree._root.left == node5);

        BinaryNode<Integer, Integer> node4 = tree.searchBinaryNode(4);
        assertTrue(node5.left == node4);
        assertNull(node5.right);

        tree.remove(4);
        assertNull(node5.left);

        tree.remove(5);
        assertNull(tree._root.left);

        tree.remove(10);
        assertNull(tree._root.right);

        assertEquals(tree.size(), 1);
        assertEquals(toKeyList(tree), Collections.singletonList(8));
    }

    @Test
    public void testRemoveOneChildLeft() {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(8, 5, 10, 4, 9));
        BinaryNode<Integer, Integer> node4 = tree.searchBinaryNode(4);
        BinaryNode<Integer, Integer> node5 = tree.searchBinaryNode(5);
        assertTrue(node5.left == node4);
        assertTrue(node5 == node4.parent);

        tree.remove(5);
        assertTrue(tree._root.left == node4);
        assertTrue(tree._root == node4.parent);
        assertNull(node4.left);
        assertNull(node4.right);
        assertEquals(tree.size(), 4);
        assertEquals(toKeyList(tree), Arrays.asList(4, 8, 9, 10));

        BinaryNode<Integer, Integer> node9 = tree.searchBinaryNode(9);
        BinaryNode<Integer, Integer> node10 = tree.searchBinaryNode(10);
        assertTrue(node10.left == node9);
        assertTrue(node10 == node9.parent);

        tree.remove(10);
        assertTrue(tree._root.right == node9);
        assertTrue(tree._root == node9.parent);
        assertNull(node9.left);
        assertNull(node9.right);
        assertEquals(tree.size(), 3);
        assertEquals(toKeyList(tree), Arrays.asList(4, 8, 9));
    }

    @Test
    public void testRemoveOneChildRight() {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(8, 5, 10, 6, 11));

        BinaryNode<Integer, Integer> node6 = tree.searchBinaryNode(6);
        BinaryNode<Integer, Integer> node5 = tree.searchBinaryNode(5);
                assertTrue(node5.right == node6);
        assertTrue(node5 == node6.parent);

        tree.remove(5);
        assertTrue(tree._root.left == node6);
        assertTrue(tree._root == node6.parent);
        assertNull(node6.left);
        assertNull(node6.right);
        assertEquals(tree.size(), 4);
        assertEquals(toKeyList(tree), Arrays.asList(6, 8, 10, 11));

        BinaryNode<Integer, Integer> node11 = tree.searchBinaryNode(11);
        BinaryNode<Integer, Integer> node10 = tree.searchBinaryNode(10);
                assertTrue(node10.right == node11);
        assertTrue(node10 == node11.parent);

        tree.remove(10);
        assertTrue(tree._root.right == node11);
        assertTrue(tree._root == node11.parent);
        assertNull(node11.left);
        assertNull(node11.right);
        assertEquals(tree.size(), 3);
        assertEquals(toKeyList(tree), Arrays.asList(6, 8, 11));
    }

    @Test
    public void testRemoveTwoChildrenSimple() {
        BinarySearchTree<Integer, Integer> tree =new BinarySearchTree<>();
        tree.addAll(Arrays.asList(8, 5, 10, 4, 6, 9, 11));
        BinaryNode<Integer, Integer> node4 = tree.searchBinaryNode(4);
        BinaryNode<Integer, Integer> node5 = tree.searchBinaryNode(5);
        BinaryNode<Integer, Integer> node6 = tree.searchBinaryNode(6);
                assertTrue(node5.left == node4);
        assertTrue(node5.right == node6);
        assertTrue(node5 == node4.parent);
        assertTrue(node5 == node6.parent);

        tree.remove(5);
        assertEquals((int)node5.entry.getKey(), 6);
        assertEquals(tree._root.left.entry, node6.entry);
    }

    @Test
    public void testRemoveRoot() {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(8, 5, 10, 4, 9, 20, 11, 15, 13));

        BinaryNode<Integer, Integer> oldRoot = tree.searchBinaryNode(8);

        BinaryNode<Integer, Integer> node9 = tree.searchBinaryNode(9);
        tree.remove(8);

        assertEquals((int)tree._root.entry.getKey(), 9);
        assertEquals(tree._root.size(), 8);
        assertEquals(toKeyList(tree), Arrays.asList(4, 5, 9, 10, 11, 13, 15, 20));

        assertEquals((int)oldRoot.entry.getKey(), 9);
        assertEquals(oldRoot.size(), 8);
    }

    @Test
    public void testPredecessor() {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(3, 1, 2, 5, 4));

        BinaryNode<Integer, Integer> node = tree.searchBinaryNode(5);

        assertEquals((int) node.entry.getKey(), 5);
        assertEquals((int) node.predecessor().entry.getKey(), 4);
        assertEquals((int) node.predecessor().predecessor().entry.getKey(), 3);
        assertEquals((int) node.predecessor().predecessor().predecessor().entry.getKey(), 2);
        assertEquals((int) node.predecessor().predecessor().predecessor().predecessor().entry.getKey(), 1);
        assertNull(node.predecessor().predecessor().predecessor().predecessor().predecessor());
    }

    @Test
    public void testSuccessor() {
        BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
        tree.addAll(Arrays.asList(3, 1, 2, 5, 4));
        BinaryNode<Integer, Integer> node = tree.searchBinaryNode(1);

        assertEquals((int) node.entry.getKey(), 1);
        assertEquals((int) node.successor().entry.getKey(), 2);
        assertEquals((int) node.successor().successor().entry.getKey(), 3);
        assertEquals((int) node.successor().successor().successor().entry.getKey(), 4);
        assertEquals((int) node.successor().successor().successor().successor().entry.getKey(), 5);
        assertNull(node.successor().successor().successor().successor().successor());
    }


    @Test
    public void testPrint() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            BinarySearchTree<Integer, Integer> tree = new BinarySearchTree<>();
            for (int j = 0; j < 10; j++) {
                tree.insert(random.nextInt(100), random.nextInt(100));
            }
            System.out.println(tree);
        }
    }

}