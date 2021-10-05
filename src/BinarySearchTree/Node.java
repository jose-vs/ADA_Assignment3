/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BinarySearchTree;

/**
 *
 * @author jcvsa
 */
public class Node<E> {
    private Node<E> left, right; 
    private E element; 
    
    public Node(E element) { 
        this.element = element; 
    }
    
    public Node(E element, Node<E> left, Node<E> right) { 
        this.element = element;
        this.left = left; 
        this.right = right;
    }
    
 
    @Override
    public Node clone()  { 
        return new Node<>(this.element, this.left, this.right);
    }
    
    @Override
    public String toString() { 
        String str = "[";
        
        if(getLeft() != null)
            str += getLeft(); 
        str += getElement(); 
        if(getRight() != null)
            str += getRight();
        str += "]";
        
        return str; 
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public E getElement() {
        return element;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
    
    public void setElement(E element) {
        this.element = element;
    }
    
    
    
}
