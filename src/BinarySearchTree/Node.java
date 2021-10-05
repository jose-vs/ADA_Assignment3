/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BinarySearchTree;

import java.awt.*;

/**
 *
 * @author jcvsa
 */
public class Node<E> {
    private Node parent; // Temporary
    private Node left, right; 
    private E element;
    private Color color;

    
    public Node(E element) { 
        this.element = element; 
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

    public Node getParent() { return parent; }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public E getElement() {
        return element;
    }

    public Color getColor() { return color; }

    public void setParent(Node parent) { this.parent = parent; }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }
    
    public void setElement(E element) {
        this.element = element;
    }

    public void setColor(Color color) { this.color = color; }
}
