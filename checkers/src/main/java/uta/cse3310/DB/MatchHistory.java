package uta.cse3310.DB;

import java.sql.*;

public class MatchHistory {
    
    private int blackPlayerID;
    private int redPlayerID;

    private String boardState;



    public int getBlackPlayerID(int matchID) throws SQLException , ClassNotFoundException{

        ResultSet rs = DB.getSpecificData(matchID, "MATCH");

        int result = rs.getInt("BLACKPLAYERID");
        // return result;
        return 0;
    }

    public void setplayerID(int playerID){}

    public String getboardState(String playerID){

        return boardState;
    }

    public void setboardState(){}

    public void saveGame(){}

    public void loadGame(){}
}

