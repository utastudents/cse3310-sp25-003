package uta.cse3310.PairUp.module;

/* pairup/model/Participant.java */
public class Participant {
    private String playerId;    // null if it's a bot
    private boolean isBot;

    public Participant(String playerId, boolean isBot) {
        this.playerId = playerId;
        this.isBot = isBot;
    }

    public String getPlayerId() {
        return playerId;
    }

    public boolean isBot() {
        return isBot;
    }
}
