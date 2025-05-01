import mypackage.Menu;

class PictureThis {
    public static void main(String args[]){
        mypackage.DatabaseInitializer.initializeDatabase();
        Menu.initialize();
    }
}