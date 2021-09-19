package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class MainPanel extends JPanel {

    // Constants
    private final int SQUARE_WIDTH = 60;
    private final int HALF_SQUARE_WIDTH = SQUARE_WIDTH / 2;
    private final int SQUARE_HEIGHT = 30;
    private final int START_X = (MainFrame.APP_WIDTH / 2) - (SQUARE_WIDTH / 2);
    private final int START_Y = 50;
    private final int X_GAP = 10;
    private final int Y_GAP = 75;

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

        Queue<int[]> previousLevelPoints = new LinkedList<>();
        Queue<Node> stack = new LinkedList<>();
        Node current = root;
        stack.offer(current);

        int linesDrawn = 0;
        int startX = START_X;
        int startY = START_Y;
        int level = 0;
        int maxNodeForLevel = 1;
        int nNodesForLevel = 0;
        while (!(stack.size() == maxNodeForLevel && stackAllNull(stack))) {
            Node popped = stack.poll();

            previousLevelPoints.offer(new int[]{level, startX, startY});

            // Draw node
            g.drawRect(startX, startY, SQUARE_WIDTH, SQUARE_HEIGHT);

            // Draw lines
            if (!previousLevelPoints.isEmpty() && previousLevelPoints.peek()[0] == level - 1) {
                int[] point = previousLevelPoints.peek();
                g.drawLine(point[1] + HALF_SQUARE_WIDTH, point[2] + SQUARE_HEIGHT, startX + HALF_SQUARE_WIDTH, startY);
                linesDrawn++;
            }

            // Pop after 2 children nodes are connected to parent.
            if (linesDrawn == 2) {
                linesDrawn = 0;
                previousLevelPoints.poll();
            }

            if (popped == null) {
                stack.offer(null);
                stack.offer(null);
                g.drawString("NIL", startX + 10, startY + (SQUARE_HEIGHT / 2));
            } else {
                stack.offer(popped.left);
                stack.offer(popped.right);
                g.drawString(popped.element.toString(), startX + 10, startY + (SQUARE_HEIGHT / 2));
            }

            if (nNodesForLevel % 2 == 0) {
                startX += (SQUARE_WIDTH + X_GAP);
            } else {
                startX += SQUARE_WIDTH + X_GAP;
            }
            nNodesForLevel++;
            if (nNodesForLevel == maxNodeForLevel) {
                level++;
                maxNodeForLevel *= 2;
                nNodesForLevel = 0;
                startY += SQUARE_HEIGHT + Y_GAP;
                startX = START_X - ((SQUARE_WIDTH + X_GAP) * maxNodeForLevel / 2) + SQUARE_WIDTH / 2;
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
