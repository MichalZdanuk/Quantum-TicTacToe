package ttt_app;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI implements ActionListener {
    static Board gameBoard = new Board();
    JFrame frame;
    // ArrayList<JButton> buttonList = new ArrayList<JButton>();
    ArrayList<ButtonOnBoard> buttonOnBoardList = new ArrayList<ButtonOnBoard>();

    GUI(Board passedBoard) {
        frame = new JFrame("Quantum Tic-Tac-Toe");
        for (int i = 0; i < 9; i++) {
            buttonOnBoardList.add(new ButtonOnBoard(new JButton(), ""));
            buttonOnBoardList.get(i).getJButton().addActionListener(this);
            frame.add(buttonOnBoardList.get(i).getJButton());
            // buttonList.add(new JButton(Integer.toString(i)));
            // buttonList.get(i).addActionListener(this);
            // frame.add(buttonList.get(i));
        }

        Image icon = Toolkit.getDefaultToolkit().getImage(
                "C:\\Users\\Michal\\Desktop\\SEM_IV\\Projekt_indywidualny\\Quantum-TicTacToe\\quantum-tic-tac-toe\\graphics\\x.png");

        frame.setIconImage(icon);
        frame.setLayout(new GridLayout(3, 3));
        frame.setSize(600, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    String text = "";
    int numberOfMoves = 0;
    Character mark;
    ArrayList<Integer> chosenTiles = new ArrayList<Integer>(0);

    public void actionPerformed(ActionEvent e) {
        
        if (numberOfMoves == 2) {
            numberOfMoves = 0;
            gameBoard.changePlayer();
            chosenTiles.clear();
        }

        if (gameBoard.player == true) {
            mark = 'x';
        } else if (gameBoard.player == false) {
            mark = 'o';
        }
        text = mark + " ";
        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttonOnBoardList.get(i).getJButton()) {
                buttonOnBoardList.get(i).addText(text);
                chosenTiles.add(i);
            }
        }
        numberOfMoves++;
        //gameBoard.makeMove("multi"); <- trzeba przekazac tutaj wybrane dwa pola

    }

    public static void main(String[] args) {

        new GUI(gameBoard);
    }
}