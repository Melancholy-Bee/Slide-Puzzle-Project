package mypackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class WinMenu {
    private static JFrame frame = new JFrame();
    private static JPanel panel = new JPanel();
    private static JButton restart = new JButton("Restart");
    private static JButton newGameButton = new JButton ("New Game");
    private static JButton quit = new JButton("Quit");

    public static void initialize(){
        //initialize the frame and place panel on it
        frame.setTitle("Picture This");
        frame.setSize(500,500);
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


        // new game button Emma coppied from main menu
        newGameButton.setFocusable((false));
        newGameButton.setFont(new Font("Dialog", Font.BOLD, 15));
        newGameButton.setPreferredSize(new Dimension(200,75));
        newGameButton.setBackground(new Color(0,115,150));
        gbc.gridy = 1;
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

        // Restart button temporarily closing game - Sylas
        restart.setFocusable((false));
        restart.setFont(new Font("Dialog", Font.BOLD, 15));
        restart.setPreferredSize(new Dimension(200,75));
        restart.setBackground(new Color(0,115,150));
        gbc.gridy = 2;
        panel.add(restart, gbc);

        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Closes the program
            }
        });


         //quit button sylas coppied from main menu
         quit.setFocusable((false));
         quit.setFont(new Font("Dialog", Font.BOLD, 15));
         quit.setPreferredSize(new Dimension(200,75));
         quit.setBackground(new Color(0,115,150));
         gbc.gridy = 3;
         panel.add(quit, gbc);
 
         quit.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 System.exit(0); // Closes the program
             }
         });
         frame.setVisible(true);
    }
}