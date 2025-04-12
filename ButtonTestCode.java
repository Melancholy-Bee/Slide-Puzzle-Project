import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mypackage.DatabaseInitializer;
import mypackage.Menu;

class ButtonTestCode {
    public static void main(String args[]){
        mypackage.DatabaseInitializer.initializeDatabase();
        Menu.initialize();
    }
}