package mypackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

//written by Landon
public class ImageButton extends JButton {
    private File imageFile;
    private ImageIcon imageIcon;
    
    //creates a button with an attached image file used to make it
    ImageButton(File image){
        super();
        imageFile = image;
        try {
            imageIcon = new ImageIcon(ImageProcessor.resizeImage(ImageIO.read(imageFile), 290, 290));
            super.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in ImageButton");
        }
        //Replaces the selected file in the ImageSelector class with whatever is clicked
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ImageSelector.getInstance().selectedFile = imageFile;
                Menu.imageHandling();
            }
         });
    }

    public File getImageFile() {
        return imageFile;
    }
}
