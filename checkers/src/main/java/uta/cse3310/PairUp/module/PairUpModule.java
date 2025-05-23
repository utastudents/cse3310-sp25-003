package uta.cse3310.PairUp.module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import uta.cse3310.PageManager.PairResponsePayload;
import uta.cse3310.PageManager.JoinGamePayload;


/* PAIRUP-001/002: Manage list of lobbies, each with up to 2 participants (human or bot) */
public class PairUpModule {

    private List<Lobby> activeLobbies = new ArrayList<>();
    private static final int MAX_LOBBIES = 10;
    private static final long IDLE_TIMEOUT = 300_000; // 5 minutes in ms

    /* PAIRUP-003: Create lobby with 1 human. */
    public String createLobby(String playerId) throws LobbyException {
        if (activeLobbies.size() >= MAX_LOBBIES)
            throw new LobbyException("Max lobby limit reached");
        String lid = UUID.randomUUID().toString();
        Lobby lobby = new Lobby(lid);
        lobby.getSlots()[0] = new Participant(playerId, false);
        activeLobbies.add(lobby);
        storeOrUpdateLobby(lobby); // optional DB
        return lid;
    }

    /* PAIRUP-004: Join existing lobby if vacant. */
    public boolean joinLobby(String lobbyId, String playerId) throws LobbyException {
        Lobby lobby = findLobby(lobbyId);
        if (lobby == null)
            throw new LobbyException("Lobby not found");
        if (lobby.isClosed())
            throw new LobbyException("Lobby is closed");
        if (lobby.getSlots()[1] != null)
            throw new LobbyException("Lobby is full");
        lobby.getSlots()[1] = new Participant(playerId, false);
        storeOrUpdateLobby(lobby);
        checkLobbyFull(lobby);
        return true;
    }

    /* PAIRUP-005: Create lobby pre-filled with bot, then close. */
    public String createBotLobby(String playerId) throws LobbyException {
        String lid = createLobby(playerId);
        Lobby lobby = findLobby(lid);
        if (lobby != null && lobby.getSlots()[1] == null) {
            lobby.getSlots()[1] = new Participant(null, true);
            lobby.setClosed(true);
            storeOrUpdateLobby(lobby);
            notifyGameManager(lobby); // PAIRUP-007
        }
        return lid;
    }

    /* 2-bots scenario (PAIRUP-002). */
    public String createDoubleBotLobby() throws LobbyException {
        if (activeLobbies.size() >= MAX_LOBBIES)
            throw new LobbyException("Max lobby limit reached");
        String lid = UUID.randomUUID().toString();
        Lobby lobby = new Lobby(lid);
        lobby.getSlots()[0] = new Participant(null, true);
        lobby.getSlots()[1] = new Participant(null, true);
        lobby.setClosed(true);
        activeLobbies.add(lobby);
        storeOrUpdateLobby(lobby);
        notifyGameManager(lobby);
        return lid;
    }

    /* PAIRUP-009: Return a list of open lobbies. */
    public List<Lobby> refreshLobbies() {
        return activeLobbies.stream()
                .filter(l -> !l.isClosed())
                .collect(Collectors.toList());
    }

    /* PAIRUP-010/011: Cancel an open lobby. */
    public void cancelLobby(String lobbyId) throws LobbyException {
        Lobby lobby = findLobby(lobbyId);
        if (lobby == null)
            throw new LobbyException("Lobby not found");
        activeLobbies.remove(lobby);
        notifySubscribers(lobbyId, "cancelled");
    }

    /* PAIRUP-008: Handle user quitting a lobby before/after it's closed. */
    public void handleUserQuit(String lobbyId, String playerId) throws LobbyException {
        Lobby lobby = findLobby(lobbyId);
        if (lobby == null)
            throw new LobbyException("Lobby not found");
        if (!lobby.isClosed()) {
            if (lobby.getSlots()[0] != null && lobby.getSlots()[0].getPlayerId() != null
                    && lobby.getSlots()[0].getPlayerId().equals(playerId)) {
                lobby.getSlots()[0] = null;
            } else if (lobby.getSlots()[1] != null && lobby.getSlots()[1].getPlayerId() != null
                    && lobby.getSlots()[1].getPlayerId().equals(playerId)) {
                lobby.getSlots()[1] = null;
            }
            if (lobby.getSlots()[0] == null && lobby.getSlots()[1] == null) {
                activeLobbies.remove(lobby);
                notifySubscribers(lobbyId, "userQuitAllGone");
            } else {
                storeOrUpdateLobby(lobby);
                notifySubscribers(lobbyId, "userQuit");
            }
        } else {
            notifySubscribers(lobbyId, "quitInClosedLobby");
        }
    }

    /* PAIRUP-014: Remove lobbies idle too long (schedule call). */
    public void removeIdleLobbies() {
        long now = System.currentTimeMillis();
        activeLobbies.removeIf(lobby -> {
            boolean expired = (now - lobby.getCreationTime()) > IDLE_TIMEOUT;
            if (expired)
                notifySubscribers(lobby.getLobbyId(), "idleRemoved");
            return expired;
        });
    }

