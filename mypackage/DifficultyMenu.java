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
import java.awt.Window;
// Rebecca 
public class DifficultyMenu {
    static JFrame frame = new JFrame();
    static JPanel panel = new JPanel();
    static JButton Easy = new JButton("Easy");//2x2
    static JButton Medium = new JButton("Medium");//3x3
    static JButton Hard = new JButton("Hard");//4x4
    static JButton ExtraHard = new JButton("Extra Hard");//5x5
    static JButton Extreme = new JButton("Extreme");//6x6
    static JButton close = new JButton("x");// close button

    public static void removeAllDifficultyListeners() {
        for (ActionListener al : Easy.getActionListeners()) {
            Easy.removeActionListener(al);
        }
        for (ActionListener al : Medium.getActionListeners()) {
            Medium.removeActionListener(al);
        }
        for (ActionListener al : Hard.getActionListeners()) {
            Hard.removeActionListener(al);
        }
        for (ActionListener al : ExtraHard.getActionListeners()) {
            ExtraHard.removeActionListener(al);
        }
        for (ActionListener al : Extreme.getActionListeners()) {
            Extreme.removeActionListener(al);
        }
    }
    
    public static void initialize(File image){
        frame.setTitle("Picture This!");
        // Get screen dimensions
        frame.setSize(Menu.screenWidth, Menu.screenHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel, BorderLayout.CENTER);
        panel.setBackground(new Color(169,221,214));
        panel.setLayout(new GridBagLayout());
        // creates a layout on the panel for the buttons. It is anchored to the center with 30px spaces between the buttons
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 30, 0);
        gbc.fill = GridBagConstraints.NONE;

        close.setFocusable((false));
        close.setFont(new Font("Dialog",Font.PLAIN,10));
        close.setPreferredSize(new Dimension(100,50));
        close.setBackground(new Color(223,16,16));
        close.addActionListener(new ActionListener(){
             @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        //each button should have the same functonality but send a different value to the ProccessImage function
        // EASY button
        ActionListener easyListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int level = 2;
                Menu.imageHandling(level);
                System.out.println("Image Handling Called");
                removeAllDifficultyListeners();
                frame.dispose();
            }
        };
        Easy.setFocusable(false);
        Easy.setFont(new Font("Dialog", Font.PLAIN, 15));
        Easy.setPreferredSize(new Dimension(200, 75));
        Easy.setBackground(new Color(0, 115, 150));
        gbc.gridy = 1;
        panel.add(Easy, gbc);
        Easy.addActionListener(easyListener);

        // MEDIUM button
        ActionListener mediumListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int level = 3;
                Menu.imageHandling(level);
                System.out.println("Image Handling Called");
                removeAllDifficultyListeners();
                frame.dispose();
            }
        };
        Medium.setFocusable(false);
        Medium.setFont(new Font("Dialog", Font.PLAIN, 15));
        Medium.setPreferredSize(new Dimension(200, 75));
        Medium.setBackground(new Color(0, 115, 150));
        gbc.gridy = 2;
        panel.add(Medium, gbc);
        Medium.addActionListener(mediumListener);

        // HARD button
        ActionListener hardListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int level = 4;
                Menu.imageHandling(level);
                System.out.println("Image Handling Called");
                removeAllDifficultyListeners();
                frame.dispose();
            }
        };
        Hard.setFocusable(false);
        Hard.setFont(new Font("Dialog", Font.PLAIN, 15));
        Hard.setPreferredSize(new Dimension(200, 75));
        Hard.setBackground(new Color(0, 115, 150));
        gbc.gridy = 3;
        panel.add(Hard, gbc);
        Hard.addActionListener(hardListener);

        // EXTRA HARD button
        ActionListener extraHardListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int level = 5;
                Menu.imageHandling(level);
                System.out.println("Image Handling Called");
                removeAllDifficultyListeners();
                frame.dispose();
            }
        };
        ExtraHard.setFocusable(false);
        ExtraHard.setFont(new Font("Dialog", Font.PLAIN, 15));
        ExtraHard.setPreferredSize(new Dimension(200, 75));
        ExtraHard.setBackground(new Color(0, 115, 150));
        gbc.gridy = 4;
        panel.add(ExtraHard, gbc);
        ExtraHard.addActionListener(extraHardListener);

        // EXTREME button
        ActionListener extremeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int level = 6;
                Menu.imageHandling(level);
                System.out.println("Image Handling Called");
                removeAllDifficultyListeners();
                frame.dispose();
            }
        };
        Extreme.setFocusable(false);
        Extreme.setFont(new Font("Dialog", Font.PLAIN, 15));
        Extreme.setPreferredSize(new Dimension(200, 75));
        Extreme.setBackground(new Color(0, 115, 150));
        gbc.gridy = 5;
        panel.add(Extreme, gbc);
        Extreme.addActionListener(extremeListener);


    }

}