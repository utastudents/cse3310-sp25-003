package uta.cse3310.PageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

import uta.cse3310.DB.DB;
import uta.cse3310.PairUp.PairUp;
import uta.cse3310.GameManager.GameManager;

public class PageManager {
    private DB db;
    private PairUp pairUp;
    private GameManager gameManager;
    private Map<Integer, GameStatus> activeGames;
    private ArrayList<String> waitingPlayers;
    private Integer turn = 0;

    public PageManager() {
        db = new DB();
        pairUp = new PairUp(db);
        gameManager = new GameManager();
        activeGames = new HashMap<>();
        waitingPlayers = new ArrayList<>();
    }

    private UserEventReply handleLogin(LoginPayload payload) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();

        if (payload.username.equals("testUser") && payload.password.equals("password123")) {
            reply.status.message = "Login successful";
        } else {
            reply.status.message = "Invalid username or password";
        }

        return reply;
    }

    private UserEventReply handleGameStatus(String gameID) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();
        reply.status.gameID = gameID;
        reply.status.message = "Game is in progress";
        reply.status.turn = turn;
        return reply;
    }

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
            JsonObject jsonData = JsonConverter.parseJsonObject(userEvent.msg);

            if (jsonData == null) {
                return JsonConverter.createErrorReply("Invalid JSON format", userEvent.id);
            }

            String username = JsonConverter.extractField(userEvent.msg, "username");
            if (username == null || username.isEmpty()) {
                return JsonConverter.createErrorReply("Username is required", userEvent.id);
            }

            String password = JsonConverter.extractField(userEvent.msg, "password");
            String email = JsonConverter.extractField(userEvent.msg, "email");

            c = DB.initConnection();
            stmt = c.createStatement();

            if (email != null && !email.isEmpty()) {
                try {
                    DB.initUser(stmt, username, email, password);
                    reply.status.success = true;
                    reply.status.message = "User registered successfully";
                } catch (SQLException e) {
                    reply.status.success = false;
                    reply.status.message = "Registration failed: " + e.getMessage();
                }
            } else {
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
        return null; // unchanged
    }

    public UserEventReply handleGameMove(UserEvent userEvent) {
        return null; // unchanged
    }

    public UserEventReply handleSummaryRequest(UserEvent userEvent) {
        return null; // unchanged
    }

    public UserEventReply sendGameUpdate(Integer gameId, GameStatus gameStatus) {
        return null; // unchanged
    }

    public boolean sendNotification(Integer userId, String message) {
        return false; // unchanged
    }

    public int broadcastMessage(String message) {
        return 0; // unchanged
    }

    public void updateWaitingPlayers(ArrayList<String> newWaitingPlayers) {
        this.waitingPlayers = newWaitingPlayers;
        broadcastMessage("Waiting players list updated");
    }

    public GameStatus getGameStatus(Integer gameId) {
        if (gameId == null) return null;
        return activeGames.get(gameId);
    }
}
