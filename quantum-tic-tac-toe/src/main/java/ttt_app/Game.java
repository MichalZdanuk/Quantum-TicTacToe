package ttt_app;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private static Scanner scanner = new Scanner(System.in);
    private static String chosenTile = "";
    private static String chosenMark = "";
    private static String mode = "";
    private static Bot bot = new Bot();
    static Random rnd = new Random();

    public static void main(String[] args) {
        Board gameBoard = new Board();
        printGameRules();
        chooseGameMode();

        gameBoard.displayBoard();

        while ((!gameBoard.checkIfWinner()) && (!gameBoard.checkIfDraw())) {
            gameBoard.makeMove(mode);
            gameBoard.displayBoard();
            if (gameBoard.checkIfIsEntanglement()) {
                gameBoard.changePlayer();
                if ((mode.equals("single") && (gameBoard.player == true))) {
                    System.out.println("--------BOT MOVE--------");
                }
                System.out.println("Player " + ((gameBoard.player == false) ? 'x' : 'o') + " turn");
                System.out.print("Entanglement occured! ");
                gameBoard.printChainOfTiles();
                System.out.println("Please choose one from entangled tiles: ");
                gameBoard.printListOfEntangledTiles();
                System.out.print("\n");
                if (mode.equals("multi") || (mode.equals("single") && (gameBoard.player == false))) {
                    chosenTile = scanner.nextLine();
                    while (!validateChosenTile(chosenTile, gameBoard)) {
                        System.out.print("You' ve chosen wrong tile! Chose one from: ");
                        gameBoard.printListOfEntangledTiles();
                        System.out.println();
                        chosenTile = scanner.nextLine();
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
                        System.out.println();
                        chosenMark = scanner.nextLine();
                    }
                    gameBoard.resolveEntanglement(chosenMark, gameBoard.tileList.get(Integer.parseInt(chosenTile)));
                    gameBoard.changePlayer();
                } else if (mode.equals("single") && (gameBoard.player = true)) {
                    bot.botEntangleMove(gameBoard);
                    bot.delay(1500);
                    gameBoard.displayBoard();
                    if (gameBoard.checkIfWinner() || gameBoard.checkIfDraw()) {
                        break;
                    }
                    gameBoard.makeMove("single");
                }

                gameBoard.displayBoard();
            }
            if (!gameBoard.isMistake) {
                gameBoard.changePlayer();
            }
        }
        if (gameBoard.draw) {
            System.out.println("Draw!!!Nobody wins");
            return;
        }
        System.out.println("Congratulations to player " + gameBoard.whoIsWinner() + " who has won!!!");
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