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
}
