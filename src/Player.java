public enum Player {
    WHITE, BLACK;

    public Player getOpposite() {
        if (this == WHITE)
            return BLACK;
        return WHITE;
    }
}