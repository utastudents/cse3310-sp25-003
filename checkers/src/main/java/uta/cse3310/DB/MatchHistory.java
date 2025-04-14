package uta.cse3310.DB;

import java.sql.*;

public class MatchHistory {
    
    private int playerID;
    private String boardState;



    public int getplayerID(String userName) throws SQLException , ClassNotFoundException{

        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:uta\\cse3310\\DB\\Database.db");
        c.setAutoCommit(false);
        Statement stmt = c.createStatement();
      
        String sql = "SELECT * FROM USER_DATABASE WHERE USERNAME == "+userName+"";
        ResultSet rs = stmt.executeQuery(sql);

        int result = rs.getInt("PLAYERID");
        return result;
    }

    public void setplayerID(int playerID){}

    public String getboardState(String playerID){

        return boardState;
    }

    public void setboardState(){}

    public void saveGame(){}

    public void loadGame(){}
}

