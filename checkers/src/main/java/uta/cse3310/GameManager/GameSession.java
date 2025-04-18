package uta.cse3310.GameManager;

public class GameSession {

    private GameState state;

    public GameSession(String gameId) {
        // Start with Player1 as the first player
        this.state = new GameState(gameId, "Player1");
    }

    public GameState getGameState() {
        return state;
    }

    public void applyMove(Move move) {
        Position from = move.getFrom();
        Position to = move.getTo();
        state.applyMove(from.getX(), from.getY(), to.getX(), to.getY());
    }

    public void removePiece(Position pos) {
        state.removePiece(pos.getX(), pos.getY());
    }

    public char[][] getBoardAsCharArray() {
        return state.getBoardAsArray(); // returns char[][] for BotI or display
    }

    public String getCurrentPlayerId() {
        return state.getCurrentPlayerId();
    }

    public void switchPlayer(String nextPlayerId) {
        state.switchTurn(nextPlayerId);
    }
}
