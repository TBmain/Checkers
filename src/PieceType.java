public class PieceType {
    private Player p;
    private boolean king;

    public PieceType(Player p, boolean king) {
        this.p = p;
        this.king = king;
    }

    public PieceType(PieceType piece) {
        this.p = piece.p;
        this.king = piece.king;
    }

    public Player getPlayer() {
        return p;
    }

    public PieceType setKing() {
        PieceType piece = new PieceType(this);
        piece.king = true;
        return piece;
    }

    public boolean isKing() {
        return king;
    }
}