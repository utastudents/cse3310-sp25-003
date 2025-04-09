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
    
    public UserEventReply handleLogin(UserEvent userEvent) {
        // Design: validate user with DB or create new user
        return null;
    }
    
    public UserEventReply handleJoinGame(UserEvent userEvent) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();
        reply.recipients.add(userEvent.id);

        // Temporary dummy info for testing
        reply.status.message = "Join Game event received";
        reply.status.gameID = "placeholder-game-id";
        reply.status.opponent = "Waiting for opponent...";

        return reply;
    }
    
    public UserEventReply handleGameMove(UserEvent userEvent) {
        // Design: parse move data and validate with GameManager
        return null;
    }
    
    public UserEventReply handleSummaryRequest(UserEvent userEvent) {
        // Design: get leaderboard data from DB
        return null;
    }
    
    public UserEventReply sendGameUpdate(Integer gameId, GameStatus gameStatus) {
        // Design: create reply with game status
        return null;
    }
    
    public boolean sendNotification(Integer userId, String message) {
        // Design: send notification to a specific user
        return false;
    }
    
    public int broadcastMessage(String message) {
        // Design: broadcast message to all users
        return 0;
    }
    
    public void updateWaitingPlayers(ArrayList<String> waitingPlayers) {
        // Design: update waiting players list
    }
    
    public GameStatus getGameStatus(Integer gameId) {
        // Design: get game status
        return null;
    }
}
