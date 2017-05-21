import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;


public class MainFrame extends JFrame implements ActionListener{
    private JButton reset = new JButton("RESET");

    private MainFrame(String s){
        setVisible(true);
        setSize(635, 585);
        setTitle("Minesweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit toolkit = getToolkit();
        Dimension size = toolkit.getScreenSize();
        setLocation(size.width/2 - getWidth()/2,
                size.height/2 - getHeight()/2);

        setLayout(new BorderLayout());
        add(reset, NORTH);
        reset.addActionListener(this);
        add(new Board(), CENTER);
    }

    public static void main(String[] args) {
        new MainFrame("Minesweeper");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(reset)){
            dispose();
            new MainFrame("Minesweeper");
        }
    }
}
