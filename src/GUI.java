import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

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
    private GameBoard gameBoard;
    private ArrayList<Coordinates> possibleMoves;
    private JTextArea comment;

    public GUI() {
        setTitle("Checkers Project - Nadav Barak");
        setIconImage(Toolkit.getDefaultToolkit().getImage("imgs\\light piece.png"));
        start();
    }

    private void start() {
        if (!settingsPopup()) {
            System.out.println("CANCELED");
            return;
        }
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
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setupMenu();
        setupBoard();
        setupComments();
        pack();
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null); // window centered to the screen
        setVisible(true);
    }

    private void setupMenu() {
        JMenuBar menu = new JMenuBar();
        menu.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JMenu options = new JMenu("Options");
        JMenu info = new JMenu("Info");

        menu.add(options);
        menu.add(info);

        JMenuItem restart = new JMenuItem("Restart");
        JMenuItem undo = new JMenuItem("Undo");
        JMenuItem rules = new JMenuItem("Rules");
        JMenuItem credits = new JMenuItem("Credits");

        options.add(restart);
        options.add(undo);
        info.add(rules);
        info.add(credits);

        restart.addActionListener(e -> System.out.println("game.restart()"));
        undo.addActionListener(e -> System.out.println("game.undo()"));
        rules.addActionListener(e -> System.out.println("rules()"));
        credits.addActionListener(e -> System.out.println("credits()"));

        getContentPane().add(menu);
    }

    private void setupBoard() {
        game = new Game();
        gameBoard = new GameBoard(this);
        possibleMoves = new ArrayList<>();

        JLabel board = new JLabel(background);
        board.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        board.add(gameBoard);

        JPanel boardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        boardPanel.add(board);

        getContentPane().add(boardPanel);
    }

    private void setupComments() {
        comment = new JTextArea(game.getComment());
        comment.setFont(new Font("Verdana", Font.PLAIN, 14));
        comment.setBackground(null);
        comment.setEditable(false);
        comment.setLineWrap(false);
        comment.setWrapStyleWord(true);
        comment.setAutoscrolls(true);

        JPanel commentPanel = new JPanel();
        commentPanel.add(comment);

        getContentPane().add(commentPanel);
    }

    private void drawPieces() {
        for (Component comp : gameBoard.getComponents()) {
            Tile tile = (Tile) comp;
            if (tile.isVisible()) {
                PieceType piece = game.getBoardState().getBoard()[tile.xGrid()][tile.yGrid()];
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
        PieceType piece = game.getBoardState().getBoard()[tile.xGrid()][tile.yGrid()];
        if (piece != null && piece.getPlayer() == game.getBoardState().getTurn()) {
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
        if (game.isActive()) {
            Tile tile = (Tile) e.getSource();
            game.checkPiece(game.getBoardState().getCoordinates()[tile.xGrid()][tile.yGrid()]);
            draw();

            Coordinates c = game.getSelected();
            if (c != null)
                mark(gameBoard.getTile(c.getX(), c.getY()));

            comment.setText(game.getComment());
        }
    }

    private boolean settingsPopup() {
        JPanel panel = new JPanel(new GridLayout(5,1)); // settings window

        // 2 players & 1 player buttons
        JRadioButton twoPlayers = new JRadioButton("Player VS Player", true);
        JRadioButton onePlayer = new JRadioButton("Player VS AI");
        ButtonGroup gameMode = new ButtonGroup();
        gameMode.add(twoPlayers);
        gameMode.add(onePlayer);

        // play as white or as black (only for 1 player option) buttons
        JRadioButton white = new JRadioButton("Play as white", true);
        JRadioButton black = new JRadioButton("Play as black");
        ButtonGroup playAs = new ButtonGroup();
        playAs.add(white);
        playAs.add(black);

        // difficulty slider
        JSlider difficulty = new JSlider(1, 4, 3);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, new JLabel("Easy"));
        labelTable.put(2, new JLabel("Medium"));
        labelTable.put(3, new JLabel("Hard"));
        labelTable.put(4, new JLabel("Impossible"));
        difficulty.setLabelTable(labelTable);
        difficulty.setPaintLabels(true);
        difficulty.setPaintTicks(true);
        difficulty.setMajorTickSpacing(1);
        difficulty.setPreferredSize(new Dimension(250,50));

        // show/hide AI options according to 1/2 players
        twoPlayers.addActionListener(e -> { white.setVisible(false);
                                            black.setVisible(false);
                                            difficulty.setVisible(false);});
        onePlayer.addActionListener(e -> { white.setVisible(true);
                                           black.setVisible(true);
                                           difficulty.setVisible(true);});

        // add everything to the settings window
        panel.add(twoPlayers);
        panel.add(onePlayer);
        panel.add(white);
        panel.add(black);
        panel.add(difficulty);

        // hide 1 player options
        white.setVisible(false);
        black.setVisible(false);
        difficulty.setVisible(false);

        // show window
        int result = JOptionPane.showConfirmDialog(null, panel, "Game Settings",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // process results
        if (result == JOptionPane.OK_OPTION) {
            if (onePlayer.isSelected()) {
                Settings.TWO_PLAYERS = false;
                Settings.FIRST_MOVE = (white.isSelected()) ? true : false;
                Settings.AI_DEPTH = difficulty.getValue() * 2; // TODO make different depths
            }
            return true;
        }
        return false;
    }
}