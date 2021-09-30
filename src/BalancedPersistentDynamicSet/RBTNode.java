package BalancedPersistentDynamicSet;

import BinarySearchTree.Node;

import java.awt.*;

public class RBTNode<E> extends Node<E> {

    private Color color;
    public RBTNode(E element) {
        super(element);
        this.color = Color.RED;
    }

    public Color getColor() { return color; }
    public void setColor(Color color) {
        this.color = color;
    }
}
