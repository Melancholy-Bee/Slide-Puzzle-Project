package mypackage;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Board extends JPanel {
    private ArrayList<ArrayList<Tile>> tileGrid;
    private int gridSize;
    private Tile emptyTile;
    private ArrayList<ArrayList<BufferedImage>> originalImageGrid;
    private int moveCount = 0;
    private JLabel moveCounterLabel;
    private JPanel boardPanel;


    private static JButton settings = new JButton("Settings");
    private static JButton reset = new JButton("Reset");
    private static JButton newGame = new JButton("New Game");
    private static JButton load = new JButton("Load");
    private static JButton save = new JButton("Save");

    public Board(ArrayList<ArrayList<BufferedImage>> imageGrid) {
        this.gridSize = imageGrid.size();
        this.tileGrid = new ArrayList<>();
        this.originalImageGrid = imageGrid;
        setLayout(new GridBagLayout());
        this.setBackground(new Color(169,221,214));
        initializeBoard(imageGrid);
    }

    private void initializeBoard(ArrayList<ArrayList<BufferedImage>> imageGrid) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;

        for (int row = 0; row < gridSize; row++) {
            ArrayList<Tile> rowTiles = new ArrayList<>();
            for (int col = 0; col < gridSize; col++) {
                BufferedImage img = imageGrid.get(row).get(col);
                int correct = ImageProcessor.goal.get(row).get(col);

                int goalX = (correct / gridSize);
                int goalY = (correct % gridSize);

                Tile tile = new Tile(img, goalY, goalX);  // goal position
                tile.setPosX(col);
                tile.setPosY(row);
                rowTiles.add(tile);

                JButton button = makeTileButton(tile);
                gbc.gridx = col;
                gbc.gridy = row;
                add(button, gbc);

                if (tile.getIsEmpty()) {
                    emptyTile = tile;
                }
            }
            tileGrid.add(rowTiles);
        }
    }

    private JButton makeTileButton(Tile tile) {
        
        JButton button = new JButton();
        BufferedImage img = tile.getImage();
    
        if (img != null) {
            // Set button size to match the image dimensions
            button.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
            button.setIcon(new ImageIcon(img));
        } else {
            // For empty tile, set a default size
            button.setPreferredSize(new Dimension(100, 100));
            button.setBackground(Color.LIGHT_GRAY);
        }

        // Remove padding and border to avoid gaps between the tiles
        button.setBorderPainted(false); // Remove border
        button.setFocusPainted(false);  // Remove focus outline
        button.setContentAreaFilled(false); // Remove background fill

        // Add click listener
        button.addActionListener(e -> {
            if (tile.validMove(emptyTile)) {
                swapTiles(tile, emptyTile);
                moveCount++;
                moveCounterLabel.setText("Moves: " + moveCount);
                refreshBoard();
                if (checkWin()) {
                    WinMenu.initialize();
                    java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(Board.this);
                    if (window != null) {
                        window.dispose();
                    }
                }
            }
        });

        return button;
    }

    private void swapTiles(Tile clicked, Tile empty) {
        int tempX = clicked.getPosX();
        int tempY = clicked.getPosY();

        // Swap positions in tile objects
        clicked.setPosX(empty.getPosX());
        clicked.setPosY(empty.getPosY());
        empty.setPosX(tempX);
        empty.setPosY(tempY);

        // Swap positions in tileGrid
        tileGrid.get(tempY).set(tempX, empty);
        tileGrid.get(clicked.getPosY()).set(clicked.getPosX(), clicked);

        refreshBoard();
    }

    private void refreshBoard() {
        // Clear the current board
        removeAll();
    
        // Re-add the tiles to the panel with the updated positions
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0); // No padding around the tiles
        gbc.fill = GridBagConstraints.NONE; // No resizing of buttons
    
        // Iterate through the grid and re-add buttons
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Tile tile = tileGrid.get(row).get(col);
                JButton button = makeTileButton(tile);
                
                // Update position based on the tile's position in the grid
                gbc.gridx = col;
                gbc.gridy = row;
                add(button, gbc);
            }
        }
    
        // Revalidate and repaint to update the UI
        revalidate();
        repaint();
    }

    private boolean checkWin() {
        for (ArrayList<Tile> row : tileGrid) {
            for (Tile tile : row) {
                if (!tile.getIsEmpty() && !tile.correct()) {
                    return false;
                }
            }
        }
        return true;
    }



    //modifyed to allow for more buttons
    public static void show(ArrayList<ArrayList<BufferedImage>> imageGrid) {
        JFrame frame = new JFrame("Sliding Puzzle");
        Board board = new Board(imageGrid);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
        frame.add(board, BorderLayout.CENTER); // Add board to center
    
        // Settings button
        settings.setFocusable(false);
        settings.setFont(new Font("Dialog", Font.BOLD, 15));
        settings.setPreferredSize(new Dimension(200, 75));
        settings.setBackground(new Color(0, 115, 150));
    
        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsMenu.initialize(frame);
            }
        });

        //save button rebecca
        save.setFocusable(false);
        save.setFont(new Font("Dialog", Font.BOLD, 15));
        save.setPreferredSize(new Dimension(200, 75));
        save.setBackground(new Color(0, 115, 150));
    
        //save.addActionListener(new ActionListener() {
          //  @Override
            
        //});
        
        //reset button rebecca
        JButton reset = new JButton("Reset");
        reset.setFocusable(false);
        reset.setFont(new Font("Dialog", Font.BOLD, 15));
        reset.setPreferredSize(new Dimension(200, 75));
        reset.setBackground(new Color(0, 115, 150));

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    board.moveCount = 0;
                    board.moveCounterLabel.setText("Moves: 0");
                    frame.dispose(); // Dispose old frame
                    Board.show(board.originalImageGrid); // Start fresh
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        //load button rebecca
        load.setFocusable(false);
        load.setFont(new Font("Dialog", Font.BOLD, 15));
        load.setPreferredSize(new Dimension(200, 75));
        load.setBackground(new Color(0, 115, 150));
    
        //load.addActionListener(new ActionListener() {
          //  @Override
            
       // });

        //new game copied from main and edited 
        newGame.setFocusable(false);
        newGame.setFont(new Font("Dialog", Font.BOLD, 15));
        newGame.setPreferredSize(new Dimension(200, 75));
        newGame.setBackground(new Color(0, 115, 150));
    
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //call ImageSelector to bring up image options
                    ImageSelector.getInstance().selectGameImage();

                } catch (Exception er) {
                    System.err.println("Error: " + er.getMessage());
                    er.printStackTrace(); // Optional: useful for debugging
                }
                frame.setVisible(false);
            }
        });

    
        // button in box centered at the bottom
        Box bottomBox = Box.createVerticalBox();
        bottomBox.add(Box.createVerticalGlue());
        bottomBox.add(settings);
        bottomBox.add(save);
        bottomBox.add(load);
        bottomBox.add(newGame);
        bottomBox.add(reset);
        bottomBox.add(Box.createVerticalGlue());
    
        frame.add(bottomBox, BorderLayout.EAST);

        board.moveCounterLabel = new JLabel("Moves: 0");
        board.moveCounterLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        board.moveCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(board.moveCounterLabel, BorderLayout.NORTH); // or BorderLayout.SOUTH

        frame.pack();
        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.9); // 90% of screen width
        int height = (int) (screenSize.height * 0.9); // 90% of screen height
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    
        /*  Finalize frame
        frame.pack();
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null); // center on screen
        frame.setVisible(true); */
    }

}




