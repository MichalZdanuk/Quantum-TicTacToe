package ttt_app;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static Scanner scanner = new Scanner(System.in);
    private static String chosenTile = "";
    private static String chosenMark = "";

    public static void main(String[] args) {
        Board gameBoard = new Board();
        printGameRules();
        try {
            URL myUrl = new URL("https://github.com/MichalZdanuk/Quantum-TicTacToe");
            System.out.println(myUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        gameBoard.displayBoard();

        while (true) {
            gameBoard.makeMove();
            gameBoard.newDisplayBoard();
            if (gameBoard.checkIfIsEntanglement()) {
                gameBoard.whichPlayer();
                System.out.print("Entanglement occured! Please choose one from entangled tiles: ");
                gameBoard.printListOfEntangledTiles();
                System.out.print("\n");
                chosenTile = scanner.nextLine();
                while (!validateChosenTile(chosenTile, gameBoard)) {
                    System.out.print("You' ve chosen wrong tile! Chose one from: ");
                    gameBoard.printListOfEntangledTiles();
                    System.out.print("\n");
                    chosenTile = scanner.nextLine();
                }
                System.out.println();
                System.out.println("Choose a mark to be collapsed on tile: " + chosenTile + " from "
                        + gameBoard.tileList.get(Integer.parseInt(chosenTile)).printAllMarks());
                chosenMark = scanner.nextLine();
                while (!validateChosenMark(chosenMark, gameBoard.tileList.get(Integer.parseInt(chosenTile)))) {
                    System.out.print("You' ve chosen wrong mark! Choose one from: "
                            + gameBoard.tileList.get(Integer.parseInt(chosenTile)).printAllMarks());
                    System.out.print("\n");
                    chosenMark = scanner.nextLine();
                }
                gameBoard.resolveEntanglement(chosenMark, gameBoard.tileList.get(Integer.parseInt(chosenTile)));
                gameBoard.newDisplayBoard();
            }

        }

    }

    private static boolean validateChosenMark(String chosenMark, Tile chosenTile) {
        for (int i = 0; i < chosenTile.marklist.size(); i++) {
            if (chosenTile.marklist.get(i).markSyntax().equals(chosenMark)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateChosenTile(String tile, Board board) {
        ArrayList<Integer> listOfNumbersToCheck = board.entangledTilesNumbers();
        for (int i = 0; i < board.getSize(); i++) {
            if (listOfNumbersToCheck.get(i) == Integer.parseInt(tile)) {
                return true;
            }
        }
        return false;
    }

    private static void printGameRules() {
        System.out.println("Welcome to game Quantum Tic-Tac-Toe!");
        System.out.println("This is a bit more advanced version of classical tic-tac-toe game");
        System.out.println("Rules:");
        System.out.println(
                "1. Each round one player put small two marks, which are numbered same as round \ne.g. It's first round (time for player X), so he'll put two x1 marks");
        System.out.println("2. You can't place two small marks during round in the same tile.");
        System.out.println(
                "3. When after one player's move occures entanglement then second player \nchoose tile and mark to be collapsed here.");
        System.out.println("4. Collapsed tiles with chosen mark evolve to big marks.");
        System.out.println(
                "5. To win a game you have to place three big marks in horizontal/vertical/diagonal line \n(same as classical tic-tac-toe).");
        System.out.println("Good luck!");
    }

}