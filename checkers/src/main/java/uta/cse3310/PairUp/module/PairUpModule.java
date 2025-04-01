package uta.cse3310.PairUp.module;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class PairUpModule {
    // PAIRUP-004: Create empty lobby
    public String createLobby(String playerId) throws LobbyException {
        if (activeLobbies.size() >= MAX_LOBBIES) {
            throw new LobbyException("Maximum lobby limit reached");
        }
        String lobbyId = UUID.randomUUID().toString();
        Lobby lobby = new Lobby(lobbyId);
        lobby.getSlots()[0] = new Participant(playerId, false);
        activeLobbies.add(lobby);
        return lobbyId;
    }

    // PAIRUP-005: Join existing lobby
    public boolean joinLobby(String lobbyId, String playerId) throws LobbyException {
        Lobby lobby = findLobby(lobbyId);
        if (lobby == null) throw new LobbyException("Lobby not found");
        if (lobby.isClosed()) throw new LobbyException("Lobby is closed");
        if (lobby.getSlots()[1] != null) throw new LobbyException("Lobby is full");
        
        lobby.getSlots()[1] = new Participant(playerId, false);
        checkLobbyFull(lobby);
        return true;
    }

    // PAIRUP-006: Create lobby with bot
    public String createBotLobby(String playerId) throws LobbyException {
        String lobbyId = createLobby(playerId);
        Lobby lobby = findLobby(lobbyId);
        lobby.getSlots()[1] = new Participant(null, true);
        lobby.setClosed(true);
        notifyGameManager(lobby);
        return lobbyId;
    }

    // PAIRUP-008: Refresh lobby list
    public List<Lobby> refreshLobbies() {
        return activeLobbies.stream()
            .filter(l -> !l.isClosed())
            .collect(Collectors.toList());
    }

    // PAIRUP-010: Cancel lobby
    public void cancelLobby(String lobbyId) throws LobbyException {
        Lobby lobby = findLobby(lobbyId);
        if (lobby == null) throw new LobbyException("Lobby not found");
        activeLobbies.remove(lobby);
        notifySubscribers(lobbyId, "cancelled");
    }

    // Helper method
    private Lobby findLobby(String lobbyId) {
        return activeLobbies.stream()
            .filter(l -> l.getLobbyId().equals(lobbyId))
            .findFirst()
            .orElse(null);
    }

    private void checkLobbyFull(Lobby lobby) {
        if (lobby.getSlots()[0] != null && lobby.getSlots()[1] != null) {
            lobby.setClosed(true);
            notifyGameManager(lobby);
        }
    }

    // Placeholder for external notifications
    private void notifyGameManager(Lobby lobby) { /* Implementation */ }
    private void notifySubscribers(String lobbyId, String status) { /* Implementation */ }
}
    
}
