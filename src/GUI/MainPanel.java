package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private final JLabel leftChildLegend;
    private final JLabel rightChildLegend;
    private final JTextField numberInput;
    private final JButton btnAdd;
    // Fields
    Node root = null;
    int[] sample = {50, 24, 20, 30, 19, 70, 65, 80, 64, 85};
    private SpringLayout layout;

    public MainPanel() {
        setBackground(Color.DARK_GRAY);

        // Populate with dummy data
        for (Integer num : sample) {
            addNode(new Node(num));
        }

        initLayout();

        leftChildLegend = new JLabel("Left Child line is color green.");
        leftChildLegend.setFont(new Font("Impact", Font.PLAIN, 18));
        leftChildLegend.setForeground(Color.GREEN);
        rightChildLegend = new JLabel("Right Child line is color orange.");
        rightChildLegend.setFont(new Font("Impact", Font.PLAIN, 18));
        rightChildLegend.setForeground(Color.ORANGE);
        numberInput = new JTextField(0);
        numberInput.setPreferredSize(new Dimension(100, 30));
        btnAdd = new JButton("Add");
        btnAdd.setPreferredSize(new Dimension(100, 30));
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (numberInput.getText().length() > 0) {
                    try {
                        int number = Integer.parseInt(numberInput.getText());
                        addNodeGUI(number);
                        numberInput.setText("");
                    } catch (NumberFormatException err) {
                        JOptionPane.showMessageDialog(getParent(),
                                "Input must be a valid whole number.",
                                "Number Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        add(leftChildLegend);
        add(rightChildLegend);
        add(numberInput);
        add(btnAdd);

        setLayoutConstraints();
    }

    private void initLayout() {
        layout = new SpringLayout();
        setLayout(layout);
    }

    private void setLayoutConstraints() {
        layout.putConstraint(SpringLayout.WEST, leftChildLegend, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, leftChildLegend, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, rightChildLegend, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, rightChildLegend, 10, SpringLayout.SOUTH, leftChildLegend);
        layout.putConstraint(SpringLayout.WEST, numberInput, 10, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, numberInput, -10, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, btnAdd, 10, SpringLayout.EAST, numberInput);
        layout.putConstraint(SpringLayout.SOUTH, btnAdd, -10, SpringLayout.SOUTH, this);
    }

    private void addNodeGUI(int number) {
        addNode(new Node(number));
        repaint();
        revalidate();
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
                    } else current = current.right;
                } else if (current.element.compareTo(node.element) > 0) {
                    if (current.left == null) {
                        current.left = node;
                        isDone = true;
                    } else current = current.left;
                } else {
                    isDone = true;
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Impact", Font.PLAIN, 18));
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

            // Draw lines
            // Green line means left child connected
            // Orange line means right child connected
            if (linesDrawn == 0) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.ORANGE);
            }

            if (!previousLevelPoints.isEmpty() && previousLevelPoints.peek()[0] == level - 1) {
                int[] point = previousLevelPoints.peek();
                if (popped != null) {
                    g.drawLine(point[1] + HALF_SQUARE_WIDTH, point[2] + SQUARE_HEIGHT, startX + HALF_SQUARE_WIDTH, startY);
                }
                linesDrawn++;
            }

            // Pop after 2 children nodes are connected to parent.
            if (linesDrawn == 2) {
                linesDrawn = 0;
                previousLevelPoints.poll();
            }

            // Revert color back to white to draw nodes and strings.
            g.setColor(Color.WHITE);

            if (popped == null) {
                stack.offer(null);
                stack.offer(null);
            } else {
                stack.offer(popped.left);
                stack.offer(popped.right);

                // Draw node
                g.drawRect(startX, startY, SQUARE_WIDTH, SQUARE_HEIGHT);
                g.drawString(popped.element.toString(), startX + 20, startY + (SQUARE_HEIGHT / 2) + 10);
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
