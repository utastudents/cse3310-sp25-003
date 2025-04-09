package uta.cse3310.DB;

import java.sql.Connection;
import java.sql.SQLException;
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

    public String getUserName(String name){

        return userName;
    }

    public void setUserName(String name){

    }

    public String getPassWord(String pass){

        return passWord;
    }

    public void setPassWord(String pass){

    }

    public static String getEmail(int userID) throws SQLException, ClassNotFoundException{
        
        Connection c = DB.initConnection();
        java.sql.Statement stmt = c.createStatement();

        ResultSet rs = DB.getSpecificUserData(stmt, userID);
        String result = rs.getString("EMAIL");
        
        return result;
    }

    public void setEmail(String email){

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
