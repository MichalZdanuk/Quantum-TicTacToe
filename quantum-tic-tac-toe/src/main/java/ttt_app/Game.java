package ttt_app;

public class Game {
    public static void main(String[] args) {
        Board gameBoard = new Board();
        System.out.println("Welcome to game Quantum Tic-Tac-Toe!");
        gameBoard.displayBoard();

        while (true) {
            gameBoard.makeMove();
            gameBoard.displayBoard();
        }
    }

}
