
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Board extends JPanel {

    // Field parameters:
    private final int xNum = 20;
    private final int yNum = 20;
    private final int minesNum = 50;

    private int[][] minesCount = new int[xNum][yNum];
    private Cell[][] cells = new Cell[xNum][yNum];
    private static final int MINE = 10;

    public Board() {
        super();
        setBackground(Color.gray);
        createBoard();
        createMines();
        this.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            leftClick(e.getX(), e.getY());
                        }

                        if (e.getButton() == MouseEvent.BUTTON3) {
                            rightClick(e.getX(), e.getY());
                        }
                    }
                }
        );
    }

    private void createBoard() {
        for (int i = 0; i < yNum; i++) {
            for (int j = 0; j < xNum; j++) {
                if (i % 2 == 0) {
                    final Point point = new Point(30 * (j + 1), 25 * i);
                    cells[i][j] = new Cell(point);
                } else {
                    final Point point = new Point(30 * (j + 1) - 15, 25 * i);
                    cells[i][j] = new Cell(point);
                }
            }
        }
    }

    private void createMines() {
        // creating random mine spots
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < yNum; i++) {
            for (int j = 0; j < xNum; j++) {
                list.add(i * 100 + j);
            }
        }
        minesCount = new int[xNum][yNum];
        for (int i = 0; i < minesNum; i++) {
            int random = (int) (Math.random() * list.size());
            minesCount[list.get(random) / 100][list.get(random) % 100] = MINE;
            list.remove(random);
        }

        // counting neighbour mines
        for (int i = 0; i < yNum; i++) {
            for (int j = 0; j < xNum; j++) {
                if (minesCount[i][j] != MINE) {
                    int neighbourMines = 0;
                    if (i % 2 == 0) {
                        // up left
                        if (i > 0 && minesCount[i - 1][j] == MINE) {
                            neighbourMines++;
                        }
                        // up right
                        if (i > 0 && j < xNum - 1 && minesCount[i - 1][j + 1] == MINE) {
                            neighbourMines++;
                        }
                        // right
                        if (j < xNum - 1 && minesCount[i][j + 1] == MINE) {
                            neighbourMines++;
                        }
                        // down right
                        if (i < yNum - 1 && j < xNum - 1 && minesCount[i + 1][j + 1] == MINE) {
                            neighbourMines++;
                        }
                        // down left
                        if (i < yNum - 1 && minesCount[i + 1][j] == MINE) {
                            neighbourMines++;
                        }
                        // left
                        if (j > 0 && minesCount[i][j - 1] == MINE) {
                            neighbourMines++;
                        }
                    } else {
                        // up left
                        if (i > 0 && j > 0 && minesCount[i - 1][j - 1] == MINE) {
                            neighbourMines++;
                        }
                        // up right
                        if (i > 0 && minesCount[i - 1][j] == MINE) {
                            neighbourMines++;
                        }
                        // right
                        if (j < xNum - 1 && minesCount[i][j + 1] == MINE) {
                            neighbourMines++;
                        }
                        // down right
                        if (i < yNum - 1 && minesCount[i + 1][j] == MINE) {
                            neighbourMines++;
                        }
                        // down left
                        if (i < yNum - 1 && j > 0 && minesCount[i + 1][j - 1] == MINE) {
                            neighbourMines++;
                        }
                        // left
                        if (j > 0 && minesCount[i][j - 1] == MINE) {
                            neighbourMines++;
                        }
                    }
                    minesCount[i][j] = neighbourMines;
                }
            }
        }

    }

    private void leftClick(int x, int y) {
        for (int i = 0; i < yNum; i++) {
            for (int j = 0; j < xNum; j++) {
                if (cells[i][j].getPolygon().contains(x, y)) {
                    if (minesCount[i][j] == MINE) {
                        cells[i][j].setOpen();
                        cells[i][j].setColor(Color.RED);
                        lost();
                    } else if (minesCount[i][j] == 0) {
                        cells[i][j].setOpen();
                        cells[i][j].setColor(Color.WHITE);
                        ArrayList<Integer> toClear = new ArrayList<>();
                        toClear.add(i * 100 + j);
                        clearZeros(toClear);
                        checkWin();
                    } else {
                        cells[i][j].setOpen();
                        cells[i][j].setColor(Color.WHITE);
                        checkWin();
                    }
                }
            }
        }
        repaint();
    }

    private void rightClick(int x, int y) {
        for (int i = 0; i < yNum; i++) {
            for (int j = 0; j < xNum; j++) {
                if (cells[i][j].getPolygon().contains(x, y)) {
                    cells[i][j].setColor(Color.GREEN);
                }
            }
        }
        repaint();
    }

    private void lost() {
        for (int i = 0; i < yNum; i++) {
            for (int j = 0; j < xNum; j++) {
                cells[i][j].setOpen();
                if (cells[i][j].getColor() != Color.GREEN) {
                    if (minesCount[i][j] == MINE) {
                        cells[i][j].setColor(Color.RED);
                    } else {
                        cells[i][j].setColor(Color.WHITE);
                    }
                }
                if (cells[i][j].getColor() == Color.GREEN && minesCount[i][j] != MINE) {
                    cells[i][j].setColor(Color.ORANGE);
                }
            }
        }
        JOptionPane.showMessageDialog(null, "Unfortunately, You lost!");
    }

    private void clearZeros(ArrayList<Integer> toClear) {
        if (toClear.size() != 0) {
            int i = toClear.get(0) / 100;
            int j = toClear.get(0) % 100;
            toClear.remove(0);
            if (i % 2 == 0) {
                // up left
                if (i > 0 && !cells[i - 1][j].isOpen()) {
                    cells[i - 1][j].setOpen();
                    cells[i - 1][j].setColor(Color.WHITE);
                    if (minesCount[i - 1][j] == 0) {
                        toClear.add((i - 1) * 100 + j);
                    }
                }
                // up right
                if (i > 0 && j < xNum - 1 && !cells[i - 1][j + 1].isOpen()) {
                    cells[i - 1][j + 1].setOpen();
                    cells[i - 1][j + 1].setColor(Color.WHITE);
                    if (minesCount[i - 1][j + 1] == 0) {
                        toClear.add((i - 1) * 100 + (j + 1));
                    }
                }
                // right
                if (j < xNum - 1 && !cells[i][j + 1].isOpen()) {
                    cells[i][j + 1].setOpen();
                    cells[i][j + 1].setColor(Color.WHITE);
                    if (minesCount[i][j + 1] == 0) {
                        toClear.add(i * 100 + (j + 1));
                    }
                }
                // down right
                if (i < yNum - 1 && j < xNum - 1 && !cells[i + 1][j + 1].isOpen()) {
                    cells[i + 1][j + 1].setOpen();
                    cells[i + 1][j + 1].setColor(Color.WHITE);
                    if (minesCount[i + 1][j + 1] == 0) {
                        toClear.add((i + 1) * 100 + (j + 1));
                    }
                }
                // down left
                if (i < yNum - 1 && !cells[i + 1][j].isOpen()) {
                    cells[i + 1][j].setOpen();
                    cells[i + 1][j].setColor(Color.WHITE);
                    if (minesCount[i + 1][j] == 0) {
                        toClear.add((i + 1) * 100 + j);
                    }
                }
                // left
                if (j > 0 && !cells[i][j - 1].isOpen()) {
                    cells[i][j - 1].setOpen();
                    cells[i][j - 1].setColor(Color.WHITE);
                    if (minesCount[i][j - 1] == 0) {
                        toClear.add(i * 100 + (j - 1));
                    }
                }
            } else {
                // up left
                if (i > 0 && j > 0 && !cells[i - 1][j - 1].isOpen()) {
                    cells[i - 1][j - 1].setOpen();
                    cells[i - 1][j - 1].setColor(Color.WHITE);
                    if (minesCount[i - 1][j - 1] == 0) {
                        toClear.add((i - 1) * 100 + (j - 1));
                    }
                }
                // up right
                if (i > 0 && !cells[i - 1][j].isOpen()) {
                    cells[i - 1][j].setOpen();
                    cells[i - 1][j].setColor(Color.WHITE);
                    if (minesCount[i - 1][j] == 0) {
                        toClear.add((i - 1) * 100 + j);
                    }
                }
                // right
                if (j < xNum - 1 && !cells[i][j + 1].isOpen()) {
                    cells[i][j + 1].setOpen();
                    cells[i][j + 1].setColor(Color.WHITE);
                    if (minesCount[i][j + 1] == 0) {
                        toClear.add(i * 100 + (j + 1));
                    }
                }
                // down right
                if (i < yNum - 1 && !cells[i + 1][j].isOpen()) {
                    cells[i + 1][j].setOpen();
                    cells[i + 1][j].setColor(Color.WHITE);
                    if (minesCount[i + 1][j] == 0) {
                        toClear.add((i + 1) * 100 + j);
                    }
                }
                // down left
                if (i < yNum - 1 && j > 0 && !cells[i + 1][j - 1].isOpen()) {
                    cells[i + 1][j - 1].setOpen();
                    cells[i + 1][j - 1].setColor(Color.WHITE);
                    if (minesCount[i + 1][j - 1] == 0) {
                        toClear.add((i + 1) * 100 + (j - 1));
                    }
                }
                // left
                if (j > 0 && !cells[i][j - 1].isOpen()) {
                    cells[i][j - 1].setOpen();
                    cells[i][j - 1].setColor(Color.WHITE);
                    if (minesCount[i][j - 1] == 0) {
                        toClear.add(i * 100 + (j - 1));
                    }
                }
            }
            clearZeros(toClear);
        }
    }

    private void checkWin() {
        boolean win = true;
        for (int i = 0; i < yNum; i++) {
            for (int j = 0; j < xNum; j++) {
                if (minesCount[i][j] != MINE && !cells[i][j].isOpen()) {
                    win = false;
                    break;
                }
            }
        }
        if (win) {
            for (int i = 0; i < yNum; i++) {
                for (int j = 0; j < xNum; j++) {
                    if (minesCount[i][j] == MINE) {
                        cells[i][j].setColor(Color.GREEN);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Congratulations, You won!");
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < yNum; i++) {
            for (int j = 0; j < xNum; j++) {
                g.setColor(cells[i][j].getColor());
                g.fillPolygon(cells[i][j].getPolygon());
                g.setColor(Color.BLACK);
                g.drawPolygon(cells[i][j].getPolygon());
                if (cells[i][j].isOpen()) {
                    g.setFont(new Font("Arial", Font.BOLD, 14));
                    int x = cells[i][j].getPolygon().xpoints[0] - 2;
                    int y = cells[i][j].getPolygon().ypoints[0] + 23;
                    if (minesCount[i][j] != MINE) {
                        g.drawString(minesCount[i][j] + "", x, y);
                    }
                }
            }
        }
    }
}
