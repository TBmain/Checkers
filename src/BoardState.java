import java.util.ArrayList;

public class BoardState {
    private PieceType[][] board;
    private Player turn;
    private static Coordinates[][] coordinates = new Coordinates[8][8];
    // TODO private boolean jump;

    public BoardState() {
        board = new PieceType[8][8];
        turn = Player.WHITE;
        fillBoard();
        fillCoordinates();
    }

    public BoardState(BoardState boardState) {
        board = boardState.board.clone();
        turn = boardState.turn;
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
    public ArrayList<Coordinates> getPossibleMoves(Coordinates c) {
        ArrayList<Coordinates> possibleMoves = new ArrayList<>();

        int x = c.getX();
        int y = c.getY();

        if (getBoard()[x][y].isKing()) {
            // king case - 4 directions to check
        }
        else {
            int dy = (getTurn() == Player.WHITE) ? -1 : +1;

            if (checkIndex(x + 1, y + dy) && getBoard()[x + 1][y + dy] == null)
                possibleMoves.add(coordinates[x + 1][y + dy]);
            if (checkIndex(x - 1, y + dy) && getBoard()[x - 1][y + dy] == null)
                possibleMoves.add(coordinates[x - 1][y + dy]);
        }
        // if take (jump/eat) set true
        return possibleMoves;
    }

    private boolean checkIndex(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    public PieceType[][] getBoard() {
        return board;
    }

    public Player getTurn() {
        return turn;
    }

    public Coordinates[][] getCoordinates() {
        return coordinates;
    }

    public void nextTurn() {
        turn = turn.getOpposite();
    }
}
