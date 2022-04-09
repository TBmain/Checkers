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
        available = new Available(gameBoard, boardState);
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
        else if (boardState.getBoard()[tile.xGrid()][tile.yGrid()] != null) {
            if (checkOwnPiece(tile)) isAvailable(tile);
            else System.out.println("not your piece");
        }
        else System.out.println("no piece");
    }

    private boolean checkOwnPiece(Tile piece) {
        String fileName = getFileName(piece);
        if (boardState.isWhiteTurn() && fileName.contains("dark"))
            return false;
        if (!boardState.isWhiteTurn() && fileName.contains("light"))
            return false;
        return true;
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
                if (checkIndex(x + 1, y - 1) && boardState.getBoard()[x + 1][y - 1] == null)
                    possibleMoves.add(gameBoard.getTile(x + 1, y - 1));
                if (checkIndex(x - 1, y - 1) && boardState.getBoard()[x - 1][y - 1] == null)
                    possibleMoves.add(gameBoard.getTile(x - 1, y - 1));
            }
            else {
                if (checkIndex(x + 1, y + 1) && boardState.getBoard()[x + 1][y + 1] == null)
                    possibleMoves.add(gameBoard.getTile(x + 1, y + 1));
                if (checkIndex(x - 1, y + 1) && boardState.getBoard()[x - 1][y + 1] == null)
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
        if (possibleMoves.contains(tile)) {
            deletePossibleMoves();
            boardState.getBoard()[tile.xGrid()][tile.yGrid()] = boardState.getBoard()[selected.xGrid()][selected.yGrid()];
            boardState.getBoard()[selected.xGrid()][selected.yGrid()] = null;
            draw(tile);
            System.out.println("Move completed");
            possibleMoves.clear();
            selected = null;
            boardState.nextTurn();
            /*if (!available.pushAvailable(gameBoard, boardState)) TODO Available.java : checkAvailable
            gameOver(!boardState.isWhiteTurn());*/
        }
        else if (tile.getIcon() != null && checkOwnPiece(tile) && !available.contains(tile)) { // TODO remove "!" and actually fill "available" somehow
            deletePossibleMoves();
            possibleMoves.clear();
            selected = null;
            isAvailable(tile);
        }
        else System.out.println("Can't move to here");

    }

    private String getFileName(Tile piece) {
        String path = piece.getIcon().toString();
        int index = path.lastIndexOf('\\');
        return path.substring(index + 1);
    }

    private void gameOver(boolean winner) {
        if (winner)
            System.out.println("White won");
        else
            System.out.println("Black won");
    }

    private boolean checkIndex(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    // TODO remove these three functions and somehow move them to GUI.java - need to rethink this whole thing, maybe Game.java will be the main Object
    private void drawPossibleMoves() {
        for (Tile tile : possibleMoves)
            tile.setIcon(new ImageIcon("imgs\\possible move.png"));
    }

    private void deletePossibleMoves() {
        for (Tile tile : possibleMoves)
            tile.setIcon(null);
    }

    private void draw(Tile tile) {
        System.out.println(selected.getIcon());
        tile.setIcon(selected.getIcon());
        System.out.println(tile.getIcon());
        selected.setIcon(null);
    }
}