/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BalancedPersistentDynamicSet.BPDS;
import BinarySearchTree.BinarySearchTree;

import java.util.Arrays;

/**
 *
 * @author jcvsa
 */
public class Main {
    public static void main(String[] args) {
        // Mock data
        int[] sampleData = {50, 24, 20, 30, 19, 70, 65, 80, 64, 85};
        BinarySearchTree<Integer> sampleTree = new BinarySearchTree<Integer>();
        BPDS<Integer> sampleBalancedTree = new BPDS<Integer>();
        Arrays.stream(sampleData).forEach(e -> {
            sampleTree.add(e);
            sampleBalancedTree.add(e);
        });

        // Init GUI
        MainPanel mainPanel = new MainPanel(sampleTree, sampleBalancedTree);
        MainFrame frame = new MainFrame("Binary Search Tree", mainPanel);
        frame.setVisible(true);
    }
}
