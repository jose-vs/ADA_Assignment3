/*
 *  Balanced Persistent Dynamic Set class that extends from Persistent Dynamic 
 *  Set class. Uses a hook method for color in the BST class.
 *
 *  References used:
 *  - https://algorithmtutor.com/Data-Structures/Tree/Red-Black-Trees/
 *  - https://www.youtube.com/watch?v=5IBxA-bZZH8
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
 * @author Vinson
 * @param <E>
 */
public class BPDS<E extends Comparable> extends PersistentDynamicSet<E> {

    public BPDS() {
        super();
    }

    public BPDS(Collection<? extends E> c) {
        super(c);
    }

    /**
     * Modified updateVersion from Persistent Dynamic Set and augmented it to
     * make it work with a Red Black Tree.
     *
     * @param node
     */
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

        rootNode = newVersion.clone();

        // Recolor and balance the tree.
        Queue<Node> clonedLatestVersion = new LinkedList<>(latestVersion);
        balanceInsert(clonedLatestVersion);

        // Make sure root node is black
        if (rootNode != null) {
            rootNode.setColor(Color.BLACK);
        }

        versions.add(newVersion);
    }

    /**
     * Method to balance the tree when inserting. This is to make sure nodes
     * adhere to the rules of a Red Black Tree. Modified the implementation from
     * algorithm tutor to not use parent links and to base it off our Persistent
     * Dynamic Set class.
     *
     * @param latestVersion
     */
    private void balanceInsert(Queue<Node> latestVersion) {

        Node current = latestVersion.poll();
        Node parent = latestVersion.poll();
        Node grandParent = latestVersion.poll();

        if (parent == null) {
            current.setColor(Color.BLACK);
            return;
        }

        if (grandParent == null) {
            return;
        }

        while (parent.getColor().equals(Color.RED)) {
            if (parent.equals(grandParent.getRight())) {
                Node uncle = grandParent.getLeft();

                if (uncle != null && uncle.getColor().equals(Color.RED)) {
                    grandParent.getLeft().setColor(Color.BLACK);
                    grandParent.getRight().setColor(Color.BLACK);
                    grandParent.setColor(Color.RED);

                    current = grandParent;
                    parent = latestVersion.poll();
                    grandParent = latestVersion.poll();

                    if (parent == null) {
                        break;
                    }
                } else {
                    if (current.equals(parent.getLeft())) {
                        current = parent;
                        parent = grandParent;
                        grandParent = latestVersion.poll();
                        rightRotate(current, parent);
                    }

                    parent.setColor(Color.BLACK);
                    if (grandParent == null) {
                        break;
                    }
                    grandParent.setColor(Color.RED);
                    leftRotate(grandParent, latestVersion.peek());
                }
            } else {
                Node uncle = grandParent.getRight();

                if (uncle != null && uncle.getColor().equals(Color.RED)) {
                    grandParent.getLeft().setColor(Color.BLACK);
                    grandParent.getRight().setColor(Color.BLACK);
                    grandParent.setColor(Color.RED);

                    current = grandParent;
                    parent = latestVersion.poll();
                    grandParent = latestVersion.poll();

                    if (parent == null) {
                        break;
                    }
                } else {
                    if (current.equals(parent.getLeft())) {
                        current = parent;
                        parent = grandParent;
                        grandParent = latestVersion.poll();
                        leftRotate(current, parent);
                    }

                    parent.setColor(Color.BLACK);
                    if (grandParent == null) {
                        break;
                    }
                    grandParent.setColor(Color.RED);
                    rightRotate(grandParent, latestVersion.peek());
                }
            }

            if (current.equals(rootNode)) {
                break;
            }
        }
    }

    /**
     * Performs right rotation of the tree without using an implicit parent
     * reference from the Node class.
     *
     * @param node
     * @param parent
     */
    public void rightRotate(Node node, Node parent) {
        if (node.getLeft() == null || node.getLeft().getRight() == null) {
            return;
        }
        Node newParent = node.getLeft();
        node.setLeft(newParent.getRight());
        if (parent == null) {
            rootNode = newParent;
        } else if (node == parent.getRight()) {
            parent.setRight(newParent);
        } else {
            parent.setLeft(newParent);
        }
        newParent.setRight(node);
//        if (node.getLeft() == null) {
//            return;
//        }
//
//        Node newParent = node.getLeft();
//        Node tempRight = newParent.getRight();
//
//        if (parent != null) {
//            if (parent.getLeft().equals(node)) {
//                parent.setLeft(newParent);
//            } else {
//                parent.setRight(newParent);
//            }
//        }
//
//        newParent.setRight(node);
//        node.setLeft(tempRight);
//
//        if (parent == null) {
//            rootNode = newParent;
//        }
    }

    /**
     * Performs left rotation of the tree without using an implicit parent
     * reference from the Node class.
     *
     * @param node
     * @param parent
     */
    public void leftRotate(Node node, Node parent) {
        if (node.getRight() == null || node.getRight().getLeft() == null) {
            return;
        }
        Node newParent = node.getRight();
        node.setRight(newParent.getLeft());
        if (parent == null) {
            rootNode = newParent;
        } else if (node == parent.getRight()) {
            parent.setRight(newParent);
        } else {
            parent.setLeft(newParent);
        }
        newParent.setLeft(node);
//        if (node.getRight() == null) {
//            return;
//        }
//
//        Node newParent = node.getRight();
//        Node tempLeft = newParent.getLeft();
//
//        if (parent != null) {
//            if (parent.getLeft().equals(node)) {
//                parent.setLeft(newParent);
//            } else {
//                parent.setRight(newParent);
//            }
//        }
//
//        newParent.setLeft(node);
//        node.setRight(tempLeft);
//
//        if (parent == null) {
//            rootNode = newParent;
//        }
    }

    @Override
    public boolean hasColorHook() {
        return true;
    }
}
