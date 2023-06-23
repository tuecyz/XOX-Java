package org.example;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToe ticTacToe = new TicTacToe();
        });
    }
}

class TicTacToe implements ActionListener {

    private JFrame frame;
    private JPanel titlePanel;
    private JPanel buttonPanel;
    private JLabel textField;
    private JButton[] buttons;
    private JButton clearButton;
    private boolean player1Turn;

    TicTacToe() {
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());

        textField = new JLabel("Tic-Tac-Toe");
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setFont(new Font("Ink Free", Font.BOLD, 40));

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));

        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        clearButton = new JButton("Blokları Temizle");
        clearButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        clearButton.setFocusable(false);
        clearButton.addActionListener(this);

        titlePanel.add(textField, BorderLayout.NORTH);
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(clearButton, BorderLayout.SOUTH);

        resetGame();

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clearButton) {
            resetGame();
            return;
        }

        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (buttons[i].getText().isEmpty()) {
                    if (player1Turn) {
                        buttons[i].setText("X");
                        buttons[i].setForeground(Color.RED);
                        textField.setText("O's Turn");
                    } else {
                        buttons[i].setText("O");
                        buttons[i].setForeground(Color.BLUE);
                        textField.setText("X's Turn");
                    }
                    player1Turn = !player1Turn;
                    checkWin();
                }
            }
        }
    }
    private void checkWin() {
        int[][] winningPositions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6} // Diagonals
        };

        for (int[] pos : winningPositions) {
            String buttonText = buttons[pos[0]].getText();
            if (!buttonText.isEmpty() && buttonText.equals(buttons[pos[1]].getText()) &&
                    buttonText.equals(buttons[pos[2]].getText())) {
                buttons[pos[0]].setBackground(Color.GREEN);
                buttons[pos[1]].setBackground(Color.GREEN);
                buttons[pos[2]].setBackground(Color.GREEN);

                disableButtons();
                textField.setText(buttonText + " wins");
                return;
            }
        }

        boolean isBoardFull = true;
        for (int i = 0; i < 9; i++) {
            if (buttons[i].getText().isEmpty()) {
                isBoardFull = false;
                break;
            }
        }

        if (isBoardFull) {
            disableButtons();
            textField.setText("Berabere");
        }
    }

    private void disableButtons() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
    }

    private void resetGame() {
        for (JButton button : buttons) {
            button.setText("");
            button.setBackground(null);
            button.setEnabled(true);
        }

        player1Turn = true;
        textField.setText("X's Turn");
    }
}
