package ttt_app;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Bot {
    Random rnd;
    private int botFirstTile;
    private int botSecondTile;
    String chosenMark = "";

    Bot() {
        rnd = new Random();
    }

    private ArrayList<Integer> botTiles = new ArrayList<>();
    private ArrayList<Integer> botEntangledTiles = new ArrayList<>();

    public ArrayList<Integer> botMove(Board board) {
        delay();
        botTiles.clear();
        botFirstTile = rnd.nextInt(9);
        botSecondTile = rnd.nextInt(9);
        while (!board.validateGivenTiles(botFirstTile, botSecondTile, "single")
                && !board.checkIfChosenTileIsFull(botFirstTile, "single")
                && !board.checkIfChosenTileIsFull(botSecondTile, "single")) {
            botFirstTile = rnd.nextInt(9);
            botSecondTile = rnd.nextInt(9);
        }
        botTiles.add(botFirstTile);
        botTiles.add(botSecondTile);
        (board.tileList.get(botFirstTile)).putMark(new Mark('o', board.getRoundNumber()));
        (board.tileList.get(botSecondTile)).putMark(new Mark('o', board.getRoundNumber()));
        board.roundNumber++;

        System.out.println("Bot First tile: " + botFirstTile);
        System.out.println("Bot Second tile: " + botSecondTile);
        return botTiles;
    }

    private static int index;

    public int botEntangleMove(Board board) {
        delay();
        botEntangledTiles.clear();
        index = rnd.nextInt(board.entangledTilesList.size());
        while (!validateChosenTile(board.entangledTilesList.get(index).getNumberOfTile(), board)) {
            index = rnd.nextInt(board.entangledTilesList.size());
        }

        chooseEntangledMark(board.entangledTilesList.get(index).getNumberOfTile(), board);
        board.resolveEntanglement(chosenMark,
                board.tileList.get(board.entangledTilesList.get(index).getNumberOfTile()));
        return board.entangledTilesList.get(index).getNumberOfTile();
    }

    private static boolean validateChosenTile(int givenIndex, Board board) {

        ArrayList<Integer> listOfNumbersToCheck = board.entangledTilesNumbers();
        for (int i = 0; i < board.getSize(); i++) {
            if (listOfNumbersToCheck.get(i) == givenIndex) {
                return true;
            }
        }
        return false;
    }

    public static int chosenBotMark;

    private void chooseEntangledMark(int tile, Board board) {
        System.out.println("bot choosing mark");
        chosenBotMark = rnd.nextInt(board.tileList.get(tile).marklist.size());
        while (!isMarkEntangled(chosenBotMark, board)) {
            chosenBotMark = rnd.nextInt(board.tileList.get(tile).marklist.size());
        }
        System.out.println(
                "Bot has chosen: " + board.tileList.get(board.entangledTilesList.get(index).getNumberOfTile()).marklist
                        .get(chosenBotMark).markSyntax());
        chosenMark = board.tileList.get(board.entangledTilesList.get(index).getNumberOfTile()).marklist
                .get(chosenBotMark).markSyntax();
    }

    private static boolean isMarkEntangled(int markToBeChecked, Board board) {
        System.out.println("checking chosen mark");
        System.out
                .println("chosen mark to be checked: "
                        + board.tileList.get(board.entangledTilesList.get(index).getNumberOfTile()).marklist
                                .get(markToBeChecked).markSyntax());
        for (Mark mark : board.marksInEntanglementList) {
            if (mark.markSyntax()
                    .equals(board.tileList.get(board.entangledTilesList.get(index).getNumberOfTile()).marklist
                            .get(markToBeChecked).markSyntax())) {
                return true;
            }
        }
        return false;
    }

    public void delay() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
