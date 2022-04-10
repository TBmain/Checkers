public class BoardState {
    private PieceType board[][];
    private Player turn;

    public BoardState() {
        board = new PieceType[8][8];
        turn = Player.WHITE;
        fillBoard();
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

    public PieceType[][] getBoard() {
        return board;
    }

    public Player getTurn() {
        return turn;
    }

    public void nextTurn() {
        turn = turn.getOpposite();
    }
}
