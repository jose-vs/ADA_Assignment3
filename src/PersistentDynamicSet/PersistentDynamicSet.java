/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistentDynamicSet;

import BinarySearchTree.BinarySearchTree;
import BinarySearchTree.Node;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author jcvsa
 * @param <E>
 */
public class PersistentDynamicSet<E extends Comparable> extends BinarySearchTree {

    public List<Node> versions;
    private Stack<Node> buildVersion;

    public PersistentDynamicSet() {
        super();
        versions = new LinkedList<>();
        buildVersion = new Stack<>();
    }

    public PersistentDynamicSet(Collection<? extends E> c) {
        super(c);
        versions = new LinkedList<>();
    }

    @Override
    protected void saveInsert(Node current, Node left, Node right) {
        current.setLeft(left);
        current.setRight(right);
        buildVersion.add(current);
    }

    @Override
    protected void saveVersion() {

        System.out.println("________________BUILDING________________");

        Node newVersion = buildVersion.pop();

        System.out.println("____________BOTTOM____________");
        System.out.println("NodeSize: " + getSize(newVersion));
        System.out.println("NodeElement: " + newVersion.getElement());
        System.out.println(toString(newVersion));
        System.out.println(hashCodeString(newVersion));

        while (!buildVersion.isEmpty()) {
            Node temp = buildVersion.pop();

            System.out.println("____________CURRENT____________");
            System.out.println("NodeSize: " + getSize(temp));
            System.out.println("NodeElement: " + temp.getElement());
            System.out.println(toString(temp));
            System.out.println(hashCodeString(temp));

            if (temp.getLeft() == flag) {
                temp.setLeft(newVersion);
            } else if (temp.getRight() == flag) {
                temp.setRight(newVersion);
            }

            newVersion = temp;

        }

        System.out.println("____________NEW VERSION____________");
        System.out.println("NodeSize: " + getSize(newVersion));
        System.out.println("NodeElement: " + newVersion.getElement());
        System.out.println(toString(newVersion));
        System.out.println(hashCodeString(newVersion));

        versions.add(newVersion);

    }

    public static void main(String[] args) {
        PersistentDynamicSet<Integer> tree = new PersistentDynamicSet<>();

        tree.insert(5);
        tree.insert(4);
        tree.insert(9);
        tree.insert(7);
        tree.insert(10);
        tree.insert(6);
        tree.insert(2);
        tree.insert(3);
//        tree.insert("cow");
//        tree.insert("fly");
//        tree.insert("dog");
//        tree.insert("bat");
//        tree.insert("fox");
//        tree.insert("cat");
//        tree.insert("eel");
//        tree.insert("ant");
//        tree.insert("ape");
//        tree.insert("pig");
//        tree.insert("owl");
//        tree.insert("bee");
//        tree.insert("rat");

        System.out.println("Original Tree: " + tree);
        System.out.println("Size: " + tree.size());

        System.out.println();
        System.out.println("________________________MAIN________________________");
        System.out.println();

        for (Node n : tree.versions) {
            System.out.println("____________CURRENT____________");
            System.out.println("NodeSize: " + tree.getSize(n));
            System.out.println(tree.toString(n));
            System.out.println(tree.hashCodeString(n));
        }

    }

}
