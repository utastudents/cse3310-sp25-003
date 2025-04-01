package uta.cse3310.PairUp.module;

import java.util.ArrayList;
import java.util.List;

public class Participant {
    // pairup/model/Participant.java
    private String playerId;    // null for bots
    private boolean isBot;
    
    public Participant(String playerId, boolean isBot) {
        this.playerId = playerId;
        this.isBot = isBot;
    }
    // Getters and setters
}

// pairup/model/Lobby.java
public class Lobby {
    private String lobbyId;
    private Participant[] slots = new Participant[2];
    private boolean isClosed;
    private long creationTime;
    
    public Lobby(String lobbyId) {
        this.lobbyId = lobbyId;
        this.creationTime = System.currentTimeMillis();
    }
    // Getters and setters
}

// pairup/exception/LobbyException.java
public class LobbyException extends Exception {
    public LobbyException(String message) {
        super(message);
    }
}

// pairup/service/PairUpModule.java
public class PairUpModule {
    private List<Lobby> activeLobbies = new ArrayList<>();
    private static final int MAX_LOBBIES = 10;
    private static final int IDLE_TIMEOUT = 300; // X seconds
    
    // Method implementations below
}
}
