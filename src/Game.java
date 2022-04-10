import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Game {
    private BoardState boardState;
    private Available available;
    private ArrayList<Coordinates> possibleMoves;
    private Coordinates selected;

    public Game() {
        boardState = new BoardState();
        available = new Available(boardState);
        possibleMoves = new ArrayList<>();
        selected = null;
    }

    public void checkPiece(Coordinates c) {
        if (selected != null)
            finishMove(c);
        else if (boardState.getBoard()[c.getX()][c.getY()] != null) {
            if (checkOwnPiece(c))
                isAvailable(c);
            else
                System.out.println("not your piece");
        }
        else System.out.println("no piece");
    }

    private boolean checkOwnPiece(Coordinates c) {
        return boardState.getBoard()[c.getX()][c.getY()].getPlayer() == boardState.getTurn();
    }

    private void isAvailable(Coordinates c) {
        System.out.println("you own this piece");
        if (!available.contains(c)) { // TODO remove "!" and actually fill "available" somehow
            mark(c);
            addPossibleMoves(c);
        }
        else System.out.println("can't move this piece");
        // check if the piece selected is one of the pieces in the arraylist
        // if it does, call mark
    }

    private void mark(Coordinates c) {
        if (selected != null) /*unmark(selected)*/ System.out.println("sus");;
        selected = c;
        // mark the piece and the possible moves
        System.out.println("Piece: (" + c.getX() + ", " + c.getY() + ")");
    }

    private void addPossibleMoves(Coordinates c) { // currently no check for off boundaries (ArrayIndexOutOfBoundsException)
        int x = c.getX();
        int y = c.getY();

        if (boardState.getBoard()[x][y].isKing()) {
            // king case - 4 directions to check
        }
        else {
            int dy = (boardState.getTurn() == Player.WHITE) ? -1 : +1;

            if (checkIndex(x + 1, y + dy) && boardState.getBoard()[x + 1][y + dy] == null)
                possibleMoves.add(boardState.getCoordinates()[x + 1][y + dy]);
            if (checkIndex(x - 1, y + dy) && boardState.getBoard()[x - 1][y + dy] == null)
                possibleMoves.add(boardState.getCoordinates()[x - 1][y + dy]);
        }
        // drawPossibleMoves();
    }

    private void unmark(Tile piece) {
        // unmark the selected piece and possible moves
        //deletePossibleMoves();
        possibleMoves.clear();
    }

    private void finishMove(Coordinates c) {
        if (possibleMoves.contains(c)) {
            //deletePossibleMoves();
            boardState.getBoard()[c.getX()][c.getY()] = boardState.getBoard()[selected.getX()][selected.getY()];
            boardState.getBoard()[selected.getX()][selected.getY()] = null;
            //draw(tile);
            System.out.println("Move completed");
            possibleMoves.clear();
            selected = null;
            boardState.nextTurn();
            /*if (!available.pushAvailable(gameBoard, boardState)) TODO Available.java : checkAvailable
            gameOver(!boardState.isWhiteTurn());*/
        }
        else if (boardState.getBoard()[c.getX()][c.getY()] != null && checkOwnPiece(c) && !available.contains(c)) { // TODO remove "!" and actually fill "available" somehow
            //deletePossibleMoves();
            possibleMoves.clear();
            selected = null;
            isAvailable(c);
        }
        else System.out.println("Can't move to here");
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

    public ArrayList<Coordinates> getPossibleMoves() {
        return possibleMoves;
    }
}