import java.util.ArrayList;

public class BoardState {
    private PieceType[][] board;
    private Player turn;
    private boolean jump;
    private static Coordinates[][] coordinates = new Coordinates[8][8];

    public BoardState() {
        board = new PieceType[8][8];
        turn = Player.WHITE;
        jump = true;
        fillBoard();
        fillCoordinates();
    }

    public BoardState(BoardState boardState) {
        board = boardState.board.clone();
        turn = boardState.turn;
        jump = boardState.jump;
    }

    private void fillBoard() {
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 3; y++)
                if ((x + y) % 2 != 0)
                    board[x][y] = new PieceType(Player.BLACK, false);

        for (int x = 0; x < 8; x++)
            for (int y = 5; y < 8; y++)
                if ((x + y) % 2 != 0)
                    board[x][y] = new PieceType(Player.WHITE, false);
    }

    private void fillCoordinates() {
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 8; y++)
                coordinates[x][y] = new Coordinates(x, y);
    }
    public ArrayList<Coordinates> getPossibleMoves(Coordinates c, boolean combo) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();

        int x = c.getX();
        int y = c.getY();

        if (getBoard()[x][y].isKing()) {
            possibleMoves.addAll(getKingMoves(c));
        }
        else {
            int dy = (getTurn() == Player.WHITE) ? -1 : +1;
            ArrayList<Coordinates> move;
            if ((move = checkMove(c, 1, dy)) != null)
                possibleMoves.addAll(move);
            if ((move = checkMove(c, -1, dy)) != null)
                possibleMoves.addAll(move);
            if (combo) {
                if ((move = checkMove(c, 1, -dy)) != null)
                    possibleMoves.addAll(move);
                if ((move = checkMove(c, -1, -dy)) != null)
                    possibleMoves.addAll(move);
            }
        }
        return possibleMoves;
    }

    private ArrayList<Coordinates> getKingMoves(Coordinates c) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();
        ArrayList<Coordinates> moves;
        if ((moves = checkMove(c, 1, 1)) != null)
            possibleMoves.addAll(moves);
        if ((moves = checkMove(c, 1, -1)) != null)
            possibleMoves.addAll(moves);
        if ((moves = checkMove(c, -1, 1)) != null)
            possibleMoves.addAll(moves);
        if ((moves = checkMove(c, -1, -1)) != null)
            possibleMoves.addAll(moves);
        return possibleMoves;
    }

    private ArrayList<Coordinates> checkMove(Coordinates c, int dx, int dy) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();
        int x = c.getX();
        int y = c.getY();

        if (!checkIndex(x + dx, y + dy))
            return null;

        if (jump) {
            if (getBoard()[x][y].isKing()) {
                int i = 1;
                while (checkIndex(x + i * dx, y + i * dy) && getBoard()[x + i * dx][y + i * dy] == null)
                    i++;
                if (checkIndex(x + i * dx, y + i * dy)) {
                    if (getBoard()[x + i * dx][y + i * dy].getPlayer() == getTurn().getOpposite()) {
                        while (checkIndex(x + (i + 1) * dx, y + (i + 1) * dy) && getBoard()[x + (i + 1) * dx][y + (i + 1) * dy] ==  null) {
                            possibleMoves.add(getCoordinates()[x + (i + 1) * dx][y + (i + 1) * dy]);
                            i++;
                        }
                    }
                }
            }
            else if (getBoard()[x + dx][y + dy] != null) {
                if (getBoard()[x + dx][y + dy].getPlayer() == getTurn().getOpposite()) {
                    if (checkIndex(x + 2 * dx, y + 2 * dy) && getBoard()[x + 2 * dx][y + 2 * dy] == null)
                        possibleMoves.add(getCoordinates()[x + 2 * dx][y + 2 * dy]);
                }
            }
        }
        else if (getBoard()[x][y].isKing()) {
            int i = 1;
            while (checkIndex(x + i * dx, y + i * dy) && getBoard()[x + i * dx][y + i * dy] == null) {
                possibleMoves.add(getCoordinates()[x + i * dx][y + i * dy]);
                i++;
            }
        }
        else {
            if (getBoard()[x + dx][y + dy] == null)
                possibleMoves.add(getCoordinates()[x + dx][y + dy]);
        }
        return possibleMoves;
    }

    public void move(Coordinates from, Coordinates to) {
        getBoard()[to.getX()][to.getY()] = getBoard()[from.getX()][from.getY()];
        getBoard()[from.getX()][from.getY()] = null;

        makeKing(to);

        int distance = Math.abs(to.getX() - from.getX());
        int dx = (to.getX() - from.getX()) / distance;
        int dy = (to.getY() - from.getY()) / distance;

        for (int i = 1; i < distance; i++)
            board[from.getX() + i * dx][from.getY() + i * dy] = null;
    }

    private boolean checkIndex(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    private void makeKing(Coordinates c) {
        PieceType piece = getBoard()[c.getX()][c.getY()];
        if (!piece.isKing()) {
            if (piece.getPlayer() == Player.WHITE && c.getY() == 0)
                piece.setKing();
            else if (piece.getPlayer() == Player.BLACK && c.getY() == 7)
                piece.setKing();
        }
    }
    public PieceType[][] getBoard() {
        return board;
    }

    public Player getTurn() {
        return turn;
    }

    public boolean getJump() {
        return jump;
    }

    public void setJumpFalse() {
        jump = false;
    }

    public Coordinates[][] getCoordinates() {
        return coordinates;
    }

    public void nextTurn() {
        turn = turn.getOpposite();
        jump = true;
    }
}
