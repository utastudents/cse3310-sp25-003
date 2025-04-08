package uta.cse3310.PageManager;

public class JoinGamePayload {
    public String playerHandle;
    public String action;        // join or wait
    public String opponentType;  // bot or human
    public String lobbyId;       // same as game ID if joining existing lobby
}
