/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BinarySearchTree;

import java.awt.Color;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author jcvsa
 * @param <E>
 */
public class BinarySearchTree<E extends Comparable<E>> extends AbstractSet {

    protected Node rootNode;
    protected boolean added;

    protected Node save_node = new Node(Flag.SAVE_NODE);
    protected Node remove_node = new Node(Flag.REMOVE_NODE);

    public BinarySearchTree() {
        super();
        added = false;
    }

    public BinarySearchTree(Collection<? extends E> c) {
        this();
        for (E element : c) {
            insert(element);
        }
    }

    /**
     *
     * @param element
     */
    public void insert(E element) {
        Node node = insertUtil(rootNode, element);
        updateVersion(node);
    }

    /**
     *
     * @param current
     * @param element
     * @return
     */
    private Node insertUtil(Node current, E element) {
        if (current == null) {
            Node node = new Node(element);
            saveNode(node, null, null);

            // Color Hook for RBT
            if (hasColorHook()) {
                node.setColor(Color.RED);
            }
            return node;
        }

        int compare = element.compareTo((E) current.getElement());

        if (compare < 0) {
            saveNode(new Node(current.getElement(), current.getColor()), save_node, current.getRight());
            current.setLeft(insertUtil(current.getLeft() != null ? current.getLeft().clone() : null, element));

        } else if (compare > 0) {
            saveNode(new Node(current.getElement(), current.getColor()), current.getLeft(), save_node);
            current.setRight(insertUtil(current.getRight() != null ? current.getRight().clone() : null, element));
        }

        return current;
    }

    /**
     *
     * @param element
     */
    public void remove(E element) {
        updateVersion(removeUtil(rootNode, element));
    }

    /**
     *
     * @param current
     * @param element
     * @return
     */
    public Node removeUtil(Node current, E element) {
        if (current == null) {
            return null;
        }

        int compare = element.compareTo((E) current.getElement());

        /**
         * Element to be removed found
         */
        if (element == current.getElement()) {
            /**
             * if the node has no children
             */
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            }

            /**
             * if the node only has 1 child
             */
            Node clone;

            if (current.getRight() == null) {

                clone = current.getLeft().clone();
                saveNode(clone, clone.getLeft(), clone.getRight());
                return clone;
            }
            if (current.getLeft() == null) {
                clone = current.getRight().clone();
                saveNode(clone, clone.getLeft(), clone.getRight());
                return clone;
            }

            /**
             * if the node has both children
             */
            E smallestValue = findSmallestValue(current.getRight());
            current.setElement(smallestValue);
            saveNode(new Node(current.getElement(), current.getColor()), current.getLeft(), save_node);

            current.setRight(removeUtil(current.getRight() != null ? current.getRight().clone() : null, smallestValue));
            return current;
        }

        if (compare < 0) {
            saveNode(new Node(current.getElement(), current.getColor()), save_node, current.getRight());
            current.setLeft(removeUtil(current.getLeft() != null ? current.getLeft().clone() : null, element));
            return current;
        }

        saveNode(new Node(current.getElement(), current.getColor()), current.getLeft(), save_node);
        current.setRight(removeUtil(current.getRight() != null ? current.getRight().clone() : null, element));
        return current;
    }

    protected void saveNode(Node current, Node left, Node right) {
    }

    protected void updateVersion(Node node) {
        rootNode = node;
    }

    /**
     *
     * @param root
     * @return
     */
    public E findSmallestValue(Node root) {
        return root.getLeft() == null ? (E) root.getElement() : findSmallestValue(root.getLeft());
    }

    /**
     *
     * @param element
     * @return
     */
    public boolean contains(E element) {
        return containsUtil(rootNode, element);
    }

    /**
     *
     * @param current
     * @param element
     * @return
     */
    private boolean containsUtil(Node current, E element) {
        if (current == null) {
            return false;
        }
        if (element == current.getElement()) {
            return true;
        }

        int compare = element.compareTo((E) current.getElement());
        return compare < 0
                ? containsUtil(current.getLeft(), element)
                : containsUtil(current.getRight(), element);
    }

    /**
     *
     * @return
     */
    @Override
    public Iterator iterator() {
        return new BinaryTreeIterator(rootNode);
    }

    /**
     *
     * @return
     */
    @Override
    public int size() {
        return getSize(rootNode);
    }

    public int getSize(Node current) {
        return current == null ? 0 : getSize(current.getLeft()) + 1 + getSize(current.getRight());
    }

    /**
     *
     */
    @Override
    public void clear() {
        rootNode = null;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return (rootNode == null);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Tree: " + rootNode;
    }

    /**
     *
     * @param node
     * @return
     */
    public String toString(Node node) {
        return "Node: " + node;
    }

    /**
     *
     * @param node
     * @return
     */
    public String hashCodeString(Node node) {
        return traverseInOrder(node);
    }

    /**
     *
     * @param node
     * @return
     */
    public String traverseInOrder(Node node) {
        String s = "";

        if (node != null) {

            s += traverseInOrder(node.getLeft());
            s += "Node Element: " + node.getElement() + " ";
            s += "Node HashCode: " + node.hashCode() + " ";
            s += "Left: "
                    + ((node.getLeft() == null) ? "null " : node.getLeft().hashCode() + " ");
            s += "Right: "
                    + ((node.getRight() == null) ? "null " : node.getRight().hashCode() + " ") + "\n";
            s += traverseInOrder(node.getRight());
        }

        return s;
    }

    /**
     * Getter for root node.
     *
     * @return the root node.
     */
    public Node getRootNode() {
        return rootNode;
    }

    /**
     * Color hook used for RBT. Default to false but can be overriden to true
     * when using RBT.
     *
     * @return boolean value on whether a node should have a color or not.
     */
    public boolean hasColorHook() {
        return false;
    }

    /**
     *
     */
    public class BinaryTreeIterator implements Iterator<E> {

        private LinkedList<E> list;
        private Iterator<E> iterator;

        public BinaryTreeIterator(Node rootNode) {
            list = new LinkedList<>();
            traverseInOrder(rootNode);
            iterator = list.iterator();
        }

        private void traverseInOrder(Node node) {
            if (node != null) {
                traverseInOrder(node.getLeft());
                list.add((E) node.getElement());
                traverseInOrder(node.getRight());
            }
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public E next() {
            return iterator.next();
        }

    }

    protected enum Flag {
        SAVE_NODE, REMOVE_NODE
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.insert(5);
        tree.insert(4);
        tree.insert(9);
        tree.insert(7);
        tree.insert(10);
        tree.insert(6);
        tree.insert(2);
        tree.insert(3);
        System.out.println("Original Tree: " + tree);
        System.out.println("Size: " + tree.size());
        tree.remove(5);
        System.out.println("Modified Tree: " + tree);
        System.out.println("Size: " + tree.size());
    }
}
