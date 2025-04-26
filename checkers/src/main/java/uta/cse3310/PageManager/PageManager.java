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
import uta.cse3310.DB.PlayerInfo;
import uta.cse3310.PairUp.PairUp;
import uta.cse3310.GameManager.GameManager;

public class PageManager {
    private PairUp pairUp;
    private GameManager gameManager;
    private Map<Integer, GameStatus> activeGames;
    private ArrayList<String> waitingPlayers;
    private Integer turn = 0;

    public PageManager() {
        pairUp = new PairUp(DB.getDB());
        gameManager = new GameManager();
        activeGames = new HashMap<>();
        waitingPlayers = new ArrayList<>();
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

        LoginPayload login = JsonConverter.parseLoginPayload(userEvent.msg);

        if (login == null) {
            reply.status.success = false;
            reply.status.message = "Invalid JSON format";
            return reply;
        }
        if (login.username == null || login.username.isEmpty()) {
            reply.status.success = false;
            reply.status.message = "Username is required";
            return reply;
        }
        if (login.password == null || login.password.isEmpty()) {
            reply.status.success = false;
            reply.status.message = "Password is required";
            return reply;
        }

        PlayerInfo user = DB.getDB().getPlayerInfo(userEvent.id);

        String username = user.getUsername();
        String password = user.getPassword();

        if (login.username == username && login.password == password) {
            reply.status.success = true;
            reply.status.message = "User logged in successfully";
        } else {
            reply.status.success = false;
            reply.status.message = "Username or password does not match";
        }

        return reply;
    }

    public UserEventReply handleSignup(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();
        reply.recipients.add(userEvent.id);
        reply.type = "loginResult";

        SignupPayload signup = JsonConverter.parseSigupPayload(userEvent.msg);

        if (signup == null) {
            reply.status.success = false;
            reply.status.message = "Invalid JSON format";
            return reply;
        }
        if (signup.username == null || signup.username.isEmpty()) {
            reply.status.success = false;
            reply.status.message = "Username is required";
            return reply;
        }
        if (signup.password == null || signup.password.isEmpty()) {
            reply.status.success = false;
            reply.status.message = "Password is required";
            return reply;
        }

        if (signup.email != null && !signup.email.isEmpty()) {
            try {
                DB.getDB().initUser(signup.username, signup.email, signup.password);
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
    }

    public UserEventReply handleJoinGame(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();
        reply.recipients.add(userEvent.id);
        reply.type = "joinGameResult";

        try {
            JsonObject jsonData = JsonConverter.parseJsonObject(userEvent.msg);
            if (jsonData == null) {
                return JsonConverter.createErrorReply("Invalid JSON format", userEvent.id);
            }

            String entity1 = JsonConverter.extractField(userEvent.msg, "entity1");
            String entity2 = JsonConverter.extractField(userEvent.msg, "entity2");
            String opponentType1 = JsonConverter.extractField(userEvent.msg, "opponentType1");
            String opponentType2 = JsonConverter.extractField(userEvent.msg, "opponentType2");
            String action = JsonConverter.extractField(userEvent.msg, "action");
            String lobbyId = JsonConverter.extractField(userEvent.msg, "lobbyId");

            reply.status.success = true;
            reply.status.message = entity1 + " and " + (entity2 != null ? entity2 : "[none]") +
                    " sent join game request for lobby " + lobbyId + " (action=" + action + ")";
            reply.status.opponent = entity2;
            reply.status.gameID = lobbyId;

            return reply;

        } catch (Exception e) {
            return JsonConverter.createErrorReply("Error processing join game: " + e.getMessage(), userEvent.id);
        }
    }

    public UserEventReply handleGameMove(UserEvent userEvent) {
        return null;
    }

    public UserEventReply handleSummaryRequest(UserEvent userEvent) {
        return null;
    }

    public UserEventReply sendGameUpdate(Integer gameId, GameStatus gameStatus) {
        return null;
    }

    public boolean sendNotification(Integer userId, String message) {
        return false;
    }

    public int broadcastMessage(String message) {
        return 0;
    }

    public void updateWaitingPlayers(ArrayList<String> newWaitingPlayers) {
        this.waitingPlayers = newWaitingPlayers;
        broadcastMessage("Waiting players list updated");
    }

    public GameStatus getGameStatus(Integer gameId) {
        if (gameId == null)
            return null;
        return activeGames.get(gameId);
    }
}