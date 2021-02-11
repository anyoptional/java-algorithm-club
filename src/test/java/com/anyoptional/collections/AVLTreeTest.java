package com.anyoptional.collections;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class AVLTreeTest {

    @Test
    public void testInsertSorted() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        avlTree.addAll(Arrays.asList(1, 3, 5, 7, 9));
        assertFalse(avlTree.isEmpty());
        assertEquals(5, avlTree.size());
        assertTrue(avlTree._root == avlTree.searchBinaryNode(3));
        assertTrue(verifyBalanced(avlTree));
        System.out.println(avlTree);
    }

    @Test
    public void testRandomInsert() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            AVLTree<Integer, Integer> avlTree = new AVLTree<>();
            for (int j = 0; j < 300; j++) {
                avlTree.insert(random.nextInt(250), j);
                assertFalse(avlTree.isEmpty());
                assertEquals(j + 1, avlTree.size());
                assertTrue(verifyBalanced(avlTree));
            }
        }
    }

    @Test
    public void testRemove() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        avlTree.addAll(Arrays.asList(1, 3, 5, 7, 9));
        assertTrue(avlTree._root == avlTree.searchBinaryNode(3));

        avlTree.remove(1);
        assertTrue(verifyBalanced(avlTree));
        assertTrue(avlTree._root == avlTree.searchBinaryNode(7));
        assertTrue(avlTree._root.left.parent == avlTree._root);
        assertTrue(avlTree._root.left == avlTree.searchBinaryNode(3));
        assertTrue(avlTree._root.right == avlTree.searchBinaryNode(9));
        assertTrue(avlTree._root.left.right == avlTree.searchBinaryNode(5));
        assertTrue(avlTree._root.left.right.parent == avlTree._root.left);
    }

    @Test
    public void testRandomRemove() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            AVLTree<Integer, Integer> avlTree = new AVLTree<>();
            for (int j = 0; j < 300; j++) {
                avlTree.insert(random.nextInt(250), j);
                assertTrue(verifyBalanced(avlTree));
            }
            for (int j = 0; j < 600; j++) {
                avlTree.remove(random.nextInt(250));
                assertTrue(verifyBalanced(avlTree));
            }
        }
    }

    private <K, V> boolean verifyBalanced(AVLTree<K, V> avlTree) {
        if (avlTree.isEmpty()) return true;
        Queue<BinaryNode<K, V>> queue = new Queue<>();
        queue.enqueue(avlTree._root);
        BinaryNode<K, V> cur = avlTree._root;
        while (!queue.isEmpty()) {
            cur = queue.dequeue();
            assertHeightEquals(cur);
            if (!cur.isAvlBalanced()) {
                return false;
            }
            if (cur.hasLeftChild()) {
                queue.enqueue(cur.left);
            }
            if (cur.hasRightChild()) {
                queue.enqueue(cur.right);
            }
        }
        return true;
    }

    private <K, V> void assertHeightEquals(BinaryNode<K, V> node) {
        int lh = node.left != null ? node.left.height() : -1;
        int rh = node.right != null ? node.right.height() : -1;
        int height = Math.max(lh, rh) + 1;
        assertEquals(height, node.height());
    }

}