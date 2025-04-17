package uta.cse3310.GameManager;

public class Piece {
    private String playerId;
    private boolean isKing;

    public Piece(String playerId, boolean isKing) {
        this.playerId = playerId;
        this.isKing = isKing;
    }

    public String getPlayerId() {
        return playerId;
    }

    public boolean isKing() {
        return isKing;
    }

    public void promoteToKing() {
        this.isKing = true;
    }

    public PieceColor getColor() {
        return playerId.equals("BotI") ? PieceColor.RED : PieceColor.BLACK;
    }
}
