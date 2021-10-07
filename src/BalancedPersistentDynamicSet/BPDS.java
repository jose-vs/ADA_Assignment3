/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BalancedPersistentDynamicSet;

import BinarySearchTree.Node;
import PersistentDynamicSet.PersistentDynamicSet;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Uses Red Black Tree to keep it balanced.
 *
 * @author jcvsa
 * @param <E>
 */
public class BPDS<E extends Comparable> extends PersistentDynamicSet {

    public BPDS() {
        super();
    }

    public BPDS(Collection<? extends E> c) {
        super(c);
    }

    @Override
    protected void updateVersion(Node node) {
        super.updateVersion(node);

        // Recolor and balance the tree.
        Queue<Node> clonedLatestVersion = new LinkedList<>(latestVersion);
        fixTree(clonedLatestVersion);
    }

    private void fixTree(Queue<Node> latestVersion) {

        Node current = latestVersion.poll();
        Node parent = latestVersion.poll();
        Node grandParent = latestVersion.poll();

        if (current == null || parent == null || grandParent == null) {
            return;
        }

        while (parent.getColor().equals(Color.RED)) {
            if (parent.equals(grandParent.getRight())) {
                Node uncle = grandParent.getLeft();

                if (uncle != null && uncle.getColor().equals(Color.RED)) {
                    uncle.setColor(Color.BLACK);
                    parent.setColor(Color.BLACK);
                    grandParent.setColor(Color.RED);

                    current = grandParent;
                    parent = latestVersion.poll();
                    grandParent = latestVersion.poll();
                } else {
                    if (current.equals(parent.getLeft())) {
                        current = parent;
                        parent = grandParent;
                        grandParent = latestVersion.poll();
                        rightRotate(current, parent);
                    }

                    parent.setColor(Color.BLACK);
                    if (grandParent != null) {
                        grandParent.setColor(Color.RED);
                        leftRotate(grandParent, latestVersion.peek());
                    }
                   
                }
            } else {
                Node uncle = grandParent.getRight();

                if (uncle != null && uncle.getColor().equals(Color.RED)) {
                    uncle.setColor(Color.BLACK);
                    parent.setColor(Color.BLACK);
                    grandParent.setColor(Color.RED);

                    current = grandParent;
                    parent = latestVersion.poll();
                    grandParent = latestVersion.poll();
                } else {
                    if (current.equals(parent.getLeft())) {
                        current = parent;
                        parent = grandParent;
                        grandParent = latestVersion.poll();
                        leftRotate(current, parent);
                    }

                    parent.setColor(Color.BLACK);
                    if (grandParent != null) {
                        grandParent.setColor(Color.RED);
                        rightRotate(grandParent, latestVersion.peek());
                    }
                }
            }

            if (current.equals(rootNode)) {
                break;
            }
        }

        // Make sure root node is black
        if (rootNode != null && !rootNode.getColor().equals(Color.BLACK)) {
            rootNode.setColor(Color.BLACK);
        }
    }

    public void rightRotate(Node node, Node parent) {
        if (node.getLeft() == null) {
            return;
        }

        Node newParent = node.getLeft();
        Node tempRight = newParent.getRight();

        if (parent != null) {
            if (parent.getLeft().equals(node)) {
                parent.setLeft(newParent);
            } else {
                parent.setRight(newParent);
            }
        }

        newParent.setRight(node);
        node.setLeft(tempRight);

        if (parent == null) {
            rootNode = newParent;
        }
    }

    public void leftRotate(Node node, Node parent) {
        if (node.getRight() == null) {
            return;
        }

        Node newParent = node.getRight();
        Node tempLeft = newParent.getLeft();

        if (parent != null) {
            if (parent.getLeft().equals(node)) {
                parent.setLeft(newParent);
            } else {
                parent.setRight(newParent);
            }
        }

        newParent.setLeft(node);
        node.setRight(tempLeft);

        if (parent == null) {
            rootNode = newParent;
        }
    }

    @Override
    public boolean hasColorHook() {
        return true;
    }
}
