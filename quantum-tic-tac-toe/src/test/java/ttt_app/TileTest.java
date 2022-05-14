package ttt_app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TileTest {

    @Test
    public void createEmptyTile() {
        Tile emptyTile = new Tile(0);
        int expectedMarkListSize = 0;
        assertEquals(expectedMarkListSize, emptyTile.marklist.size());
        assertEquals(false, emptyTile.checkIfTileColapsed());
        assertEquals(false, emptyTile.checkIfTileEntangled());
        assertEquals(true, emptyTile.checkIfIsEmpty());
    }

    @Test
    public void shouldCorrectlyChangeTileStateAfterPuttingMark() {
        Tile tile = new Tile(0);
        int expectedMarkListSize = 2;
        tile.putMark(new Mark('x', 1));
        tile.putMark(new Mark('o', 2));
        assertEquals(expectedMarkListSize, tile.marklist.size());
        assertEquals(false, tile.checkIfTileColapsed());
        assertEquals(false, tile.checkIfTileEntangled());
        assertEquals(false, tile.checkIfIsEmpty());
    }

    @Test
    public void shouldCorrectlyChangeTileStateAfterPuttingBigMark() {
        Tile tileWithBigMark = new Tile(0);
        tileWithBigMark.setBigMark('x', 0);
        Mark expectedBigMark = new Mark('x', 0);
        assertEquals(expectedBigMark.markSyntax(), tileWithBigMark.getBigMark().markSyntax());
        assertEquals(expectedBigMark.getMark(), tileWithBigMark.getBigMark().getMark());
        assertEquals(expectedBigMark.getMoveNumber(), tileWithBigMark.getBigMark().getMoveNumber());
    }

    @Test
    public void shouldCorrectlySetEntanglement() {
        Tile entangledTile = new Tile(0);
        entangledTile.setEntanglement();
        assertEquals(true, entangledTile.checkIfTileEntangled());
    }

    @Test
    public void shouldCorrectlySetCollapse() {
        Tile collapsedTile = new Tile(0);
        collapsedTile.setCollapse();
        assertEquals(true, collapsedTile.checkIfTileColapsed());
    }
}