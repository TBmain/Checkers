import java.awt.*;
import java.util.ArrayList;

public class Available extends ArrayList<Tile> {

    private boolean take;

    public Available(GameBoard gameBoard, BoardState boardState) {
        pushAvailable(gameBoard, boardState);
    }

    public boolean pushAvailable(GameBoard gameBoard, BoardState boardState) {
        take = false;
        PieceType[][] board = boardState.getBoard();
        clear();
        for (Component comp : gameBoard.getComponents()) {
            Tile tile = (Tile) comp;
            if (tile.isVisible()) {
                PieceType piece = boardState.getBoard()[tile.xGrid()][tile.yGrid()];
                if (piece != null && piece.getPlayer() == boardState.getTurn())
                    checkAvailable(board, tile);
            }
        }
        if (take)
            removeNoTake(board);
        if (isEmpty()) return false;
        return true;
    }

    private void checkAvailable(PieceType[][] board, Tile tile) { // TODO use Game.java : use some version of addPossibleMoves and then check its size to know if the piece is available
        // check if piece is available
        // if it's able to take set take to true
        if (take == false)
            take = checkTake(board, tile.xGrid(), tile.yGrid());
    }

    private boolean checkTake(PieceType[][] board, int x, int y) {
        // TODO check if piece is in take position
        return false;
    }

    private void removeNoTake(PieceType[][] board) {
        for (Tile tile : this)
            if (checkTake(board, tile.xGrid(), tile.yGrid()))
                remove(tile);
    }
}
