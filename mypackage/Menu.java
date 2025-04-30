package mypackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Observer;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Menu {
    private static JFrame frame = new JFrame();
    private static JPanel panel = new JPanel();
    private static JButton newGameButton = new JButton("Create New Game");
    private static JButton load = new JButton ("Load Current Game");
    private static JButton settings = new JButton("Settings");
    private static JButton quit = new JButton("Quit");
    private static BufferedImage winBufferedImage;
    public static int size;

    public static void initialize(){
        //initialize the frame and place panel on it
        frame.setTitle("Picture This");
        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.9); // 90% of screen width
        int height = (int) (screenSize.height * 0.9); // 90% of screen height
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel, BorderLayout.CENTER);
        panel.setBackground(new Color(169,221,214));
        panel.setLayout(new GridBagLayout());
        // creates a layout on the panel for the buttons. It is anchored to the center with 30px spaces between the buttons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 30, 0);
        gbc.fill = GridBagConstraints.NONE;
        
        // Load Button Rebecca
        load.setFocusable(false);
        load.setPreferredSize(new Dimension(200,75));
        load.setFont(new Font("Dialog", Font.BOLD, 15));
        load.setBackground(new Color(0,115,150));
        gbc.gridy = 1;
        panel.add(load, gbc);

        // new game button Emma
        newGameButton.setFocusable((false));
        newGameButton.setFont(new Font("Dialog", Font.BOLD, 15));
        newGameButton.setPreferredSize(new Dimension(200,75));
        newGameButton.setBackground(new Color(0,115,150));
        gbc.gridy = 2;
        panel.add(newGameButton, gbc);

        newGameButton.addActionListener(new ActionListener() {
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

        //settings button Sylas
        settings.setFocusable((false));
        settings.setFont(new Font("Dialog", Font.BOLD, 15));
        settings.setPreferredSize(new Dimension(200,75));
        settings.setBackground(new Color(0,115,150));
        gbc.gridy = 3;
        panel.add(settings, gbc);

        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SettingsMenu.initialize(frame);
            }
        });

        //quit button sylas 
        quit.setFocusable((false));
        quit.setFont(new Font("Dialog", Font.BOLD, 15));
        quit.setPreferredSize(new Dimension(200,75));
        quit.setBackground(new Color(0,115,150));
        gbc.gridy = 4;
        panel.add(quit, gbc);

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Closes the program
            }
        });
        frame.setVisible(true);
    }

    public static void imageHandling(int n){
        size = n;
        File selectedFile = ImageSelector.getInstance().selectedFile;

        while(selectedFile == null) {
            selectedFile = ImageSelector.getInstance().selectedFile;
            ImageSelector.getInstance().selectGameImage();
            System.out.println("Looping");
        }

        
        try {
            ImageService.saveImage(selectedFile);
            winBufferedImage = null;
            winBufferedImage = ImageIO.read(selectedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        System.out.println("Image selected: " + selectedFile.getAbsolutePath());
    
        // Process the image (chop into pieces)
        ArrayList<ArrayList<BufferedImage>> choppedImages = ImageProcessor.processImage(selectedFile, n);
        if (choppedImages == null) {
            System.out.println("Image processing failed.");
            return;
        }

        //Make a Board
        Board.show(choppedImages);
    

    }

    public static BufferedImage getWinImage() {
        return winBufferedImage;
    }
}