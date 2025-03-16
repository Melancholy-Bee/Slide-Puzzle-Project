package mypackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class Menu {
    static JFrame frame = new JFrame();
    static JPanel panel = new JPanel();
    static JButton newGameButton = new JButton("Create New Game");
    static JButton load = new JButton ("Load");
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
                ImageProcessor.processAndDisplayImage(); // Calls your image processing code
            }
        });

        //settings button Sylas
        settings.setFocusable(false);
        settings.setFont(new Font("Dialog", Font.PLAIN, 15));
        settings.setPreferredSize(new Dimension(200, 75));
        settings.setBackground(new Color(0, 115, 150));
        panel.add(settings);

        settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Closes the program
            }
        });

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
