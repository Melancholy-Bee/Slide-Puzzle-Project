package mypackage;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsMenu {
    private static JFrame settingsFrame = new JFrame("Settings");
    private static JPanel panel = new JPanel();
    private static JButton defaultSizeButton = new JButton("Default Size");
    private static JButton fitScreenButton = new JButton("Fit to Screen");
    private static JButton returnButton = new JButton("Return to Menu");

    public static void initialize(JFrame mainFrame) {
        settingsFrame.setSize(500, 500);
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setLayout(new BorderLayout());

        panel.setBackground(new Color(169,221,214));
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.add(defaultSizeButton);
        panel.add(fitScreenButton);
        panel.add(returnButton);
        

        defaultSizeButton.setFocusable((false));
        defaultSizeButton.setFont(new Font("Dialog", Font.BOLD, 15));
        defaultSizeButton.setPreferredSize(new Dimension(200,75));
        defaultSizeButton.setBackground(new Color(0,115,150));
        defaultSizeButton.addActionListener(new ActionListener() { //Returns mainscreen to regular size
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setSize(Menu.screenWidth, Menu.screenHeight);
                mainFrame.setLocationRelativeTo(null);
            }
        });


        fitScreenButton.setFocusable((false));
        fitScreenButton.setFont(new Font("Dialog", Font.BOLD, 15));
        fitScreenButton.setPreferredSize(new Dimension(200,75));
        fitScreenButton.setBackground(new Color(0,115,150));
        fitScreenButton.addActionListener(new ActionListener() { //Makes main window the size of screen, does not yet center
            @Override
            public void actionPerformed(ActionEvent e) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                mainFrame.setSize(screenSize.width, screenSize.height);
                mainFrame.setLocation(0, 0);
            }
        });
        

        returnButton.setFocusable((false));
        returnButton.setFont(new Font("Dialog", Font.BOLD, 15));
        returnButton.setPreferredSize(new Dimension(200,75));
        returnButton.setBackground(new Color(0,115,150));
        returnButton.addActionListener(new ActionListener() { //Closes the settings menu
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.dispose(); 
            }
        });

        settingsFrame.add(panel, BorderLayout.CENTER);
        settingsFrame.setVisible(true);
    }
}
