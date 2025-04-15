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
        JoinGamePayload payload = JsonConverter.getGson().fromJson(userEvent.msg, JoinGamePayload.class);

        // Safety check in case payload fails to parse
        if (payload == null || payload.playerHandle == null || payload.opponentType == null) {
            return JsonConverter.createErrorReply("Invalid join game payload.", userEvent.id);
        }

        // If the player is waiting, add them to waiting list
        if (payload.action.equalsIgnoreCase("wait")) {
            waitingPlayers.add(payload.playerHandle);
            reply.status.message = "Waiting for opponent...";
            reply.status.success = true;
            reply.status.gameID = "waiting-" + payload.playerHandle;
            reply.status.opponent = "None yet";
            return reply;
        }

        // TODO: Call pairUp.pairPlayer once implemented
        // PairResponsePayload response = pairUp.pairPlayer(
        //     payload.playerHandle, payload.opponentType, payload.action, payload.lobbyId
        // );

        // // Fill status with response from PairUp
        // reply.status.success = true;
        // reply.status.message = "Paired successfully";
        // reply.status.gameID = response.gameID;
        // reply.status.opponent = response.opponentHandle;

        // Dummy response for now
        reply.status.success = true;
        reply.status.message = "Join Game request processed (no pairing yet)";
        reply.status.gameID = "dummy-game-id";
        reply.status.opponent = "dummy-opponent";

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
        // TODO: Retrieve game status for a given game
        return null;
    }
}
