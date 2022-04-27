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
    private Comment comment;
    private int noProgress;
    private boolean active;

    public Game() {
        boardState = new BoardState();
        available = new Available(boardState);
        possibleMoves = new ArrayList<>();
        selected = null;
        combo = false;
        comment = Comment.WHITE;
        noProgress = 0;
        active = true;
    }

    public void checkPiece(Coordinates c) {
        if (boardState.getBoard()[c.getX()][c.getY()] != null) {
            if (checkOwnPiece(c))
                isAvailable(c);
            else
                comment = Comment.NOT_OWN_PIECE;
        }
        else if (selected != null)
            finishMove(c);
    }

    private boolean checkOwnPiece(Coordinates c) {
        return boardState.getBoard()[c.getX()][c.getY()].getPlayer() == boardState.getTurn();
    }

    private void isAvailable(Coordinates c) {
        if (available.contains(c)) {
            selected = c;
            possibleMoves.clear();
            possibleMoves = boardState.getPossibleMoves(c, combo);
            comment = Comment.NO_COMMENT;
        }
        else comment = Comment.NOT_AVAILABLE;
    }

    private void finishMove(Coordinates c) {
        if (possibleMoves.contains(c)) {
            boardState.move(selected, c);
            possibleMoves.clear();
            available.clear();
            if (boardState.getJump()) {
                selected = c;
                possibleMoves = boardState.getPossibleMoves(selected, combo = true);
            }
            if (possibleMoves.size() == 0) {
                if (noProgressMoves(c))
                    return;
                selected = null;
                combo = false;
                comment = boardState.nextTurn();
                if (available.setAvailable(boardState))
                    gameOver(boardState.getTurn().getOpposite());
            }
            else available.add(selected);
        }
        else comment = Comment.NOT_POSSIBLE;
    }

    private void gameOver(Player winner) {
        if (winner == null)
            comment = Comment.TIE;
        else if (winner == Player.WHITE)
            comment = Comment.WHITE_WON;
        else
            comment = Comment.BLACK_WON;
        active = false;
    }

    private boolean noProgressMoves(Coordinates c) {
        if (boardState.getBoard()[c.getX()][c.getY()].isKing() && !boardState.getJump()) {
            if (++noProgress == 15) {
                gameOver(null);
                return true;
            }
        }
        else
            noProgress = 0;
        return false;
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

    public String getComment() {
        return comment.toString();
    }

    public boolean isActive() {
        return active;
    }
}