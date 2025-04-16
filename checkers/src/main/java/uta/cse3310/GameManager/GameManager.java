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
        b1 = new BotI();
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
            // NOTE: Bot I simply receives the current board and returns a Move.
            // If the Move == null, it can be assumed Bot I has no available moves
            // to make.
            // I'd imagine that it should be predetermined if the game can even
            // comtinue running before calling b1.onUserMove(board), and afterwards
            // it should be checked (whether it be in GameManager or another group's
            // code) if the game is over or not.
            // - Brant
            b1.onUserMove(board);
        }
    }

    // Optional: expose session or board to other classes if needed
    public GameSession getSession() {
        return session;
    }
}
