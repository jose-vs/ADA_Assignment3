/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BalancedPersistentDynamicSet;

import BinarySearchTree.BinarySearchTree;
import BinarySearchTree.Node;

import java.awt.*;
import java.util.Collection;

/**
 * Uses Red Black Tree to keep it balanced.
 *
 * @author jcvsa
 */
public class BPDS<E> extends BinarySearchTree<E> {

    public BPDS() {
        super();
    }

    public BPDS(Collection<? extends E> c) {
        this();
        for (E element : c)
            add(element);

    }

    @Override
    public boolean add(E o) {
        boolean isAdded = super.add(o);

        // Recolor nodes
        fixTree(latestNode);

        return isAdded;
    }

    private void fixTree(Node node) {
        if (node == null || node.getParent() == null || node.getParent().getParent() == null) return;

        while (node.getParent() != null && node.getParent().getColor().equals(Color.RED)) {
            if (node.getParent().equals(node.getParent().getParent().getRight())) {
                Node uncle = node.getParent().getParent().getLeft();

                if (uncle != null && uncle.getColor().equals(Color.RED)) {
                    uncle.setColor(Color.BLACK);
                    node.getParent().setColor(Color.BLACK);
                    node.getParent().getParent().setColor(Color.RED);
                    node = node.getParent().getParent();
                } else {
                    if (node.equals(node.getParent().getLeft())) {
                        node = node.getParent();
                        rightRotate(node);
                    }

                    node.getParent().setColor(Color.BLACK);
                    node.getParent().getParent().setColor(Color.RED);
                    leftRotate(node.getParent().getParent());
                }
            } else {
                Node uncle = node.getParent().getParent().getRight();

                if (uncle != null && uncle.getColor().equals(Color.RED)) {
                    uncle.setColor(Color.BLACK);
                    node.getParent().setColor(Color.BLACK);
                    node.getParent().getParent().setColor(Color.RED);
                    node = node.getParent().getParent();
                } else {
                    if (node.equals(node.getParent().getRight())) {
                        node = node.getParent();
                        leftRotate(node);
                    }

                    node.getParent().setColor(Color.BLACK);
                    node.getParent().getParent().setColor(Color.RED);
                    rightRotate(node.getParent().getParent());
                }
            }

            if (node.equals(rootNode)) break;
        }

        // Make sure root node is black
        if (rootNode != null && !rootNode.getColor().equals(Color.BLACK))
            rootNode.setColor(Color.BLACK);
    }

    public void rightRotate(Node node) {
        if (node.getLeft() == null)
            return;

        Node newParent = node.getLeft();
        Node tempRight = newParent.getRight();

        if (node.getParent() != null) {
            if (node.getParent().getLeft().equals(node))
                node.getParent().setLeft(newParent);
            else
                node.getParent().setRight(newParent);
        }

        newParent.setParent(node.getParent());
        newParent.setRight(node);
        node.setParent(newParent);
        node.setLeft(tempRight);

        if (newParent.getParent() == null)
            rootNode = newParent;
    }

    public void leftRotate(Node node) {
        if (node.getRight() == null)
            return;

        Node newParent = node.getRight();
        Node tempLeft = newParent.getLeft();

        if (node.getParent() != null) {
            if (node.getParent().getLeft().equals(node))
                node.getParent().setLeft(newParent);
            else
                node.getParent().setRight(newParent);
        }

        newParent.setParent(node.getParent());
        newParent.setLeft(node);
        node.setParent(newParent);
        node.setRight(tempLeft);

        if (newParent.getParent() == null)
            rootNode = newParent;
    }

    private void colorSwap(Node node) {
        if (node.getColor().equals(Color.BLACK))
            node.setColor(Color.RED);
        else if (node.getColor().equals(Color.RED))
            node.setColor(Color.BLACK);
    }

    @Override
    public boolean hasColorHook() {
        return true;
    }
}
