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
    
    public static void initialize(){
        frame.setTitle("Picture This");
        frame.setSize(1000,1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel, BorderLayout.CENTER);
        panel.setBackground(new Color(123,167,237));
        
        // Load Button Rebecca
        load.setBounds(100,50,50,50);
        load.setSize(150,150);
        panel.add(load);
        // new game button Emma
        newGameButton.setFocusable((false));
        newGameButton.setFont(new Font("Dialog", Font.PLAIN, 15));
        newGameButton.setPreferredSize(new Dimension(200,75));
        panel.add(newGameButton);

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageProcessor.processAndDisplayImage(); // Calls your image processing code
            }
        });
    }
}
