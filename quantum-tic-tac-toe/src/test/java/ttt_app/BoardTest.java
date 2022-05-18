package ttt_app;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class BoardTest {

    @Test
    public void shouldCorrectlyCreateBoard() {
        Board board = new Board();
        ArrayList<Tile> expectedArrayList = new ArrayList<Tile>(9);
        for (int i = 0; i < 9; i++) {
            expectedArrayList.add(i, new Tile(i));
        }

        assertEquals(expectedArrayList.size(), board.tileList.size());
        for (int i = 0; i < expectedArrayList.size(); i++) {
            assertEquals(expectedArrayList.get(i).marklist, board.tileList.get(i).marklist);
            assertEquals(expectedArrayList.get(i).checkIfIsEmpty(), board.tileList.get(i).checkIfIsEmpty());
            assertEquals(expectedArrayList.get(i).checkIfTileColapsed(), board.tileList.get(i).checkIfTileColapsed());
            assertEquals(expectedArrayList.get(i).checkIfTileEntangled(), board.tileList.get(i).checkIfTileEntangled());
        }
    }

    @Test
    public void shouldCorrectlyDetectWinner() {
        Board board = new Board();
        board.tileList.get(0).setBigMark('x', '1');
        board.tileList.get(1).setBigMark('x', '2');
        board.tileList.get(2).setBigMark('x', '3');
        boolean isWinner = true;

        assertEquals(isWinner, board.checkIfWinner());
        boolean isCorrectMark = board.whoIsWinner() == 'x';
        assertEquals(true, isCorrectMark);
    }

    @Test
    public void shouldCorrectlyDetectWinnerWhenBothHasThreeMarksInLine() {
        Board board = new Board();
        board.tileList.get(0).setBigMark('o', '2');
        board.tileList.get(1).setBigMark('o', '4');
        board.tileList.get(2).setBigMark('o', '6');
        board.tileList.get(3).setBigMark('x', '3');
        board.tileList.get(4).setBigMark('x', '5');
        board.tileList.get(5).setBigMark('x', '7');
        boolean isWinner = true;
        System.out.println("d");
        assertEquals(isWinner, board.checkIfWinner());
        boolean isCorrectMark = board.whoIsWinner() == 'o';
        assertEquals(true, isCorrectMark);
    }

    @Test
    public void shouldCorrectlyDetectDrawCasualCase() {
        Board board = new Board();
        board.tileList.get(0).setBigMark('x', '1');
        board.tileList.get(1).setBigMark('o', '2');
        board.tileList.get(2).setBigMark('x', '7');
        board.tileList.get(3).setBigMark('o', '6');
        board.tileList.get(4).setBigMark('x', '3');
        board.tileList.get(5).setBigMark('x', '5');
        board.tileList.get(6).setBigMark('o', '8');
        board.tileList.get(7).setBigMark('x', '9');
        board.tileList.get(8).setBigMark('o', '4');
        for (int i = 0; i < board.tileList.size(); i++) {
            board.tileList.get(i).setCollapse();
        }
        boolean isDraw = true;

        assertEquals(isDraw, board.checkIfDraw());
    }

    @Test
    public void shouldCorrectlyDetectDrawOneEmptyTileCase() {
        Board board = new Board();
        board.tileList.get(0).setBigMark('x', '1');
        board.tileList.get(0).setCollapse();
        board.tileList.get(1).setBigMark('o', '2');
        board.tileList.get(1).setCollapse();
        board.tileList.get(2).setBigMark('o', '8');
        board.tileList.get(2).setCollapse();
        board.tileList.get(3).setBigMark('o', '4');
        board.tileList.get(3).setCollapse();
        board.tileList.get(4).setBigMark('x', '3');
        board.tileList.get(4).setCollapse();
        board.tileList.get(5).setBigMark('x', '5');
        board.tileList.get(5).setCollapse();
        board.tileList.get(7).setBigMark('x', '7');
        board.tileList.get(7).setCollapse();
        board.tileList.get(8).setBigMark('o', '6');
        board.tileList.get(8).setCollapse();
        boolean isDraw = true;

        assertEquals(isDraw, board.checkIfDraw());
    }

    @Test
    public void shouldReturnFalseWhenPointedTailIsNotNumber() {
        Board board = new Board();
        boolean expectedValue = false;
        boolean actualValue = board.validateMove("1", "NotNumber", "single", 'x');
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void shouldReturnFalseWhenPointedTailIsHigherThan8() {
        Board board = new Board();
        boolean expectedValue = false;
        boolean actualValue = board.validateMove("1", "9", "single", 'x');
        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void shouldReturnFalseWhenPointedSameTiles() {
        Board board = new Board();
        boolean expectedValue = false;
        boolean actualValue = board.validateMove("1", "1", "single", 'x');
        assertEquals(expectedValue, actualValue);

    }

    @Test
    public void shouldReturnFalseWhenPointedTileIsCollapsed() {
        Board board = new Board();
        board.tileList.get(4).setCollapse();
        boolean expectedValue = false;
        boolean actualValue = board.validateMove("1", "4", "single", 'x');
        assertEquals(expectedValue, actualValue);

    }

    @Test
    public void shouldDetectWhenEntanglementOccuredCase1() {
        Board board = new Board();
        board.tileList.get(0).putMark(new Mark('x', 1));
        board.tileList.get(0).putMark(new Mark('o', 2));
        board.tileList.get(1).putMark(new Mark('x', 1));
        board.tileList.get(1).putMark(new Mark('o', 2));

        boolean isEntanglement = board.checkIfIsEntanglement();
        assertEquals(true, isEntanglement);
    }

    @Test
    public void shouldDetectWhenEntanglementOccuredCase2() {
        Board board = new Board();
        board.tileList.get(0).putMark(new Mark('x', 1));
        board.tileList.get(0).putMark(new Mark('o', 4));
        board.tileList.get(1).putMark(new Mark('x', 1));
        board.tileList.get(1).putMark(new Mark('o', 2));
        board.tileList.get(5).putMark(new Mark('o', 2));
        board.tileList.get(5).putMark(new Mark('x', 3));
        board.tileList.get(8).putMark(new Mark('x', 3));
        board.tileList.get(8).putMark(new Mark('o', 4));

        boolean isEntanglement = board.checkIfIsEntanglement();
        assertEquals(true, isEntanglement);
    }

    @Test
    public void shouldReturnCorrectListOfEntangledTiles() {
        Board board = new Board();
        board.tileList.get(0).putMark(new Mark('x', 1));
        board.tileList.get(0).putMark(new Mark('o', 4));
        board.tileList.get(1).putMark(new Mark('x', 1));
        board.tileList.get(1).putMark(new Mark('o', 2));
        board.tileList.get(5).putMark(new Mark('o', 2));
        board.tileList.get(5).putMark(new Mark('x', 3));
        board.tileList.get(8).putMark(new Mark('x', 3));
        board.tileList.get(8).putMark(new Mark('o', 4));
        ArrayList<Integer> expectedEntangledTileNumbers = new ArrayList<Integer>();
        expectedEntangledTileNumbers.add(0);
        expectedEntangledTileNumbers.add(1);
        expectedEntangledTileNumbers.add(5);
        expectedEntangledTileNumbers.add(8);
        board.checkIfIsEntanglement();

        ArrayList<Integer> actualEntangledTileNumbers = board.entangledTilesNumbers();
        assertEquals(expectedEntangledTileNumbers, actualEntangledTileNumbers);
    }

    @Test
    public void shouldCorrectlyResolveEntanglementCase1() {
        Board board = new Board();
        board.tileList.get(0).putMark(new Mark('x', 1));
        board.tileList.get(0).putMark(new Mark('o', 4));
        board.tileList.get(1).putMark(new Mark('x', 1));
        board.tileList.get(1).putMark(new Mark('o', 2));
        board.tileList.get(7).putMark(new Mark('o', 2));
        board.tileList.get(7).putMark(new Mark('x', 3));
        board.tileList.get(6).putMark(new Mark('x', 3));
        board.tileList.get(6).putMark(new Mark('o', 4));

        if (board.checkIfIsEntanglement()) {
            board.resolveEntanglement("x1", board.tileList.get(0));
        }
        int[] indices = { 0, 1, 7, 6 };

        for (int i = 0; i < indices.length; i++) {
            assertEquals(true, board.tileList.get(indices[i]).checkIfTileColapsed());
        }

        assertEquals("x1", board.tileList.get(0).getBigMark().markSyntax());
        assertEquals("o2", board.tileList.get(1).getBigMark().markSyntax());
        assertEquals("o4", board.tileList.get(6).getBigMark().markSyntax());
        assertEquals("x3", board.tileList.get(7).getBigMark().markSyntax());

    }

    @Test
    public void shouldCorrectlyResolveEntanglementCase2() {
        Board board = new Board();
        board.tileList.get(0).putMark(new Mark('x', 1));
        board.tileList.get(0).putMark(new Mark('x', 3));
        board.tileList.get(1).putMark(new Mark('x', 1));
        board.tileList.get(1).putMark(new Mark('o', 2));
        board.tileList.get(4).putMark(new Mark('o', 2));
        board.tileList.get(4).putMark(new Mark('x', 3));

        if (board.checkIfIsEntanglement()) {
            board.resolveEntanglement("x3", board.tileList.get(0));
        }
        int[] indices = { 0, 1, 4 };

        for (int i = 0; i < indices.length; i++) {
            assertEquals(true, board.tileList.get(indices[i]).checkIfTileColapsed());
        }

        assertEquals("x3", board.tileList.get(0).getBigMark().markSyntax());
        assertEquals("x1", board.tileList.get(1).getBigMark().markSyntax());
        assertEquals("o2", board.tileList.get(4).getBigMark().markSyntax());

    }

}
