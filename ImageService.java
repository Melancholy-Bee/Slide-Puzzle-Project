import java.io.File;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.Image;


//Class and methods written by Landon Armstrong
public class ImageService {

    //opens a file selector, filters for JPG, JPEG, and PNG images
    public static File fileFinder() throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, JPEG, and PNG Images", "jpg", "jpeg", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        //return null if no file is chosen
        return null;
    }

    //opens file selector at specified folder
    public static File fileFinder(String path) throws Exception{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        System.out.println(path);
        JFileChooser chooser = new JFileChooser(new File(path));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, JPEG, and PNG Images", "jpg", "jpeg", "png");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        }
        //return null if no file is chosen
        return null;
    }

    //Creates new folder for images and copies a selected image to that folder
    public static void saveImage() throws Exception{
        File image = fileFinder();
        File imageFile = new File("Images");
        
        //makes the folder if it doesn't already exist
        imageFile.mkdirs();
        
        //copies image into folder
        Files.copy(image.toPath(), new File(imageFile.getAbsolutePath() + "/" + image.getName()).toPath(), REPLACE_EXISTING);
    }

    //User selects from images in the image folder
    public static Image loadImage() throws Exception {
        String imageFolderPath = (new File("Images")).getAbsolutePath(); //holds the desired filepath to the imageFolder
        File imageFile = fileFinder(imageFolderPath); //opens file chooser at the image folder
        if(imageFile != null){
            Image image = ImageIO.read(imageFile);
            return image;
        }
        else {
            return null;
        }
    }
}
