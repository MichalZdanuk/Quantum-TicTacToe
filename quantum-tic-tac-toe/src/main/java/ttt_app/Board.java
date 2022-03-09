package ttt_app;

import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    public ArrayList<Tile> tileList;
    public ArrayList<Tile> collapsedTileList;
    private Scanner scanner;
    private boolean player;
    private boolean win;

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
        Character character = ((player == false) ? 'x' : 'y');
        System.out.println("Now it's player: " + ((player == false) ? "x" : "y") + " move");
        System.out.println("Please choose the tile (0-8):");
        int chosenTile = scanner.nextInt();
        if (!checkIfFullTile(chosenTile)) {
            (tileList.get(chosenTile)).makeMove(character);
            player = !player;
        }
    }

    private boolean checkIfFullTile(int tileNumber) {
        if ((tileList.get(tileNumber)).marklist.size() == 9) {
            System.out.println("Tile " + tileNumber + " is full. Please choose different tile!");
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