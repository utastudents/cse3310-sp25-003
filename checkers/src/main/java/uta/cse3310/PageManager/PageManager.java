package uta.cse3310.PageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import uta.cse3310.DB.DB;
import uta.cse3310.PairUp.PairUp;
import uta.cse3310.GameManager.GameManager;

public class PageManager {
    // database interface
    private DB db;
    
    // pairUp interface
    private PairUp pairUp;
    
    // gameManager interface
    private GameManager gameManager;
    
    // map to track active games by gameId
    private Map<Integer, GameStatus> activeGames;
    
    // cache for waiting players
    private ArrayList<String> waitingPlayers;
    
    // Turn tracker (global, not unique per client or game)
    private Integer turn = 0;
    
    // Constructor
    public PageManager() {
        db = new DB();
        pairUp = new PairUp(db);
        gameManager = new GameManager();
        activeGames = new HashMap<>();
        waitingPlayers = new ArrayList<>();
    }
    
    /**
     * Processes input from the user.
     */
    public UserEventReply ProcessInput(UserEvent userEvent) {
        if (userEvent.eventType == null || userEvent.eventType.isEmpty()) {
            return handleDefaultEvent(userEvent);
        }
        
        switch (userEvent.eventType) {
            case "login":
                return handleLogin(userEvent);
            case "joinGame":
                return handleJoinGame(userEvent);
            case "gameMove":
                return handleGameMove(userEvent);
            case "summaryRequest":
                return handleSummaryRequest(userEvent);
            case "getPlayersUsername":
                return handleGetPlayersUsername(userEvent);
            default:
                return handleDefaultEvent(userEvent);
        }
    }
    
    private UserEventReply handleDefaultEvent(UserEvent userEvent) {
        UserEventReply ret = new UserEventReply();
        ret.status = new GameStatus();
        
        if (turn == 0) {
            ret.status.turn = 1;
            turn = 1;
        } else {
            ret.status.turn = 0;
            turn = 0;
        }
        
        ret.recipients = new ArrayList<>();
        ret.recipients.add(userEvent.id);
        
        return ret;
    }
    
    public UserEventReply handleGetPlayersUsername(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();
        reply.recipients.add(userEvent.id);
        reply.type = "playersUsernameList";
        
        ArrayList<String> players = new ArrayList<>();
        
        if (waitingPlayers != null && !waitingPlayers.isEmpty()) {
            players.addAll(waitingPlayers);
        }
        
        reply.status.playersList = players;
        reply.status.success = true;
        reply.status.message = "Players list retrieved successfully";
        
        return reply;
    }
    
