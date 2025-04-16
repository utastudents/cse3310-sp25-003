package uta.cse3310.PageManager;

public class JoinGamePayload {
    public String playerHandle;
    public String action;        // join or wait
    public boolean opponentType;  // bot (0) or human (1)
    public String lobbyId;       // same as game ID if joining existing lobby
}
