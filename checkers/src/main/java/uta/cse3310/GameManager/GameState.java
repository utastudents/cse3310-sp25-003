package uta.cse3310.GameManager;
import java.util.HashMap;
import java.util.Map;



public class GameState {
    private String gameId;
    private String currentPlayerId;
    private Map<Position, Piece> board;  
    public GameState(String gameId, String startingPlayerId) {
        this.gameId = gameId;
        this.currentPlayerId = startingPlayerId;
        this.board = new HashMap<>();
        initializeBoard();
    } 
    private void initializeBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = (row + 1) % 2; col < 8; col += 2) {
                board.put(new Position(row, col), new Piece("Player2", false));
            }
        }
        for (int row = 5; row < 8; row++) {
            for (int col = (row + 1) % 2; col < 8; col += 2) {
                board.put(new Position(row, col), new Piece("Player1", false));
            }
        }
    }
    public String getGameId() {
        return gameId;
    }
    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void switchTurn(String nextPlayerId) {
        this.currentPlayerId = nextPlayerId;
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }

    public void applyMove(Position from, Position to) {
        if (board.containsKey(from)) {
            Piece movingPiece = board.remove(from);
            board.put(to, movingPiece);
        }
    }

    public void removePiece(Position pos) {
        board.remove(pos);
    }

    public Piece getPieceAt(Position pos) {
        return board.get(pos);
    }

}
