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
// Rebecca 
public class DifficultyMenu {
    static JFrame frame = new JFrame();
    static JPanel panel = new JPanel();
    static JButton Easy = new JButton("Easy");//2x2
    static JButton Medium = new JButton("Medium");//3x3
    static JButton Hard = new JButton("Hard");//4x4
    static JButton Extreme = new JButton("Extreme");//5x5
    public static initialize(){
        frame.setTitle("Difficulty Menu");
        frame.setSize(1000,1000);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel, BorderLayout.CENTER);
        panel.setBackground(new Color(169,221,214));

        //each button should have the same functonality but send a different value to the ProccessImage function
        // easy button
        Easy.setFocusable((false));
        Easy.setFont(new Font("Dialog", Font.PLAIN, 15));
        Easy.setPreferredSize(new Dimension(200,75));
        Easy.setBackground(new Color(0,115,150));
        panel.add(Easy);
        //Easy.addActionListener(new ActionListener(){
          //  @Override
            //public void actionPerformed(ActionEvent e) {
                //send difficulty selection to ProccessImage
              //  int level = 1;
                //processImage(File image, int level);
            //}
        //});

        //medium button
        Medium.setFocusable((false));
        Medium.setFont(new Font("Dialog", Font.PLAIN, 15));
        Medium.setPreferredSize(new Dimension(200,75));
        Medium.setBackground(new Color(0,115,150));
        panel.add(Medium);
        //Medium.addActionListener(new ActionListener(){
          //  @Override
            //public void actionPerformed(ActionEvent e) {
                //send difficulty selection to ProccessImage
              //  int level = 2;
                //processImage(File image, int level);
            //}
        //}):


        //Hard Button
        Hard.setFocusable((false));
        Hard.setFont(new Font("Dialog", Font.PLAIN, 15));
        Hard.setPreferredSize(new Dimension(200,75));
        Hard.setBackground(new Color(0,115,150));
        panel.add(Hard);
        //Hard.addActionListener(new ActionListener(){
          //  @Override
            //public void actionPerformed(ActionEvent e) {
                //send difficulty selection to ProccessImage
              //  int level = 3;
                //processImage(File image, int level);
            //}
        //});


        //Extreme Button
        Extreme.setFocusable((false));
        Extreme.setFont(new Font("Dialog", Font.PLAIN, 15));
        Extreme.setPreferredSize(new Dimension(200,75));
        Extreme.setBackground(new Color(0,115,150));
        panel.add(Extreme);
        //Extreme.addActionListener(new ActionListener(){
          //  @Override
            //public void actionPerformed(ActionEvent e) {
                //send difficulty selection to ProccessImage
              //  int level = 4;
                //processImage(File image, int level);
            //}
        //});



    }

}