import java.awt.*;
import java.util.ArrayList;

public class Available extends ArrayList<Tile> {

    private boolean take;

    public Available(GameBoard gameBoard, BoardState boardState) {
        pushAvailable(gameBoard, boardState);
    }

    public boolean pushAvailable(GameBoard gameBoard, BoardState boardState) {
        take = false;
        boolean whiteTurn = boardState.isWhiteTurn();
        pieceType[][] board = boardState.getBoard();
        clear();
        for (Component comp : gameBoard.getComponents()) {
            Tile tile = (Tile) comp;
            if (tile.isVisible()) {
                pieceType piece = boardState.getBoard()[tile.xGrid()][tile.yGrid()];
                if ((piece == pieceType.white || piece == pieceType.whiteKing) && whiteTurn)
                    checkAvailable(board, tile);
                else if ((piece == pieceType.black || piece == pieceType.blackKing) && !whiteTurn)
                    checkAvailable(board, tile);
            }
        }
        if (take)
            removeNoTake(board);
        if (isEmpty()) return false;
        return true;
    }

    private void checkAvailable(pieceType[][] board, Tile tile) {
        // check if piece is available
        // if it's able to take set take to true
        if (take == false)
            take = checkTake(board, tile.xGrid(), tile.yGrid());
    }

    private boolean checkTake(pieceType[][] board, int x, int y) {
        // TODO check if piece is in take position
        return false;
    }

    private void removeNoTake(pieceType[][] board) {
        for (Tile tile : this)
            if (checkTake(board, tile.xGrid(), tile.yGrid()))
                remove(tile);
    }
}
