package uta.cse3310.DB;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MatchHistory {

    private int id;
    private int blackId;
    private int redId;
    private String boardState;
    private int winnerId;
    private int losserId;

    // Default constructor
    public MatchHistory() {
        this.id = 0;
        this.blackId = 0;
        this.redId = 0;
        this.boardState = "";
        this.winnerId = 0;
        this.losserId = 0;
    }

    // Parameterized constructor
    public MatchHistory(int id, int blackId, int redId, String boardState, int winnerId, int losserId) {
        this.id = id;
        this.blackId = blackId;
        this.redId = redId;
        this.boardState = boardState;
        this.winnerId = winnerId;
        this.losserId = losserId;
    }

    public int getId() {
        return id;
    }

    public int getBlackPlayerID() {
        return blackId;
    }

    public int getRedPlayerID() {
        return redId;
    }

    public String getBoardState() {
        return boardState;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public int getLosserId() {
        return losserId;
    }

    public void setId(int id) {
        if (id >= 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("Match ID cannot be negative");
        }
    }

    public void setBlackId(int blackId) {
        if (blackId > 0) {
            this.blackId = blackId;
        } else {
            throw new IllegalArgumentException("Black player ID must be positive");
        }
    }

    public void setRedId(int redId) {
        if (redId > 0) {
            this.redId = redId;
        } else {
            throw new IllegalArgumentException("Red player ID must be positive");
        }
    }

    public void setBoardState(String boardState) {
        if (boardState != null && !boardState.trim().isEmpty()) {
            this.boardState = boardState;
        } else {
            throw new IllegalArgumentException("Board state cannot be null or empty");
        }
    }

    public void setWinnerId(int winnerId) {
        if (winnerId > 0) {
            this.winnerId = winnerId;
        } else {
            throw new IllegalArgumentException("Winner ID must be positive");
        }
    }

    public void setLosserId(int losserId) {
        if (losserId > 0) {
            this.losserId = losserId;
        } else {
            throw new IllegalArgumentException("Losser ID must be positive");
        }
    }

    public boolean isValid() {
        return id > 0 && blackId > 0 && redId > 0 && 
               !boardState.isEmpty() && winnerId > 0 && losserId > 0;
    }

    @Override
    public String toString() {
        return String.format("Match[ID=%d, Black=%d, Red=%d, Winner=%d, Losser=%d]", 
                           id, blackId, redId, winnerId, losserId);
    }
}