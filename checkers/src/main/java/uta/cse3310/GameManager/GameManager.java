package uta.cse3310.GameManager;

import uta.cse3310.GamePlay.GamePlay;
import uta.cse3310.Bot.BotI.BotI;
import uta.cse3310.Bot.BotII.BotII;

public class GameManager {

    private GamePlay gp;
    private BotI b1;
    private BotII b2;

    //Only ONE active game session
    private GameSession session;

    public GameManager() {
        gp = new GamePlay();
        b1 = new BotI(this);  // BotI expects GameManager reference
        b2 = new BotII();
    }

    //Create a single game session
    public void createGame() {
        System.out.println("Game created.");
        session = new GameSession("Game001"); // Hardcoded ID or just use null
    }

    //Handle a move from the user and notify BotI
    public void receiveMove(Move userMove) {
        if (session != null) {
            GameState state = session.getGameState();

            Position from = userMove.getFrom();
            Position to = userMove.getTo();

            // Apply user's move
            state.applyMove(from.getX(), from.getY(), to.getX(), to.getY());

            System.out.println("Player move applied: " + from.getX() + "," + from.getY() +
                               " -> " + to.getX() + "," + to.getY());

            // Switch to bot
            state.switchTurn("BotI");

            // Notify bot with current board and move
            notifyBot1Move(userMove);
        } else {
            System.out.println("No active game session.");
        }
    }

    // Pass the board to BotI after user move
    private void notifyBot1Move(Move userMove) {
        if (session != null) {
            char[][] board = session.getGameState().getBoardAsArray();
            b1.onUserMove(board, userMove);
        }
    }

    // Optional: expose session or board to other classes if needed
    public GameSession getSession() {
        return session;
    }
}
