import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    // Images
    private Icon background;
    private Icon whitePiece;
    private Icon blackPiece;
    private Icon whiteKing;
    private Icon blackKing;
    private Icon selectedPiece; // TODO should be changed to 4 icons of all pieces selected
    private Icon possibleMove;
    private Icon lastMove; // TODO same thing as selectedPiece - *another idea is to just use button borders*

    private Game game;
    private JLabel board;
    private GameBoard gameBoard;
    private BoardState boardState;

    public GUI() {
        setTitle("Checkers Project - Nadav Barak");
        setIconImage(Toolkit.getDefaultToolkit().getImage("imgs\\light piece.png"));
        start();
    }

    private void start() {
        System.out.println("Starting...");
        loadImages();
        System.out.println("Images loaded");
        setupFrame();
        System.out.println("Frame loaded");
        drawPieces();
        System.out.println("Pieces drawn");
        JOptionPane.showMessageDialog(this, "Game Is Ready!");
    }

    private void loadImages() {
        background = new ImageIcon("imgs\\gameboard.png");
        whitePiece = new ImageIcon("imgs\\light piece.png");
        blackPiece = new ImageIcon("imgs\\dark piece.png");
        whiteKing = new ImageIcon("imgs\\light king.png");
        blackKing = new ImageIcon("imgs\\dark king.png");
        selectedPiece = new ImageIcon("imgs\\selected piece.png");
        possibleMove = new ImageIcon("imgs\\possible move.png");
        lastMove = new ImageIcon("imgs\\last move.png");
    }

    private void setupFrame() {
        game = new Game();
        gameBoard = game.getGameBoard();
        boardState = game.getBoardState();
        board = new JLabel(background);
        getContentPane().add(board);
        board.add(gameBoard);
        board.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        pack();
        // System.out.println(getWidth() + " | " + getHeight());
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); // window centered to the screen
        setVisible(true);
    }

    private void drawPieces() {
        for (Component comp : gameBoard.getComponents()) {
            Tile tile = (Tile) comp;
            if (tile.isVisible()) {
                pieceType piece = boardState.getBoard()[tile.xGrid()][tile.yGrid()];
                if (piece != null) {
                    switch (piece) {
                        case white:
                            tile.setIcon(whitePiece);
                            break;
                        case black:
                            tile.setIcon(blackPiece);
                            break;
                        case whiteKing:
                            tile.setIcon(whiteKing);
                            break;
                        case blackKing:
                            tile.setIcon(blackKing);
                            break;
                    }
                }
            }
        }
    }
}