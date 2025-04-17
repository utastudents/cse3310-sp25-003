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
}
