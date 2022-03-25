package ttt_app;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static Scanner scanner = new Scanner(System.in);
    private static String chosenTile = "";
    private static String chosenMark = "";
    private static String mode = "";
    private static int index;
    static Random rnd = new Random();

    public static void main(String[] args) {
        Board gameBoard = new Board();
        try {
            URL myUrl = new URL("https://github.com/MichalZdanuk/Quantum-TicTacToe");
            System.out.println(myUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        printGameRules();
        chooseGameMode();

        gameBoard.displayBoard();

        while ((!gameBoard.checkIfWinner()) && (!gameBoard.checkIfDraw())) {
            gameBoard.makeMove(mode);
            gameBoard.displayBoard();
            if (gameBoard.checkIfIsEntanglement()) {
                gameBoard.changePlayer();
                if ((mode.equals("single") && (gameBoard.isBot() == 1))) {
                    System.out.println("--------BOT MOVE--------");
                }
                System.out.println("Player " + gameBoard.whichPlayerTurn() + " turn");
                System.out.print("Entanglement occured! ");
                gameBoard.printChainOfTiles();
                System.out.println("Please choose one from entangled tiles: ");
                gameBoard.printListOfEntangledTiles();
                System.out.print("\n");
                if (mode.equals("multi") || (mode.equals("single") && (gameBoard.isBot() == 0))) {
                    chosenTile = scanner.nextLine();
                    while (!validateChosenTile(chosenTile, gameBoard)) {
                        System.out.print("You' ve chosen wrong tile! Chose one from: ");
                        gameBoard.printListOfEntangledTiles();
                        System.out.print("\n");
                        chosenTile = scanner.nextLine();
                    }
                } else if (mode.equals("single")) {
                    index = rnd.nextInt(gameBoard.entangledTilesList.size());
                    chosenTile = Integer.toString(gameBoard.entangledTilesList.get(index).getNumberOfTile());
                    while (!validateChosenTile(chosenTile, gameBoard)) {
                        index = rnd.nextInt(gameBoard.entangledTilesList.size());
                        chosenTile = Integer.toString(gameBoard.entangledTilesList.get(index).getNumberOfTile());
                    }
                    gameBoard.delay();
                    System.out.println("Bot has chosen: " + chosenTile);
                }

                System.out.println();
                System.out.println("Choose a mark to be collapsed on tile: " + chosenTile + " from");
                for (Mark mark : gameBoard.tileList.get(Integer.parseInt(chosenTile)).marklist) {
                    for (Mark markInEntanglement : gameBoard.marksInEntanglementList) {
                        if (mark.markSyntax().equals(markInEntanglement.markSyntax())) {
                            System.out.print(" {" + markInEntanglement.markSyntax() + "}");
                        }
                    }
                }
                System.out.println();
                if (mode.equals("multi") || (mode.equals("single") && ((gameBoard.isBot() == 0)))) {
                    chosenMark = scanner.nextLine();
                    while (!validateChosenMark(chosenMark, gameBoard.tileList.get(Integer.parseInt(chosenTile)),
                            gameBoard)) {
                        System.out.print("You' ve chosen wrong mark! Choose one from: ");
                        for (Mark mark : gameBoard.tileList.get(Integer.parseInt(chosenTile)).marklist) {
                            for (Mark markInEntanglement : gameBoard.marksInEntanglementList) {
                                if (mark.markSyntax().equals(markInEntanglement.markSyntax())) {
                                    System.out.print(" {" + markInEntanglement.markSyntax() + "}");
                                }
                            }
                        }
                        System.out.print("\n");
                        chosenMark = scanner.nextLine();
                    }
                } else if (mode.equals("single")) {
                    index = rnd.nextInt(gameBoard.tileList.get(Integer.parseInt(chosenTile)).marklist.size());
                    chosenMark = gameBoard.tileList.get(Integer.parseInt(chosenTile)).marklist.get(index)
                            .markSyntax();
                    while (!validateChosenMark(chosenMark, gameBoard.tileList.get(Integer.parseInt(chosenTile)),
                            gameBoard)) {
                        index = rnd
                                .nextInt(gameBoard.tileList.get(Integer.parseInt(chosenTile)).marklist.size());
                        chosenMark = gameBoard.tileList.get(Integer.parseInt(chosenTile)).marklist.get(index)
                                .markSyntax();

                    }

                    gameBoard.delay();
                    System.out.println("Bot has chosen: " + chosenMark);
                }

                gameBoard.resolveEntanglement(chosenMark, gameBoard.tileList.get(Integer.parseInt(chosenTile)));
                gameBoard.displayBoard();
                gameBoard.changePlayer();
            }
            if (!gameBoard.isMistake) {
                gameBoard.changePlayer();
            }
        }
        if (gameBoard.draw) {
            System.out.println("Draw!!!Nobody wins");
            return;
        }
        gameBoard.changePlayer();
        System.out.println("Congratulations to player " + gameBoard.whichPlayerTurn() + " who has won!!!");
    }

    private static boolean validateChosenMark(String chosenMark, Tile chosenTile, Board board) {
        for (int i = 0; i < chosenTile.marklist.size(); i++) {
            if (chosenTile.marklist.get(i).markSyntax().equals(chosenMark) && isMarkEntangled(chosenMark, board)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isMarkEntangled(String markToBeChecked, Board board) {
        for (Mark mark : board.marksInEntanglementList) {
            if (mark.markSyntax().equals(markToBeChecked)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validateChosenTile(String tile, Board board) {
        if (tile.length() != 1) {
            return false;
        }

        if (!Character.isDigit(tile.charAt(0))) {
            return false;
        }
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

    private static void chooseGameMode() {
        System.out.println("\nPlease choose game mode!");
        System.out.println("Type \"single\" to play against bot or \"multi\" to play against player");
        mode = scanner.nextLine();
        while (!validateMode(mode)) {
            System.out.println("Chosen wrong mode choose: single or multi");
            mode = scanner.nextLine();
        }

    }

    private static boolean validateMode(String mode) {
        if (mode.equals("single") || mode.equals("multi")) {
            return true;
        } else {
            return false;
        }
    }
}