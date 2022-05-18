package ttt_app;

import java.util.ArrayList;

public class Tile {

    private boolean isColapsed;
    private boolean isEntangled;
    private boolean isEmpty;
    public ArrayList<Mark> marklist;
    private Mark bigValue;
    private int numberOfTile;

    Tile(int numberOfTile) {
        isColapsed = false;
        isEmpty = true;
        isEntangled = false;
        this.numberOfTile = numberOfTile;
        marklist = new ArrayList<Mark>();

    }

    public int getNumberOfTile() {
        return numberOfTile;
    }

    public void setBigMark(Character mark, int moveNumber) {
        bigValue = new Mark(mark, moveNumber);
        isEmpty = false;
    }

    public Mark getBigMark() {
        return bigValue;
    }

    public void putMark(Mark mark) {
        marklist.add(mark);
        isEmpty = false;
    }

    public void setCollapse() {
        isColapsed = true;
    }

    public void setEntanglement() {
        isEntangled = true;
    }

    private void printMark(int index) {
        if (index >= marklist.size()) {
            System.out.print("    ");
        } else {
            System.out.print((marklist.get(index)).getMark());
            System.out.print((marklist.get(index)).getMoveNumber());
            boolean isTwoDigit = (marklist.get(index)).getMoveNumber() >= 10 ? true : false;
            if (isTwoDigit) {
                System.out.print(" ");
            } else {
                System.out.print("  ");
            }
        }
    }

    public void printMarkLine(int row) {
        printMark(0 + row * 3);
        printMark(1 + row * 3);
        printMark(2 + row * 3);
    }

    public boolean checkIfIsEmpty() {
        return isEmpty;
    }

    public boolean checkIfTileColapsed() {
        return isColapsed;
    }

    public boolean checkIfTileEntangled() {
        return isEntangled;
    }

}