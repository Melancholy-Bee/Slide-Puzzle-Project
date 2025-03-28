package mypackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageButton extends JButton {
    private File imageFile;
    private ImageIcon imageIcon;
    
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
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked " + imageFile.getAbsolutePath());
            }
         });
    }

    public File getImageFile() {
        return imageFile;
    }

}
