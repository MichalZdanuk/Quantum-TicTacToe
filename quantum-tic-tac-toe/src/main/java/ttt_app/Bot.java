package ttt_app;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Bot {
    private Random rnd;
    private int botFirstTileNumber;
    private int botSecondTileNumber;
    private String chosenMark = "";
    private ArrayList<Integer> botTiles = new ArrayList<>();
    private ArrayList<Integer> botEntangledTiles = new ArrayList<>();
    private int chosenTileIndex;
    private int chosenMarkIndex;

    Bot() {
        rnd = new Random();
    }

    public ArrayList<Integer> botMove(Board board) {
        delay(2500);
        botTiles.clear();
        botFirstTileNumber = rnd.nextInt(9);
        botSecondTileNumber = rnd.nextInt(9);
        while (!board.validateGivenTiles(botFirstTileNumber, botSecondTileNumber, "single")
                && !board.checkIfChosenTileIsFull(botFirstTileNumber, "single")
                && !board.checkIfChosenTileIsFull(botSecondTileNumber, "single")) {
            botFirstTileNumber = rnd.nextInt(9);
            botSecondTileNumber = rnd.nextInt(9);
        }
        botTiles.add(botFirstTileNumber);
        botTiles.add(botSecondTileNumber);
        (board.tileList.get(botFirstTileNumber)).putMark(new Mark('o', board.getRoundNumber()));
        (board.tileList.get(botSecondTileNumber)).putMark(new Mark('o', board.getRoundNumber()));
        board.roundNumber++;
        return botTiles;
    }

    public int botEntangleMove(Board board) {
        delay(2500);
        botEntangledTiles.clear();
        chosenTileIndex = rnd.nextInt(board.entangledTilesList.size());
        while (!validateChosenTile(board.entangledTilesList.get(chosenTileIndex).getNumberOfTile(), board)) {
            chosenTileIndex = rnd.nextInt(board.entangledTilesList.size());
        }
        System.out.println(
                "Bot has chosen entangled tile: " + board.entangledTilesList.get(chosenTileIndex).getNumberOfTile());

        chooseEntangledMark(board.entangledTilesList.get(chosenTileIndex).getNumberOfTile(), board);
        board.resolveEntanglement(chosenMark,
                board.tileList.get(board.entangledTilesList.get(chosenTileIndex).getNumberOfTile()));
        return board.entangledTilesList.get(chosenTileIndex).getNumberOfTile();
    }

    private boolean validateChosenTile(int givenIndex, Board board) {
        ArrayList<Integer> listOfNumbersToCheck = board.entangledTilesNumbers();
        for (int i = 0; i < board.getSize(); i++) {
            if (listOfNumbersToCheck.get(i) == givenIndex) {
                return true;
            }
        }
        return false;
    }

    private void chooseEntangledMark(int tile, Board board) {
        chosenMarkIndex = rnd.nextInt(board.tileList.get(tile).marklist.size());
        while (!isMarkEntangled(chosenMarkIndex, board)) {
            chosenMarkIndex = rnd.nextInt(board.tileList.get(tile).marklist.size());
        }
        System.out.println(
                "Bot has chosen: "
                        + board.tileList.get(board.entangledTilesList.get(chosenTileIndex).getNumberOfTile()).marklist
                                .get(chosenMarkIndex).markSyntax());
        chosenMark = board.tileList.get(board.entangledTilesList.get(chosenTileIndex).getNumberOfTile()).marklist
                .get(chosenMarkIndex).markSyntax();
    }

    private boolean isMarkEntangled(int markToBeChecked, Board board) {
        for (Mark mark : board.marksInEntanglementList) {
            if (mark.markSyntax()
                    .equals(board.tileList.get(board.entangledTilesList.get(chosenTileIndex).getNumberOfTile()).marklist
                            .get(markToBeChecked).markSyntax())) {
                return true;
            }
        }
        return false;
    }

    public void delay(int delayTime) {
        try {
            TimeUnit.MILLISECONDS.sleep(delayTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
