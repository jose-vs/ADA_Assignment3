/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BalancedPersistentDynamicSet.BPDS;
import BinarySearchTree.BinarySearchTree;
import GUI.MainFrame;
import GUI.MainPanel;
import PersistentDynamicSet.PersistentDynamicSet;

import java.util.Arrays;

/**
 *
 * @author jcvsa
 */
public class MainGUILauncher {

    public static void main(String[] args) {
        // Mock data
        int[] sampleData = {50, 24, 20, 30, 19, 70, 65, 80};
        BinarySearchTree<Integer> tree = new BinarySearchTree<Integer>();
        PersistentDynamicSet<Integer> persistentTree = new PersistentDynamicSet<Integer>();
        BPDS<Integer> balancedPersistentTree = new BPDS<Integer>();
        Arrays.stream(sampleData).forEach(e -> {
            tree.insert(e);
            persistentTree.insert(e);
            balancedPersistentTree.insert(e);
        });

        // Init GUI
        MainPanel mainPanel = new MainPanel(tree, persistentTree, balancedPersistentTree);
        MainFrame frame = new MainFrame("Binary Search Tree", mainPanel);
        frame.setVisible(true);
    }
}
