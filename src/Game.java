import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Game implements ActionListener {
    private GameBoard gameBoard;
    private BoardState boardState;
    private Available available;
    private ArrayList<Tile> possibleMoves;
    private Tile selected;

    public Game() {
        gameBoard = new GameBoard(this);
        boardState = new BoardState();
        available = new Available();
        possibleMoves = new ArrayList<>();
        selected = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checkPiece((Tile) e.getSource());
    }

    private void checkPiece(Tile tile) {
        if (selected != null)
            finishMove(tile);
        else if (boardState.getBoard()[tile.xGrid()][tile.yGrid()] != null)
            checkOwnPiece(tile);
        else System.out.println("no piece");
    }

    private void checkOwnPiece(Tile piece) {
        boolean own = true;
        String fileName = getFileName(piece);
        if (boardState.isWhiteTurn() && fileName.contains("dark"))
            own = false;
        else if (!boardState.isWhiteTurn() && fileName.contains("light"))
            own = false;
        if (own)
            isAvailable(piece);
        else System.out.println("not your piece");
    }

    private void isAvailable(Tile piece) {
        System.out.println("you own this piece");
        if (!available.contains(piece)) { // TODO remove "!" and actually fill "available" somehow
            mark(piece);
            addPossibleMoves(piece);
        }
        else System.out.println("can't move this piece");
        // check if the piece selected is one of the pieces in the arraylist
        // if it does, call mark
    }

    private void mark(Tile piece) {
        if (selected != null) unmark(selected);
        selected = piece;
        // mark the piece and the possible moves
        System.out.println("Piece: " + piece.getName());
    }

    private void addPossibleMoves(Tile piece) { // currently no check for off boundaries (ArrayIndexOutOfBoundsException)
        int x = piece.xGrid();
        int y = piece.yGrid();
        String fileName = getFileName(piece);
        if (fileName.contains("king")) {
            // king case - 4 directions to check
            if (fileName.contains("light"));
            else ;
        }
        else {
            // normal piece - 2 directions to check
            if (fileName.contains("light")) {
                // not really sure about what's going on with out of bounds indexes
                // TODO should add a method for checkPossible
                if (boardState.getBoard()[x + 1][y - 1] == null)
                    possibleMoves.add(gameBoard.getTile(x + 1, y - 1));
                if (boardState.getBoard()[x - 1][y - 1] == null)
                    possibleMoves.add(gameBoard.getTile(x - 1, y - 1));
            }
            else {
                if (boardState.getBoard()[x + 1][y + 1] == null)
                    possibleMoves.add(gameBoard.getTile(x + 1, y + 1));
                if (boardState.getBoard()[x - 1][y + 1] == null)
                    possibleMoves.add(gameBoard.getTile(x - 1, y + 1));
            }
        }
        drawPossibleMoves();
    }

    private void unmark(Tile piece) {
        // unmark the selected piece and possible moves
        deletePossibleMoves();
        possibleMoves.clear();
    }

    private void finishMove(Tile tile) {
        // TODO first check if tile contains another piece owned by the same player
        // if it does - unmark, selected = null, possibleMove.clear() and call checkPiece(tile)
        if (possibleMoves.contains(tile))
            System.out.println("Move completed");
        else System.out.println("Can't move to here");
        // if succeeded to finish the move
        deletePossibleMoves();
        possibleMoves.clear();
        selected = null;
        boardState.nextTurn();
        if (available.pushAvailable(gameBoard, boardState)) // TODO should skip the turn or end the game?
            System.out.println("no moves");
    }

    private String getFileName(Tile piece) {
        String path = piece.getIcon().toString();
        int index = path.lastIndexOf('\\');
        return path.substring(index + 1);
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    // TODO remove these two functions and somehow move them to GUI.java - need to rethink this whole thing, maybe Game.java will be the main Object
    private void drawPossibleMoves() {
        for (Tile tile : possibleMoves)
            tile.setIcon(new ImageIcon("imgs\\possible move.png"));
    }

    private void deletePossibleMoves() {
        for (Tile tile : possibleMoves)
            tile.setIcon(null);
    }
}