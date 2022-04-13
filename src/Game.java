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
    private boolean combo;

    public Game() {
        boardState = new BoardState();
        available = new Available(boardState);
        possibleMoves = new ArrayList<>();
        selected = null;
        combo = false;
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
            possibleMoves = boardState.getPossibleMoves(c, combo);
        }
        else System.out.println("can't move this piece");
    }

    private void finishMove(Coordinates c) {
        if (possibleMoves.contains(c)) {
            boardState.move(selected, c);
            System.out.println("Move completed");
            possibleMoves.clear();
            available.clear();
            if (boardState.getJump()) {
                selected = c;
                possibleMoves = boardState.getPossibleMoves(selected, combo = true);
            }
            if (possibleMoves.size() == 0) {
                selected = null;
                combo = false;
                boardState.nextTurn();
                if (!available.setAvailable(boardState))
                    gameOver(boardState.getTurn().getOpposite());
            }
            else available.add(selected);
        }
        else System.out.println("Can't move to here");
    }

    private void gameOver(Player winner) {
        if (winner == Player.WHITE)
            System.out.println("White won");
        else
            System.out.println("Black won");
    }

    public BoardState getBoardState() {
        return boardState;
    }

    public ArrayList<Coordinates> getPossibleMoves() {
        return possibleMoves;
    }

    public Coordinates getSelected() {
        return selected;
    }
}