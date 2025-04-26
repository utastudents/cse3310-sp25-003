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

    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    public int getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(int winnerId) {
        this.winnerId = winnerId;
    }

    public int getLosserId() {
        return losserId;
    }

    public void setLosserId(int losserId) {
        this.losserId = losserId;
    }

}
