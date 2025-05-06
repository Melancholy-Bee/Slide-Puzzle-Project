package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Board extends JPanel {
    private ArrayList<ArrayList<Tile>> tileGrid;
    private int gridSize;
    private Tile emptyTile;
    private ArrayList<ArrayList<BufferedImage>> originalImageGrid;
    private long moveCount = 0;
    private JLabel moveCounterLabel;

    //additions for database (maura)
    private JPanel boardPanel;
    private static int screenWidth, screenHeight;
    private static final DatabaseManager db = new DatabaseManager();
    
    BufferedImage animatingImg;
    Timer animatingTimer;
    int xVel, yVel, imgX, imgY, endX, endY, startX, startY;
    boolean animating;


    private static JButton settings = new JButton("Settings");
    private static JButton reset = new JButton("Reset");
    private static JButton newGame = new JButton("New Game");
    private static JButton load = new JButton("Load");
    private static JButton save = new JButton("Save");

    private static final String SAVE_PATH = "game_state.json";

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
        if(checkWin()){
            java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(Board.this);
            if (window != null) {
                window.dispose();
            }
            Menu.imageHandling(Menu.size);
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
            }
        });
        tile.setButton(button);
        return button;
    }

    private void swapTiles(Tile clicked, Tile empty) {
        animateTile(clicked);   //actual swap is called once animation is finished
    }
    private void finishSwap(Tile clicked, Tile empty){
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

    //addition for database (maura)
    public void handleTileClick(Tile tile) {
        if (isValidMove(tile)) {
          moveTile(tile);
          moveCount++;
          refreshBoard();
        }
    }

    public void updateBoardDisplay() {
        if (boardPanel == null) {
            System.err.println("boardPanel is null — skipping update.");
            return;
          }
        boardPanel.removeAll();

        for (int row = 0; row < gridSize; row++) {
          for (int col = 0; col < gridSize; col++) {
            boardPanel.add(tileGrid.get(row).get(col).getButton());
          }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
      }

      //end

    private void animateTile(Tile clicked){
        if (animatingTimer != null && animatingTimer.isRunning()) {
            animatingTimer.stop();
        }
        animatingImg = clicked.getImage();
        animatingTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                //change image position and repaint
                if(animating){
                    imgX += xVel;
                    imgY += yVel;
                    if(imgX == endX && imgY == endY){
                        //animation finished and tiles swapped
                        animating = false;
                        animatingTimer.stop();
                        finishSwap(clicked, emptyTile);
                    }
                }
                repaint();
            }
        });
        imgX = clicked.getButton().getX();
        imgY = clicked.getButton().getY();
        startX = clicked.getButton().getX();
        startY = clicked.getButton().getY();
        //change in x and y
        int dx = Math.abs(imgX - emptyTile.getButton().getX());
        int dy = Math.abs(imgY - emptyTile.getButton().getY());

        if(dx > dy){
            xVel = 5;
            yVel = 0;
            endX = imgX + clicked.getImage().getWidth();
            if(imgX > emptyTile.getButton().getX()){
                xVel *= -1;
                endX = imgX - clicked.getImage().getWidth();
            }
            endY = imgY;
        }
        else{
            xVel = 0;
            yVel = 5;
            endY = imgY + clicked.getImage().getHeight();
            if(imgY > emptyTile.getButton().getY()){
                yVel *= -1;
                endY = imgY - clicked.getImage().getHeight();
            }
            endX = imgX;
        }
        animating = true;
        animatingTimer.restart();
    }

    public void paint(Graphics g){
        super.paint(g);
        if(animating){
            Graphics graphic2D = (Graphics2D) g;
            //draw over clicked tile
            g.setColor(new Color(169,221,214));
            g.fillRect(startX, startY, animatingImg.getWidth(), animatingImg.getHeight());

            graphic2D.drawImage(animatingImg, imgX, imgY, null);
        }
    }

    private void refreshBoard() {
        // Clean up old buttons (remove listeners to avoid memory leaks)
        for (ArrayList<Tile> row : tileGrid) {
            for (Tile tile : row) {
                JButton btn = tile.getButton();
                for (ActionListener al : btn.getActionListeners()) {
                    btn.removeActionListener(al);
                }
            }
        }

        // Now remove all components
        removeAll();

        // Re-add the tiles to the panel with the updated positions
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Tile tile = tileGrid.get(row).get(col);
                JButton button = makeTileButton(tile);
                gbc.gridx = col;
                gbc.gridy = row;
                add(button, gbc);
            }
        }

        revalidate();
        repaint();

        if (checkWin()) {
            WinMenu.initialize(moveCount);
            java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(Board.this);
            if (window != null) {
                window.dispose();
            }
        }
    }

    //board serialization (maura)
    public String serializeBoard() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"gridSize\":").append(gridSize).append(",");
        sb.append("\"moveCount\":").append(moveCount).append(",");
        sb.append("\"emptyX\":").append(emptyTile.getPosX()).append(",");
        sb.append("\"emptyY\":").append(emptyTile.getPosY()).append(",");
        sb.append("\"tiles\":[");
        
        for (int row = 0; row < gridSize; row++) {
          for (int col = 0; col < gridSize; col++) {
            Tile tile = tileGrid.get(row).get(col);
            sb.append("{")
              .append("\"currentX\":").append(row).append(",")  // Position in grid
              .append("\"currentY\":").append(col).append(",")  // Position in grid
              .append("\"goalX\":").append(tile.getGoalX()).append(",")
              .append("\"goalY\":").append(tile.getGoalY()).append(",")
              .append("\"tileId\":").append(row * gridSize + col)
              .append("},");
          }
        }
      
        if (sb.charAt(sb.length() - 1) == ',') sb.deleteCharAt(sb.length() - 1);
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }

     //board deserialize (maura)
    public void deserializeBoard(String data) {
        data = data.trim();
        data = data.substring(1, data.length() - 1);

        String[] parts = data.split("\"tiles\":\\[", 2);
        String header = parts[0];
        String tilesPart = parts[1].substring(0, parts[1].length() - 1);

        // Parse header fields
        String[] fields = header.split(",");
        gridSize = Integer.parseInt(fields[0].split(":")[1].trim());
        moveCount = Long.parseLong(fields[1].split(":")[1].trim());
        int emptyX = Integer.parseInt(fields[2].split(":")[1].trim());
        int emptyY = Integer.parseInt(fields[3].split(":")[1].trim());

        // Initialize grid with nulls
        tileGrid = new ArrayList<>();
        for (int i = 0; i < gridSize; i++) {
            ArrayList<Tile> row = new ArrayList<>(Collections.nCopies(gridSize, null));
            tileGrid.add(row);
    }

        // Split the tiles data and loop through each entry to deserialize
        String[] entries = tilesPart.split("\\},\\{");
        ArrayList<Tile> tiles = new ArrayList<>();

        for (String entry : entries) {
            entry = entry.replace("{", "").replace("}", "");
            String[] kv = entry.split(",");

            int currentX = Integer.parseInt(kv[0].split(":")[1].trim());
            int currentY = Integer.parseInt(kv[1].split(":")[1].trim());
            int goalX = Integer.parseInt(kv[2].split(":")[1].trim());
            int goalY = Integer.parseInt(kv[3].split(":")[1].trim());
            int tileId = Integer.parseInt(kv[4].split(":")[1].trim());

            // Use goal position for the image

            
            BufferedImage img = ImageProcessor.getOriginalImageGrid().get(goalY).get(goalX);
            Tile tile = new Tile(img, goalX, goalY);
            tile.setPosX(currentX);
            tile.setPosY(currentY);

            // Add the tile to a list for reference
            tiles.add(tile);

            // If this tile is the empty space, assign it
            if (currentX == emptyX && currentY == emptyY) {
                emptyTile = tile;
            }
        }

        // Now, place each tile in the grid according to its calculated position
        for (Tile tile : tiles) {
            int posX = tile.getPosX();
            int posY = tile.getPosY();
            tileGrid.get(posY).set(posX, tile);
        }

        // Rebuild the visual board with buttons
        removeAll();  // Remove old buttons from the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.NONE;

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Tile tile = tileGrid.get(row).get(col);
                JButton button = makeTileButton(tile);
                GridBagConstraints constraints = new GridBagConstraints();
                constraints.gridx = col;
                constraints.gridy = row;
                constraints.insets = new Insets(0, 0, 0, 0);
                add(button, constraints);
            }
        }
        revalidate();
        repaint();

        // After all tiles are placed, make sure the empty tile is set correctly
        // (emptyTile already has the correct position and image set to null).
        // We can also clear the image of the empty tile if necessary:
        if (emptyTile != null) {
            JButton emptyButton = new JButton();
            emptyButton.setPreferredSize(new Dimension(100, 100)); // or match tile size
            emptyButton.setBackground(Color.LIGHT_GRAY);
            emptyButton.setBorderPainted(false);
            emptyButton.setFocusPainted(false);
            emptyButton.setContentAreaFilled(false);
            emptyTile.setButton(emptyButton);
        }


        // Refresh the board display
        refreshBoard();

        // Rebuild the JButton grid for display and interaction
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Tile tile = tileGrid.get(row).get(col);
                JButton button;

                if (tile == emptyTile || tile.getImage() == null) {
                    button = new JButton();  // Empty or missing image
                    button.setBackground(Color.WHITE);  // Optional styling
                } else {
                    button = new JButton(new ImageIcon(tile.getImage()));
                }
                

            // Replace with your actual click handler
                button.addActionListener(e -> handleTileClick(tile));

                tile.setButton(button);
            }
        }

        boardPanel = new JPanel(new GridLayout(gridSize, gridSize));
        frame.add(boardPanel, BorderLayout.CENTER);

        // Update the display of the board
        updateBoardDisplay();
    }

    //write serialized board (maura)
    public void saveToFile(String path) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(path))) {
            w.write(serializeBoard());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //read board state and deserialize (maura)
    public void loadFromFile(String path) {
        try {
            String content = Files.readString(Paths.get(path));
            deserializeBoard(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
        JFrame frame = new JFrame("Picture This!");
        Board board = new Board(imageGrid);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
        frame.add(board, BorderLayout.CENTER); // Add board to center
    
        // Settings button
        settings.setFocusable(false);
        settings.setFont(new Font("Dialog", Font.BOLD, 15));
        settings.setPreferredSize(new Dimension(200, 75));
        settings.setMaximumSize(new Dimension(250,80));
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
        save.setMaximumSize(new Dimension(250,80));
        save.setBackground(new Color(0, 115, 150));
    
        //save button to database maura
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.db.saveGame("MySave", board);
                JOptionPane.showMessageDialog(frame, "Game saved.");
            }
        
            private void saveBoardState() {
                // Serialize the board state to a JSON-like string
                String serializedBoard = serializeBoard();
        
                // Save the serialized board state to a file (game_state.json)
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("game_state.json"))) {
                    writer.write(serializedBoard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        
        //reset button rebecca
        JButton reset = new JButton("Reset");
        reset.setFocusable(false);
        reset.setFont(new Font("Dialog", Font.BOLD, 15));
        reset.setPreferredSize(new Dimension(200, 75));
        reset.setMaximumSize(new Dimension(250,80));
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
        load.setMaximumSize(new Dimension(250,80));
        load.setBackground(new Color(0, 115, 150));
    
        //load from database (maura)
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                db.loadGame(board);
                JOptionPane.showMessageDialog(frame, "Game loaded successfully.");
              }
        });

        //new game copied from main and edited 
        newGame.setFocusable(false);
        newGame.setFont(new Font("Dialog", Font.BOLD, 15));
        newGame.setPreferredSize(new Dimension(200, 75));
        newGame.setMaximumSize(new Dimension(250,80));
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
        frame.setSize(Menu.screenWidth, Menu.screenHeight);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

}