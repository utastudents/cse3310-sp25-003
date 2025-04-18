package uta.cse3310.GameManager;

public class Move {
    private final Position from;
    private final Position to;
    private final String playerId;
    private boolean isCapture;
    private boolean isKingMove;

    public Move(Position from, Position to, String playerId) {
        this.from = from;
        this.to = to;
        this.playerId = playerId;
        this.isCapture = false;
        this.isKingMove = false;
    }

    // Constructor with row/col coordinates
    public Move(int fromRow, int fromCol, int toRow, int toCol, String playerId) {
        this(new Position(fromRow, fromCol), new Position(toRow, toCol), playerId);
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public String getPlayerId() {
        return playerId;
    }

    public boolean isCapture() {
        return isCapture;
    }

    public void setCapture(boolean isCapture) {
        this.isCapture = isCapture;
    }

    public boolean isKingMove() {
        return isKingMove;
    }

    public void setKingMove(boolean isKingMove) {
        this.isKingMove = isKingMove;
    }

    /**
     * Checks if this move is a jump move (distance of 2 squares diagonally)
     */
    public boolean isJump() {
        int dx = Math.abs(to.getX() - from.getX());
        int dy = Math.abs(to.getY() - from.getY());
        return dx == 2 && dy == 2;
    }

    @Override
    public String toString() {
        return "Move[from=" + from + ", to=" + to + ", player=" + playerId + "]";
    }
}
