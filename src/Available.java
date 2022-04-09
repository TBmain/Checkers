import java.awt.*;
import java.util.ArrayList;

public class Available extends ArrayList<Tile> {

    private boolean take;

    public Available() {
        super(); // do I need this?
    }

    public void add() {
        take = false;
        clear();
        // add the available pieces
        check(); // should be called for each piece
        if (take)
            removeNoTake();
    }

    private void check() {
        // check if piece is available
        // if it's able to take set take to true
    }

    private void removeNoTake() {
        for (Tile tile : this);
            // remove noTake pieces
    }
}
