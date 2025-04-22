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


    public int getBlackPlayerID(int matchID) throws SQLException , ClassNotFoundException {
        ResultSet rs = DB.getSpecificData(matchID , "MATCH_DATABASE");
        return rs.getInt("BLACKPLAYERID");
    }

    public int getRedPlayerID(int matchID) throws SQLException , ClassNotFoundException {
        ResultSet rs = DB.getSpecificData(matchID , "MATCH_DATABASE");
        return rs.getInt("REDPLAYERID");
    }

    public void setBlackPlayerID(Statement stmt, int playerID ,int matchID) throws SQLException , ClassNotFoundException{
        DB.setSpecificDataInt(stmt,matchID,"BLACKPLAYERID",playerID,"MATCH_DATABASE");
    }

    public void seRedPlayerID(Statement stmt,int playerID, int matchID) throws SQLException , ClassNotFoundException{
        DB.setSpecificDataInt(stmt,matchID,"REDPLAYERID",playerID,"MATCH_DATABASE");
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
