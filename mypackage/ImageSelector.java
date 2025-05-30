package mypackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;

//Written by Landon
public class ImageSelector {
    private static ImageSelector imageSelectorInstance = null;
    boolean windowOpen;
    JFrame frame;
    JPanel panel;
    File selectedFile;
    private ImageSelector(){
        frame = new JFrame("Picture This!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Get screen dimensions
        frame.setSize(Menu.screenWidth, Menu.screenHeight);
        frame.setLocationRelativeTo(null);
        windowOpen = true;
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3, 5, 5));
        panel.setBackground(new Color(169,221,214));
        selectedFile = null;
    }

    // Singleton implementation of the imageSelector
    public static synchronized ImageSelector getInstance()
    {
        if (imageSelectorInstance == null)
            imageSelectorInstance = new ImageSelector();
 
        return imageSelectorInstance;
    }

    //creates the image selection window
    public void selectGameImage(){
        
        ArrayList<ImageButton> imageButtons = getImageButtons();
        refreshButtons(imageButtons);
        System.out.println("Image Buttons Made");
    }

    //gets a list of the image files
    public ArrayList<File> getFileList(String path){
        File folder = new File(path);
        File[] arrayOfFiles = folder.listFiles();
        ArrayList<File> listOfFiles = new ArrayList<File>();

        for(int i = 0; i < arrayOfFiles.length; i++){
            if(arrayOfFiles[i].isFile()){
                listOfFiles.add(arrayOfFiles[i]);
            }
        }
        return listOfFiles;
    }
    
    //creates a list of buttons with images
    private ArrayList<ImageButton> getImageButtons(){
        File folder = new File("Images");
        ArrayList<File> listOfFiles = getFileList(folder.getAbsolutePath());
        ArrayList<ImageButton> tempButtonList = new ArrayList<ImageButton>();

        for(int i = 0; i < listOfFiles.size(); i++){
            tempButtonList.add(new ImageButton(listOfFiles.get(i)));
        }

        return tempButtonList;
    }

    //adds imageButtons to the panel
    private void addImages(ArrayList<ImageButton> imageButtons, GridBagConstraints gbc){
            for (int i = 0; i < imageButtons.size(); i++) {
                imageButtons.get(i).setFocusable(false);
                imageButtons.get(i).setBackground(new Color(0,115,150));
                panel.add(imageButtons.get(i), gbc);
                
                //constrain the images to 2 columns
                gbc.gridx = (gbc.gridx+1)%2;
                if(gbc.gridx == 0){
                    gbc.gridy++;
                }
            }
    }

    //refreshes images and buttons on the selection window
    private void refreshButtons(ArrayList<ImageButton> imageButtons){
        panel.removeAll();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 0, 30, 0);
        gbc.fill = GridBagConstraints.NONE;
        frame.getContentPane().removeAll();
        frame.getContentPane().repaint();

        //add upload button
        JButton upload = new JButton("Upload");
        upload.setFocusable(false);
        upload.setPreferredSize(new Dimension(200,75));
        upload.setFont(new Font("Dialog", Font.BOLD, 15));
        upload.setBackground(new Color(0,115,150));
        gbc.gridy = 0;
        panel.add(upload, gbc);

        //add image buttons
        gbc.gridx = 1;
        gbc.gridy = 0;
        addImages(imageButtons, gbc);

        //set upload button action
        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File temp = ImageService.fileFinder();
                    if(temp != null){
                        ImageService.saveImage(temp);
                        ImageSelector.getInstance().selectGameImage();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        //Make the panel scrollable vertically
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); //removes the horizontal scroll bar

        frame.add(scrollPane);
        frame.setVisible(true);
    }

}
