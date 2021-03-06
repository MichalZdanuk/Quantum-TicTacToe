package ttt_app;

public class Mark {
    private Character mark;
    private int moveNumber;
    private String markSyntax = "";

    Mark(Character mark, int moveNumber) {
        this.mark = mark;
        this.moveNumber = moveNumber;
        markSyntax = mark + "" + moveNumber;
    }

    public Character getMark() {
        return mark;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public boolean isEqual(Mark markToCompare) {
        if ((mark == markToCompare.getMark()) && (moveNumber == markToCompare.getMoveNumber())) {
            return true;
        } else {
            return false;
        }
    }

    public String markSyntax() {
        markSyntax = mark + "" + moveNumber;
        return markSyntax;
    }

    public Character whichPlayer() {
        if (mark == 'x') {
            return 'x';
        } else if (mark == 'o') {
            return 'o';
        }
        return null;
    }
}