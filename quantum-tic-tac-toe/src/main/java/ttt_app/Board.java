package ttt_app;

import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    public ArrayList<Tile> tileList;
    public ArrayList<Tile> collapsedTileList;
    private Scanner scanner;
    private boolean player;
    private boolean win;
    private int moveCounter = 1;

    Board() {
        setBoard();
        collapsedTileList = new ArrayList<Tile>();
        player = false;
        win = false;
        scanner = new Scanner(System.in);
    }

    public boolean checkIfWinner() {
        if (win) {
            return true;
        }

        return false;
    }

    public void displayBoard() {
        System.out.println("-------------------------------");// górna krawedz

        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {

                System.out.print("|");
                (tileList.get(0 + j * 3)).printMarkLine(i);
                System.out.print("|");
                (tileList.get(1 + j * 3)).printMarkLine(i);
                System.out.print("|");
                (tileList.get(2 + j * 3)).printMarkLine(i);
                System.out.print("|");
                System.out.println();
            }
            System.out.println("-------------------------------");
        }

    }

    public void makeMove() {
        Character character = ((player == false) ? 'x' : 'o');
        System.out.println("It's " + moveCounter + " move.");
        System.out.println("Now it's player " + ((player == false) ? "x" : "o") + " move");
        System.out.println("Please choose the tile (0-8):");
        int chosenFirstTile = scanner.nextInt();
        int chosenSecondTile = scanner.nextInt();
        if ((chosenFirstTile > 8) || (chosenSecondTile > 8) || (chosenFirstTile < 0) || chosenSecondTile < 0) {
            System.out.println("You've chosen wrong tile number! Number has to be between 0 and 8");
            return;
        }

        if (!checkIfDifferentTiles(chosenFirstTile, chosenSecondTile)) {
            System.out.println("You've chosen same tile twice in one move! Please choose two different tiles");
            return;
        }

        if (!checkIfTileIsFull(chosenFirstTile) && !checkIfTileIsFull(chosenSecondTile)) {
            (tileList.get(chosenFirstTile)).makeMove(new Mark(character, moveCounter));
            (tileList.get(chosenSecondTile)).makeMove(new Mark(character, moveCounter));
            player = !player;
            moveCounter++;
        }
    }

    private boolean checkIfTileIsFull(int tileNumber) {
        if ((tileList.get(tileNumber)).marklist.size() == 9) {
            System.out.println("Tile " + tileNumber + " is full. Please choose different tile!");
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIfDifferentTiles(int firstTile, int secondTile) {
        if (firstTile != secondTile) {
            return true;
        } else {
            return false;
        }
    }

    private void setBoard() {
        tileList = new ArrayList<Tile>(9);
        for (int i = 0; i < 9; i++) {
            tileList.add(i, new Tile());
        }
    }

}