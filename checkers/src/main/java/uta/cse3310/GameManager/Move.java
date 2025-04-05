package uta.cse3310.GameManager;

public class Move {

    // Attributes representing the starting position and ending position of the move
    private Position from;
    private Position to;
    private String playerId;

    // Constructor to initialize the Move object
    public Move(Position from, Position to, String playerId) {
        this.from = from;
        this.to = to;
        this.playerId = playerId;
    }

    // Getter for 'from' position
    public Position getFrom() {
        return from;
    }

    // Setter for 'from' position
    public void setFrom(Position from) {
        this.from = from;
    }

    // Getter for 'to' position
    public Position getTo() {
        return to;
    }

    // Setter for 'to' position
    public void setTo(Position to) {
        this.to = to;
    }

    // Getter for the player ID who made the move
    public String getPlayerId() {
        return playerId;
    }

    // Setter for the player ID
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    // Optionally, you can override the toString() method to print a friendly representation
    @Override
    public String toString() {
        return "Move [from=" + from + ", to=" + to + ", playerId=" + playerId + "]";
    }
}
