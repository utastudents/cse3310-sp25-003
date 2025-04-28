package uta.cse3310.PageManager;

public class JoinGamePayload {
    public String entity1;
    public String entity2;
    public boolean opponentType1; // "bot" (false) or "human" (true)
    public boolean opponentType2; // "bot" (false) or "human" (true)
    public String lobbyId; // shared lobby/game ID
    public String action; // "join" or "wait" or other possible instructions
    
    public JoinGamePayload() {
    }
    
    public JoinGamePayload(String playerId, boolean isBot) {
        this.entity1 = playerId;
        this.opponentType1 = !isBot;
        this.action = "wait";
    }
} 