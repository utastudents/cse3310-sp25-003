package uta.cse3310.PageManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        // Toggle turn for demonstration purposes
        if (turn == 0) {
            ret.status.turn = 1;
            turn = 1;
        } else {
            ret.status.turn = 0;
            turn = 0;
        }

        // Send response back to the originating user
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
        // TODO: Implement login logic using DB
        return null;
    }

    public UserEventReply handleJoinGame(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();
        reply.recipients.add(userEvent.id);
        reply.type = "joinGameResult";

        // Parse JoinGamePayload from JSON string
        JoinGamePayload payload = JsonConverter.parseJsonObject(userEvent.msg) == null
            ? null
            : JsonConverter.toJson(JsonConverter.parseJsonObject(userEvent.msg)).equals("") ? null
            : new com.google.gson.Gson().fromJson(userEvent.msg, JoinGamePayload.class);

        // Safety check
        if (payload == null || payload.entity1 == null || payload.entity2 == null || payload.action == null) {
            return JsonConverter.createErrorReply("Invalid join game payload.", userEvent.id);
        }

        // Convert opponentType1 and opponentType2 to booleans: 0 = bot, 1 = human
        boolean isEntity1Bot = !payload.opponentType1; // false means human, true means bot
        boolean isEntity2Bot = !payload.opponentType2;


        // If action is "wait", add entity1 to waiting list
        if (payload.action.equalsIgnoreCase("wait")) {
            waitingPlayers.add(payload.entity1);
            reply.status.message = payload.entity1 + " is waiting for a match.";
            reply.status.success = true;
            reply.status.gameID = payload.lobbyId;
            reply.status.opponent = "None yet";
            return reply;
        }

        // TODO: When PairUp integration is ready, pass to PairUp
        // PairResponsePayload response = pairUp.pairPlayer(payload.entity1, isEntity2Bot, payload.action, payload.lobbyId);

        // Placeholder response
        reply.status.message = "JoinGame request acknowledged. PairUp not yet called.";
        reply.status.success = true;
        reply.status.gameID = payload.lobbyId;
        reply.status.opponent = payload.entity2;

        return reply;
    }

    public UserEventReply handleGameMove(UserEvent userEvent) {
        // TODO: Implement game move validation with GameManager
        return null;
    }

    public UserEventReply handleSummaryRequest(UserEvent userEvent) {
        // TODO: Get leaderboard/summary info from DB
        return null;
    }

    public UserEventReply sendGameUpdate(Integer gameId, GameStatus gameStatus) {
        // TODO: Create reply with current game state
        return null;
    }

    public boolean sendNotification(Integer userId, String message) {
        // TODO: Send a direct message to a specific client
        return false;
    }

    public int broadcastMessage(String message) {
        // TODO: Send message to all users
        return 0;
    }

    public void updateWaitingPlayers(ArrayList<String> waitingPlayers) {
        this.waitingPlayers = waitingPlayers;
    }

    public GameStatus getGameStatus(Integer gameId) {
        return null;
    }
}
