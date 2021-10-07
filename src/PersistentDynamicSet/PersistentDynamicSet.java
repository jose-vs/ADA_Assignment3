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
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author jcvsa
 * @param <E>
 */
public class PersistentDynamicSet<E extends Comparable> extends BinarySearchTree {

    protected List<Node> versions;
    protected Stack<Node> buildVersion;
    protected Queue<Node> latestVersion;

    public PersistentDynamicSet() {
        super();
        versions = new LinkedList<>();
        buildVersion = new Stack<>();
        latestVersion = new LinkedList<>();
    }

    public PersistentDynamicSet(Collection<? extends E> c) {
        super(c);
        versions = new LinkedList<>();
        buildVersion = new Stack<>();
        latestVersion = new LinkedList<>();
    }

    /**
     * 
     * @param current
     * @param left
     * @param right 
     */
    @Override
    protected void saveNode(Node current, Node left, Node right) {
        try {
            current.setLeft(left);
            current.setRight(right);
            buildVersion.add(current);
        } catch (NullPointerException e) {
            System.out.println("Removing Node");
        }

    }

    /**
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
        versions.add(newVersion);
    }

    public Node getVersion(int version) {
        return versions.get(version);
    }

    public static void main(String[] args) {
        PersistentDynamicSet<Integer> tree = new PersistentDynamicSet<>();

        System.out.println();
        System.out.println("________________________MAIN________________________");
        System.out.println();

        System.out.println("________________________Adding________________________");

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

        System.out.println("________________________REMOVING________________________");
        tree.remove(5);
        tree.remove(9);
        
        System.out.println("Modified Tree: " + tree);
        System.out.println("Size: " + tree.size());

        System.out.println("________________________ALL VERSIONS________________________");

        for (Node n : tree.versions) {
            System.out.println("Node Version HashCode: " + n.hashCode());
            System.out.println(tree.toString(n));
        }

    }

}
