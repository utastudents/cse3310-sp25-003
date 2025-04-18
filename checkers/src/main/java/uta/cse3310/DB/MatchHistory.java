package uta.cse3310.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchHistory {

    private int playerID;
    private String boardState;

    // DB credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME";
    private static final String USER = "your_db_username";
    private static final String PASS = "your_db_password";

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getBoardState() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "SELECT board_state FROM match_history WHERE player_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, playerID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                boardState = rs.getString("board_state");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching board state: " + e.getMessage());
        }
        return boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "UPDATE match_history SET board_state = ? WHERE player_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, boardState);
            stmt.setInt(2, playerID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating board state: " + e.getMessage());
        }
    }

    public void saveGame() {
        setBoardState(boardState);
        System.out.println("Game saved to database successfully.");
    }

    public void loadGame() {
        boardState = getBoardState();
        if (boardState != null) {
            System.out.println("Game loaded from database successfully.");
        } else {
            System.out.println("No saved game found.");
        }
    }
}
