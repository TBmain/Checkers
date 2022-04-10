public class PieceType {
    private Player p;
    private boolean king;

    public PieceType(Player p, boolean king) {
        this.p = p;
        this.king = king;
    }

    public Player getPlayer() {
        return p;
    }

    public boolean isKing() {
        return king;
    }
}
