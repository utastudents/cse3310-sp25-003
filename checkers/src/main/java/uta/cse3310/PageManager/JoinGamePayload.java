package uta.cse3310.PageManager;

public class JoinGamePayload {
    public String entity1;
    public String entity2;
    public boolean opponentType1; // "bot" (0) or "human" (1)
    public boolean opponentType2; // "bot" (0) or "human" (1)
    public String lobbyId; // shared lobby/game ID
    public String action; // "join" or "wait" or other possible instructions
}
