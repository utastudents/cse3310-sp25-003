package uta.cse3310.GameManager;

public class Move {
    private final Position from;
    private final Position to;
    private final String playerId;

    public Move(Position from, Position to, String playerId) {
        this.from = from;
        this.to = to;
        this.playerId = playerId;
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

    @Override
    public String toString() {
        return "Move[from=" + from + ", to=" + to + ", player=" + playerId + "]";
    }
}
