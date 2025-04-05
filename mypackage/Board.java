package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Board extends JPanel {
    private ArrayList<ArrayList<Tile>> tileGrid;
    private int gridSize;
    private Tile emptyTile;

    public Board(ArrayList<ArrayList<BufferedImage>> imageGrid) {
        this.gridSize = imageGrid.size();
        this.tileGrid = new ArrayList<>();
        setLayout(new GridBagLayout());
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
                refreshBoard();
                if (checkWin()) {
                    JOptionPane.showMessageDialog(this, "Puzzle Solved!");
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

    // Add this static method to make the board display itself in a JFrame
    public static void show(ArrayList<ArrayList<BufferedImage>> imageGrid) {
        JFrame frame = new JFrame("Sliding Puzzle");
        Board board = new Board(imageGrid);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(board);
        frame.pack();

        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}