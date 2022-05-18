package ttt_app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BotTest {
    @Test
    public void shouldCorrectlyMakeMoveOnEmptyBoard() {
        Board board = new Board();
        Bot bot = new Bot();
        int expectedNumberOfEmptyTilesAfterMove = 7;
        bot.botMove(board);
        int actualNumberOfEmptyTilesAfterMove = 0;
        for (int i = 0; i < board.tileList.size(); i++) {
            if (board.tileList.get(i).checkIfIsEmpty()) {
                actualNumberOfEmptyTilesAfterMove++;
            }
        }
        assertEquals(expectedNumberOfEmptyTilesAfterMove, actualNumberOfEmptyTilesAfterMove);
    }

    @Test
    public void shouldCorrectlyMakeOnBoardWithAlreadySetBigMarks() {
        Board board = new Board();
        Bot bot = new Bot();
        board.tileList.get(0).setCollapse();
        board.tileList.get(3).setCollapse();
        board.tileList.get(7).setCollapse();
        board.tileList.get(0).setBigMark('x', 1);
        board.tileList.get(3).setBigMark('o', 2);
        board.tileList.get(7).setBigMark('x', 3);
        int expectedNumberOfEmptyTilesAfterMove = 4;
        bot.botMove(board);
        int actualNumberOfEmptyTilesAfterMove = 0;
        for (int i = 0; i < board.tileList.size(); i++) {
            if (board.tileList.get(i).checkIfIsEmpty()) {
                actualNumberOfEmptyTilesAfterMove++;
            }
        }
        assertEquals(expectedNumberOfEmptyTilesAfterMove, actualNumberOfEmptyTilesAfterMove);

    }

    @Test
    public void shouldCorrectlyMakeEntangledMoveCase1() {
        Board board = new Board();
        Bot bot = new Bot();
        board.tileList.get(0).putMark(new Mark('x', 1));
        board.tileList.get(0).putMark(new Mark('o', 2));
        board.tileList.get(1).putMark(new Mark('x', 1));
        board.tileList.get(1).putMark(new Mark('o', 2));
        int expectedNumberOfColapsedTiles = 2;
        int actualNumberOfColapsedTiles = 0;
        if (board.checkIfIsEntanglement()) {
            bot.botEntangleMove(board);
        }
        for (int i = 0; i < board.tileList.size(); i++) {
            if (board.tileList.get(i).checkIfTileColapsed()) {
                actualNumberOfColapsedTiles++;
            }
        }
        assertEquals(expectedNumberOfColapsedTiles, actualNumberOfColapsedTiles);
    }

    @Test
    public void shouldCorrectlyMakeEntangledMoveCase2() {
        Board board = new Board();
        Bot bot = new Bot();
        board.tileList.get(4).putMark(new Mark('x', 1));
        board.tileList.get(4).putMark(new Mark('x', 3));
        board.tileList.get(5).putMark(new Mark('x', 1));
        board.tileList.get(5).putMark(new Mark('o', 2));
        board.tileList.get(8).putMark(new Mark('o', 2));
        board.tileList.get(8).putMark(new Mark('x', 3));
        int expectedNumberOfColapsedTiles = 3;
        int actualNumberOfColapsedTiles = 0;
        if (board.checkIfIsEntanglement()) {
            bot.botEntangleMove(board);
        }
        for (int i = 0; i < board.tileList.size(); i++) {
            if (board.tileList.get(i).checkIfTileColapsed()) {
                actualNumberOfColapsedTiles++;
            }
        }
        assertEquals(expectedNumberOfColapsedTiles, actualNumberOfColapsedTiles);
    }
}
