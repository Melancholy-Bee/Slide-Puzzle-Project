import mypackage.Menu;

class PictureThis {
    public static void main(String args[]){
        mypackage.DatabaseInitializer.initializeDatabase();
        Menu.initialize();
        //database verification (maura)
        System.out.println("Working directory: " + System.getProperty("user.dir"));
    }
}