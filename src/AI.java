import java.util.ArrayList;
import java.util.HashMap;

public class AI {
    private Player player;
    private int depth;

    public AI() {
        player = (Settings.FIRST_MOVE) ? Player.BLACK : Player.WHITE;
        depth = lvlToDepth(Settings.AI_LEVEL);
    }

    private int lvlToDepth(int lvl) {
        HashMap<Integer, Integer> difficulty = new HashMap<>() {{
           put(1, 1);
           put(2, 3);
           put(3, 8);
           put(4, 11);
        }};
        return difficulty.get(lvl);
    }

    public BoardState move(BoardState boardState) {
        ArrayList<BoardState> states = boardState.getSuccessors();
        return calcMoves(states);
    }

    private BoardState calcMoves(ArrayList<BoardState> successors) {
        if (successors.size() == 1)
            return successors.get(0);
        int bestScore = Integer.MIN_VALUE;
        ArrayList<BoardState> bestMoves = new ArrayList<>();
        for (BoardState succ : successors) {
            int val = minimax(succ, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (val > bestScore) {
                bestScore = val;
                bestMoves.clear();
            }
            if (val == bestScore)
                bestMoves.add(succ);
        }
        return randomMove(bestMoves);
    }

    private int minimax(BoardState node, int depth, int alpha, int beta) {
        if (depth == 0 || node.getSuccessors().size() == 0)
            return calcScore(node);
        int score;
        // MAX
        if (node.getTurn() == player) {
            score = Integer.MIN_VALUE;
            for (BoardState child : node.getSuccessors()) {
                score = Math.max(score, minimax(child, depth - 1, alpha, beta));
                alpha = Math.max(alpha, score);
                if (alpha >= beta)
                    break;
            }
        }
        // MIN
        else {
            score = Integer.MAX_VALUE;
            for (BoardState child : node.getSuccessors()) {
                score = Math.min(score, minimax(child, depth - 1, alpha, beta));
                beta = Math.min(beta, score);
                if (alpha >= beta)
                    break;
            }
        }
        return score;
    }

    private BoardState randomMove(ArrayList<BoardState> moves) {
        int random = (int)(Math.random() * moves.size());
        return moves.get(random);
    }

    private int calcScore(BoardState node) {
        if (node.getPieceCount(player) == 0)
            return Integer.MIN_VALUE;
        if (node.getPieceCount(player.getOpposite()) == 0)
            return Integer.MAX_VALUE;
        if (node.tie())
            return 0;
        return node.getPieceCount(player) - node.getPieceCount(player.getOpposite());
    }

    public Player getPlayer() {
        return player;
    }
}
