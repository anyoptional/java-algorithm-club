package com.anyoptional.collections;

import com.anyoptional.lang.Nullable;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

public class AVLTreeTest {

    @Test
    public void testInsertSorted() {
        AVLTree<Integer, Integer> avlTree = new AVLTree<>();
        avlTree.insert(1, null);
        avlTree.insert(3, null);
        avlTree.insert(5, null);
        avlTree.insert(7, null);
        avlTree.insert(9, null);
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
            for (int j = 0; j < 1000; j++) {
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
        avlTree.insert(1, null);
        avlTree.insert(3, null);
        avlTree.insert(5, null);
        avlTree.insert(7, null);
        avlTree.insert(9, null);
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
            for (int j = 0; j < 1000; j++) {
                avlTree.insert(random.nextInt(500), j);
                assertTrue(verifyBalanced(avlTree));
            }
            for (int j = 0; j < 5000; j++) {
                avlTree.remove(random.nextInt(500));
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
            if (!((AVLTree.Node<K, V>)cur).isBalanced()) {
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
        assertEquals(getHeight(node), node.height);
    }

    private <K, V> int getHeight(@Nullable BinaryNode<K, V> node) {
        if (node == null) return -1;
        if (node.isLeaf()) return 0;
        int lh = getHeight(node.left);
        int rh = getHeight(node.right);
        return Math.max(lh, rh) + 1;
    }

}