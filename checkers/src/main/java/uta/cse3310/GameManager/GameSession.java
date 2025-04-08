package uta.cse3310.GameManager;

// [TODO] : Replace this with the actual GameSession class
class GameSession {
    private String gameId;
    private String currentPlayer;
    private String boardState;

    public GameSession(String gameId) {
        this.gameId = gameId;
        this.currentPlayer = "Player1"; // Placeholder for current player
        this.boardState = ""; // Placeholder for board state
    }

    public String getGameId() {
        return gameId;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getBoardState() {
        return boardState;
    }
}