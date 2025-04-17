package uta.cse3310.PageManager;

import java.util.ArrayList;

import uta.cse3310.DB.DB;
import uta.cse3310.PairUp.PairUp;

public class PageManager {
    // Database instance
    private DB db;

    // PairUp instance
    private PairUp pu;

    // Turn tracker (global, not unique per client or game)
    private Integer turn = 0;

    // Constructor
    public PageManager() {
        db = new DB();
        pu = new PairUp(db); // Pass the database instance to PairUp
    }

    /**
     * Handles the "Join Game" event.
     * 
     * @param U UserEvent containing event details
     * @return UserEventReply with the response
     */
    private UserEventReply handleJoinGame(UserEvent U) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();
        reply.recipients.add(U.id);

        // Temporary dummy info for testing
        reply.status.message = "Join Game event received";
        reply.status.gameID = "placeholder-game-id";
        reply.status.opponent = "Waiting for opponent...";

        return reply;
    }

    /**
     * Handles the "Login" event.
     * 
     * @param payload LoginPayload containing login details
     * @return UserEventReply with the response
     */
    private UserEventReply handleLogin(LoginPayload payload) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();

        // Dummy logic for login validation
        if (payload.username.equals("testUser") && payload.password.equals("password123")) {
            reply.status.message = "Login successful";
        } else {
            reply.status.message = "Invalid username or password";
        }

        return reply;
    }

    /**
     * Handles the "Game Status" event.
     * 
     * @param gameID The ID of the game to fetch status for
     * @return UserEventReply with the game status
     */
    private UserEventReply handleGameStatus(String gameID) {
        UserEventReply reply = new UserEventReply();
        reply.status = new GameStatus();
        reply.recipients = new ArrayList<>();

        // Dummy logic for game status
        reply.status.gameID = gameID;
        reply.status.message = "Game is in progress";
        reply.status.turn = turn;

        return reply;
    }

    /**
     * Processes input from the user.
     * 
     * @param U UserEvent containing input details
     * @return UserEventReply with the response
     */
    public UserEventReply ProcessInput(UserEvent U) {
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
        ret.recipients.add(U.id);

        return ret;
    }
}
