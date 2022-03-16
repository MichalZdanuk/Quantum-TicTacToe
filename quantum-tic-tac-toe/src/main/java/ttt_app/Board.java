package ttt_app;

import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    public ArrayList<Tile> tileList;
    //public ArrayList<Tile> collapsedTileList; // probable useless
    private Scanner scanner;
    private boolean player;
    private boolean win;
    private int moveCounter = 1;
    private int startingTileNumber;
    private ArrayList<Tile> entangledTilesList = new ArrayList<Tile>(0);
    private ArrayList<Mark> marksInEntanglementList = new ArrayList<Mark>(0);
    private ArrayList<Tile> availableTiles = new ArrayList<Tile>(0);
    private boolean isEntanglement = false;
    private Tile nextTile;

    Board() {
        setBoard();
        //collapsedTileList = new ArrayList<Tile>(); // probable useless
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
        System.out.println("----------------------------------------");

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
            System.out.println("----------------------------------------");
        }

    }

    public void makeMove() {
        Character character = ((player == false) ? 'x' : 'o');
        System.out.println("It's " + moveCounter + " move.");
        System.out.println("Now it's player " + ((player == false) ? "x" : "o") + " move");
        System.out.println("Please choose the tile (0-8):");

        String firstScanned = scanner.nextLine();
        String secondScanned = scanner.nextLine();
        if (firstScanned.length() != 1 && firstScanned.length() != 1) {
            System.out.println("Please give a number between 0 and 8!");
            return;
        }

        boolean flag = Character.isDigit(firstScanned.charAt(0)) && Character.isDigit(secondScanned.charAt(0));
        if (!flag) {
            System.out.println("Please give a digit between 0 and 8!");
            return;
        }

        int chosenFirstTile = Integer.parseInt(firstScanned);
        int chosenSecondTile = Integer.parseInt(secondScanned);
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

        if (checkIfIsEntanglement()) {
            System.out.println("Znaleziono splatanie.super!!!");
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
            tileList.add(i, new Tile(i));
        }
    }

    private boolean checkIfIsEntanglement() {
        availableTiles.clear();
        createListOfAvailableTiles(tileList);
        for (Tile komorka : availableTiles) {
            marksInEntanglementList.clear();
            entangledTilesList.clear();
            startingTileNumber = komorka.getNumberOfTile();
            entangledTilesList.add(komorka);

            wykonajPrzeszukanie(komorka);
            if (isEntanglement) {
                // funkcja obslugi duzego znaku
                return true;
            } else {
                continue;
            }
        }
        return isEntanglement;
    }

    private void wykonajPrzeszukanie(Tile komorka) {

        for (Mark znak : komorka.marklist) {
            if (checkIfMarkIsOnPossibleEntanglementList(znak)) {
                continue;
            }

            if (znajdzTenSamZnaczekWInnejKomorce(znak, komorka)) {
                marksInEntanglementList.add(znak);
                if (startingTileNumber == nextTile.getNumberOfTile()) {
                    entangledTilesList.add(nextTile);
                    isEntanglement = true;
                    return;
                } else {
                    entangledTilesList.add(nextTile);
                    wykonajPrzeszukanie(nextTile);

                }
            } else {
                marksInEntanglementList.clear();
                entangledTilesList.clear();
            }
        }
    }

    private boolean znajdzTenSamZnaczekWInnejKomorce(Mark znaczek, Tile komorka) {
        for (int i = 0; i < availableTiles.size(); i++) {
            if (availableTiles.get(i) != komorka) {
                for (int j = 0; j < availableTiles.get(i).marklist.size(); j++) {
                    if (availableTiles.get(i).marklist.get(j).isEqual(znaczek)) {
                        nextTile = availableTiles.get(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void createListOfAvailableTiles(ArrayList<Tile> tileList2) {
        for (int i = 0; i < tileList2.size(); i++) {
            if (!tileList2.get(i).checkIfIsEmpty()) {
                availableTiles.add(tileList2.get(i));
            }
        }
    }

    private boolean checkIfMarkIsOnPossibleEntanglementList(Mark markToCheck) {
        for (int i = 0; i < marksInEntanglementList.size(); i++) {
            if (marksInEntanglementList.get(i).isEqual(markToCheck)) {
                return true;
            }
        }
        return false;
    }

}