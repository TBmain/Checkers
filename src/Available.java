import java.awt.*;
import java.util.ArrayList;

public class Available extends ArrayList<Coordinates> {

    private boolean take;

    public Available(BoardState boardState) {
        addAvailable(boardState);
    }

    public boolean addAvailable(BoardState boardState) {
        PieceType[][] board = boardState.getBoard();
        take = false;
        clear();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y] != null && board[x][y].getPlayer() == boardState.getTurn())
                    checkAvailable(board, new Coordinates(x, y));
            }
        }
        if (take)
            removeNoTake(board);
        if (isEmpty())
            return false;
        return true;
    }

    private void checkAvailable(PieceType[][] board, Coordinates c) { // TODO use Game.java : use some version of addPossibleMoves and then check its size to know if the piece is available
        // check if piece is available
        // if it's able to take set take to true
        if (take == false)
            take = checkTake(board, c);
    }

    private boolean checkTake(PieceType[][] board, Coordinates c) {
        // TODO check if piece is in take position
        return false;
    }

    private void removeNoTake(PieceType[][] board) {
        for (Coordinates c : this)
            if (checkTake(board, c))
                remove(c);
    }
}
