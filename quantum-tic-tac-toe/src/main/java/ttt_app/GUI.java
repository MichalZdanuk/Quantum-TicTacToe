package ttt_app;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GUI implements ActionListener {
    static Board gameBoard = new Board();
    JFrame frame;
    JLabel infoLabel = new JLabel("Player X move", SwingConstants.CENTER);
    JPanel doNothing = new JPanel();
    JTextField tf2 = new JTextField();

    ArrayList<ButtonOnBoard> buttonOnBoardList = new ArrayList<ButtonOnBoard>();

    GUI(Board passedBoard) {
        frame = new JFrame("Quantum Tic-Tac-Toe");
        for (int i = 0; i < 9; i++) {
            buttonOnBoardList.add(new ButtonOnBoard(new JButton(), ""));
            buttonOnBoardList.get(i).getJButton().addActionListener(this);
            frame.add(buttonOnBoardList.get(i).getJButton());
            if (i == 2) {
                frame.add(infoLabel);
            } else if (i == 5) {
                frame.add(tf2);
            } else if (i == 8) {
                frame.add(doNothing);
            }
        }

        Image icon = Toolkit.getDefaultToolkit().getImage(
                "C:\\Users\\Michal\\Desktop\\SEM_IV\\Projekt_indywidualny\\Quantum-TicTacToe\\quantum-tic-tac-toe\\graphics\\x.png");

        frame.setIconImage(icon);
        frame.setLayout(new GridLayout(3, 4));
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    String text = "";
    int numberOfMoves = 0;
    Character mark;
    ArrayList<Integer> chosenTiles = new ArrayList<Integer>(0);
    int previousMove;
    ArrayList<Integer> blockedTiles = new ArrayList<Integer>(0);

    public void actionPerformed(ActionEvent e) {
        // System.out.println("inside action");

        if (gameBoard.player == false) {
            mark = 'x';
            infoLabel.setText("Player X move");
        } else if (gameBoard.player == true) {
            mark = 'o';
            infoLabel.setText("Player O move");
        }

        text = mark + Integer.toString(gameBoard.getRoundNumber()) + " ";
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttonOnBoardList.get(i).getJButton()) {
                buttonOnBoardList.get(i).addText(text);
                chosenTiles.add(i);
                blockedTiles.add(i);
                buttonOnBoardList.get(i).getJButton().setEnabled(false);
                break;
            }
        }
        if (numberOfMoves == 1) {
            gameBoard.makeMove(chosenTiles, "multi");
            if (gameBoard.checkIfIsEntanglement()) {
                System.out.println("--entangled--");

                for (int i = 0; i < gameBoard.entangledTilesList.size(); i++) {
                    buttonOnBoardList.get(gameBoard.entangledTilesList.get(i).getNumberOfTile()).getJButton()
                            .setBackground(Color.CYAN);
                    buttonOnBoardList.get(gameBoard.entangledTilesList.get(i).getNumberOfTile()).getJButton()
                            .setForeground(Color.BLACK);
                }
                infoLabel.setText("Entanglement occured");
                for (int i = 0; i < 9; i++) {
                    buttonOnBoardList.get(i).getJButton().setEnabled(false);
                }

            }
        }
        numberOfMoves++;

        if (numberOfMoves == 2) {
            if (!gameBoard.checkIfIsEntanglement()) {
                for (int i = 0; i < blockedTiles.size(); i++) {
                    buttonOnBoardList.get(blockedTiles.get(i)).getJButton().setEnabled(true);
                }
            }
            numberOfMoves = 0;
            gameBoard.changePlayer();
            blockedTiles.clear();
            chosenTiles.clear();

            if (!gameBoard.checkIfIsEntanglement()) {
                if (gameBoard.player == false) {
                    infoLabel.setText("Player X move");
                } else if (gameBoard.player == true) {
                    infoLabel.setText("Player O move");
                }
            }

        }

    }

    public static void main(String[] args) {
        new GUI(gameBoard);
        // System.out.println("inside main");
    }
}