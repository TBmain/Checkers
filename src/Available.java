import java.util.ArrayList;

public class Available extends ArrayList<Coordinates> {
    public Available(BoardState boardState) {
        setAvailable(boardState);
    }

    public boolean setAvailable(BoardState boardState) {
        if (boardState.tie())
            return false;
        PieceType[][] board = boardState.getBoard();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y] != null && board[x][y].getPlayer() == boardState.getTurn())
                    checkAvailable(boardState, boardState.getCoordinates()[x][y]);
            }
        }
        if (isEmpty()) {
            if (boardState.getJump()) {
                boardState.setJumpFalse();
                setAvailable(boardState);
            }
        }
        return isEmpty();
    }

    private void checkAvailable(BoardState boardState, Coordinates c) {
        if (boardState.getPossibleMoves(c, false).size() > 0)
            add(c);
    }
}