import java.util.ArrayList;

public class BoardState {
    private PieceType[][] board;
    private Player turn;
    private boolean jump;
    private int whiteCount;
    private int blackCount;
    private ArrayList<Coordinates> lastMove;
    private static Coordinates[][] coordinates = new Coordinates[8][8];

    public BoardState() {
        board = new PieceType[8][8];
        turn = Player.WHITE;
        jump = true;
        whiteCount = 12;
        blackCount = 12;
        lastMove = new ArrayList<>();
        fillBoard();
        fillCoordinates();
    }

    public BoardState(BoardState boardState) {
        board = new PieceType[8][8];
        for (int i = 0; i < 8; i++)
            board[i] = boardState.board[i].clone();
        turn = boardState.turn;
        jump = boardState.jump;
        whiteCount = boardState.whiteCount;
        blackCount = boardState.blackCount;
        lastMove = new ArrayList<>(boardState.lastMove);
    }

    private void fillBoard() {
        Player p1 = (Settings.REVERSE) ? Player.BLACK : Player.WHITE;
        Player p2 = p1.getOpposite();
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 3; y++)
                if ((x + y) % 2 != 0)
                    board[x][y] = new PieceType(p2, false);

        for (int x = 0; x < 8; x++)
            for (int y = 5; y < 8; y++)
                if ((x + y) % 2 != 0)
                    board[x][y] = new PieceType(p1, false);
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
            int dy = (getTurn() == Player.WHITE) ? ((Settings.REVERSE) ? +1 : -1) : ((Settings.REVERSE) ? -1 : +1);
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

    public ArrayList<Coordinates> move(Coordinates from, Coordinates to) {
        lastMove.clear();
        lastMove.add(from);
        lastMove.add(to);
        getBoard()[to.getX()][to.getY()] = getBoard()[from.getX()][from.getY()];
        getBoard()[from.getX()][from.getY()] = null;

        makeKing(to);

        int distance = Math.abs(to.getX() - from.getX());
        int dx = (to.getX() - from.getX()) / distance;
        int dy = (to.getY() - from.getY()) / distance;

        if (jump) {
            PieceType tile;
            int sub;
            for (int i = 1; i < distance; i++) {
                tile = board[from.getX() + i * dx][from.getY() + i * dy];
                if (tile != null) {
                    sub = (tile.isKing()) ? 3 : 1;
                    if (getTurn() == Player.WHITE)
                        blackCount -= sub;
                    else
                        whiteCount -= sub;
                    board[from.getX() + i * dx][from.getY() + i * dy] = null;
                }
            }
            return getPossibleMoves(to, true);
        }
        return new ArrayList<>();
    }

    private boolean checkIndex(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    private void makeKing(Coordinates c) {
        PieceType piece = getBoard()[c.getX()][c.getY()];
        if (!piece.isKing()) {
            if (piece.getPlayer() == Player.WHITE && c.getY() == ((Settings.REVERSE) ? 7 : 0)) {
                getBoard()[c.getX()][c.getY()] = piece.setKing();
                whiteCount += 2;
            }
            else if (piece.getPlayer() == Player.BLACK && c.getY() == ((Settings.REVERSE) ? 0 : 7)) {
                getBoard()[c.getX()][c.getY()] = piece.setKing();
                blackCount += 2;
            }
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

    public int getPieceCount(Player p) {
        if (p == Player.WHITE)
            return whiteCount;
        return blackCount;
    }

    public ArrayList<Coordinates> getLastMove() {
        return lastMove;
    }

    public Coordinates[][] getCoordinates() {
        return coordinates;
    }

    public Comment nextTurn() {
        turn = turn.getOpposite();
        jump = true;
        if (turn == Player.WHITE)
            return Comment.WHITE;
        return Comment.BLACK;
    }

    public ArrayList<BoardState> getSuccessors() {
        ArrayList<BoardState> successors = new ArrayList<>();

        Available pieces = new Available(this);
        for (Coordinates piece : pieces) {
            ArrayList<Coordinates> moves = getPossibleMoves(piece, false);
            for (Coordinates move : moves) {
                BoardState successor = new BoardState(this); // deep copy BoardState
                successors.addAll(successor.possibleSuccessors(piece, move));
            }
        }
        return successors;
    }

    private ArrayList<BoardState> possibleSuccessors(Coordinates from, Coordinates to) {
        ArrayList<BoardState> results = new ArrayList<>();

        ArrayList<Coordinates> comboMoves = move(from, to);

        if (comboMoves.size() == 0) {
            nextTurn();
            results.add(this);
        }

        for (Coordinates comboMove : comboMoves) {
            BoardState successorCombo = new BoardState(this); // deep copy BoardState
            results.addAll(successorCombo.possibleSuccessors(to, comboMove));
        }

        return results;
    }

    public void printBoard() {
        String shape;
        for (int x = 0; x < 8; x++) {
            System.out.println();
            for (int y = 0; y < 8; y++) {
                shape = "-";
                if (board[y][x] != null) {
                    if (board[y][x].getPlayer() == Player.WHITE)
                        shape = "x";
                    else
                        shape = "o";
                }
                System.out.print(shape + " ");
            }
        }
        System.out.println();
    }
}