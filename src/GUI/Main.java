/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author jcvsa
 */
public class Main {
    public static void main(String[] args) {
        MainPanel mainPanel = new MainPanel();
        MainFrame frame = new MainFrame("Binary Search Tree", mainPanel);
        frame.setVisible(true);
    }
}
