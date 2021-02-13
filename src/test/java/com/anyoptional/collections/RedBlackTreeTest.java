package com.anyoptional.collections;

import org.junit.Test;

import java.util.Random;
import java.util.function.Consumer;
import static org.junit.Assert.*;

public class RedBlackTreeTest {

    @Test
    public void testRandomInsert() {
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            RedBlackTree<Integer, Integer> rbTree = new RedBlackTree<>();
            for (int j = 0; j < 1000; j++) {
                rbTree.insert(random.nextInt(500), null);
                assertValidRBTree(rbTree);
            }
        }
    }

    private <K, V> void assertValidRBTree(RedBlackTree<K, V> rbTree) {
        if (rbTree.isEmpty()) return;
        // 定义1
        assertTrue(((RedBlackTree.Node) rbTree._root).isBlack());
        final Height height = new Height(0);
        rbTree._root.traverseLevel(new Consumer<BinaryNode<K, V>>() {
            @Override
            public void accept(BinaryNode<K, V> node) {
                if (node.isLeaf()) {
                    RedBlackTree.Node<K, V> cur = (RedBlackTree.Node<K, V>) node;
                    int h = 1;
                    while (cur != null) {
                        if (cur.isBlack()) {
                            h += 1;
                        } else {
                            // 定义3
                            if (!cur.isRoot()) {
                                assertTrue(((RedBlackTree.Node<K, V>) cur.parent).isBlack());
                            }
                            if (cur.hasLeftChild()) {
                                assertTrue(((RedBlackTree.Node<K, V>) cur.left).isBlack());
                            }
                            if (cur.hasRightChild()){
                                assertTrue(((RedBlackTree.Node<K, V>) cur.right).isBlack());
                            }
                        }
                        cur = (RedBlackTree.Node<K, V>) cur.parent;
                    }
                    if (height.height == 0) {
                        height.height = h;
                    } else {
                        // 定义4
                        assertEquals(height.height, h);
                    }
                }
            }
        });
    }

    static class Height {
        int height;

        Height(int h) {
            height = h;
        }
    }

}