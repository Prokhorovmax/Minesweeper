import javax.swing.*;

public class Main {

    private static JFrame mainFrame;

    public void newGame(int col, int row, int mines) {
        mainFrame.dispose();
        mainFrame = new MainFrame(col, row, mines);
    }

    public static void main(String[] args) {
        mainFrame = new MainFrame(20, 20, 50);
    }
}
