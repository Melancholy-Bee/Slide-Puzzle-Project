package mypackage;

import java.sql.*;

public class DatabaseManager {
  private static final String DB_URL = "jdbc:sqlite:puzzle.db";

  public DatabaseManager() {
    try (Connection conn = DriverManager.getConnection(DB_URL)) {
      try (Statement stmt = conn.createStatement()) {
        stmt.executeUpdate("""
          CREATE TABLE IF NOT EXISTS game_state (
            id INTEGER PRIMARY KEY,
            name TEXT NOT NULL,
            data TEXT NOT NULL
          )
        """);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void saveGame(String name, Board board) {
    String serializedData = board.serializeBoard();
    try (Connection conn = DriverManager.getConnection(DB_URL)) {
        String sql = "INSERT OR REPLACE INTO game_state (id, name, data) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 1); // single save slot
            pstmt.setString(2, name);
            pstmt.setString(3, serializedData);
            pstmt.executeUpdate();
            System.out.println("Game saved.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


  public void loadGame(Board board) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT data FROM game_state WHERE id = 1";
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    String serializedData = rs.getString("data");
                    board.deserializeBoard(serializedData);
                    System.out.println("Game loaded.");
                } else {
                    System.out.println("No saved game found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


