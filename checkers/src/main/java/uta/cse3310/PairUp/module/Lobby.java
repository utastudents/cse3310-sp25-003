package uta.cse3310.PairUp.module;

/* pairup/model/Lobby.java */
public class Lobby {

    private String lobbyId;
    private Participant[] slots = new Participant[2];
    private boolean closed;
    private long creationTime;

    public Lobby(String lobbyId) {
        this.lobbyId = lobbyId;
        this.closed = false; 
        this.creationTime = System.currentTimeMillis();
    }

    public String getLobbyId() {
        return lobbyId;
    }

    public Participant[] getSlots() {
        return slots;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public long getCreationTime() {
        return creationTime;
    }

    // ─── New helper methods ───────────────────────────────────

    /** Returns true if both slots are occupied (human or bot). */
    public boolean isFull() {
        return slots[0] != null && slots[1] != null;
    }

    /** Returns the first human participant, or null if none. */
    public Participant getHumanParticipant() {
        for (Participant p : slots) {
            if (p != null && !p.isBot()) {
                return p;
            }
        }
        return null;
    }

    /** Removes the participant with the given playerId. 
      * Returns true if a participant was removed. */
    public boolean removeParticipant(String playerId) {
        for (int i = 0; i < slots.length; i++) {
            Participant p = slots[i];
            if (p != null && playerId.equals(p.getPlayerId())) {
                slots[i] = null;
                return true;
            }
        }
        return false;
    }
}
