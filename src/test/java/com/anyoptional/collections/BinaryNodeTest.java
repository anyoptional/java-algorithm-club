package com.anyoptional.collections;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class BinaryNodeTest {

    // 按层次组成树状结构
    BinaryNode<Integer, String> a = new BinaryNode<>(0);
    BinaryNode<Integer, String> b = new BinaryNode<>(1);
    BinaryNode<Integer, String> c = new BinaryNode<>(2);
    BinaryNode<Integer, String> d = new BinaryNode<>(3);
    BinaryNode<Integer, String> e = new BinaryNode<>(4);
    BinaryNode<Integer, String> f = new BinaryNode<>(5);
    BinaryNode<Integer, String> g = new BinaryNode<>(6);
    BinaryNode<Integer, String> h = new BinaryNode<>(7);

    @Before
    public void setup() {
        a.left = b;
        a.right = c;
        b.parent = a;
        c.parent = a;

        b.left = d;
        d.parent = b;
        b.right = e;
        e.parent = b;

        c.left = f;
        f.parent = c;
        c.right = g;
        g.parent = c;

        d.left = h;
        h.parent = d;

        a.updateHeightAbove();
        b.updateHeightAbove();
        c.updateHeightAbove();
        d.updateHeightAbove();
        e.updateHeightAbove();
        f.updateHeightAbove();
        g.updateHeightAbove();
        h.updateHeightAbove();

        System.out.println(a);
    }


    @Test
    public void testSuccessorPredecessor() {
        BinaryNode<Integer, String> t0 = new BinaryNode<>(0);
        assertNull(t0.successor());
        assertNull(t0.predecessor());

        BinaryNode<Integer, String> t1 = new BinaryNode<>(0);
        BinaryNode<Integer, String> t2 = new BinaryNode<>(1);
        t1.left = t2;
        t2.parent = t1;
        assertEquals(t2, t1.predecessor());
        assertNull(t1.successor());
        assertEquals(t1, t2.successor());
        assertNull(t2.predecessor());


        assertNull(h.predecessor());
        assertEquals(d, h.successor());

        assertEquals(h, d.predecessor());
        assertEquals(b, d.successor());

        assertEquals(d, b.predecessor());
        assertEquals(e, b.successor());

        assertEquals(b, e.predecessor());
        assertEquals(a, e.successor());

        assertEquals(e, a.predecessor());
        assertEquals(f, a.successor());

        assertEquals(a, f.predecessor());
        assertEquals(c, f.successor());

        assertEquals(f, c.predecessor());
        assertEquals(g, c.successor());

        assertNull(g.successor());
        assertEquals(c, g.predecessor());
    }

    @Test
    public void testSize() {
        BinaryNode<Integer, String> tree = new BinaryNode<>(0);
        assertEquals(1, tree.size());
        assertTrue(tree.isRoot());
        assertTrue(tree.isLeaf());
        assertFalse(tree.hasLeftChild());
        assertFalse(tree.hasRightChild());
        assertFalse(tree.isLeftChild());
        assertFalse(tree.isRightChild());
        assertNull(tree.uncle());
        assertNull(tree.sibling());
        assertNull(tree.grandParent());
    }

    @Test
    public void testRelation() {
        assertEquals(0, (int)a.entry.getKey());
        assertEquals(1, (int)b.entry.getKey());
        assertEquals(2, (int)c.entry.getKey());
        assertEquals(3, (int)d.entry.getKey());
        assertEquals(4, (int)e.entry.getKey());
        assertEquals(5, (int)f.entry.getKey());
        assertEquals(6, (int)g.entry.getKey());
        assertEquals(7, (int)h.entry.getKey());

        assertNull(a.entry.getValue());
        assertNull(b.entry.getValue());
        assertNull(c.entry.getValue());
        assertNull(d.entry.getValue());
        assertNull(e.entry.getValue());
        assertNull(f.entry.getValue());
        assertNull(g.entry.getValue());
        assertNull(h.entry.getValue());

        assertEquals(8, a.size());
        assertEquals(4, b.size());
        assertEquals(3, c.size());
        assertEquals(2, d.size());
        assertEquals(1, e.size());
        assertEquals(1, f.size());
        assertEquals(1, g.size());
        assertEquals(1, h.size());

        assertTrue(a.hasBothChildren());
        assertTrue(a.hasLeftChild());
        assertTrue(a.hasRightChild());
        assertTrue(a.hasAnyChild());
        assertFalse(a.isLeftChild());
        assertFalse(a.isRightChild());
        assertTrue(a.isRoot());
        assertFalse(a.isLeaf());
        assertNull(a.uncle());
        assertNull(a.grandParent());
        assertNull(a.sibling());
        assertEquals(3, a.height());

        assertTrue(b.isLeftChild());
        assertFalse(b.isRightChild());
        assertTrue(b.hasBothChildren());
        assertFalse(b.isRoot());
        assertEquals(c, b.sibling());
        assertNull(b.grandParent());
        assertFalse(b.isLeaf());
        assertEquals(2, b.height());


        assertTrue(c.isRightChild());
        assertFalse(c.isLeftChild());
        assertTrue(c.hasBothChildren());
        assertFalse(c.isRoot());
        assertEquals(b, c.sibling());
        assertNull(c.grandParent());
        assertFalse(c.isLeaf());
        assertEquals(1, c.height());

        assertFalse(d.hasBothChildren());
        assertTrue(d.hasLeftChild());
        assertFalse(d.hasRightChild());
        assertTrue(d.hasAnyChild());
        assertTrue(d.isLeftChild());
        assertFalse(d.isRightChild());
        assertFalse(d.isRoot());
        assertEquals(e, d.sibling());
        assertEquals(a, d.grandParent());
        assertFalse(d.isLeaf());
        assertEquals(1, d.height());

        assertFalse(e.hasBothChildren());
        assertFalse(e.hasLeftChild());
        assertFalse(e.hasRightChild());
        assertFalse(e.hasAnyChild());
        assertTrue(e.isRightChild());
        assertFalse(e.isLeftChild());
        assertFalse(e.isRoot());
        assertEquals(d, e.sibling());
        assertEquals(a, d.grandParent());
        assertTrue(e.isLeaf());
        assertEquals(0, e.height());

        assertFalse(h.hasBothChildren());
        assertFalse(h.hasLeftChild());
        assertFalse(h.hasRightChild());
        assertFalse(h.hasAnyChild());
        assertTrue(h.isLeftChild());
        assertFalse(h.isRightChild());
        assertFalse(h.isRoot());
        assertNull(h.sibling());
        assertEquals(b, h.grandParent());
        assertTrue(h.isLeaf());
        assertEquals(0, h.height());

        assertFalse(f.hasBothChildren());
        assertFalse(f.hasLeftChild());
        assertFalse(f.hasRightChild());
        assertFalse(f.hasAnyChild());
        assertTrue(f.isLeftChild());
        assertFalse(f.isRightChild());
        assertFalse(f.isRoot());
        assertTrue(f.isLeaf());
        assertEquals(b, f.uncle());
        assertEquals(g, f.sibling());
        assertEquals(a, f.grandParent());
        assertEquals(0, f.height());

        assertFalse(g.hasBothChildren());
        assertFalse(g.hasLeftChild());
        assertFalse(g.hasRightChild());
        assertFalse(g.hasAnyChild());
        assertTrue(g.isRightChild());
        assertFalse(g.isLeftChild());
        assertFalse(g.isRoot());
        assertTrue(g.isLeaf());
        assertEquals(b, g.uncle());
        assertEquals(f, g.sibling());
        assertEquals(a, g.grandParent());
        assertEquals(0, g.height());
    }

    @Test
    public void testTraversePreOrder() {
        List<Integer> keys = new ArrayList<>();
        a.traversePreOrder(new Consumer<BinaryNode<Integer, String>>() {
            @Override
            public void accept(BinaryNode<Integer, String> binNode) {
                keys.add(binNode.entry.getKey());
            }
        });
        assertEquals(Arrays.asList(0, 1, 3, 7, 4, 2, 5, 6), keys);
    }

    @Test
    public void testTraverseInOrder() {
        List<Integer> keys = new ArrayList<>();
        a.traverseInOrder(new Consumer<BinaryNode<Integer, String>>() {
            @Override
            public void accept(BinaryNode<Integer, String> binNode) {
                keys.add(binNode.entry.getKey());
            }
        });
        assertEquals(Arrays.asList(7, 3, 1, 4, 0, 5, 2, 6), keys);
    }

    @Test
    public void testTraversePostOrder() {
        List<Integer> keys = new ArrayList<>();
        a.traversePostOrder(new Consumer<BinaryNode<Integer, String>>() {
            @Override
            public void accept(BinaryNode<Integer, String> binNode) {
                keys.add(binNode.entry.getKey());
            }
        });
        assertEquals(Arrays.asList(7, 3, 4, 1, 5, 6, 2, 0), keys);
    }

    @Test
    public void testTraverseLevel() {
        List<Integer> keys = new ArrayList<>();
        a.traverseLevel(new Consumer<BinaryNode<Integer, String>>() {
            @Override
            public void accept(BinaryNode<Integer, String> binNode) {
                keys.add(binNode.entry.getKey());
            }
        });
        assertEquals(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7), keys);
    }

}