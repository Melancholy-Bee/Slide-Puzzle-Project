package mypackage;

import java.sql.*;

public class DatabaseInitializer {

  private static final String DB_URL = "jdbc:sqlite:puzzleGame.db";

  public static void initializeDatabase() {
    try (Connection conn = DriverManager.getConnection(DB_URL)) {
      if (conn != null) {
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("Connected to SQLite, driver: " + meta.getDriverName());

        if (isTableExists(conn, "User") &&
            isTableExists(conn, "GameState") &&
            isTableExists(conn, "Image")) {
          System.out.println("Tables already exist. Skipping creation.");
        } else {
          createTables(conn);
          System.out.println("Tables created successfully.");
        }

        if (isTableEmpty(conn, "User")) {
          System.out.println("Note: 'User' table is empty.");
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
      return rs.next() && rs.getInt(1) == 0;
    }
  }

  private static void createTables(Connection conn) throws SQLException {
    try (Statement stmt = conn.createStatement()) {
      stmt.executeUpdate("""
        CREATE TABLE IF NOT EXISTS User (
          userId INTEGER PRIMARY KEY AUTOINCREMENT,
          username TEXT UNIQUE NOT NULL
        )
      """);

      stmt.executeUpdate("""
        CREATE TABLE IF NOT EXISTS GameState (
          gameStateId INTEGER PRIMARY KEY AUTOINCREMENT,
          userId INTEGER NOT NULL,
          moveCount INTEGER NOT NULL,
          gridSize INTEGER NOT NULL,
          isCompleted INTEGER NOT NULL,
          boardStateJson TEXT NOT NULL,
          FOREIGN KEY (userId) REFERENCES User(userId)
        )
      """);

      stmt.executeUpdate("""
        CREATE TABLE IF NOT EXISTS Image (
          imageId INTEGER PRIMARY KEY AUTOINCREMENT,
          filePath TEXT NOT NULL,
          userId INTEGER NOT NULL,
          FOREIGN KEY (userId) REFERENCES User(userId)
        )
      """);
    }
  }
}
