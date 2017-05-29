import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;

public class MainFrame extends JFrame {

    public MainFrame(int columnNumber, int rowNumber, int minesNumber) {
        setSize(30 * (columnNumber + 1) + 5, 25 * rowNumber + 95);
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2,
                size.height / 2 - getHeight() / 2);

        setLayout(new BorderLayout());

        JButton resetButton = new JButton("RESET");
        JButton settingsButton = new JButton("SETTINGS");
        JPanel toolbar = new JPanel();

        toolbar.setLayout(new FlowLayout());
        toolbar.add(resetButton);
        toolbar.add(settingsButton);
        toolbar.setBackground(Color.GRAY);

        Board board = new Board(columnNumber, rowNumber, minesNumber);
        add(board, CENTER);
        add(toolbar, NORTH);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(
                e -> new Main().newGame(columnNumber, rowNumber, minesNumber)
        );
        settingsButton.setFocusPainted(false);
        settingsButton.addActionListener(
                e -> new Settings()
        );
        setVisible(true);
    }
}