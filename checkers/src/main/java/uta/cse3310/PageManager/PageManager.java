package uta.cse3310.PageManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.Gson;

import uta.cse3310.DB.DB;
import uta.cse3310.DB.PlayerInfo;
import uta.cse3310.PairUp.PairUp;
import uta.cse3310.GameManager.GameManager;
import uta.cse3310.GameManager.GameState;
import uta.cse3310.GameManager.Move;
import uta.cse3310.GameManager.Position;
import uta.cse3310.GameTermination.GameTermination;
import uta.cse3310.GameTermination.GameStatus;
import uta.cse3310.PairUp.module.PairUpModule;
import uta.cse3310.PairUp.module.LobbyException;

public class PageManager {
    private PairUp pairUp;
    private GameManager gameManager;
    private Map<String, GameState> activeGames;
    private ArrayList<String> waitingPlayers;
    private Map<String, String> userToGameMap;
    private Gson gson;

    public PageManager() {
        this.pairUp = new PairUp(null);
        this.gameManager = new GameManager();
        this.activeGames = new HashMap<>();
        this.waitingPlayers = new ArrayList<>();
        this.userToGameMap = new HashMap<>();
        this.gson = new Gson();
    }

    public UserEventReply ProcessInput(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        
        try {
            switch (userEvent.eventType) {
                case "login":
                    return handleLogin(userEvent);
                case "signup":
                    return handleSignup(userEvent);
                case "joinGame":
                    return handleJoinGame(userEvent);
                case "gameMove":
                    return handleGameMove(userEvent);
                case "getPlayersUsername":
                    return handleGetPlayersUsername(userEvent);
                case "summaryRequest":
                    return handleSummaryRequest(userEvent);
                case "goToLoginPage":
                    return handleGoToLoginPage(userEvent);
                default:
                    return handleDefaultEvent(userEvent);
            }
        } catch (Exception e) {
            reply.setMessage("Error processing request: " + e.getMessage());
            reply.setSuccess(false);
            return reply;
        }
    }
    
    private UserEventReply handleGoToLoginPage(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        reply.setMessage("Navigate to login page");
        reply.setSuccess(true);
        reply.setRecipients(new ArrayList<>(Arrays.asList(userEvent.id)));

        return reply;
    }

    private UserEventReply handleDefaultEvent(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        reply.setMessage("Unknown event type: " + userEvent.eventType);
        reply.setSuccess(false);
        reply.setRecipients(new ArrayList<>(Arrays.asList(userEvent.id)));

        return reply;
    }

    public UserEventReply handleGetPlayersUsername(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        ArrayList<String> playersList = new ArrayList<>();
        
        playersList.addAll(waitingPlayers);
        
        for (GameState game : activeGames.values()) {
            if (game.getCurrentPlayerId() != null) {
                playersList.add(game.getCurrentPlayerId());
            }
        }
        
        reply.setPlayersList(playersList);
        reply.setMessage("Players list retrieved successfully");
        reply.setSuccess(true);
        reply.setRecipients(new ArrayList<>(Arrays.asList(userEvent.id)));

        return reply;
    }

    public UserEventReply handleLogin(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        try {
            LoginPayload payload = gson.fromJson(userEvent.msg, LoginPayload.class);
            DB db = DB.getDB(); //connect to DB
            Statement stmt = db.connection.createStatement(); //sql stmt to run query on DB
    
            // check if the username exists
            String userCheckQuery = "SELECT * FROM USER_DATABASE WHERE USERNAME = '" + payload.username + "'";
            ResultSet usernameResult = stmt.executeQuery(userCheckQuery);
    
            if (usernameResult.next()) {
                // Username exists then check password
                String correctPassword = usernameResult.getString("PASSWORD");
                if (payload.password.equals(correctPassword)) { //if password match
                    reply.setMessage("User logged in successfully");
                    reply.setSuccess(true);
                } else {
                    reply.setMessage("Incorrect password");
                    reply.setSuccess(false);
                }
            } else {
                // Username not in DB
                reply.setMessage("Account not found");
                reply.setSuccess(false); //dont allow login
            }
    
            usernameResult.close();
            stmt.close();
        } catch (Exception e) 
        {
            reply.setMessage("Login failed: " + e.getMessage());
            reply.setSuccess(false);
        }
    
        reply.setType("login"); // set aslogin response for index.html

        //setting recipient for reply as only on user 
        reply.setRecipients(new ArrayList<>(Arrays.asList(userEvent.id)));
        return reply;
    }
    
    
    public UserEventReply handleSignup(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        try {
            SignupPayload payload = gson.fromJson(userEvent.msg, SignupPayload.class);
            DB db = DB.getDB();
            Statement stmt = db.connection.createStatement();
    
            // Check if username already in DB
            String checkSql = "SELECT * FROM USER_DATABASE WHERE USERNAME = '" + payload.username + "'";
            ResultSet usernameResult = stmt.executeQuery(checkSql);
    
            if (usernameResult.next()) { //if username already found in DB
                reply.setMessage("Username already exists");
                reply.setSuccess(false);
            } else {
                //send the info to DB to store
                db.initUser(payload.username, payload.email, payload.password);
                reply.setMessage("Signup successful");
                reply.setSuccess(true);
            }
    
            usernameResult.close();
            stmt.close();
        } catch (Exception e) {
            reply.setMessage("Signup failed: " + e.getMessage());
            reply.setSuccess(false);
        }
    
        reply.setRecipients(new ArrayList<>(Arrays.asList(userEvent.id)));
        return reply;
    }

