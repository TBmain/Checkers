import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JFrame implements ActionListener {

    // Images
    private Icon background;
    private Icon whitePiece;
    private Icon blackPiece;
    private Icon whiteKing;
    private Icon blackKing;
    private Icon selectedWhitePiece;
    private Icon selectedBlackPiece;
    private Icon selectedWhiteKing;
    private Icon selectedBlackKing;
    private Icon possibleMove;
    private Icon lastMove; // TODO same thing as selectedPiece - *another idea is to just use button borders*

    private Game game;
    private JLabel board;
    private GameBoard gameBoard;
    private BoardState boardState;
    private ArrayList<Coordinates> possibleMoves;

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
        selectedWhitePiece = new ImageIcon("imgs\\selected light piece.png");
        selectedBlackPiece = new ImageIcon("imgs\\selected dark piece.png");
        selectedWhiteKing = new ImageIcon("imgs\\selected light king.png");
        selectedBlackKing = new ImageIcon("imgs\\selected dark king.png");
        possibleMove = new ImageIcon("imgs\\possible move.png");
        lastMove = new ImageIcon("imgs\\last move.png");
    }

    private void setupFrame() {
        game = new Game();
        gameBoard = new GameBoard(this);
        boardState = game.getBoardState();
        board = new JLabel(background);
        possibleMoves = new ArrayList<>();
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
                PieceType piece = boardState.getBoard()[tile.xGrid()][tile.yGrid()];
                if (piece != null) {
                    if (piece.getPlayer() == Player.WHITE) {
                        if (piece.isKing())
                            tile.setIcon(whiteKing);
                        else
                            tile.setIcon(whitePiece);
                    }
                    else {
                        if (piece.isKing())
                            tile.setIcon(blackKing);
                        else
                            tile.setIcon(blackPiece);
                    }
                }
                else tile.setIcon(null);
            }
        }
    }

    private void draw() {
        for (Coordinates c : possibleMoves)
            gameBoard.getTile(c.getX(), c.getY()).setIcon(null);

        possibleMoves.clear();
        possibleMoves.addAll(game.getPossibleMoves());

        drawPieces();

        for (Coordinates c : possibleMoves)
            gameBoard.getTile(c.getX(), c.getY()).setIcon(possibleMove);
    }

    private void mark(Tile tile) {
        PieceType piece = boardState.getBoard()[tile.xGrid()][tile.yGrid()];
        if (piece != null && piece.getPlayer() == boardState.getTurn()) {
            Player p = piece.getPlayer();
            switch(p) {
                case WHITE:
                    if (tile.getIcon() == whitePiece)
                        tile.setIcon(selectedWhitePiece);
                    else
                        tile.setIcon(selectedWhiteKing);
                    break;
                case BLACK:
                    if (tile.getIcon() == blackPiece)
                        tile.setIcon(selectedBlackPiece);
                    else
                        tile.setIcon(selectedBlackKing);
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Tile tile = (Tile) e.getSource();
        game.checkPiece(game.getCoordinates()[tile.xGrid()][tile.yGrid()]);
        draw();

        Coordinates c = game.getSelected();
        if (c != null)
            mark(gameBoard.getTile(c.getX(), c.getY()));
    }
}