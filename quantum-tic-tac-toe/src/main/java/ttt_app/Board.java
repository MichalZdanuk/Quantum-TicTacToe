package ttt_app;

import java.util.ArrayList;
import java.util.Scanner;

public class Board {

    public ArrayList<Tile> tileList;
    // public ArrayList<Tile> collapsedTileList; // probably useless
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
    private ArrayList<Integer> numbersList;

    Board() {
        setBoard();
        // collapsedTileList = new ArrayList<Tile>(); // probably useless
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
        System.out.println("\n\n");
    }

    public void newDisplayBoard() {

        System.out.println("----------------------------------------");
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {

                System.out.print("|");
                if (!tileList.get(0 + j * 3).checkIfTileColapsed()) {
                    (tileList.get(0 + j * 3)).printMarkLine(i);
                } else {
                    System.out.print("     " + tileList.get(0 + j * 3).getBigMark().printColapsed() + "     ");
                }
                System.out.print("|");

                if (!tileList.get(1 + j * 3).checkIfTileColapsed()) {
                    (tileList.get(1 + j * 3)).printMarkLine(i);
                } else {
                    System.out.print("     " + tileList.get(1 + j * 3).getBigMark().printColapsed() + "     ");
                }
                System.out.print("|");

                if (!tileList.get(2 + j * 3).checkIfTileColapsed()) {
                    (tileList.get(2 + j * 3)).printMarkLine(i);
                } else {
                    System.out.print("     " + tileList.get(2 + j * 3).getBigMark().printColapsed() + "     ");
                }
                System.out.print("|");
                System.out.println();
            }
            System.out.println("----------------------------------------");
        }
        System.out.println("\n\n");

    }

    public void makeMove() {
        Character character = ((player == false) ? 'x' : 'o');
        System.out.println("It's " + moveCounter + " move.");
        whichPlayer();
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

        if (checkIfChosenTileIsCollapsed(chosenFirstTile, chosenSecondTile)) {
            System.out.println("You've chosen collapsed tile! Please choose different tile");
            return;
        }

        if (!checkIfChosenTileIsFull(chosenFirstTile) && !checkIfChosenTileIsFull(chosenSecondTile)) {
            (tileList.get(chosenFirstTile)).makeMove(new Mark(character, moveCounter));
            (tileList.get(chosenSecondTile)).makeMove(new Mark(character, moveCounter));
            player = !player;
            moveCounter++;
        }

    }

    private boolean checkIfChosenTileIsCollapsed(int chosenFirstTile, int chosenSecondTile) {
        if ((tileList.get(chosenFirstTile).checkIfTileColapsed())
                || (tileList.get(chosenSecondTile).checkIfTileColapsed())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkIfChosenTileIsFull(int tileNumber) {
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

    public boolean checkIfIsEntanglement() {
        availableTiles.clear();
        createListOfAvailableTiles(tileList);
        for (Tile komorka : availableTiles) {
            marksInEntanglementList.clear();
            entangledTilesList.clear();
            startingTileNumber = komorka.getNumberOfTile();
            entangledTilesList.add(komorka);

            searchForEntanglement(komorka);
            if (isEntanglement) {
                for (int i = 0; i < entangledTilesList.size(); i++) {
                    tileList.get(entangledTilesList.get(i).getNumberOfTile()).setEntanglement();
                }
                return true;
            } else {
                continue;
            }
        }
        return isEntanglement;
    }

    private void searchForEntanglement(Tile komorka) {

        for (Mark znak : komorka.marklist) {
            if (checkIfMarkIsOnPossibleEntanglementList(znak)) {
                continue;
            }

            if (findSameMarkInDifferentTile(znak, komorka)) {
                marksInEntanglementList.add(znak);
                if (startingTileNumber == nextTile.getNumberOfTile()) {
                    isEntanglement = true;
                    return;
                } else {
                    entangledTilesList.add(nextTile);
                    searchForEntanglement(nextTile);

                }
            } else {
                marksInEntanglementList.clear();
                entangledTilesList.clear();
            }
        }
    }

    private boolean findSameMarkInDifferentTile(Mark znaczek, Tile komorka) {
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
            if ((!tileList2.get(i).checkIfIsEmpty()) && (!tileList2.get(i).checkIfTileColapsed())) {
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

    public void printListOfEntangledTiles() {
        for (int i = 0; i < entangledTilesList.size(); i++) {
            System.out.print("{" + entangledTilesList.get(i).getNumberOfTile() + "} ");
        }
    }

    public ArrayList<Integer> entangledTilesNumbers() {
        numbersList = new ArrayList<Integer>();
        for (int i = 0; i < entangledTilesList.size(); i++) {
            numbersList.add(entangledTilesList.get(i).getNumberOfTile());
        }
        return numbersList;
    }

    public int getSize() {
        return numbersList.size();
    }

    ArrayList<Mark> restMarks = new ArrayList<Mark>();
    Tile connectedTile;
    ArrayList<Mark> usedMarks = new ArrayList<Mark>();

    public void resolveEntanglement(String chosenMark, Tile tile) {
        usedMarks.add(new Mark(chosenMark.charAt(0), Integer.parseInt(chosenMark.replaceAll("[\\D]", ""))));
        tile.setBigMark(chosenMark.charAt(0), Integer.parseInt(chosenMark.replaceAll("[\\D]", "")));
        tile.setCollapse();
        createListOfRestMarks(chosenMark, tile);
        while (restMarks.size() != 0) {
            connectedTile = findTile(restMarks.get(0), tile);
            connectedTile.setBigMark(restMarks.get(0).markSyntax().charAt(0),
                    Integer.parseInt(restMarks.get(0).markSyntax().replaceAll("[\\D]", "")));
            connectedTile.setCollapse();
            usedMarks.add(new Mark(restMarks.get(0).markSyntax().charAt(0),
                    Integer.parseInt(restMarks.get(0).markSyntax().replaceAll("[\\D]", ""))));
            extendListOfRestMarks(restMarks.get(0), connectedTile);
            restMarks.remove(0);
        }
        player = !player;
        isEntanglement = false;

    }

    private Tile findTile(Mark mark, Tile discardedTile) {
        for (Tile tile : tileList) {
            if (discardedTile == tile) {
                continue;
            }
            if (tile.checkIfIsEmpty()) {
                continue;
            }
            if (tile.checkIfTileColapsed()) {
                continue;
            }
            for (Mark searchingMark : tile.marklist) {
                if (searchingMark.isEqual(mark)) {
                    return tile;
                }
            }
        }
        return null;
    }

    private void createListOfRestMarks(String chosenMark, Tile tile) {
        for (int i = 0; i < tile.marklist.size(); i++) {
            if (!tile.marklist.get(i).markSyntax().equals(chosenMark)) {
                restMarks.add(tile.marklist.get(i));
            }
        }
    }

    private void extendListOfRestMarks(Mark mark, Tile connectedTile) {
        boolean skip = false;
        for (int i = 0; i < connectedTile.marklist.size(); i++) {
            for (Mark usedMark : usedMarks) {
                if (usedMark.isEqual(connectedTile.marklist.get(i))) {
                    skip = true;
                    break;
                }
            }

            if ((!connectedTile.marklist.get(i).equals(mark)) && (!skip)) {
                restMarks.add(connectedTile.marklist.get(i));
            }
        }
    }

    public void whichPlayer() {
        System.out.println("Now it's player " + ((player == false) ? "x" : "o") + " move");
    }

}