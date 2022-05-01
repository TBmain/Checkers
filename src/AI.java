import java.util.ArrayList;

public class AI {
    private Player player;
    private int depth;

    public AI() {
        player = (Settings.FIRST_MOVE) ? Player.BLACK : Player.WHITE;
        depth = Settings.AI_DEPTH;
    }

    public BoardState move(BoardState boardState) { // TODO random AI for now
        int rand = (int)(Math.random() * 100);
        ArrayList<BoardState> states = boardState.getSuccessors();
        return states.get(rand % states.size());
    }

    public Player getPlayer() {
        return player;
    }
}
