package uta.cse3310.PairUp.module;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.logging.Logger;

/* pairup/module/PairUpModule.java */
public class PairUpModule {
    private static final Logger LOG = Logger.getLogger(PairUpModule.class.getName());
    private final List<Lobby> activeLobbies = Collections.synchronizedList(new ArrayList<>());
    private static final int MAX_LOBBIES = 10;
    private static final long IDLE_TIMEOUT = 300_000; // 5 minutes in ms

    /* PAIRUP-003: Create lobby with 1 human. */
    public synchronized String createLobby(String playerId) throws LobbyException {
        requireNonEmpty(playerId, "playerId");
        LOG.info("Creating lobby for " + playerId);
        if (activeLobbies.size() >= MAX_LOBBIES) {
            LOG.warning("Max lobby limit reached");
            throw new LobbyException("Max lobby limit reached");
        }
        String lid = UUID.randomUUID().toString();
        Lobby lobby = new Lobby(lid);
        lobby.getSlots()[0] = new Participant(playerId, false);
        activeLobbies.add(lobby);
        storeOrUpdateLobby(lobby);
        return lid;
    }

    /* PAIRUP-004: Join existing lobby if vacant. */
    public synchronized boolean joinLobby(String lobbyId, String playerId) throws LobbyException {
        requireNonEmpty(lobbyId, "lobbyId");
        requireNonEmpty(playerId, "playerId");
        LOG.info("Player " + playerId + " joining lobby " + lobbyId);

        Lobby lobby = findLobby(lobbyId)
            .orElseThrow(() -> new LobbyException("Lobby not found"));

        if (lobby.isClosed()) {
            LOG.warning("Lobby closed: " + lobbyId);
            throw new LobbyException("Lobby is closed");
        }
        if (lobby.isFull()) {
            LOG.warning("Lobby full: " + lobbyId);
            throw new LobbyException("Lobby is full");
        }

        lobby.getSlots()[1] = new Participant(playerId, false);
        storeOrUpdateLobby(lobby);

        if (lobby.isFull()) {
            lobby.setClosed(true);
            storeOrUpdateLobby(lobby);
            notifyGameManager(lobby);
        }
        return true;
    }

    /* PAIRUP-005: Create lobby pre-filled with bot, then close. */
    public synchronized String createBotLobby(String playerId) throws LobbyException {
        requireNonEmpty(playerId, "playerId");
        LOG.info("Creating bot lobby for " + playerId);
        String lid = createLobby(playerId);
        Lobby lobby = findLobby(lid).orElse(null);
        if (lobby != null && !lobby.isFull()) {
            lobby.getSlots()[1] = new Participant(null, true);
            lobby.setClosed(true);
            storeOrUpdateLobby(lobby);
            notifyGameManager(lobby);
        }
        return lid;
    }

    /* 2-bots scenario (PAIRUP-002). */
    public synchronized String createDoubleBotLobby() throws LobbyException {
        LOG.info("Creating double-bot lobby");
        if (activeLobbies.size() >= MAX_LOBBIES) {
            throw new LobbyException("Max lobby limit reached");
        }
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
    public synchronized List<Lobby> refreshLobbies() {
        return activeLobbies.stream()
            .filter(l -> !l.isClosed())
            .collect(Collectors.toList());
    }

    /* PAIRUP-010/011: Cancel an open lobby. */
    public synchronized void cancelLobby(String lobbyId) throws LobbyException {
        requireNonEmpty(lobbyId, "lobbyId");
        LOG.info("Cancelling lobby " + lobbyId);
        Lobby lobby = findLobby(lobbyId)
            .orElseThrow(() -> new LobbyException("Lobby not found"));
        activeLobbies.remove(lobby);
        notifySubscribers(lobbyId, "cancelled");
    }

    /* PAIRUP-008: Handle user quitting a lobby. */
    public synchronized void handleUserQuit(String lobbyId, String playerId) throws LobbyException {
        requireNonEmpty(lobbyId, "lobbyId");
        requireNonEmpty(playerId, "playerId");
        LOG.info("Player " + playerId + " quitting lobby " + lobbyId);

        Lobby lobby = findLobby(lobbyId)
            .orElseThrow(() -> new LobbyException("Lobby not found"));

        if (!lobby.isClosed()) {
            boolean removed = lobby.removeParticipant(playerId);
            if (!removed) {
                // nothing to remove
            } else if (lobby.getHumanParticipant() == null) {
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
    public synchronized void removeIdleLobbies() {
        long now = System.currentTimeMillis();
        LOG.info("Removing idle lobbies");
        Iterator<Lobby> it = activeLobbies.iterator();
        while (it.hasNext()) {
            Lobby l = it.next();
            if ((now - l.getCreationTime()) > IDLE_TIMEOUT) {
                it.remove();
                notifySubscribers(l.getLobbyId(), "idleRemoved");
            }
        }
    }

    /* PAIRUP-015: Merge idle players from different lobbies. */
    public synchronized void checkIdlePlayersFromDifferentLobbies() {
        LOG.info("Merging idle single-player lobbies");
        List<Lobby> lonely = activeLobbies.stream()
            .filter(l -> !l.isClosed())
            .filter(l -> {
                Participant h = l.getHumanParticipant();
                return h != null && ((l.getSlots()[0] == h) ^ (l.getSlots()[1] == h));
            })
            .collect(Collectors.toList());

        while (lonely.size() >= 2) {
            Lobby a = lonely.remove(0), b = lonely.remove(0);
            Participant pa = a.getHumanParticipant();
            Participant pb = b.getHumanParticipant();

            String newId = UUID.randomUUID().toString();
            Lobby merged = new Lobby(newId);
            merged.getSlots()[0] = pa;
            merged.getSlots()[1] = pb;
            merged.setClosed(true);

            activeLobbies.add(merged);
            storeOrUpdateLobby(merged);

            activeLobbies.remove(a);
            activeLobbies.remove(b);
            notifySubscribers(a.getLobbyId(), "merged");
            notifySubscribers(b.getLobbyId(), "merged");
            notifyGameManager(merged);
        }
    }

    // ─── Helpers ─────────────────────────────────────────────────

    private Optional<Lobby> findLobby(String lobbyId) {
        return activeLobbies.stream()
            .filter(l -> l.getLobbyId().equals(lobbyId))
            .findFirst();
    }

    private void requireNonEmpty(String value, String name) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(name + " must be non-empty");
        }
    }

    // Stub for persistence (no-op)
    private void storeOrUpdateLobby(Lobby lobby) { }

    // Stub for notifying GameManager (no-op)
    private void notifyGameManager(Lobby lobby) { }

    // Stub for notifying subscribers (no-op)
    private void notifySubscribers(String lobbyId, String status) { }
}
