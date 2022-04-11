import java.awt.*;
import java.util.ArrayList;

public class Available extends ArrayList<Coordinates> {

    private boolean take;

    public Available(Game game) {
        setAvailable(game);
    }

    public boolean setAvailable(Game game) {
        BoardState boardState = game.getBoardState();
        PieceType[][] board = boardState.getBoard();
        take = false;
        clear();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y] != null && board[x][y].getPlayer() == boardState.getTurn())
                    checkAvailable(game, game.getCoordinates()[x][y]);
            }
        }
        if (take)
            removeNoTake(board);
        if (isEmpty())
            return false;
        return true;
    }

    private void checkAvailable(Game game, Coordinates c) { // TODO use Game.java : use some version of addPossibleMoves and then check its size to know if the piece is available
        // check if piece is available
        // if it's able to take set take to true
        if (addPossibleMoves(game, c).size() > 0)
            add(c);
    }

    private boolean checkTake(PieceType[][] board, Coordinates c) {
        return false;
    }

    private void removeNoTake(PieceType[][] board) {
        for (Coordinates c : this)
            if (checkTake(board, c))
                remove(c);
    }


    // TODO somehow get this to be in Game and in Available with writing it twice
    private ArrayList<Coordinates> addPossibleMoves(Game game, Coordinates c) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();
        BoardState boardState = game.getBoardState();
        int x = c.getX();
        int y = c.getY();

        if (boardState.getBoard()[x][y].isKing()) {
            // king case - 4 directions to check
        }
        else {
            int dy = (boardState.getTurn() == Player.WHITE) ? -1 : +1;

            if (checkIndex(x + 1, y + dy) && boardState.getBoard()[x + 1][y + dy] == null)
                possibleMoves.add(game.getCoordinates()[x + 1][y + dy]);
            if (checkIndex(x - 1, y + dy) && boardState.getBoard()[x - 1][y + dy] == null)
                possibleMoves.add(game.getCoordinates()[x - 1][y + dy]);
        }
        // if take (jump/eat) set true
        return possibleMoves;
    }

    private boolean checkIndex(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
}
