package uta.cse3310.DB;
import java.util.Vector;

public class PlayerInfo {
    
    private String userName;
    private String passWord;
    private String email;
    private Vector<Boolean> winLoss = new Vector<>();
    private int wins;
    private int losses;
    private double elo;

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

    public String getEmail(String email){

        return email;
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

    public double getElo(){

        return elo;
    }

    public void setElo(double elo){
        this.elo = elo;
    }

    public double calculateElo(int win , int loss){
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
    }

}