    /*
     * PAIRUP-015: Placeholder logic for pairing 2 idle players from separate
     * lobbies.
     */
    public void checkIdlePlayersFromDifferentLobbies() {
        // Gather lonely human players
        List<Lobby> lonelyLobbies = activeLobbies.stream()
                .filter(l -> !l.isClosed())
                .filter(l -> {
                    Participant[] slots = l.getSlots();
                    int count = 0;
                    for (Participant p : slots) {
                        if (p != null && !p.isBot())
                            count++;
                    }
                    return count == 1;
                })
                .collect(Collectors.toList());

        // Pair them two at a time
        while (lonelyLobbies.size() >= 2) {
            Lobby lobbyA = lonelyLobbies.remove(0);
            Lobby lobbyB = lonelyLobbies.remove(0);

            Participant playerA = getHumanFromLobby(lobbyA);
            Participant playerB = getHumanFromLobby(lobbyB);

            // Create new lobby
            String newLobbyId = UUID.randomUUID().toString();
            Lobby newLobby = new Lobby(newLobbyId);
            newLobby.getSlots()[0] = playerA;
            newLobby.getSlots()[1] = playerB;
            newLobby.setClosed(true); // full lobby

            // Add to active list
            activeLobbies.add(newLobby);
            storeOrUpdateLobby(newLobby);

            // Remove old lobbies
            activeLobbies.remove(lobbyA);
            activeLobbies.remove(lobbyB);
            notifySubscribers(lobbyA.getLobbyId(), "merged");
            notifySubscribers(lobbyB.getLobbyId(), "merged");

            // Notify game manager
            notifyGameManager(newLobby);
        }
    }

    // Helper method to extract the human player from a lobby
    private Participant getHumanFromLobby(Lobby lobby) {
        for (Participant p : lobby.getSlots()) {
            if (p != null && !p.isBot()) {
                return p;
            }
        }
        return null;
    }

    //Helper for getting userID
    private String findOpponentHandle(Lobby lobby, String currentPlayerId) {
    for (Participant p : lobby.getSlots()) {
        if (p != null && !p.isBot() && !p.getPlayerId().equals(currentPlayerId)) {
            return p.getPlayerId();
        }
    }
    return null;
}

    public boolean checkLobbyFull(String lobbyId) throws LobbyException {
        Lobby lobby = findLobby(lobbyId);
        if (lobby == null) {
            throw new LobbyException("Lobby not found");
        }
        return lobby.isFull();
    }

    // Helper method for testing
    void addLobby(Lobby lobby) {
        activeLobbies.add(lobby);
    }

    private Lobby findLobby(String lobbyId) {
        System.out.println(Arrays.toString(activeLobbies.toArray()));
        return activeLobbies.stream()
                .filter(l -> l.getLobbyId().equals(lobbyId))
                .findFirst()
                .orElse(null);
    }

    private void checkLobbyFull(Lobby lobby) {
        if (lobby.getSlots()[0] != null && lobby.getSlots()[1] != null) {
            lobby.setClosed(true);
            storeOrUpdateLobby(lobby);
            notifyGameManager(lobby); // PAIRUP-006/007
        }
    }

    /* PAIRUP-012: Stub for optional DB calls. */
    private void storeOrUpdateLobby(Lobby lobby) {
        // e.g., do DB writes or do nothing
    }

    /* External notifications placeholders. */
    private void notifyGameManager(Lobby lobby) {
        /* to GM: LID, players, etc. */ }

    private void notifySubscribers(String lobbyId, String status) {
        /* to PageMgr, JoinGame, etc. */ }


    //PairPlayerMethod
    public synchronized PairResponsePayload pairPlayer(JoinGamePayload payload) throws LobbyException {
    PairResponsePayload response = new PairResponsePayload();

    String entity1 = payload.entity1;
    String entity2 = payload.entity2;
    boolean opponentType1 = payload.opponentType1;
    boolean opponentType2 = payload.opponentType2;
    String lobbyId = payload.lobbyId;
    String action = payload.action;

    // Determine if either player is a bot
    boolean botScenario = !opponentType1 || !opponentType2;

    if (botScenario) {
        String newLobbyId = createDoubleBotLobby();
        response.gameID = newLobbyId;
        response.opponentHandle = "BOT";
        return response;
    }

    if (action.equalsIgnoreCase("wait")) {
        String newLobbyId = createLobby(entity1);
        response.gameID = newLobbyId;
        response.opponentHandle = "WAITING";
        return response;
    }
    System.out.println("here");
    System.out.println("action: " + action);

    if (action.equalsIgnoreCase("join")) {
        System.out.println("here2");
        if (lobbyId == null || lobbyId.isEmpty()) {
            throw new LobbyException("Lobby ID is required when joining.");
        }
        System.out.println("here3");

        String testLobbyId = createLobby(entity1);
        // boolean success = joinLobby(lobbyId, entity2);
        boolean success = joinLobby(testLobbyId, entity2);
        System.out.println(success);
        if (!success) {
            throw new LobbyException("Failed to join lobby");
        }

        // Lobby joinedLobby = findLobby(lobbyId);
        Lobby joinedLobby = findLobby(testLobbyId);
        if (joinedLobby == null) {
            throw new LobbyException("Lobby not found after join");
        }

        Participant[] slots = joinedLobby.getSlots();
        String opponent = null;

        for (Participant p : slots) {
            if (p != null && !p.isBot() && !p.getPlayerId().equals(entity2)) {
                opponent = p.getPlayerId();
                break;
            }
        }

        response.gameID = joinedLobby.getLobbyId();
        response.opponentHandle = opponent != null ? opponent : "WAITING";
        return response;
    }

    throw new LobbyException("Invalid action or opponent types.");
}
}
