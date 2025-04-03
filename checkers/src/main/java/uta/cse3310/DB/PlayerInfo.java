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

    public double getElo(double elo){

        return elo;
    }

    public void setElo(double elo){

    }

    public double calculateElo(int win , int loss){

        return elo;
    }

}
