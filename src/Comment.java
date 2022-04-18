public enum Comment {
    WHITE ("White's turn to play"),
    BLACK ("Black's turn to play"),
    NOT_OWN_PIECE ("This piece isn't yours"),
    NOT_AVAILABLE ("You can't move this piece"),
    MUST_JUMP ("You must jump (take)"),
    NOT_POSSIBLE ("You can only move to the marked tiles."),
    WHITE_WON ("GAME OVER - WHITE WON!"),
    BLACK_WON ("GAME OVER - BLACK WON!"),
    TIE ("GAME OVER - TIE!"),
    NO_COMMENT ("");

    private final String comment;

    Comment(String s) {
        comment = s;
    }

    public String toString() {
        return comment;
    }
}
