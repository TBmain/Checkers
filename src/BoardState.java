public class BoardState {
    private pieceType board[][];
    private boolean whiteTurn;

    public BoardState() {
        board = new pieceType[8][8];
        whiteTurn = true;
        fillBoard();
    }

    private void fillBoard() {
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 3; y++)
                if ((x + y) % 2 != 0)
                    board[x][y] = pieceType.black;

        for (int x = 0; x < 8; x++)
            for (int y = 5; y < 8; y++)
                if ((x + y) % 2 != 0)
                    board[x][y] = pieceType.white;
    }

    public pieceType[][] getBoard() {
        return board;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void nextTurn() {
        whiteTurn = !whiteTurn;
    }
}
