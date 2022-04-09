import javax.swing.*;

public class Tile extends JButton {
    private int xGrid;
    private int yGrid;

    public Tile(int x, int y) {
        xGrid = x;
        yGrid = y;
    }

    public int xGrid() {
        return xGrid;
    }

    public int yGrid() {
        return yGrid;
    }
}
