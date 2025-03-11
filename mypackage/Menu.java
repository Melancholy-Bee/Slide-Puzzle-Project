package mypackage;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    static JFrame frame = new JFrame();
    static JButton newGameButton = new JButton("Create New Game");
    static JButton load = new JButton ("Load");
    
    public static void initialize(){
        // Load Button Rebecca
        
        load.setBounds(100,50,50,50);
        load.setSize(150,150);
        frame.add(load);

        newGameButton.setBounds(100, 50, 50, 50);
        newGameButton.setSize(150,150);
        frame.add(newGameButton);
        frame.setSize(1000,1000);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
