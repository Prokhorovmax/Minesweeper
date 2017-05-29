import javax.swing.*;
import java.awt.*;

public class Settings extends JFrame {

    public Settings() {
        setVisible(true);
        setSize(500, 240);
        setTitle("Settings");

        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width / 2 - getWidth() / 2,
                size.height / 2 - getHeight() / 2);

        setLayout(new GridLayout(4, 2));

        JTextField column = new JTextField();
        JTextField row = new JTextField();
        JTextField mines = new JTextField();

        JButton ok = new JButton("OK");
        ok.setFocusPainted(false);

        JLabel text1 = new JLabel("ENTER COLUMN NUMBER [6..40]:");
        JLabel text2 = new JLabel("ENTER ROW NUMBER [2..30]:");
        JLabel text3 = new JLabel("ENTER MINES NUMBER:");
        JLabel text4 = new JLabel("TO SAVE CHANGES PRESS OK.");
        text1.setHorizontalAlignment(SwingConstants.CENTER);
        text2.setHorizontalAlignment(SwingConstants.CENTER);
        text3.setHorizontalAlignment(SwingConstants.CENTER);
        text4.setHorizontalAlignment(SwingConstants.CENTER);

        add(text1);
        add(column);
        add(text2);
        add(row);
        add(text3);
        add(mines);
        add(text4);
        add(ok);

        ok.addActionListener(
                e -> {
                    try {
                        final int columnNumber = Integer.parseInt(column.getText());
                        final int rowNumber = Integer.parseInt(row.getText());
                        final int minesNumber = Integer.parseInt(mines.getText());
                        if (columnNumber > 6 && columnNumber < 41 && rowNumber > 1 && rowNumber < 31 &&
                                minesNumber < rowNumber * columnNumber) {
                            dispose();
                            new Main().newGame(columnNumber, rowNumber, minesNumber);
                        } else {
                            dispose();
                            new Settings();
                        }
                    } catch (NumberFormatException ex) {
                        dispose();
                        new Settings();
                    }
                }
        );
    }
}
