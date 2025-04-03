package mypackage;
import java.awt.image.BufferedImage;

//Written by Landon Armstrong 
public class Tile {
    private BufferedImage image;
    private int goalX, goalY;
    private int posX, posY;

    Tile(BufferedImage img, int x, int y){
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

    public BufferedImage getImage(){
        return image;
    }

    //mutators to change values of position
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    

    //checks if tile is empty
    public boolean getIsEmpty(){
        return (image == null);
    }
    
    //returns true if moving the tile to the tempTile's position would be a valid move
    public boolean validMove(Tile tempTile){
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
    public void swap(Tile tempTile){
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
    public boolean correct(){
        return ((posX == goalX) && (posY == goalY));
    }
}