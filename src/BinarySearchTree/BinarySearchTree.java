/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BinarySearchTree;

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
        added = false;
        
        rootNode = insertUtil(rootNode, element);
        saveVersion();
    }
    
    /**
     * 
     * @param current
     * @param element
     * @return 
     */
    private Node insertUtil(Node current, E element){ 
        if(current  == null) {
            Node node = new Node(element);
            if (rootNode == null)
                saveInsert(node, null, null);
            
            return node;   
        }
        
        int compare = element.compareTo((E) current.getElement());
        
        if(compare < 0)
            current.setLeft(insertUtil(current.getLeft(), element));

        else if (compare > 0)
            current.setRight(insertUtil(current.getRight(), element));
        
        saveInsert(current, current.getLeft(), current.getRight());
      
        return current;
    }
    
    protected void saveInsert(Node current, Node left, Node right){ }
    protected void saveVersion(){ }
    
    /**
     * 
     * @param element 
     */
    public void remove(E element) { 
        rootNode = removeUtil(rootNode, element); 
        
    }
    
    /**
     * 
     * @param current
     * @param element
     * @return 
     */
    public Node removeUtil(Node current, E element){ 
        if (current == null)
            return null; 
        
        int compare = element.compareTo((E) current.getElement());

        /**
         * Element to be removed found
         */
        if(element == current.getElement()){ 
            /**
             * if the node has no children
             */
            if(current.getLeft() == null && current.getRight() == null)
                return null; 
            
            /**
             * if the node only has 1 child
             */
            if (current.getRight() == null)
                current.getLeft(); 
            if(current.getLeft() == null)
                current.getRight();
            
            /**
             * if the node has both children 
             */
            E smallestValue = findSmallestValue(current.getRight());
            current.setElement(smallestValue);
            current.setRight(removeUtil(current.getRight(), smallestValue));
            return current;
        }
        
        if(compare < 0) { 
            current.setLeft(removeUtil(current.getLeft(), element));
            return current;
        }
        
        current.setRight(removeUtil(current.getRight(), element));

        return current; 
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
        if (current == null) 
            return false; 
        if (element == current.getElement()) 
            return true;
        
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
    public boolean isEmpty(){ 
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
    
    public String toString(Node node) {
        return traverseInOrder(node); 
    }
    
    public String traverseInOrder(Node node){ 
        String s = "";
        
        if (node != null) {
            
            s += traverseInOrder(node.getLeft());
            s += "Node Element: " + node.getElement() + " ";
            s += "Node HashCode: " + node.hashCode() + " ";
            s += "Left: " + 
                    ((node.getLeft() == null) ? "null " : node.getLeft().hashCode() + " ");
            s += "Right: " + 
                    ((node.getRight() == null) ? "null " : node.getRight().hashCode() + " ") + "\n";
            s += traverseInOrder(node.getRight());
        }
        
        return s; 
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
    
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        
        
        tree.insert(2);
        tree.insert(1);
        tree.insert(3);
        tree.insert(4);
//        tree.insert(2);
//        tree.insert(6);
//        tree.insert(9);
//        tree.insert(1);
        System.out.println("Original Tree: " + tree);
        System.out.println("Size: " + tree.size());
//        tree.remove(5);
//        tree.remove(5);
//        System.out.println("Modified Tree: " + tree);
//        System.out.println("Size: " + tree.size());
    }
}
