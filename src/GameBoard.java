import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameBoard extends JLabel {

    public GameBoard(ActionListener action) {
        setLayout(new GridLayout(8, 8));
        setPreferredSize(new Dimension(Dimensions.BOARD_WIDTH, Dimensions.BOARD_HEIGHT));
        setBorder(new EmptyBorder(Dimensions.BOARD_BORDER, Dimensions.BOARD_BORDER, Dimensions.BOARD_BORDER, Dimensions.BOARD_BORDER));
        fillTiles(action);
    }

    private void fillTiles(ActionListener action) {
        for (int x = 0; x < 8; x++)
            for (int y = 0; y < 8; y++)
                add(tile(y, x, action));
    }

    private Tile tile(int x, int y, ActionListener action) {
        Tile tile = new Tile(x, y);
        if ((x + y) % 2 == 0) tile.setVisible(false);
        else {
            tile.setName(x + "," + y);
            tile.setOpaque(false);
            tile.setContentAreaFilled(false);
            tile.setBorderPainted(false);
            tile.setFocusPainted(false);
            tile.addActionListener(action);
        }
        return tile;
    }

    public Tile getTile(int x, int y) {
        return (Tile) getComponent(x + y * 8);
    }
}
