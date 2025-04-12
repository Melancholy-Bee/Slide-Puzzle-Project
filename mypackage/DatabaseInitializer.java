package mypackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
  private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
  private static final String DB_NAME = "sliding_puzzle";
  private static final String USER = "root"; // Adjust as needed
  private static final String PASSWORD = ""; // Empty for local

  public static void initializeDatabase() {
    try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
         Statement stmt = conn.createStatement()) {

      // Create database if not exists
      stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);

      // Use the database
      stmt.executeUpdate("USE " + DB_NAME);

      // Create table if not exists
      stmt.executeUpdate("""
        CREATE TABLE IF NOT EXISTS scores (
          id INT AUTO_INCREMENT PRIMARY KEY,
          player_name VARCHAR(100),
          moves INT,
          time_seconds INT,
          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
      """);

      System.out.println("Database and table initialized.");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
