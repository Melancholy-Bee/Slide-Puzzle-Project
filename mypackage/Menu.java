package mypackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Menu {
    static JFrame frame = new JFrame();
    static JPanel panel = new JPanel();
    static JButton newGameButton = new JButton("Create New Game");
    static JButton load = new JButton ("Load Current Game");
    static JButton settings = new JButton("Settings");
    static JButton quit = new JButton("Quit");

    public static void initialize(){
        frame.setTitle("Picture This");
        frame.setSize(1000,1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel, BorderLayout.CENTER);
        panel.setBackground(new Color(169,221,214));
        
        // Load Button Rebecca
        load.setFocusable(false);
        load.setPreferredSize(new Dimension(200,75));
        load.setFont(new Font("Dialog", Font.PLAIN, 15));
        load.setBackground(new Color(0,115,150));
        panel.add(load);


        // new game button Emma
        newGameButton.setFocusable((false));
        newGameButton.setFont(new Font("Dialog", Font.PLAIN, 15));
        newGameButton.setPreferredSize(new Dimension(200,75));
        newGameButton.setBackground(new Color(0,115,150));
        panel.add(newGameButton);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Use ImageService to let the user pick an image
                    File selectedFile = ImageService.fileFinder();
                    if (selectedFile == null) {
                        System.out.println("No image selected. Exiting...");
                        return;
                    }

                    ImageService.saveImage(selectedFile); //Saves Image
                
                    System.out.println("Image selected: " + selectedFile.getAbsolutePath());
                
                    // Process the image (chop into pieces)
                    ArrayList<ArrayList<BufferedImage>> choppedImages = ImageProcessor.processImage(selectedFile);
                    if (choppedImages == null) {
                        System.out.println("Image processing failed.");
                        return;
                    }
                
                    // Display the chopped images
                    ImageProcessor.displayChoppedImages(choppedImages);
                } catch (Exception er) {
                    System.err.println("Error: " + er.getMessage());
                    er.printStackTrace(); // Optional: useful for debugging
                }
            }
        });

        //settings button Sylas
        settings.setFocusable(false);
        settings.setFont(new Font("Dialog", Font.PLAIN, 15));
        settings.setPreferredSize(new Dimension(200, 75));
        settings.setBackground(new Color(0, 115, 150));
        panel.add(settings);

        //quit button sylas
        quit.setFocusable(false);
        quit.setFont(new Font("Dialog", Font.PLAIN, 15));
        quit.setPreferredSize(new Dimension(200, 75));
        quit.setBackground(new Color(0, 115, 150));
        panel.add(quit);

        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Closes the program
            }
        });

    }
}
