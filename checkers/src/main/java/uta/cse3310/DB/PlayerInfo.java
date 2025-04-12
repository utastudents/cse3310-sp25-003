package uta.cse3310.DB;
// import uta.cse3310.DB.*;


import java.util.Vector;

import java.sql.*;


public class PlayerInfo {
    
    private String userName;
    private String passWord;
    private String email;
    private Vector<Boolean> winLoss = new Vector<>();
    private int wins;
    private int losses;
    private double elo;

    // public PlayerInfo(){
    //     userName = "";
    //     passWord = "";
    //     email = "";
    //     wins = 0;
    //     losses = 0;
    //     elo = 0;

    // }

   
    public String getUserName(int userID) throws SQLException , ClassNotFoundException{
        ResultSet rs = DB.getSpecificData(userID, "USER");
        String result = rs.getString("USERNAME");
        return result;
    }

    public void setUserName(Statement stmt, String name , int userID) throws SQLException , ClassNotFoundException{
        DB.setSpecificDataString(stmt, userID, "USERNAME", name, "USER");

    }

    
    public String getPassWord(int userID) throws SQLException , ClassNotFoundException{
        ResultSet rs = DB.getSpecificData(userID, "USER");
        String result = rs.getString("PASSWORD");
        return result;
    }

    public void setPassWord(Statement stmt, String pass, int userID) throws SQLException, ClassNotFoundException {
        DB.setSpecificDataString(stmt, userID, "PASSWORD", pass, "USER");
    }

    public static String getEmail(int userID) throws SQLException, ClassNotFoundException{
        ResultSet rs = DB.getSpecificData(userID,"USER");
        String result = rs.getString("EMAIL");
        
        return result;
    }

    public void setEmail(Statement stmt, String email, int playerID) throws SQLException, ClassNotFoundException{
        DB.setSpecificDataString(stmt, playerID, "EMAIL", email, "USER");
    }

    public int getWins(int wins){

        return wins;
    }

    public void setWins(int wins){

    }

    public int getLosses(int losses){

        return losses;
    }

    public void setLosses(int losses){

    }

    public double getElo(double elo){

        return elo;
    }

    public void setElo(double elo){

    }

    public double calculateElo(int win , int loss){

        return elo;
    }

}
