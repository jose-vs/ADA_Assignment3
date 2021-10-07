package GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    // Constants
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int APP_WIDTH = (int) SCREEN_SIZE.getWidth();
    public static final int APP_HEIGHT = (int) (SCREEN_SIZE.getHeight() - 50);

    // Fields
    private SpringLayout layout;
    private MainPanel mainPanel;

    public MainFrame(String title, MainPanel mainPanel) {
        super(title);

        // Init layout
        initLayout();

        // Init components
        initComponents(mainPanel);

        // Add components
        addAllComponents(mainPanel);

        // Set layout constraints
        setLayoutConstraints();

        // Settings
        initSettings();
    }

    private void initLayout() {
        layout = new SpringLayout();
        getContentPane().setLayout(layout);
    }

    private void initComponents(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    private void initSettings() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(APP_WIDTH, APP_HEIGHT));
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void addAllComponents(JComponent ...components) {
        for (JComponent component : components) {
            getContentPane().add(component);
        }
    }

    private void setLayoutConstraints() {
        layout.putConstraint(SpringLayout.WEST, mainPanel, 0, SpringLayout.WEST, getContentPane());
        layout.putConstraint(SpringLayout.EAST, mainPanel, 0, SpringLayout.EAST, getContentPane());
        layout.putConstraint(SpringLayout.NORTH, mainPanel, 0, SpringLayout.NORTH, getContentPane());
        layout.putConstraint(SpringLayout.SOUTH, mainPanel, 0, SpringLayout.SOUTH, getContentPane());
    }
}
