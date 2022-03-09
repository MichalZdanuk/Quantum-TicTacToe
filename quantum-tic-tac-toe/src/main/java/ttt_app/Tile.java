package ttt_app;

import java.util.ArrayList;

public class Tile {

    private boolean isColapsed;
    private boolean isEmpty;
    private boolean isEntangled;
    public ArrayList<Mark> marklist;
    private Mark bigValue;

    Tile() {
        isColapsed = false;
        isEmpty = false;
        isEntangled = false;
        marklist = new ArrayList<Mark>();

    }

    public void makeMove(Mark mark) {
        marklist.add(mark);
    }

    public void setCollapse() {
        isColapsed = true;
    }

    public void setEntanglement() {
        isEntangled = true;
    }

    private void printMark(int index) {
        if (index >= marklist.size()) {
            System.out.print("   ");
        } else {
            System.out.print((marklist.get(index)).getMark());
            System.out.print((marklist.get(index)).getMoveNumber() + " ");
        }
    }

    public void printMarkLine(int row) {
        printMark(0 + row * 3);
        printMark(1 + row * 3);
        printMark(2 + row * 3);
    }

}