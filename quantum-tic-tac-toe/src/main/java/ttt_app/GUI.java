package ttt_app;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.SwingConstants;

public class GUI implements ActionListener {

    static Board gameBoard = new Board();
    JFrame frame;
    ArrayList<JButton> buttonList;
    ArrayList<JButton> buttonListInner;
    String number;
    JLabel infoLabel = new JLabel("Player X Turn", SwingConstants.CENTER);
    JLabel emptyLabel = new JLabel();// "EMPTY", SwingConstants.CENTER);

    JPanel panel = new JPanel(new GridLayout(3, 3));

    int chosenEntangledTile;
    String chosenMark = "";
    String bigMark = "";

    GUI(Board givenBoard) {
        frame = new JFrame("Tic-Tac-Toe");
        buttonList = new ArrayList<JButton>();
        for (int i = 0; i < 9; i++) {
            number = Integer.toString(i);
            buttonList.add(new JButton());
            buttonList.get(i).addActionListener(this);
            frame.add(buttonList.get(i));
            if (i == 2) {
                frame.add(infoLabel);
            } else if (i == 5) {
                frame.add(panel);
            } else if (i == 8) {
                frame.add(emptyLabel);
            }
        }

        // inner

        buttonListInner = new ArrayList<JButton>();
        for (int i = 0; i < 9; i++) {
            number = Integer.toString(i);
            buttonListInner.add(new JButton(number));
            buttonListInner.get(i).setVisible(false);
            buttonListInner.get(i).addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 9; i++) {
                        if (e.getSource() == buttonListInner.get(i)) {
                            chosenMark = buttonListInner.get(i).getText();
                            break;
                        }
                    }
                    gameBoard.resolveEntanglement(chosenMark, gameBoard.tileList.get(chosenEntangledTile));

                    // highlight colapsed tiles
                    for (int i = 0; i < gameBoard.tileList.size(); i++) {
                        if (gameBoard.tileList.get(i).checkIfTileColapsed()) {
                            bigMark = gameBoard.tileList.get(i).getBigMark().markSyntax();
                            buttonList.get(gameBoard.tileList.get(i).getNumberOfTile()).setText(bigMark);
                            buttonList.get(gameBoard.tileList.get(i).getNumberOfTile()).setEnabled(false);
                            buttonList.get(gameBoard.tileList.get(i).getNumberOfTile())
                                    .setBackground(Color.DARK_GRAY);
                            buttonList.get(gameBoard.tileList.get(i).getNumberOfTile())
                                    .setFont(new Font("TimesRoman", Font.BOLD, 50));
                        }
                    }

                    // unlock other tiles
                    for (int i = 0; i < 9; i++) {
                        if (!gameBoard.tileList.get(i).checkIfTileColapsed()) {
                            buttonList.get(i).setEnabled(true);
                        }
                    }
                    // block again mark buttons
                    for (int i = 0; i < 9; i++) {
                        buttonListInner.get(i).setVisible(false);
                    }

                    infoLabel.setText("Player " + gameBoard.whichPlayerTurn() + " turn");
                    resolvingEntanglementFlag = false;

                    if ((!gameBoard.checkIfWinner()) && (!gameBoard.checkIfDraw())) {
                        return;
                    } else {
                        if (gameBoard.draw) {
                            infoLabel.setText("Draw!!!Nobody wins");
                            for (int i = 0; i < 9; i++) {
                                buttonList.get(i).setEnabled(false);
                            }
                            return;
                        }

                        infoLabel.setText("<html>Congratulations to player " + gameBoard.whoIsWinner()
                                + " who has won!!!</html>");
                        for (int i = 0; i < 9; i++) {
                            buttonList.get(i).setEnabled(false);
                        }
                    }
                }

            });
            panel.add(buttonListInner.get(i));
        }

        Image icon = Toolkit.getDefaultToolkit().getImage(
                "C:\\Users\\Michal\\Desktop\\SEM_IV\\Projekt_indywidualny\\Quantum-TicTacToe\\quantum-tic-tac-toe\\graphics\\x.png");

        frame.setIconImage(icon);
        frame.setLayout(new GridLayout(3, 4));
        frame.setSize(700, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    Character mark = 'x';
    int numberOFMove = 0;
    String text = "";
    ArrayList<Integer> chosenTiles = new ArrayList<Integer>(0);
    boolean resolvingEntanglementFlag = false;
    String additionalString = "";

    public void actionPerformed(ActionEvent e) {
        if ((!gameBoard.checkIfWinner()) && (!gameBoard.checkIfDraw())) {
            if (!resolvingEntanglementFlag) {
                text = mark + Integer.toString(gameBoard.getRoundNumber());

                for (int i = 0; i < buttonList.size(); i++) {
                    if (e.getSource() == buttonList.get(i)) {

                        buttonList.get(i).setText(buttonList.get(i).getText() + " " + text);
                        buttonList.get(i).setEnabled(false);
                        chosenTiles.add(i);
                        numberOFMove++;
                        break;
                    }
                }

                // if player made 2 moves change turn
                if (numberOFMove == 2) {
                    gameBoard.makeMove(chosenTiles, "multi");
                    for (int i = 0; i < chosenTiles.size(); i++) {
                        buttonList.get(chosenTiles.get(i)).setEnabled(true);
                    }
                    changeMark();
                    gameBoard.changePlayer();
                    numberOFMove = 0;

                    if (gameBoard.player == false) {
                        infoLabel.setText("Player X Turn");
                        mark = 'x';
                    } else if (gameBoard.player == true) {
                        infoLabel.setText("Player O Turn");
                        mark = 'o';
                    }
                    chosenTiles.clear();
                }

                // checking if entanglement occured
                if (gameBoard.checkIfIsEntanglement()) {

                    infoLabel.setText("<html>Entanglement occured: player " + mark + " move</html>");

                    // block all tiles
                    for (int i = 0; i < buttonList.size(); i++) {
                        buttonList.get(i).setEnabled(false);
                    }

                    // color entangled tiles
                    for (int i = 0; i < gameBoard.entangledTilesList.size(); i++) {
                        buttonList.get(gameBoard.entangledTilesList.get(i).getNumberOfTile()).setBackground(Color.CYAN);
                        buttonList.get(gameBoard.entangledTilesList.get(i).getNumberOfTile())
                                .setForeground(Color.BLACK);
                        buttonList.get(gameBoard.entangledTilesList.get(i).getNumberOfTile()).setEnabled(true);
                    }

                    resolvingEntanglementFlag = true;
                }

            } else {
                for (int i = 0; i < buttonList.size(); i++) {
                    if (e.getSource() == buttonList.get(i)) {
                        chosenEntangledTile = i;
                        break;
                    }
                }
                for (int i = 0; i < 9; i++) {
                    buttonListInner.get(i).setVisible(false);
                }

                for (int i = 0; i < gameBoard.marksInEntanglementList.size(); i++) {
                    for (int j = 0; j < gameBoard.tileList.get(chosenEntangledTile).marklist.size(); j++) {
                        if (gameBoard.marksInEntanglementList.get(i)
                                .markSyntax().equals(gameBoard.tileList.get(chosenEntangledTile).marklist.get(j)
                                        .markSyntax())) {
                            buttonListInner.get(i).setVisible(true);
                            text = gameBoard.marksInEntanglementList.get(i).markSyntax();
                            buttonListInner.get(i).setText(text);
                            break;
                        }
                    }
                }

                // block other tile buttons
                for (int i = 0; i < buttonList.size(); i++) {
                    if (!gameBoard.tileList.get(i).checkIfTileEntangled()) {
                        buttonList.get(i).setEnabled(false);
                    }
                }

            }
        } else {
            if (gameBoard.draw) {
                infoLabel.setText("Draw!!!Nobody wins");
                for (int i = 0; i < 9; i++) {
                    buttonList.get(i).setEnabled(false);
                }
                return;
            }

            infoLabel.setText("Congratulations to player " + gameBoard.whoIsWinner() + " who has won!!!");
            for (int i = 0; i < 9; i++) {
                buttonList.get(i).setEnabled(false);
            }
        }
    }

    private void changeMark() {
        if (mark == 'x') {
            mark = 'o';
        } else if (mark == 'o') {
            mark = 'x';
        }
    }

    public static void main(String[] args) {
        new GUI(gameBoard);
    }

}