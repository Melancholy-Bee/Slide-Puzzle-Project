package mypackage;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsMenu {
    private static JFrame settingsFrame = new JFrame("Settings");
    private static JPanel panel = new JPanel();
    private static JButton emptyButton1 = new JButton("Music Toggle");
    private static JButton emptyButton2 = new JButton("Background Color");
    private static JButton defaultSizeButton = new JButton("Default Size");
    private static JButton fitScreenButton = new JButton("Fit to Screen");
    private static JButton returnButton = new JButton("Return to Main Menu");

    public static void initialize(JFrame mainFrame) {
        settingsFrame.setSize(500, 500);
        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setLayout(new BorderLayout());

        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.add(emptyButton1);
        panel.add(emptyButton2);
        panel.add(defaultSizeButton);
        panel.add(fitScreenButton);
        panel.add(returnButton);
        
        emptyButton1.addActionListener(new ActionListener() { //Closes the settings menu
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.dispose(); 
            }
        });
                //will add functionality if able

        emptyButton2.addActionListener(new ActionListener() { //Closes the settings menu
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame.dispose(); 
            }
        });

        defaultSizeButton.addActionListener(new ActionListener() { //Returns mainscreen to regular size
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setSize(1000, 1000);
            }
        });

        fitScreenButton.addActionListener(new ActionListener() { //Makes main window the size of screen, does not yet center
            @Override
            public void actionPerformed(ActionEvent e) {
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                mainFrame.setSize(screenSize.width, screenSize.height);
            }
        });
        
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
