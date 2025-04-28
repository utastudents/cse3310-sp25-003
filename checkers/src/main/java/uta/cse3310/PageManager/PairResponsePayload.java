package uta.cse3310.PageManager;

public class PairResponsePayload {
    public String gameID;
    public String opponentHandle;
    
    public PairResponsePayload() {
    }
    
    public PairResponsePayload(String gameID, String opponentHandle) {
        this.gameID = gameID;
        this.opponentHandle = opponentHandle;
    }
} 