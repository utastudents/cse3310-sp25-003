package uta.cse3310.Model;


/* Gamemanager/model/Player.java */
public Player(String playerId, String displayName, boolean isBot) {
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
//Display the name
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
//If player is a bot
    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }
// Score
    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

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
