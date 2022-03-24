package ttt_app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Board {

    public ArrayList<Tile> tileList;
    private Scanner scanner;
    private boolean player;
    private int moveCounter = 1;
    private int startingTileNumber;
    private ArrayList<Tile> entangledTilesList = new ArrayList<Tile>(0);
    public ArrayList<Mark> marksInEntanglementList = new ArrayList<Mark>(0);
    private ArrayList<Tile> availableTiles = new ArrayList<Tile>(0);
    private boolean isEntanglement = false;
    private Tile nextTile;
    private ArrayList<Integer> numbersList;
    private Character processedPlayer;
    private int counter = 0;
    public boolean draw = false;
    private ArrayList<Mark> restMarks = new ArrayList<Mark>();
    private Tile connectedTile;
    private ArrayList<Mark> usedMarks = new ArrayList<Mark>();
    public boolean isMistake = false;

    Board() {
        setBoard();
        player = false;
        scanner = new Scanner(System.in);
    }
    

    public boolean checkIfWinner() {
        for (int i = 0; i < 2; i++) {
            if (i % 2 == 0) {
                processedPlayer = 'x';
            } else {
                processedPlayer = 'o';
            }
            for (int j = 0; j < 3; j++) {
                if (tileList.get(0 + j * 3).getBigMark() != null && tileList.get(1 + j * 3).getBigMark() != null
                        && tileList.get(2 + j * 3).getBigMark() != null) {
                    if (tileList.get(0 + j * 3).getBigMark().whichPlayer() == processedPlayer
                            && tileList.get(1 + j * 3).getBigMark().whichPlayer() == processedPlayer
                            && tileList.get(2 + j * 3).getBigMark().whichPlayer() == processedPlayer) {
                        return true;
                    }
                }
                if (tileList.get(0 + j).getBigMark() != null && tileList.get(3 + j).getBigMark() != null
                        && tileList.get(6 + j).getBigMark() != null) {
                    if (tileList.get(0 + j).getBigMark().whichPlayer() == processedPlayer
                            && tileList.get(3 + j).getBigMark().whichPlayer() == processedPlayer
                            && tileList.get(6 + j).getBigMark().whichPlayer() == processedPlayer) {
                        return true;
                    }
                }
            }

            if (tileList.get(0).getBigMark() != null && tileList.get(4).getBigMark() != null
                    && tileList.get(8).getBigMark() != null) {
                if (tileList.get(0).getBigMark().whichPlayer() == processedPlayer
                        && tileList.get(4).getBigMark().whichPlayer() == processedPlayer
                        && tileList.get(8).getBigMark().whichPlayer() == processedPlayer) {
                    return true;
                }
            }

            if (tileList.get(2).getBigMark() != null && tileList.get(4).getBigMark() != null
                    && tileList.get(6).getBigMark() != null) {
                if (tileList.get(2).getBigMark().whichPlayer() == processedPlayer
                        && tileList.get(4).getBigMark().whichPlayer() == processedPlayer
                        && tileList.get(6).getBigMark().whichPlayer() == processedPlayer) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfDraw() {
        for (int i = 0; i < tileList.size(); i++) {
            if (tileList.get(i).checkIfTileColapsed()) {
                counter++;
            }
        }
        if (counter >= 8) {
            draw = true;
            return true;
        }
        counter = 0;
        return false;
    }

    public void displayBoard() {
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
        System.out.println();
    }

    public void changePlayer() {
        player = !player;
    }

    public void makeMove() {
        isMistake = false;
        Character character = ((player == false) ? 'x' : 'o');
        System.out.println("It's " + moveCounter + " move.");
        whichPlayer();
        System.out.println("Please choose the tile (0-8):");

        String firstScanned = scanner.nextLine();
        String secondScanned = scanner.nextLine();
        if (firstScanned.length() != 1 && firstScanned.length() != 1) {
            System.out.println("Please give a number between 0 and 8!");
            isMistake = true;
            return;
        }

        boolean flag = Character.isDigit(firstScanned.charAt(0)) && Character.isDigit(secondScanned.charAt(0));
        if (!flag) {
            System.out.println("Please give a digit between 0 and 8!");
            isMistake = true;
            return;
        }

        int chosenFirstTile = Integer.parseInt(firstScanned);
        int chosenSecondTile = Integer.parseInt(secondScanned);
        if (!validateGivenTiles(chosenFirstTile, chosenSecondTile)) {
            isMistake = true;
            return;
        }

        if (!checkIfChosenTileIsFull(chosenFirstTile) && !checkIfChosenTileIsFull(chosenSecondTile)) {
            (tileList.get(chosenFirstTile)).putMark(new Mark(character, moveCounter));
            (tileList.get(chosenSecondTile)).putMark(new Mark(character, moveCounter));
            moveCounter++;
        }
    }

    private boolean validateGivenTiles(int chosenFirstTile, int chosenSecondTile) {
        if ((chosenFirstTile > 8) || (chosenSecondTile > 8) || (chosenFirstTile < 0) || chosenSecondTile < 0) {
            System.out.println("You've chosen wrong tile number! Number has to be between 0 and 8");
            return false;
        }

        if (!checkIfDifferentTiles(chosenFirstTile, chosenSecondTile)) {
            System.out.println("You've chosen same tile twice in one move! Please choose two different tiles");
            return false;
        }

        if (checkIfChosenTileIsCollapsed(chosenFirstTile, chosenSecondTile)) {
            System.out.println("You've chosen collapsed tile! Please choose different tile");
            return false;
        }
        return true;
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
                correctMarksInEntanglementList();
                return true;
            } else {
                continue;
            }
        }
        return isEntanglement;
    }

    private void correctMarksInEntanglementList() {
        int counter = 0;
        for (Mark mark : marksInEntanglementList) {
            for (Tile tile : entangledTilesList) {
                for (Mark markInTile : tile.marklist) {
                    if (markInTile.isEqual(mark)) {
                        counter++;
                    }
                }
            }
            if (counter == 2) {
                counter = 0;
                continue;
            } else {
                marksInEntanglementList.remove(mark);
            }
            counter = 0;
        }
    }

    private void searchForEntanglement(Tile tile) {
        for (Mark mark : tile.marklist) {
            if (checkIfMarkIsOnPossibleEntanglementList(mark)) {
                continue;
            }

            if (findSameMarkInDifferentTile(mark, tile)) {
                marksInEntanglementList.add(mark);
                if (startingTileNumber == nextTile.getNumberOfTile()) {
                    isEntanglement = true;
                    return;
                } else {
                    entangledTilesList.add(nextTile);
                    if (!isDifferentMark(mark, nextTile)) {
                        marksInEntanglementList.remove(mark);
                        entangledTilesList.remove(nextTile);
                        continue;
                    }
                    searchForEntanglement(nextTile);

                }
                if (isEntanglement == false) {
                    entangledTilesList.remove(entangledTilesList.size() - 1);
                }
            } else {
                marksInEntanglementList.clear();
                entangledTilesList.clear();
            }
        }
    }

    private boolean isDifferentMark(Mark mark, Tile tile) {
        for (Mark comparedMark : tile.marklist) {
            if (!comparedMark.isEqual(mark)) {
                return true;
            }
        }
        return false;

    }

    private boolean findSameMarkInDifferentTile(Mark mark, Tile tile) {
        for (int i = 0; i < availableTiles.size(); i++) {
            if (availableTiles.get(i) != tile) {
                for (int j = 0; j < availableTiles.get(i).marklist.size(); j++) {
                    if (availableTiles.get(i).marklist.get(j).isEqual(mark)) {
                        nextTile = availableTiles.get(i);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void createListOfAvailableTiles(ArrayList<Tile> tileList) {
        for (int i = 0; i < tileList.size(); i++) {
            if ((!tileList.get(i).checkIfIsEmpty()) && (!tileList.get(i).checkIfTileColapsed())) {
                availableTiles.add(tileList.get(i));
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
        ArrayList<Integer> sortedListOfEntangledTiles = new ArrayList<Integer>();
        for (int i = 0; i < entangledTilesList.size(); i++) {
            sortedListOfEntangledTiles.add(entangledTilesList.get(i).getNumberOfTile());
        }
        Collections.sort(sortedListOfEntangledTiles);
        for (int i = 0; i < entangledTilesList.size(); i++) {
            System.out.print("{" + sortedListOfEntangledTiles.get(i) + "} ");
        }
    }

    public void printChainOfTiles() {
        String msg = "";
        for (int i = 0; i < entangledTilesList.size(); i++) {
            msg += "{" + entangledTilesList.get(i).getNumberOfTile() + "}-->";
        }
        msg = msg.substring(0, msg.length() - 3);
        System.out.println(msg);
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

    public void resolveEntanglement(String chosenMark, Tile tile) {
        Mark firstMark = new Mark(chosenMark.charAt(0), Integer.parseInt(chosenMark.replaceAll("[\\D]", "")));
        usedMarks.add(firstMark);
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

            if ((!skip)) {
                restMarks.add(connectedTile.marklist.get(i));
            }
            skip = false;
        }
    }

    public void whichPlayer() {
        System.out.println("Now it's player " + ((player == false) ? "x" : "o") + " move");
    }

    public void printWinner() {
        System.out.println("Congratulations to player: " + processedPlayer + " who has won game");
    }
}