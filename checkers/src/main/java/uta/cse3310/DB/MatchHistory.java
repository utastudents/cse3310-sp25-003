package uta.cse3310.DB;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MatchHistory {

    private int playerID;
    private String boardState;


    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String loadGame(int matchID)throws SQLException, ClassNotFoundException {
        ResultSet rs = DB.getSpecificData(matchID, "MATCH");
        String result = rs.getString("BOARD_STATE");
        return result;

    }

    public void saveGame(String boardState, int matchID, Statement stmt) throws SQLException, ClassNotFoundException{
        DB.setSpecificDataString(stmt, matchID, "BOARD_STATE", boardState, "MATCH");
    }

}
