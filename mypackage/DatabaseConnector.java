package mypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
  private static final String URL = "jdbc:mysql://localhost:3306/";
  private static final String DB_NAME = "puzzleGame";
  private static final String USER = "root";
  private static final String PASSWORD = ""; // No password, as you mentioned

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL + DB_NAME, USER, PASSWORD);
  }

  public static Connection getServerConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}