    public UserEventReply handleJoinGame(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        try {
            PairUpModule pairUpModule = pairUp.getPairUpModule();
            JsonObject json = gson.fromJson(userEvent.msg, JsonObject.class);
            // Debugging
            System.out.println(json.toString());
            
            // Create JoinGamePayload for PairUpModule
            uta.cse3310.PageManager.JoinGamePayload payload = new uta.cse3310.PageManager.JoinGamePayload();
            // payload.entity1 = json.get("playerId").getAsString();
            payload.entity1 = json.get("entity1").getAsString();
            // payload.opponentType1 = !json.get("isBot").getAsBoolean();
            payload.opponentType1 = json.get("opponentType1").getAsString().equals("1");
            payload.action = "wait"; // Default action is wait
            
            if (json.has("lobbyId") && !json.get("lobbyId").isJsonNull()) {
                payload.lobbyId = json.get("lobbyId").getAsString();
                // Debugging
                System.out.println(payload.lobbyId);
                payload.action = "join";
                // payload.entity2 = json.get("playerId").getAsString();
                payload.entity2 = json.get("entity2").getAsString();
                // payload.opponentType2 = !json.get("isBot").getAsBoolean();
                payload.opponentType2 = json.get("opponentType2").getAsString().equals("0");
            }
            
            try {
                uta.cse3310.PageManager.PairResponsePayload pairResponse = pairUpModule.pairPlayer(payload);
                System.out.println("here");
                if (pairResponse != null) {
                    System.out.println("pairResponse != null");
                    GameState state = gameManager.createGame(pairResponse.gameID, payload.entity1);
                    activeGames.put(pairResponse.gameID, state);
                    reply.setGameState(state);
                    reply.setMessage("Game joined successfully");
                    reply.setSuccess(true);
                } else {
                    System.out.println("pairResponse == null");
                    reply.setMessage("Failed to join game");
                    reply.setSuccess(false);
                }
            } catch (LobbyException e) {
                System.out.println("lobby error");
                reply.setMessage("Lobby error: " + e.getMessage());
                reply.setSuccess(false);
            }
        } catch (Exception e) {
            reply.setMessage("Failed to join game: " + e.getMessage());
            reply.setSuccess(false);
        }
        reply.setRecipients(new ArrayList<>(Arrays.asList(userEvent.id)));

        return reply;
    }

    public UserEventReply handleGameMove(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        try {
            JsonObject moveJson = gson.fromJson(userEvent.msg, JsonObject.class);
            String gameId = moveJson.get("gameId").getAsString();
            int fromRow = moveJson.get("fromRow").getAsInt();
            int fromCol = moveJson.get("fromCol").getAsInt();
            int toRow = moveJson.get("toRow").getAsInt();
            int toCol = moveJson.get("toCol").getAsInt();
            
            Position from = new Position(fromRow, fromCol);
            Position to = new Position(toRow, toCol);
            Move move = new Move(from, to, userEvent.id.toString());
            
            GameState state = activeGames.get(gameId);
            if (state != null) {
                gameManager.receiveMove(move);
                GameTermination termination = new GameTermination();
                GameStatus gameStatus = termination.checkForGameEnd(state, null);
                reply.setGameState(state);
                reply.setMessage("Move processed successfully");
                reply.setSuccess(true);
            } else {
                reply.setMessage("Game not found");
                reply.setSuccess(false);
            }
        } catch (Exception e) {
            reply.setMessage("Failed to process move: " + e.getMessage());
            reply.setSuccess(false);
        }
        reply.setRecipients(new ArrayList<>(Arrays.asList(userEvent.id)));

        return reply;
    }

    public UserEventReply handleSummaryRequest(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        try {
            reply.setMessage("Summary retrieved successfully");
            reply.setSuccess(true);
        } catch (Exception e) {
            reply.setMessage("Failed to retrieve summary: " + e.getMessage());
            reply.setSuccess(false);
        }
        reply.setRecipients(new ArrayList<>(Arrays.asList(userEvent.id)));

        return reply;
    }

    public void updateWaitingPlayers(ArrayList<String> newWaitingPlayers) {
        this.waitingPlayers = newWaitingPlayers;
    }

    public GameState getGameStatus(String gameId) {
        return activeGames.get(gameId);
    }
}
