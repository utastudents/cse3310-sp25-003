package uta.cse3310.GameManager;

import java.util.ArrayList;
import java.util.List;


import uta.cse3310.GameManager.Position;
import uta.cse3310.Bot.BotI.BotI;
import uta.cse3310.Bot.BotII.BotII;
import uta.cse3310.GamePlay.GamePlay;
import uta.cse3310.GameTermination.GameStatus;
import uta.cse3310.GameTermination.GameTermination;

public class GameManager {

    private GamePlay gp;
    private BotI b1;
    private BotII b2;
    private GameTermination gameTermination;

    // Only ONE active game session
    private GameSession session;

    // Track moves for game termination checks
    private List<Move> moveHistory;

    public GameManager() {
        gp = new GamePlay();
        b1 = new BotI();
        b2 = new BotII();
        gameTermination = new GameTermination();
        moveHistory = new ArrayList<>();
    }

    // Create a single game session
    public void createGame() {
        System.out.println("Game created.");
        session = new GameSession("Game001"); // Hardcoded ID or just use null
        moveHistory.clear();
        gameTermination.reset();
    }
    
    // Create a game session with a specific game ID and player ID
    public GameState createGame(String gameId, String playerId) {
        System.out.println("Game created with ID: " + gameId + " for player: " + playerId);
        session = new GameSession(gameId);
        session.getGameState().switchTurn(playerId);
        moveHistory.clear();
        gameTermination.reset();
        return session.getGameState();
    }

    // Handle a move from the user and notify BotI
    public boolean receiveMove(Move userMove) {
        if (session != null) {
            GameState state = session.getGameState();

            Position from = userMove.getFrom();
            Position to = userMove.getTo();



            // Check if the move is valid
            if (!gp.isValidMove(state, userMove)) {
            System.out.println("Invalid move attempted: " + userMove);
            // Send back a boolean to signal a failure to move due to its legality
            return false;
            }

            state.applyMove(from.getX(), from.getY(), to.getX(), to.getY());
            

            // Check if the move is a jump (capture)
            if (gp.isJump(state, userMove)) { //Changed to use GamePlay's isJump()
                userMove.setCapture(true);
                // Remove the captured piece
                int capturedX = from.getX() + (to.getX() - from.getX()) / 2;
                int capturedY = from.getY() + (to.getY() - from.getY()) / 2;
                state.removePiece(capturedX, capturedY);
            }

            // Track move for game termination checks
            moveHistory.add(userMove);

            System.out.println("Player move applied: " + from.getX() + "," + from.getY() +
                    " -> " + to.getX() + "," + to.getY());

            // Check for game termination
            GameStatus status = gameTermination.checkForGameEnd(state, moveHistory);
            if (status != GameStatus.ONGOING) {
                handleGameOver(status);
                return true;
            }

            // Switch to bot
            state.switchTurn("BotI");

            // Notify bot with current board and move
            notifyBot1Move(userMove);
            return true;
        } else {
            System.out.println("No active game session.");
            return false;
        }
    }

    // Pass the board to BotI after user move
    private void notifyBot1Move(Move userMove) {
        if (session != null) {
            char[][] board = session.getGameState().getBoardAsArray();
            // Handle bot move after user move
            Move botMove = b1.onUserMove(board);

            // Check if bot has available moves
            if (botMove != null) {
                applyBotMove(botMove);
            } else {
                // Bot has no moves - check if game is over
                GameStatus status = gameTermination.checkForGameEnd(session.getGameState(), moveHistory);
                if (status != GameStatus.ONGOING) {
                    handleGameOver(status);
                }
            }
        }
    }

    // Apply bot's move to the game state
    private void applyBotMove(Move botMove) {
        GameState state = session.getGameState();

        Position from = botMove.getFrom();
        Position to = botMove.getTo();

        // Apply bot's move
        state.applyMove(from.getX(), from.getY(), to.getX(), to.getY());

        // Check if the move is a jump (capture)
        if (Math.abs(to.getX() - from.getX()) == 2 && Math.abs(to.getY() - from.getY()) == 2) {
            botMove.setCapture(true);
            // Remove the captured piece
            int capturedX = from.getX() + (to.getX() - from.getX()) / 2;
            int capturedY = from.getY() + (to.getY() - from.getY()) / 2;
            state.removePiece(capturedX, capturedY);
        }

        // Track move for game termination checks
        moveHistory.add(botMove);

        // Check for game termination
        GameStatus status = gameTermination.checkForGameEnd(state, moveHistory);
        if (status != GameStatus.ONGOING) {
            handleGameOver(status);
            return;
        }

        // Switch turn back to player
        state.switchTurn("Player1");
    }

    // Handle game over scenario
    private void handleGameOver(GameStatus status) {
        System.out.println("Game over! Result: " + status);
        System.out.println("Reason: " + gameTermination.getTerminationReason());

        switch (status) {
            case RED_WIN:
                System.out.println("Player (RED) wins!");
                break;
            case BLACK_WIN:
                System.out.println("Bot (BLACK) wins!");
                break;
            case DRAW:
                System.out.println("Game ended in a draw.");
                break;
            default:
                // Should not reach here
                break;
        }
    }

    // Optional: expose session or board to other classes if needed
    public GameSession getSession() {
        return session;
    }

    // Get the current game status
    public GameStatus getGameStatus() {
        if (session == null) {
            return null;
        }
        return gameTermination.checkForGameEnd(session.getGameState(), moveHistory);
    }
}
