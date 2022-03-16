package ttt_app;

public class Mark {
    private Character mark;
    private int moveNumber;

    Mark(Character mark, int moveNumber) {
        this.mark = mark;
        this.moveNumber = moveNumber;
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

}
