/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BinarySearchTree;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.SortedSet;

/**
 * @author jcvsa
 */
public class BST<E> extends AbstractSet<E> implements SortedSet<E> {

    private int numElements;
    protected Node rootNode;
    private Comparator<? super E> comparator;
    private E from, to;

    public BST() {
        super();
        numElements = 0;
    }

    public BST(Collection<? extends E> c) {
        this();
        for (E element : c) {
            add(element);
        }
    }

    public BST(Comparator<? super E> comparator) {
        this();
        this.comparator = comparator;
    }

    public BST(SortedSet<E> s) {
        this();
        this.comparator = s.comparator();
        for (E element : s) {
            add(element);
        }
    }

    // private constructor used to create a view of a portion of tree
    private BST(Node rootNode,
            Comparator<? super E> comparator, E fromElement, E toElement) {
        this(comparator);
        this.rootNode = rootNode;
        this.from = fromElement;
        this.to = toElement;
        // calculate the number of elements
        this.numElements = countNodes(rootNode);
    }

    private int countNodes(Node node) {
        if (node == null) {
            return 0;
        } else {
            return countNodes(node.getLeft()) + 1
                    + countNodes(node.getRight());
        }
    }

    private boolean withinView(E element) {
        return ((from != null && compare(element, from) < 0)
                || (to != null && compare(element, to) >= 0));
    }

    private int compare(E i, E j) {
        if (comparator != null) {
            return comparator.compare(i, j);

        } else if (i != null && i instanceof Comparable) {
            return ((Comparable) i).compareTo(j); //unchecked

        } else if (j != null && j instanceof Comparable) {
            return -((Comparable) j).compareTo(i);//unchecked

        } else {
            return 0;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new BinaryTreeIterator(rootNode);
    }

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public void clear() {
        rootNode = null;
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<E> subSet(E e, E e1) {
        return new BST<>(rootNode, comparator, from,
                to);
    }

    @Override
    public SortedSet<E> headSet(E e) {
        return subSet(null, to);
    }

    @Override
    public SortedSet<E> tailSet(E e) {
        return subSet(from, null);
    }

    @Override
    public E first() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E last() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString() {
        return "Tree: " + rootNode;
    }

    private class BinaryTreeIterator implements Iterator<E> {

        private LinkedList<E> list;
        private Iterator<E> iterator;

        public BinaryTreeIterator(Node rootNode) {
            list = new LinkedList<>();

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

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
