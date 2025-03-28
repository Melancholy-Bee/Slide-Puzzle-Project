package mypackage;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageSelector {
    private static ImageSelector imageSelectorInstance = null;
    JFrame frame;
    JPanel panel;
    private ImageSelector(){
        frame = new JFrame("Default Image Selector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 600);

        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3, 5, 5));
    }

    // Singleton implementation of the imageSelector
    public static synchronized ImageSelector getInstance()
    {
        if (imageSelectorInstance == null)
            imageSelectorInstance = new ImageSelector();
 
        return imageSelectorInstance;
    }

    public void selectGameImage(){
        
        ArrayList<ImageButton> imageButtons = getImageButtons();
        refreshButtons(imageButtons);
    }

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
    

    private ArrayList<ImageButton> getImageButtons(){
        File folder = new File("Images");
        ArrayList<File> listOfFiles = getFileList(folder.getAbsolutePath());
        ArrayList<ImageButton> tempButtonList = new ArrayList<ImageButton>();

        for(int i = 0; i < listOfFiles.size(); i++){
            tempButtonList.add(new ImageButton(listOfFiles.get(i)));
        }

        return tempButtonList;
    }

    private void addImages(ArrayList<ImageButton> imageButtons){
            //Adds the image buttons to the panel
            for (int i = 0; i < imageButtons.size(); i++) {
                panel.add(imageButtons.get(i));
            }
    }

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
