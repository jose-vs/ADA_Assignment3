/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistentDynamicSet;

import BinarySearchTree.BinarySearchTree;
import BinarySearchTree.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author jcvsa
 * @param <E>
 */
public class PersistentDynamicSet<E extends Comparable> extends BinarySearchTree {

    private List<Node> versions; 
    private Stack<Node> buildVersion;
    private Node currentVersion;
    
    public PersistentDynamicSet() { 
        super();
        versions = new ArrayList<>();
        buildVersion = new Stack<>();
    }
    
    public PersistentDynamicSet(Collection<? extends E> c) {
        super(c);
        versions = new LinkedList<>();
    }
    
    @Override
    protected void saveInsert(Node current, Node left, Node right) { 
        Node clone = current.clone();
//        clone.setLeft(left);
//        clone.setRight(right);
//        currentVersion = clone;
        buildVersion.add(clone);
    }
    
    @Override
    protected void saveVersion(){ 
//        versions.add(currentVersion);
//        buildVersion.clear();
        Node node = buildVersion.pop();
        buildVersion.clear();
        System.out.println("____________CURRENT____________");
        System.out.println("Node: " + toString());
        System.out.println("NodeSize: " + getSize(node));
        System.out.println(toString(node));
        
        versions.add(node);

    }
    
    public static void main(String[] args) {
        PersistentDynamicSet<Integer> tree = new PersistentDynamicSet<>();

        tree.insert("cow");
        tree.insert("fly");
        tree.insert("dog");
        tree.insert("bat");
        tree.insert("fox");
        tree.insert("cat");
        tree.insert("eel");
        tree.insert("ant");
        tree.insert("ape");
        tree.insert("pig");
        tree.insert("owl");
        tree.insert("bee");
        tree.insert("rat");
        tree.insert("eel");

        System.out.println("Original Tree: " + tree);
        System.out.println("Size: " + tree.size());
        
        for(Node e : tree.versions) { 
            System.out.println("____________CURRENT____________");
            System.out.println("Node: " + e.toString());
            System.out.println("NodeSize: " + tree.getSize(e));
            System.out.println(tree.toString(e));
        } 
    }
    
}
