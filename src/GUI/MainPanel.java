package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

public class MainPanel extends JPanel {

    // Constants
    private final int SQUARE_WIDTH = 120;
    private final int SQUARE_HEIGHT = 60;
    private final int START_X = (MainFrame.APP_WIDTH / 2) - (SQUARE_WIDTH / 2);
    private final int START_Y = 50;
    private final int GAP = 30;

    Node root = null;

    int[] sample = {50, 24, 20, 30, 19, 70, 65, 80, 64, 85};

    public MainPanel() {
        setBackground(Color.DARK_GRAY);

        for (Integer num : sample) {
            addNode(new Node(num));
        }
    }

    private void addNode(Node node) {
        if (root == null) {
            root = node;
        } else {
            Node current = root;
            boolean isDone = false;
            while (!isDone) {
                if (current.element.compareTo(node.element) < 0) {
                    if (current.right == null) {
                        current.right = node;
                        isDone = true;
                    }
                    else current = current.right;
                } else if (current.element.compareTo(node.element) > 0) {
                    if (current.left == null) {
                        current.left = node;
                        isDone = true;
                    }
                    else current = current.left;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        drawTree(g);
    }

    private void drawTree(Graphics g) {
        if (root == null) return;

        Queue<Node> stack = new LinkedList<>();
        Node current = root;
        stack.offer(current);

        int startX = START_X;
        int startY = START_Y;
        int maxNodeForLevel = 1;
        int nNodesForLevel = 0;
        while (!(stack.size() == maxNodeForLevel && stackAllNull(stack))) {
            Node popped = stack.poll();

            g.drawRect(startX, startY, SQUARE_WIDTH, SQUARE_HEIGHT);
            if (popped == null) {
                stack.offer(null);
                stack.offer(null);
                g.drawString("NIL", startX + 10, startY + (SQUARE_HEIGHT / 2));
            } else {
                stack.offer(popped.left);
                stack.offer(popped.right);
                g.drawString(popped.element.toString(), startX + 10, startY + (SQUARE_HEIGHT / 2));
            }

            startX += SQUARE_WIDTH + GAP;
            nNodesForLevel++;
            if (nNodesForLevel == maxNodeForLevel) {
                maxNodeForLevel *= 2;
                nNodesForLevel = 0;
                startY += SQUARE_HEIGHT + GAP;
                startX = START_X - ((SQUARE_WIDTH + GAP) * maxNodeForLevel / 2 ) + (SQUARE_WIDTH / 2);
            }
        }
    }

    private boolean stackAllNull(Queue<Node> stack) {
        return stack.stream().allMatch(Objects::isNull);
    }

    private class Node {

        Integer element;
        Node left, right;

        public Node(Integer element, Node left, Node right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

        public Node(Integer element) {
            this(element, null, null);
        }
    }
}
