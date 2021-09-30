///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package BinarySearchTree;
//
//import java.util.AbstractSet;
//import java.util.Collection;
//import java.util.Comparator;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.NoSuchElementException;
//import java.util.SortedSet;
//
///**
// * @author jcvsa
// */
//public abstract class BinarySearchTreeADT<E> extends AbstractSet<E> implements SortedSet<E> {
//
//    private int numElements;
//    protected Node rootNode;
//    private Comparator<? super E> comparator;
//    private E from, to;
//
//    public BinarySearchTreeADT() {
//        super();
//        numElements = 0;
//    }
//
//    public BinarySearchTreeADT(Collection<? extends E> c) {
//        this();
//        for (E element : c) {
//            add(element);
//        }
//    }
//
//    public BinarySearchTreeADT(Comparator<? super E> comparator) {
//        this();
//        this.comparator = comparator;
//    }
//
//    public BinarySearchTreeADT(SortedSet<E> s) {
//        this();
//        this.comparator = s.comparator();
//        for (E element : s) {
//            add(element);
//        }
//    }
//
//    private BinarySearchTreeADT(Node rootNode,
//            Comparator<? super E> comparator, E fromElement, E toElement) {
//        this(comparator);
//        this.rootNode = rootNode;
//        this.from = fromElement;
//        this.to = toElement;
//        this.numElements = countNodes(rootNode);
//    }
//
//    public abstract boolean add(E o);
//    public abstract boolean remove(Object o);
//    public abstract boolean contains(Object o);
//
//    // helper method which removes removalNode (presumed not null) and
//    // returns a reference to node that should take place of removalNode
//    private Node makeReplacement(Node removalNode) {
//        Node replacementNode = null;
//        // check cases when removalNode has only one child
//        if (removalNode.getLeft() != null && removalNode.getRight() == null) {
//            replacementNode = removalNode.getLeft();
//        } else if (removalNode.getLeft() == null
//                && removalNode.getRight() != null) {
//            replacementNode = removalNode.getRight();
//        } // check case when removalNode has two children
//        else if (removalNode.getLeft() != null
//                && removalNode.getRight() != null) {  // find the inorder successor and use it as replacementNode
//            Node parentNode = removalNode;
//            replacementNode = removalNode.getRight();
//            if (replacementNode.getLeft() == null) // replacementNode can be pushed up one level to replace
//            // removalNode, move the left child of removalNode to be
//            // the left child of replacementNode
//            {
//                replacementNode.setLeft(removalNode.getLeft());
//            } else {  //find left-most descendant of right subtree of removalNode
//                do {
//                    parentNode = replacementNode;
//                    replacementNode = replacementNode.getLeft();
//                } while (replacementNode.getLeft() != null);
//                // move the right child of replacementNode to be the left
//                // child of the parent of replacementNode
//                parentNode.setLeft(replacementNode.getRight());
//                // move the children of removalNode to be children of
//                // replacementNode
//                replacementNode.setLeft(removalNode.getLeft());
//                replacementNode.setRight(removalNode.getRight());
//            }
//        }
//        // else both leftChild and rightChild null so no replacementNode
//        return replacementNode;
//    }
//
//    private int countNodes(Node node) {
//        if (node == null) {
//            return 0;
//        } else {
//            return countNodes(node.getLeft()) + 1
//                    + countNodes(node.getRight());
//        }
//    }
//
//
//    private boolean withinView(E element) {
//        return !((from != null && compare(element, from) < 0)
//                || (to != null && compare(element, to) >= 0));
//    }
//
//    private int compare(E element1, E element2) {
//        if (comparator != null) {
//            return comparator.compare(element1, element2);
//
//        } else if (element1 != null && element1 instanceof Comparable) {
//            return ((Comparable) element1).compareTo(element2); //unchecked
//
//        } else if (element2 != null && element2 instanceof Comparable) {
//            return -((Comparable) element2).compareTo(element1);//unchecked
//
//        } else {
//            return 0;
//        }
//    }
//
//    @Override
//    public Iterator<E> iterator() {
//        return new BinaryTreeIterator(rootNode);
//    }
//
//    @Override
//    public int size() {
//        return numElements;
//    }
//
//    @Override
//    public void clear() {
//        rootNode = null;
//    }
//
//    @Override
//    public Comparator<? super E> comparator() {
//        return comparator;
//    }
//
//    @Override
//    public SortedSet<E> subSet(E from, E to) {
//        return new BinarySearchTreeADT<>(rootNode, comparator, from, to);
//    }
//
//    @Override
//    public SortedSet<E> headSet(E e) {
//        return subSet(null, to);
//    }
//
//    @Override
//    public SortedSet<E> tailSet(E e) {
//        return subSet(from, null);
//    }
//
//    @Override
//    public E first() {
//        if (rootNode == null) {
//            throw new NoSuchElementException("empty tree");
//        }
//        // find the least descendant of rootNode that is at least
//        // as big as fromElement by traversing down tree from root
//        Node currentNode = rootNode;
//        Node leastYetNode = null; // smallest found so far
//        while (currentNode != null) {
//            if (compare((E) currentNode.getElement(), from) >= 0) {
//                if (compare((E) currentNode.getElement(), to) < 0) {
//                    leastYetNode = currentNode;
//                }
//                // move to the left child to see if a smaller element okay
//                // since all in right subtree will be larger
//                currentNode = currentNode.getLeft();
//            } else // compare(currentNode.element, fromElement)<0
//            {  // move to the right child since this element too small
//                // so all in left subtree will also be too small
//                currentNode = currentNode.getRight();
//            }
//        }
//        if (leastYetNode == null) // no satisfactory node found
//        {
//            return null;
//        } else {
//            return (E) leastYetNode.getElement();
//        }
//    }
//
//    /**
//     *
//     *
//     *
//     * @return
//     */
//    @Override
//    public E last() {
//        if (rootNode == null) {
//            throw new NoSuchElementException("empty tree");
//        }
//        // find the greatest descendant of rootNode that is less than
//        // toElement by traversing down tree from root
//        Node currentNode = rootNode;
//        Node greatestYetNode = null; // greatest found so far
//        while (currentNode != null) {
//            if (compare((E) currentNode.getElement(), to) < 0) {
//                if (compare((E) currentNode.getElement(), from) >= 0) {
//                    greatestYetNode = currentNode;
//                }
//                // move to the right child to see if a greater element okay
//                // since all in left subtree will be smaller
//                currentNode = currentNode.getRight();
//            } else // compare(currentNode.element, toElement)>=0
//            {  // move to the left child since this element too large
//                // so all in right subtree will also be too large
//                currentNode = currentNode.getLeft();
//            }
//        }
//        if (greatestYetNode == null) // no satisfactory node found
//        {
//            return null;
//        } else {
//            return (E) greatestYetNode.getElement();
//        }
//    }
//
//    @Override
//    public String toString() {
//        return "Tree: " + rootNode;
//    }
//
//    private class BinaryTreeIterator implements Iterator<E> {
//
//        private LinkedList<E> list;
//        private Iterator<E> iterator;
//
//        public BinaryTreeIterator(Node rootNode) {
//            list = new LinkedList<>();
//            traverseInOrder(rootNode);
//            iterator = list.iterator();
//        }
//
//        private void traverseInOrder(Node node) {
//            if (node != null) {
//                traverseInOrder(node.getLeft());
//                if (withinView((E) node.getElement())) {
//                    list.add((E) node.getElement());
//                }
//                traverseInOrder(node.getRight());
//            }
//        }
//
//        @Override
//        public boolean hasNext() {
//            return iterator.hasNext();
//        }
//
//        @Override
//        public E next() {
//            return iterator.next();
//        }
//
//        @Override
//        public void remove() {
//            throw new UnsupportedOperationException();
//        }
//
//    }
//
//}
