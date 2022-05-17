package ttt_app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Board {

    public ArrayList<Tile> tileList;
    private Scanner scanner;
    public boolean player;
    public int roundNumber = 1;
    private int startingTileNumber;
    public ArrayList<Tile> entangledTilesList = new ArrayList<Tile>(0);
    public ArrayList<Mark> marksInEntanglementList = new ArrayList<Mark>(0);
    private ArrayList<Tile> availableTiles = new ArrayList<Tile>(0);
    public boolean isEntanglement = false;
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

    private int minMarkX;
    private int minMarkO;
    private int smallest;

    public boolean checkIfWinner() {
        minMarkX = 99;
        minMarkO = 99;
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
                        if (processedPlayer == 'x') {
                            smallest = Math.min(tileList.get(0 + j * 3).getBigMark().getMoveNumber(),
                                    Math.min(tileList.get(1 + j * 3).getBigMark().getMoveNumber(),
                                            tileList.get(2 + j * 3).getBigMark().getMoveNumber()));
                            if (minMarkX > smallest) {
                                minMarkX = smallest;
                            }
                        } else if (processedPlayer == 'o') {
                            smallest = Math.min(tileList.get(0 + j * 3).getBigMark().getMoveNumber(),
                                    Math.min(tileList.get(1 + j * 3).getBigMark().getMoveNumber(),
                                            tileList.get(2 + j * 3).getBigMark().getMoveNumber()));
                            if (minMarkO > smallest) {
                                minMarkO = smallest;
                            }
                        }
                    }
                }
                if (tileList.get(0 + j).getBigMark() != null && tileList.get(3 + j).getBigMark() != null
                        && tileList.get(6 + j).getBigMark() != null) {
                    if (tileList.get(0 + j).getBigMark().whichPlayer() == processedPlayer
                            && tileList.get(3 + j).getBigMark().whichPlayer() == processedPlayer
                            && tileList.get(6 + j).getBigMark().whichPlayer() == processedPlayer) {
                        if (processedPlayer == 'x') {
                            smallest = Math.min(tileList.get(0 + j).getBigMark().getMoveNumber(),
                                    Math.min(tileList.get(3 + j).getBigMark().getMoveNumber(),
                                            tileList.get(6 + j).getBigMark().getMoveNumber()));
                            if (minMarkX > smallest) {
                                minMarkX = smallest;
                            }
                        } else if (processedPlayer == 'o') {
                            smallest = Math.min(tileList.get(0 + j).getBigMark().getMoveNumber(),
                                    Math.min(tileList.get(3 + j).getBigMark().getMoveNumber(),
                                            tileList.get(6 + j).getBigMark().getMoveNumber()));
                            if (minMarkO > smallest) {
                                minMarkO = smallest;
                            }
                        }

                    }
                }
            }

            if (tileList.get(0).getBigMark() != null && tileList.get(4).getBigMark() != null
                    && tileList.get(8).getBigMark() != null) {
                if (tileList.get(0).getBigMark().whichPlayer() == processedPlayer
                        && tileList.get(4).getBigMark().whichPlayer() == processedPlayer
                        && tileList.get(8).getBigMark().whichPlayer() == processedPlayer) {
                    if (processedPlayer == 'x') {
                        smallest = Math.min(tileList.get(0).getBigMark().getMoveNumber(),
                                Math.min(tileList.get(4).getBigMark().getMoveNumber(),
                                        tileList.get(8).getBigMark().getMoveNumber()));
                        if (minMarkX > smallest) {
                            minMarkX = smallest;
                        }
                    } else if (processedPlayer == 'o') {
                        smallest = Math.min(tileList.get(0).getBigMark().getMoveNumber(),
                                Math.min(tileList.get(4).getBigMark().getMoveNumber(),
                                        tileList.get(8).getBigMark().getMoveNumber()));
                        if (minMarkO > smallest) {
                            minMarkO = smallest;
                        }
                    }
                }
            }

            if (tileList.get(2).getBigMark() != null && tileList.get(4).getBigMark() != null
                    && tileList.get(6).getBigMark() != null) {
                if (tileList.get(2).getBigMark().whichPlayer() == processedPlayer
                        && tileList.get(4).getBigMark().whichPlayer() == processedPlayer
                        && tileList.get(6).getBigMark().whichPlayer() == processedPlayer) {
                    if (processedPlayer == 'x') {
                        smallest = Math.min(tileList.get(2).getBigMark().getMoveNumber(),
                                Math.min(tileList.get(4).getBigMark().getMoveNumber(),
                                        tileList.get(6).getBigMark().getMoveNumber()));
                        if (minMarkX > smallest) {
                            minMarkX = smallest;
                        }
                    } else if (processedPlayer == 'o') {
                        smallest = Math.min(tileList.get(2).getBigMark().getMoveNumber(),
                                Math.min(tileList.get(4).getBigMark().getMoveNumber(),
                                        tileList.get(6).getBigMark().getMoveNumber()));
                        if (minMarkO > smallest) {
                            minMarkO = smallest;
                        }
                    }
                }
            }
        }
        if (minMarkO < 99 || minMarkX < 99) {
            return true;
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
                    System.out.print("     " + tileList.get(0 + j * 3).getBigMark().markSyntax() + "     ");
                }
                System.out.print("|");

                if (!tileList.get(1 + j * 3).checkIfTileColapsed()) {
                    (tileList.get(1 + j * 3)).printMarkLine(i);
                } else {
                    System.out.print("     " + tileList.get(1 + j * 3).getBigMark().markSyntax() + "     ");
                }
                System.out.print("|");

                if (!tileList.get(2 + j * 3).checkIfTileColapsed()) {
                    (tileList.get(2 + j * 3)).printMarkLine(i);
                } else {
                    System.out.print("     " + tileList.get(2 + j * 3).getBigMark().markSyntax() + "     ");
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

    Bot bot = new Bot();

    public void makeMove(String mode) {
        isMistake = false;
        Character character = ((player == false) ? 'x' : 'o');
        if ((mode.equals("single") && (roundNumber % 2 == 0))) {
            System.out.println("--------BOT MOVE--------");
        }
        System.out.println("It's " + roundNumber + " move.");
        System.out.println("It's player " + ((player == false) ? 'X' : 'O') + " turn");
        System.out.println("Please choose the tile (0-8):");

        if (mode.equals("multi") || (mode.equals("single") && (roundNumber % 2 == 1))) {
            String firstScanned = scanner.nextLine();
            String secondScanned = scanner.nextLine();
            validateMove(firstScanned, secondScanned, mode, character);
        } else if (mode.equals("single")) {
            bot.botMove(this);
        }
    }

    private boolean validateMove(String firstScanned, String secondScanned, String mode, Character character) {
        if (firstScanned.length() != 1 && firstScanned.length() != 1) {
            System.out.println("Please give a number between 0 and 8!");
            isMistake = true;
            return false;
        }

        boolean isDigitFlag = Character.isDigit(firstScanned.charAt(0)) && Character.isDigit(secondScanned.charAt(0));
        if (!isDigitFlag) {
            System.out.println("Please give a digit between 0 and 8!");
            isMistake = true;
            return false;
        }

        int chosenFirstTileIndex = Integer.parseInt(firstScanned);
        int chosenSecondTileIndex = Integer.parseInt(secondScanned);
        if (!validateGivenTiles(chosenFirstTileIndex, chosenSecondTileIndex, mode)) {
            isMistake = true;
            return false;
        }

        if (!checkIfChosenTileIsFull(chosenFirstTileIndex, mode)
                && !checkIfChosenTileIsFull(chosenSecondTileIndex, mode)) {
            (tileList.get(chosenFirstTileIndex)).putMark(new Mark(character, roundNumber));
            (tileList.get(chosenSecondTileIndex)).putMark(new Mark(character, roundNumber));
            roundNumber++;
            return true;
        }

        return false;
    }

    public boolean validateGivenTiles(int chosenFirstTile, int chosenSecondTile, String mode) {
        if ((chosenFirstTile > 8) || (chosenSecondTile > 8) || (chosenFirstTile < 0) || chosenSecondTile < 0) {
            if (mode.equals("multi")) {
                System.out.println("You've chosen wrong tile number! Number has to be between 0 and 8");
            }
            return false;
        }

        if (!checkIfDifferentTiles(chosenFirstTile, chosenSecondTile)) {
            if (mode.equals("multi")) {
                System.out.println("You've chosen same tile twice in one move! Please choose two different tiles");
            }
            return false;
        }

        if (checkIfChosenTileIsCollapsed(chosenFirstTile, chosenSecondTile)) {
            if (mode.equals("multi")) {
                System.out.println("You've chosen collapsed tile! Please choose different tile");
            }
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

    public boolean checkIfChosenTileIsFull(int tileNumber, String mode) {
        if ((tileList.get(tileNumber)).marklist.size() == 9) {
            if (mode.equals("multi")) {
                System.out.println("Tile " + tileNumber + " is full. Please choose different tile!");
            }
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

    int additionalFlag = 0;

    public boolean checkIfIsEntanglement() {
        additionalFlag = 0;
        availableTiles.clear();
        createListOfAvailableTiles(tileList);
        for (Tile komorka : availableTiles) {
            if (additionalFlag == 0) {
                marksInEntanglementList.clear();
                entangledTilesList.clear();
                startingTileNumber = komorka.getNumberOfTile();
                entangledTilesList.add(komorka);

                searchForEntanglement(komorka);
                if (isEntanglement) {
                    for (int i = 0; i < entangledTilesList.size(); i++) {
                        tileList.get(entangledTilesList.get(i).getNumberOfTile()).setEntanglement();
                    }
                    additionalFlag = 0;
                    return true;
                } else {
                    continue;
                }
            }
        }
        return isEntanglement;
    }

    private void searchForEntanglement(Tile tile) {
        for (Mark mark : tile.marklist) {
            if (additionalFlag == 0) {

                if (checkIfMarkIsOnPossibleEntanglementList(mark)) {
                    continue;
                }

                if (findSameMarkInDifferentTile(mark, tile)) {
                    marksInEntanglementList.add(mark);
                    if (startingTileNumber == nextTile.getNumberOfTile()) {
                        isEntanglement = true;
                        additionalFlag = 1;
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
                        marksInEntanglementList.remove(mark);
                    }
                } else {
                    marksInEntanglementList.clear();
                    entangledTilesList.clear();
                }
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

    public int isBot() {
        return ((player == false) ? 0 : 1);
    }

    public Character whoIsWinner() {
        if (minMarkO < minMarkX) {
            return 'o';
        } else {
            return 'x';
        }
    }

    public void delay() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void makeMove(ArrayList<Integer> listFromGUI) {
        isMistake = false;
        Character character = ((player == false) ? 'x' : 'o');

        (tileList.get(listFromGUI.get(0))).putMark(new Mark(character, roundNumber));
        (tileList.get(listFromGUI.get(1))).putMark(new Mark(character, roundNumber));
        roundNumber++;
    }

}