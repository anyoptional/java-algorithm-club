package com.anyoptional.collections;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeNodeTest {

    @Test
    public void testSize() {
        TreeNode<Integer, String> tree = new TreeNode<>(0);
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
        // 按层次组成树状结构
        TreeNode<Integer, String> a = new TreeNode<>(0);
        TreeNode<Integer, String> b = new TreeNode<>(1);
        TreeNode<Integer, String> c = new TreeNode<>(2);
        TreeNode<Integer, String> d = new TreeNode<>(3);
        TreeNode<Integer, String> e = new TreeNode<>(4);
        TreeNode<Integer, String> f = new TreeNode<>(5);
        TreeNode<Integer, String> g = new TreeNode<>(6);
        TreeNode<Integer, String> h = new TreeNode<>(7);

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
        assertEquals(3, a.getHeight());

        assertTrue(b.isLeftChild());
        assertFalse(b.isRightChild());
        assertTrue(b.hasBothChildren());
        assertFalse(b.isRoot());
        assertEquals(c, b.sibling());
        assertNull(b.grandParent());
        assertFalse(b.isLeaf());
        assertEquals(2, b.getHeight());


        assertTrue(c.isRightChild());
        assertFalse(c.isLeftChild());
        assertTrue(c.hasBothChildren());
        assertFalse(c.isRoot());
        assertEquals(b, c.sibling());
        assertNull(c.grandParent());
        assertFalse(c.isLeaf());
        assertEquals(1, c.getHeight());

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
        assertEquals(1, d.getHeight());

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
        assertEquals(0, e.getHeight());

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
        assertEquals(0, h.getHeight());

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
        assertEquals(0, f.getHeight());

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
        assertEquals(0, g.getHeight());
    }

}