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
