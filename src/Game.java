import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class Game {
    private BoardState boardState;
    private Coordinates[][] coordinates;
    private Available available;
    private ArrayList<Coordinates> possibleMoves;
    private Coordinates selected;

    public Game() {
        boardState = new BoardState();
        coordinates = fill();
        available = new Available(this);
        possibleMoves = new ArrayList<>();
        selected = null;
    }

    public void checkPiece(Coordinates c) {
        if (boardState.getBoard()[c.getX()][c.getY()] != null) {
            if (checkOwnPiece(c))
                isAvailable(c);
            else
                System.out.println("not your piece");
        }
        else if (selected != null)
            finishMove(c);
        else System.out.println("no piece");
    }

    private boolean checkOwnPiece(Coordinates c) {
        return boardState.getBoard()[c.getX()][c.getY()].getPlayer() == boardState.getTurn();
    }

    private void isAvailable(Coordinates c) {
        System.out.println("you own this piece");
        if (available.contains(c)) {
            selected = c;
            possibleMoves.clear();
            addPossibleMoves(c);
        }
        else System.out.println("can't move this piece");
        // check if the piece selected is one of the pieces in the arraylist
        // if it does, call mark
    }

    private void addPossibleMoves(Coordinates c) {
        int x = c.getX();
        int y = c.getY();

        if (boardState.getBoard()[x][y].isKing()) {
            // king case - 4 directions to check
        }
        else {
            int dy = (boardState.getTurn() == Player.WHITE) ? -1 : +1;

            if (checkIndex(x + 1, y + dy) && boardState.getBoard()[x + 1][y + dy] == null)
                possibleMoves.add(getCoordinates()[x + 1][y + dy]);
            if (checkIndex(x - 1, y + dy) && boardState.getBoard()[x - 1][y + dy] == null)
                possibleMoves.add(getCoordinates()[x - 1][y + dy]);
        }
    }

    private void finishMove(Coordinates c) {
        if (possibleMoves.contains(c)) {
            boardState.getBoard()[c.getX()][c.getY()] = boardState.getBoard()[selected.getX()][selected.getY()];
            boardState.getBoard()[selected.getX()][selected.getY()] = null;
            System.out.println("Move completed");
            possibleMoves.clear();
            selected = null;
            boardState.nextTurn();
            available.setAvailable(this);
            if (!available.setAvailable(this))
                gameOver(boardState.getTurn().getOpposite());
        }
        else System.out.println("Can't move to here");
    }

    private void gameOver(Player winner) {
        if (winner == Player.WHITE)
            System.out.println("White won");
        else
            System.out.println("Black won");
    }

    private boolean checkIndex(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    private Coordinates[][] fill() {
        Coordinates[][] c = new Coordinates[8][8];
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 8; y++)
                c[x][y] = new Coordinates(x, y);
        return c;
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public ArrayList<Coordinates> getPossibleMoves() {
        return possibleMoves;
    }

    public Coordinates[][] getCoordinates() {
        return coordinates;
    }

    public Coordinates getSelected() {
        return selected;
    }
}