/*import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel {
    private ArrayList<ArrayList<Tile>> tileGrid;
    private int gridSize;
    private Tile emptyTile;
    private ArrayList<ArrayList<BufferedImage>> originalImageGrid;
    private int moveCount = 0;
    private JLabel moveCounterLabel;
    private JPanel boardPanel;

    private static JButton settings = new JButton("Settings");
    private static JButton reset = new JButton("Reset");
    private static JButton newGame = new JButton("New Game");
    private static JButton load = new JButton("Load");
    private static JButton save = new JButton("Save");

    private Tile movingTile = null;
    private int targetEmptyX, targetEmptyY;
    private int animationStep = 0;
    private final int totalAnimationSteps = 10; // Adjust for animation speed
    private Timer animationTimer;

    public Board(ArrayList<ArrayList<BufferedImage>> imageGrid) {
        this.gridSize = imageGrid.size();
        this.tileGrid = new ArrayList<>();
        this.originalImageGrid = imageGrid;
        setLayout(new GridBagLayout());
        this.setBackground(new Color(169, 221, 214));
        initializeBoard(imageGrid);
        initializeAnimationTimer();
    }

    private void initializeAnimationTimer() {
        animationTimer = new Timer(30, new ActionListener() { // Adjust delay for speed
            @Override
            public void actionPerformed(ActionEvent e) {
                if (movingTile != null) {
                    int currentX = movingTile.getPosX();
                    int currentY = movingTile.getPosY();

                    double deltaX = (double) (targetEmptyX - currentX) / totalAnimationSteps;
                    double deltaY = (double) (targetEmptyY - currentY) / totalAnimationSteps;

                    movingTile.setVisualX(movingTile.getVisualX() + deltaX);
                    movingTile.setVisualY(movingTile.getVisualY() + deltaY);

                    animationStep++;

                    if (animationStep >= totalAnimationSteps) {
                        // Animation complete, finalize the swap
                        movingTile.setPosX(targetEmptyX);
                        movingTile.setPosY(targetEmptyY);
                        emptyTile.setPosX(currentX);
                        emptyTile.setPosY(currentY);

                        // Update tileGrid
                        tileGrid.get(currentY).set(currentX, emptyTile);
                        tileGrid.get(targetEmptyY).set(targetEmptyX, movingTile);

                        movingTile.setVisualX((double) targetEmptyX);
                        movingTile.setVisualY((double) targetEmptyY);
                        emptyTile.setVisualX((double) currentX);
                        emptyTile.setVisualY((double) currentY);

                        movingTile = null;
                        animationTimer.stop();
                        refreshBoard();
                        if (checkWin()) {
                            WinMenu.initialize();
                            java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(Board.this);
                            if (window != null) {
                                window.dispose();
                            }
                        }
                    }
                    repaint(); // Trigger repaint during animation
                }
            }
        });
        animationTimer.setRepeats(true);
    }

    private void initializeBoard(ArrayList<ArrayList<BufferedImage>> imageGrid) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;

        for (int row = 0; row < gridSize; row++) {
            ArrayList<Tile> rowTiles = new ArrayList<>();
            for (int col = 0; col < gridSize; col++) {
                BufferedImage img = imageGrid.get(row).get(col);
                int correct = ImageProcessor.goal.get(row).get(col);

                int goalX = (correct % gridSize); // Corrected goalX and goalY
                int goalY = (correct / gridSize);

                Tile tile = new Tile(img, goalX, goalY); // goal position
                tile.setPosX(col);
                tile.setPosY(row);
                tile.setVisualX((double) col);
                tile.setVisualY((double) row);
                rowTiles.add(tile);

                JButton button = makeTileButton(tile);
                gbc.gridx = col;
                gbc.gridy = row;
                add(button, gbc);

                if (tile.getIsEmpty()) {
                    emptyTile = tile;
                }
            }
            tileGrid.add(rowTiles);
        }
    }

    private JButton makeTileButton(Tile tile) {

        JButton button = new JButton();
        BufferedImage img = tile.getImage();

        if (img != null) {
            button.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
            button.setIcon(new ImageIcon(img));
        } else {
            button.setPreferredSize(new Dimension(100, 100));
            button.setBackground(Color.LIGHT_GRAY);
        }

        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);

        button.addActionListener(e -> {
            if (movingTile == null && tile.validMove(emptyTile)) {
                movingTile = tile;
                targetEmptyX = emptyTile.getPosX();
                targetEmptyY = emptyTile.getPosY();
                animationStep = 0;
                animationTimer.start();
                moveCount++;
                moveCounterLabel.setText("Moves: " + moveCount);
                // Note: refreshBoard() is called after the animation completes in the Timer's ActionListener
            }
        });

        return button;
    }

    // Removed swapTiles method as the swapping logic is now in the animation timer

    private void refreshBoard() {
        removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Tile tile = tileGrid.get(row).get(col);
                JButton button = createAnimatedTileButton(tile); // Use a different method for drawing
                gbc.gridx = col;
                gbc.gridy = row;
                add(button, gbc);
            }
        }
        revalidate();
        repaint();
    }

    // Instead of JButton, we directly draw the tiles for animation
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (ArrayList<Tile> row : tileGrid) {
            for (Tile tile : row) {
                BufferedImage img = tile.getImage();
                if (img != null) {
                    int x = (int) (tile.getVisualX() * img.getWidth());
                    int y = (int) (tile.getVisualY() * img.getHeight());
                    g2d.drawImage(img, x, y, null);
                } else {
                    int x = (int) (tile.getVisualX() * 100); // Assuming default size
                    int y = (int) (tile.getVisualY() * 100);
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fillRect(x, y, 100, 100);
                }
            }
        }
    }

    private JButton createAnimatedTileButton(Tile tile) {
        JButton button = new JButton();
        BufferedImage img = tile.getImage();
        int width = (img != null) ? img.getWidth() : 100;
        int height = (img != null) ? img.getHeight() : 100;
        button.setPreferredSize(new Dimension(width, height));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(e -> {
            if (movingTile == null && tile.validMove(emptyTile)) {
                movingTile = tile;
                targetEmptyX = emptyTile.getPosX();
                targetEmptyY = emptyTile.getPosY();
                animationStep = 0;
                animationTimer.start();
                moveCount++;
                moveCounterLabel.setText("Moves: " + moveCount);
            }
        });
        return button;
    }


    private boolean checkWin() {
        for (ArrayList<Tile> row : tileGrid) {
            for (Tile tile : row) {
                if (!tile.getIsEmpty() && !tile.correct()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void show(ArrayList<ArrayList<BufferedImage>> imageGrid) {
        JFrame frame = new JFrame("Sliding Puzzle");
        Board board = new Board(imageGrid);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(board, BorderLayout.CENTER);

        settings.setFocusable(false);
        settings.setFont(new Font("Dialog", Font.BOLD, 15));
        settings.setPreferredSize(new Dimension(200, 75));
        settings.setBackground(new Color(0, 115, 150));

        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsMenu.initialize(frame);
            }
        });

        save.setFocusable(false);
        save.setFont(new Font("Dialog", Font.BOLD, 15));
        save.setPreferredSize(new Dimension(200, 75));
        save.setBackground(new Color(0, 115, 150));

        JButton reset = new JButton("Reset");
        reset.setFocusable(false);
        reset.setFont(new Font("Dialog", Font.BOLD, 15));
        reset.setPreferredSize(new Dimension(200, 75));
        reset.setBackground(new Color(0, 115, 150));

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    board.moveCount = 0;
                    board.moveCounterLabel.setText("Moves: 0");
                    frame.dispose();
                    Board.show(board.originalImageGrid);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        load.setFocusable(false);
        load.setFont(new Font("Dialog", Font.BOLD, 15));
        load.setPreferredSize(new Dimension(200, 75));
        load.setBackground(new Color(0, 115, 150));

        newGame.setFocusable(false);
        newGame.setFont(new Font("Dialog", Font.BOLD, 15));
        newGame.setPreferredSize(new Dimension(200, 75));
        newGame.setBackground(new Color(0, 115, 150));

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageSelector.getInstance().selectGameImage();
                } catch (Exception er) {
                    System.err.println("Error: " + er.getMessage());
                    er.printStackTrace();
                }
                frame.setVisible(false);
            }
        });

        Box bottomBox = Box.createVerticalBox();
        bottomBox.add(Box.createVerticalGlue());
        bottomBox.add(settings);
        bottomBox.add(save);
        bottomBox.add(load);
        bottomBox.add(newGame);
        bottomBox.add(reset);
        bottomBox.add(Box.createVerticalGlue());

        frame.add(bottomBox, BorderLayout.EAST);

        board.moveCounterLabel = new JLabel("Moves: 0");
        board.moveCounterLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        board.moveCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(board.moveCounterLabel, BorderLayout.NORTH);

        frame.pack();
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

*/
