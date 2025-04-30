package mypackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.image.BufferedImage;
/* 
public class WinMenu {
    private static JFrame frame = new JFrame();
    private static JPanel panel = new JPanel();
    private static JButton restart = new JButton("Restart");
    private static JButton newGameButton = new JButton ("New Game");
    private static JButton quit = new JButton("Quit");
    private static BufferedImage winImage;

    public static void initialize(){
        //initialize the frame and place panel on it
        winImage = Menu.getWinImage();
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
                    System.out.println("Image Selector called");
                    ImageSelector.getInstance().selectGameImage();

                } catch (Exception er) {
                    System.err.println("Error: " + er.getMessage());
                    er.printStackTrace(); // Optional: useful for debugging
                }
                frame.dispose();
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

*/

public class WinMenu {
    private static JFrame frame = new JFrame();
    private static JPanel mainPanel = new JPanel(new BorderLayout());
    private static JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    private static BufferedImage winImage;
    private static JLabel winLabel; //= new JLabel("You Win!", SwingConstants.CENTER); // Create the label
    private static Font winFont = new Font("Arial", Font.BOLD, 36); // Style the label


    private static JButton restart = new JButton("Restart");
    private static JButton newGameButton = new JButton("New Game");
    private static JButton MainMenu = new JButton("Main Menu");
    private static JButton quit = new JButton("Quit");

    public static void removeAllDifficultyListeners() {
        for (ActionListener al : newGameButton.getActionListeners()) {
            newGameButton.removeActionListener(al);
        }
        for (ActionListener al : restart.getActionListeners()) {
            restart.removeActionListener(al);
        }
        for (ActionListener al : MainMenu.getActionListeners()) {
            MainMenu.removeActionListener(al);
        }
        for (ActionListener al : quit.getActionListeners()) {
            quit.removeActionListener(al);
        }
    }

    public static void initialize(long moveCount) {
        if (winLabel != null && winLabel.getParent() != null) {
            mainPanel.remove(winLabel);
        }
        winLabel = new JLabel("You Win!" + " It took " + String.valueOf(moveCount) + " moves!", SwingConstants.CENTER); // Create the label
        winImage = Menu.getWinImage();
        winImage = ImageProcessor.resizeImage(winImage, 500, 500);

        frame.setTitle("Picture This!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setBackground(new Color(169, 221, 214));

        // Win Label
        winLabel.setFont(winFont);
        mainPanel.add(winLabel, BorderLayout.NORTH); // Add label to the top

        // Image Panel
        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (winImage != null) {
                    int x = (getWidth() - winImage.getWidth()) / 2;
                    int y = 20;
                    g.drawImage(winImage, x, y, this);
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return (winImage != null) ? new Dimension(winImage.getWidth(), winImage.getHeight() + 20) : new Dimension(0, 0);
            }
        };
        imagePanel.setBackground(new Color(169, 221, 214));
        mainPanel.add(imagePanel, BorderLayout.CENTER);

        // Button Panel
        buttonPanel.setBackground(new Color(169, 221, 214));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Button Styling
        Font buttonFont = new Font("Dialog", Font.BOLD, 15);
        Dimension buttonSize = new Dimension(150, 60);

        ActionListener newGameListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("Image Selector called");
                    ImageSelector.getInstance().selectGameImage();
                } catch (Exception er) {
                    System.err.println("Error: " + er.getMessage());
                    er.printStackTrace();
                }
                removeAllDifficultyListeners();
                frame.dispose();
            }
        };
        newGameButton.setFocusable(false);
        newGameButton.setFont(buttonFont);
        newGameButton.setPreferredSize(buttonSize);
        newGameButton.setBackground(new Color(0, 115, 150));
        buttonPanel.add(newGameButton);
        newGameButton.addActionListener(newGameListener);

        ActionListener restartListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Menu.imageHandling(2);
                } catch (Exception er) {
                    System.err.println("Error: " + er.getMessage());
                    er.printStackTrace();
                }
                removeAllDifficultyListeners();
                frame.dispose();
            }
        };
        restart.setFocusable(false);
        restart.setFont(buttonFont);
        restart.setPreferredSize(buttonSize);
        restart.setBackground(new Color(0, 115, 150));
        buttonPanel.add(restart);
        restart.addActionListener(restartListener);

        ActionListener MainMenuListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Menu.initialize();
                } catch (Exception er) {
                    System.err.println("Error: " + er.getMessage());
                    er.printStackTrace();
                }
                frame.dispose();
                removeAllDifficultyListeners();
            }
        };
        MainMenu.setFocusable(false);
        MainMenu.setFont(buttonFont);
        MainMenu.setPreferredSize(buttonSize);
        MainMenu.setBackground(new Color(0, 115, 150));
        buttonPanel.add(MainMenu);
        MainMenu.addActionListener(MainMenuListener);

        ActionListener quitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.exit(0);
                } catch (Exception er) {
                    System.err.println("Error: " + er.getMessage());
                    er.printStackTrace();
                }
            }
        };
        quit.setFocusable(false);
        quit.setFont(buttonFont);
        quit.setPreferredSize(buttonSize);
        quit.setBackground(new Color(0, 115, 150));
        buttonPanel.add(quit);
        quit.addActionListener(quitListener);

        /* 

        newGameButton.addActionListener(e -> {
            try {
                System.out.println("Image Selector called");
                ImageSelector.getInstance().selectGameImage();
            } catch (Exception er) {
                System.err.println("Error: " + er.getMessage());
                er.printStackTrace();
            }
            frame.dispose();
        });

        restart.addActionListener(e -> {
            try{
                Menu.imageHandling(2);
            } catch (Exception er) { 
                System.err.println("Error: " + er.getMessage());
                er.printStackTrace();
            }
            frame.dispose();
        });

        MainMenu.addActionListener(e -> {
            try {
                Menu.initialize();
            } catch (Exception er) {
                System.err.println("Error: " + er.getMessage());
                er.printStackTrace();
            }
            frame.dispose();
        });

        quit.addActionListener(e -> {
            System.exit(0);
        });

        */

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * 0.9); // 90% of screen width
        int height = (int) (screenSize.height * 0.9); // 90% of screen height
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }
}