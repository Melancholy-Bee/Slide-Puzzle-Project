package mypackage;

import java.sql.*;

public class DatabaseInitializer {

  private static final String URL = "jdbc:mysql://localhost:3306/";
  private static final String USER = "root";
  private static final String PASSWORD = "";

  public static void initializeDatabase() {
    try {
      // Step 1: Connect to MySQL server (no database yet)
      try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
           Statement stmt = conn.createStatement()) {

        // Step 2: Create database if not exists
        stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS puzzleGame");
        System.out.println("Database checked/created.");

      }

      // Step 3: Connect to the puzzleGame database
      try (Connection conn = DriverManager.getConnection(URL + "puzzleGame", USER, PASSWORD)) {

        if (isTableExists(conn, "User") && isTableExists(conn, "GameState") && isTableExists(conn, "Image")) {
          System.out.println("All required tables already exist. Skipping creation.");
        } else {
          createTables(conn);
          System.out.println("Tables created successfully.");
        }

        // Optional: Check if 'User' table is empty
        if (isTableEmpty(conn, "User")) {
          System.out.println("Note: 'User' table is currently empty.");
        }

      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static boolean isTableExists(Connection conn, String tableName) throws SQLException {
    try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
      return rs.next();
    }
  }

  private static boolean isTableEmpty(Connection conn, String tableName) throws SQLException {
    String query = "SELECT COUNT(*) FROM " + tableName;
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
      if (rs.next()) {
        return rs.getInt(1) == 0;
      }
    }
    return true; // Assume empty if error
  }

  private static void createTables(Connection conn) throws SQLException {
    try (Statement stmt = conn.createStatement()) {

      stmt.executeUpdate("""
        CREATE TABLE IF NOT EXISTS User (
          userId BIGINT AUTO_INCREMENT PRIMARY KEY,
          username VARCHAR(255) UNIQUE NOT NULL
        )
      """);

      stmt.executeUpdate("""
        CREATE TABLE IF NOT EXISTS GameState (
          gameStateId BIGINT AUTO_INCREMENT PRIMARY KEY,
          userId BIGINT NOT NULL,
          moveCount INT NOT NULL,
          gridSize INT NOT NULL,
          isCompleted BOOLEAN NOT NULL,
          boardStateJson TEXT NOT NULL,
          FOREIGN KEY (userId) REFERENCES User(userId)
        )
      """);

      stmt.executeUpdate("""
        CREATE TABLE IF NOT EXISTS Image (
          imageId BIGINT AUTO_INCREMENT PRIMARY KEY,
          filePath VARCHAR(255) NOT NULL,
          userId BIGINT NOT NULL,
          FOREIGN KEY (userId) REFERENCES User(userId)
        )
      """);
    }
  }
}
