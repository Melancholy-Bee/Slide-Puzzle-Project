package mypackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Written by Landon
public class ImageSelector {
    private static ImageSelector imageSelectorInstance = null;
    boolean windowOpen;
    JFrame frame;
    JPanel panel;
    File selectedFile;
    private ImageSelector(){
        frame = new JFrame("Default Image Selector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
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
    private void addImages(ArrayList<ImageButton> imageButtons){
            for (int i = 0; i < imageButtons.size(); i++) {
                panel.add(imageButtons.get(i));
            }
    }

    //refreshes images and buttons on the selection window
    private void refreshButtons(ArrayList<ImageButton> imageButtons){
        panel.removeAll();
        frame.getContentPane().removeAll();
        frame.getContentPane().repaint();
        addImages(imageButtons);
        JButton upload = new JButton("Upload");
        upload.setPreferredSize(new Dimension(300,300));
        panel.add(upload);

        upload.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImageService.saveImage(ImageService.fileFinder());
                    ImageSelector.getInstance().selectGameImage();
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
