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
    private Map<Integer, game_status> activeGames;
    
    // cache for waiting players
    private ArrayList<String> waitingPlayers;
    
    public UserEventReply ProcessInput(UserEvent userEvent) {
        UserEventReply ret = new UserEventReply();
        ret.status = new game_status();
        
        // design: This method will use the userEvent.type to determine how to handle the request
        
        // temporary logic to maintain backward compatibility
        ret.recipients = new ArrayList<>();
        ret.recipients.add(userEvent.id);
        
        return ret;
    }
    
    public UserEventReply handleLogin(UserEvent userEvent) {
        // Design: validate user with DB or create new user
        return null;
    }
    
    public UserEventReply handleJoinGame(UserEvent userEvent) {
        // Design: add player to waiting list via PairUp
        return null;
    }
    
    public UserEventReply handleGameMove(UserEvent userEvent) {
        // Design: parse move data and validate with GameManager
        return null;
    }
    
    public UserEventReply handleSummaryRequest(UserEvent userEvent) {
        // Design: get leaderboard data from DB
        return null;
    }
    
    public UserEventReply sendGameUpdate(Integer gameId, game_status gameStatus) {
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
    
    public game_status getGameStatus(Integer gameId) {
        // Design: get game status
        return null;
    }
    
    public PageManager() {
        db = new DB();
        pairUp = new PairUp(db);
        activeGames = new HashMap<>();
        waitingPlayers = new ArrayList<>();
    }
}
