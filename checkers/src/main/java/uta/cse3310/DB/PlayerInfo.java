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

    public int getWins(int playerID) throws SQLException, ClassNotFoundException{
        ResultSet rs = DB.getSpecificData(playerID, "USER");
        int result = rs.getInt("WINS");
        
        return result;
    }

    public void setWins(Statement stmt, int wins, int playerID) throws SQLException, ClassNotFoundException{
        DB.setSpecificDataInt(stmt, playerID, "WINS", wins, "USER");
    }

    public int getLosses(int playerID) throws SQLException, ClassNotFoundException{
        ResultSet rs = DB.getSpecificData(playerID,"USER");
        int result = rs.getInt("LOSSES");

        return result;
    }

    public void setLosses(Statement stmt, int losses, int playerID) throws SQLException, ClassNotFoundException{
        DB.setSpecificDataInt(stmt, playerID, "LOSSES", losses, "USER");
    }
    
    public double getElo() {

        return elo;
    }

    public void setElo(double elo) {
        this.elo = elo;
    }

    public double calculateElo(int win, int loss) {
        double kFactor = 32; // K-factor used for Elo rating systems
        int opponentElo = 1200; // Example opponent Elo

        // Calculate expected score (score = 1/(1 + 10^((opponent Elo - Elo) / 400)))
        double expectedScore = 1 / (1 + Math.pow(10, (opponentElo - this.elo) / 400.0));

        // Calculate actual score based on win/loss
        double actualScore = win > loss ? 1 : 0; // 1 for win, 0 for loss

        // Update Elo rating (Elo = Elo + K-factor * (actual score - expected score))
        this.elo = this.elo + kFactor * (actualScore - expectedScore);

        // Update wins and losses
        this.wins += win;
        this.losses += loss;

        return this.elo;

        // USAGE:
        // PlayerInfo player = new PlayerInfo();
        // player.setElo(1200); // Initial Elo (example value)
        // player.setWins(0);
        // player.setLosses(0);

        // double newElo = player.calculateElo(1, 0); // Player wins the match
        // System.out.println("Updated Elo: " + newElo);
        // System.out.println("Total Wins: " + player.getWins(0));
        // System.out.println("Total Losses: " + player.getLosses(0));
    }

}
