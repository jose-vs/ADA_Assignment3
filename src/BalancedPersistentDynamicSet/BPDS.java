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
public class BPDS<E extends Comparable> extends PersistentDynamicSet<E> {

    public BPDS() {
        super();
    }

    public BPDS(Collection<? extends E> c) {
        super(c);
    }

    @Override
    protected void updateVersion(Node node) {
        System.out.println("________________BUILDING________________");

        /**
         *
         */
        Node newVersion = buildVersion.pop();
        latestVersion.clear();
        latestVersion.offer(newVersion);

        if (newVersion.getLeft() == save_node) {
            newVersion.setLeft(null);
        }
        if (newVersion.getRight() == save_node) {
            newVersion.setRight(null);
        }

        System.out.println("____________BOTTOM____________");
        System.out.println("NodeSize: " + getSize(newVersion));
        System.out.println("NodeElement: " + newVersion.getElement());
        System.out.println(toString(newVersion));
        System.out.println(hashCodeString(newVersion));

        /**
         *
         */
        while (!buildVersion.isEmpty()) {
            Node temp = buildVersion.pop();
            latestVersion.offer(temp);

            System.out.println("____________CURRENT____________");
            System.out.println("NodeSize: " + getSize(temp));
            System.out.println("NodeElement: " + temp.getElement());
            System.out.println(toString(temp));
            System.out.println(hashCodeString(temp));

            /**
             *
             */
            if (temp.getLeft() == save_node || temp.getRight() == save_node) {

                if (temp.getLeft() == save_node) {
                    temp.setLeft(newVersion);
                } else if (temp.getRight() == save_node) {
                    temp.setRight(newVersion);
                }

                newVersion = temp;

            }

        }

        System.out.println("____________NEW VERSION____________");
        System.out.println("NodeSize: " + getSize(newVersion));
        System.out.println("NodeElement: " + newVersion.getElement());
        System.out.println(toString(newVersion));
        System.out.println(hashCodeString(newVersion));

        // Recolor and balance the tree.
        Queue<Node> clonedLatestVersion = new LinkedList<>(latestVersion);
        fixTree(clonedLatestVersion);

        rootNode = newVersion.clone();
        
        // Make sure root node is black
        if (rootNode != null) {
            rootNode.setColor(Color.BLACK);
        }

        versions.add(newVersion);
    }

    private void fixTree(Queue<Node> latestVersion) {

        Node current = latestVersion.poll();
        Node parent = latestVersion.poll();
        Node grandParent = latestVersion.poll();

        if (current == null || parent == null || grandParent == null) {
            return;
        }

        while (parent.getColor().equals(Color.RED)) {
            if (grandParent == null) {
                break;
            }
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

            if (parent == null) {
                break;
            }
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