    public UserEventReply handleLogin(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();
        reply.recipients.add(userEvent.id);
        reply.type = "loginResult";
        
        Connection c = null;
        Statement stmt = null;
        
        try {
            // parse JSON from msg field
            JsonObject jsonData = JsonConverter.parseJsonObject(userEvent.msg);
            
            if (jsonData == null) {
                return JsonConverter.createErrorReply("Invalid JSON format", userEvent.id);
            }
            
            String username = JsonConverter.extractField(userEvent.msg, "username");
            
            if (username == null || username.isEmpty()) {
                return JsonConverter.createErrorReply("Username is required", userEvent.id);
            }
            
            // get optional fields
            String password = JsonConverter.extractField(userEvent.msg, "password");
            String email = JsonConverter.extractField(userEvent.msg, "email");
            
            c = DB.initConnection();
            stmt = c.createStatement();
            
            if (email != null && !email.isEmpty()) {
                // new user registration
                try {
                    DB.initUser(stmt, username, email, password);
                    reply.status.success = true;
                    reply.status.message = "User registered successfully";
                } catch (SQLException e) {
                    reply.status.success = false;
                    reply.status.message = "Registration failed: " + e.getMessage();
                }
            } else {
                // existing user login - would need proper validation method
                reply.status.success = true;
                reply.status.message = "Login successful";
            }
            
            return reply;
        } catch (Exception e) {
            reply.status.success = false;
            reply.status.message = "Error during login: " + e.getMessage();
            return reply;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (c != null) c.close();
            } catch (SQLException e) {
                
            }
        }
    }
    
    public UserEventReply handleJoinGame(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();
        reply.recipients.add(userEvent.id);
        reply.type = "joinGameResult";
        
        try {
            // parse JSON from msg field
            JsonObject jsonData = JsonConverter.parseJsonObject(userEvent.msg);
            
            if (jsonData == null) {
                return JsonConverter.createErrorReply("Invalid JSON format", userEvent.id);
            }
            
            String username = JsonConverter.extractField(userEvent.msg, "username");
            String playerType = JsonConverter.extractField(userEvent.msg, "playerType");
            
            if (username == null || username.isEmpty()) {
                // fall back to user ID if username not provided
                username = "user" + userEvent.id;
            }
            
            // add player to waiting list if they're not already there
            if (!waitingPlayers.contains(username)) {
                waitingPlayers.add(username);
            }
            
            // Will integrate with PairUp to match players
            // For now, we just acknowledge the join request
            
            reply.status.message = "Join Game request received. Waiting for opponent...";
            reply.status.gameID = "game-" + userEvent.id;
            reply.status.opponent = "Waiting for opponent...";
            reply.status.success = true;
            
            return reply;
        } catch (Exception e) {
            return JsonConverter.createErrorReply("Error processing join game request: " + e.getMessage(), userEvent.id);
        }
    }
    
    public UserEventReply handleGameMove(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();
        reply.recipients.add(userEvent.id);
        reply.type = "gameMoveResult";
        
        try {
            // parse the moveData from the msg field
            // according to our  PageManager.js, moveData is nested JSON: { moveData: JSON.stringify({ gameId, ...moveData }) }
            String moveDataStr = JsonConverter.extractField(userEvent.msg, "moveData");
            
            if (moveDataStr == null || moveDataStr.isEmpty()) {
                return JsonConverter.createErrorReply("Move data is required", userEvent.id);
            }
            
            // parse the inner JSON
            JsonObject moveData = JsonConverter.parseJsonObject(moveDataStr);
            
            if (moveData == null) {
                return JsonConverter.createErrorReply("Invalid move data format", userEvent.id);
            }
            
            String gameId = JsonConverter.extractField(moveDataStr, "gameId");
            
            if (gameId == null || gameId.isEmpty()) {
                return JsonConverter.createErrorReply("Game ID is required", userEvent.id);
            }
            
            // Here we would integrate with GameManager to validate and process the move
            // For now, we'll just acknowledge receipt
            
            reply.status.success = true;
            reply.status.message = "Move processed";
            
            // Turn management should come from GameManager
            if (turn == 0) {
                reply.status.turn = 1;
                turn = 1;
            } else {
                reply.status.turn = 0;
                turn = 0;
            }
            
            return reply;
        } catch (Exception e) {
            return JsonConverter.createErrorReply("Error processing game move: " + e.getMessage(), userEvent.id);
        }
    }
    
    public UserEventReply handleSummaryRequest(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();
        reply.recipients.add(userEvent.id);
        reply.type = "summaryResponse";
        
        Connection c = null;
        Statement stmt = null;
        
        try {
            c = DB.initConnection();
            stmt = c.createStatement();
            
            // Get leaderboard data
            ArrayList<String> leaderboardData = new ArrayList<>();
            String sql = "SELECT USERNAME, WINS, LOSSES FROM USER_DATABASE ORDER BY WINS DESC";
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                String username = rs.getString("USERNAME");
                int wins = rs.getInt("WINS");
                int losses = rs.getInt("LOSSES");
                leaderboardData.add(username + " - Wins: " + wins + ", Losses: " + losses);
            }
            
            reply.status.playersList = leaderboardData;
            reply.status.success = true;
            reply.status.message = "Leaderboard data retrieved successfully";
            
            DB.closeConnection(c, stmt);
            
            return reply;
        } catch (Exception e) {
            reply.status.success = false;
            reply.status.message = "Error retrieving summary: " + e.getMessage();
            
            try {
                if (stmt != null) stmt.close();
                if (c != null) c.close();
            } catch (SQLException se) {
                // Handle close error
            }
            
            return reply;
        }
    }
    
    public UserEventReply sendGameUpdate(Integer gameId, GameStatus gameStatus) {
        UserEventReply reply = new UserEventReply();
        reply.status = gameStatus;
        reply.recipients = new ArrayList<>();
        
        // Integration point with GameManager to get player IDs for the game
        
        // Update our local cache of game state
        activeGames.put(gameId, gameStatus);
        
        // Set the type for the frontend to interpret
        reply.type = "gameUpdate";
        
        return reply;
    }
    
    public boolean sendNotification(Integer userId, String message) {
        if (userId == null || message == null) {
            return false;
        }
        
        UserEventReply notification = new UserEventReply();
        notification.status = new GameStatus();
        notification.recipients = new ArrayList<>();
        notification.recipients.add(userId);
        notification.type = "notification";
        
        notification.status.message = message;
        notification.status.success = true;
        
        return true;
    }
    
    public int broadcastMessage(String message) {
        if (message == null || message.isEmpty()) {
            return 0;
        }
        
        UserEventReply broadcast = new UserEventReply();
        broadcast.status = new GameStatus();
        broadcast.recipients = new ArrayList<>();
        broadcast.type = "broadcast";
        
        broadcast.status.message = message;
        broadcast.status.success = true;
        
        // Integration point to get all connected users
        
        return 0; // Will be updated when WebSocket integration is available
    }
    
    public void updateWaitingPlayers(ArrayList<String> newWaitingPlayers) {
        waitingPlayers = newWaitingPlayers;
        
        // Notify clients about updated waiting list
        broadcastMessage("Waiting players list updated");
    }
    
    public GameStatus getGameStatus(Integer gameId) {
        if (gameId == null) {
            return null;
        }
        
        return activeGames.get(gameId);
    }
}
