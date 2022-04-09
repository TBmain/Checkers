// check if I really need an object with a private stack or I can just go for "extends Stack<>"

import java.util.Stack;

public class Undo {
    private Stack<BoardState> moves;

    public Undo() {
        moves = new Stack<>();
    }

    public BoardState abortLastMove() { // find a better name
        return moves.pop();
    }

    public void push(BoardState board) {
        moves.push(board);
    }

    // will probably need to add setBoardState function in Game.java
}