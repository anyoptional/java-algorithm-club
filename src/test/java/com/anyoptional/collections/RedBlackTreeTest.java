package com.anyoptional.collections;

import com.anyoptional.util.Comparators;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void testRandomRemove() {
        for (int i = 0; i < 20; i++) {
            Random random = new Random();
            RedBlackTree<Integer, Integer> rbTree = new RedBlackTree<>();
            for (int j = 0; j < 1000; j++) {
                rbTree.insert(random.nextInt(500), null);
            }
            for (int j = 0; j < 5000; j++) {
                rbTree.remove(random.nextInt(500));
                assertValidRBTree(rbTree);
            }
        }
    }

    private <K, V> void assertValidRBTree(RedBlackTree<K, V> rbTree) {
        if (rbTree.isEmpty()) return;
        // 定义1
        assertTrue(((RedBlackTree.Node) rbTree._root).isBlack());
        final Height height = new Height(0);
        List<K> list = new ArrayList<>();
        rbTree._root.traverseInOrder(new Consumer<BinaryNode<K, V>>() {
            @Override
            public void accept(BinaryNode<K, V> node) {
                list.add(node.entry.getKey());
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
        // 中序遍历单调递增
        assertSorted(list);
    }

    private <K> void assertSorted(List<K> list) {
        for (int i = 1; i < list.size(); i++) {
            assertTrue(Comparators.compare(list.get(i - 1), list.get(i), null) <= 0);
        }
    }

    static class Height {
        int height;

        Height(int h) {
            height = h;
        }
    }

}