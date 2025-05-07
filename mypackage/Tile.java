package mypackage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.JButton;

//Written by Landon Armstrong
public class Tile implements Serializable{
    private BufferedImage image;
    private int goalX, goalY;
    private int posX, posY;
    private JButton respectiveButton;

    //addition for database (maura)
    
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        
        if (image != null) {
            ImageIO.write(image, "png", oos);
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();  // Deserialize regular fields
        // Manually handle the image
        try {
            this.image = ImageIO.read(ois);  // Read the image
        } catch (Exception e) {
            this.image = null; // In case image is not saved or loading fails
        }
        // Recreate the JButton if necessary, or handle it as per your needs
        if (this.respectiveButton == null) {
            this.respectiveButton = new JButton();  // This could be customized further if necessary
        }
    }

    //end addition (maura)

    public Tile(BufferedImage img, int x, int y) {
        image = img;
        goalX = x;
        goalY = y;
    }

    //accessors for current position and image instance variables
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public BufferedImage getImage() {
        return image;
    }

    //mutators to change values of position
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    public void setButton(JButton button) {
        respectiveButton = button;
    }

    public JButton getButton() {
        return respectiveButton;
    }

    //checks if tile is empty
    public boolean getIsEmpty() {
        return (image == null);
    }
    
    //returns true if moving the tile to the tempTile's position would be a valid move
    public boolean validMove(Tile tempTile) {
        if((Math.abs(tempTile.getPosX() - posX) <= 1 && Math.abs(tempTile.getPosY() - posY) <= 1) && (Math.abs(tempTile.getPosX() - posX) != Math.abs(tempTile.getPosY() - posY))){ //filters so only tiles directly next to eachother can be moved
            if(tempTile.getIsEmpty()){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    //swaps the tile positions if the move is valid
    public void swap(Tile tempTile) {
        int tempX = tempTile.getPosX();
        int tempY = tempTile.getPosY();
        if(validMove(tempTile)){
            tempTile.setPosX(posX);
            tempTile.setPosY(posY);
            posX = tempX;
            posY = tempY;
        }
    }

    //returns true if the tile is in the correct position, else returns false
    public boolean correct() {
        return ((posX == goalX) && (posY == goalY));
    }

    //addition for database (maura)
    public int getGoalX() {
        return goalX;
      }
      
      public int getGoalY() {
        return goalY;
      }

      public void setImage(BufferedImage image) {
        this.image = image;
    }
    
    public void setIsEmpty(boolean isEmpty) {
        if (isEmpty) {
            this.image = null;
        }
    }
    
    //end addition

}