package uta.cse3310.GameManager;

public class Player {
    String playerId, displayName;
    boolean isBot;
    int score;
    
// Gamemanager/model/Player.java 
 Player(String playerId, String displayName, boolean isBot) {
        this.playerId = playerId;
        this.displayName = displayName;
        this.isBot = isBot;
        this.score = 0;
    }
    
//Player ID
    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }
//Display the name on screen
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
//If player is a bot true or false 
    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }
// Tracks the score for the player or bot 
    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

//Return information
    @Override
    public String toString() {
        return "Player{" +
                "Player ID='" + playerId + '\'' +
                ",Name = '" + displayName + '\'' +
                ",is Bot =" + isBot +
                ",Score=" + score +
                '}';
    }
}
