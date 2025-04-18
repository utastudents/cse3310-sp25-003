package uta.cse3310.PairUp.module;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;

public class PairUpModuleTest {

    @Test
    public void testCreateAndRefreshLobby() throws LobbyException {
        PairUpModule pum = new PairUpModule();
        String id = pum.createLobby("p1");
        assertNotNull("createLobby should return a non-null ID", id);

        List<Lobby> open = pum.refreshLobbies();
        assertEquals(1, open.size());
        assertEquals(id, open.get(0).getLobbyId());
    }

    @Test
    public void testJoinLobbyCloses() throws LobbyException {
        PairUpModule pum = new PairUpModule();
        String id = pum.createLobby("p1");
        assertTrue("joinLobby should succeed", pum.joinLobby(id, "p2"));

        // Once full, no open lobbies remain
        assertTrue(pum.refreshLobbies().isEmpty());
    }

    @Test(expected = LobbyException.class)
    public void testJoinNonexistentThrows() throws LobbyException {
        PairUpModule pum = new PairUpModule();
        pum.joinLobby("nope", "p1");
    }

    @Test
    public void testCreateBotLobbyClosed() throws LobbyException {
        PairUpModule pum = new PairUpModule();
        String id = pum.createBotLobby("p1");
        assertNotNull(id);

        // Bot lobby is immediately closed
        assertTrue(pum.refreshLobbies().isEmpty());
    }

    @Test
    public void testCreateDoubleBotLobbyClosed() throws LobbyException {
        PairUpModule pum = new PairUpModule();
        String id = pum.createDoubleBotLobby();
        assertNotNull(id);

        // Double‑bot lobby is closed
        assertTrue(pum.refreshLobbies().isEmpty());
    }

    @Test
    public void testCancelLobby() throws LobbyException {
        PairUpModule pum = new PairUpModule();
        String id = pum.createLobby("p1");
        pum.cancelLobby(id);

        // Cancelled lobby no longer appears
        assertTrue(pum.refreshLobbies().isEmpty());
    }

    @Test
    public void testHandleUserQuitRemoves() throws LobbyException {
        PairUpModule pum = new PairUpModule();
        String id = pum.createLobby("p1");
        pum.handleUserQuit(id, "p1");

        // After the only human quits, lobby is gone
        assertTrue(pum.refreshLobbies().isEmpty());
    }

    @Test
    public void testHandleUserQuitOnClosedLobby() throws LobbyException {
        PairUpModule pum = new PairUpModule();
        String id = pum.createBotLobby("p1");
        // Already closed; quitting should not throw
        pum.handleUserQuit(id, "p1");
    }

    @Test
    public void testRemoveIdleLobbies() throws LobbyException {
        PairUpModule pum = new PairUpModule();

        // Create an active (non-idle) lobby
        String activeId = pum.createLobby("active-player");
        Lobby activeLobby = pum.refreshLobbies().get(0);
        activeLobby.setCreationTime(System.currentTimeMillis()); // Now

        // Create an idle lobby manually
        Lobby idleLobby = new Lobby("idle-lobby-id");
        idleLobby.setCreationTime(System.currentTimeMillis() - 310_000); // More than 5 min
        pum.addLobby(idleLobby);

        // Call the method that should remove the idle lobby
        pum.removeIdleLobbies();

        // Only the active lobby should remain
        List<Lobby> remaining = pum.refreshLobbies();
        assertEquals(1, remaining.size());
        assertEquals(activeId, remaining.get(0).getLobbyId());
    }

    @Test
    public void testCreateLobbyWithSamePlayer() throws LobbyException {
        PairUpModule pum = new PairUpModule();
        String id1 = pum.createLobby("p1");
        String id2 = pum.createLobby("p1");

        // Ensure two separate lobbies are created
        assertNotEquals("Lobbies created by the same player should have different IDs", id1, id2);
    }
    
    @Test
    public void testCancelNonexistentLobby() {
        PairUpModule pum = new PairUpModule();
        try {
            pum.cancelLobby("nonexistent-id");
            fail("Cancelling a nonexistent lobby should throw an exception");
        } catch (LobbyException e) {
            assertEquals("Lobby not found", e.getMessage());
        }
    }

    @Test
    public void testHandleUserQuitNonexistentLobby() {
        PairUpModule pum = new PairUpModule();
        try {
            pum.handleUserQuit("nonexistent-id", "p1");
            fail("Quitting a nonexistent lobby should throw an exception");
        } catch (LobbyException e) {
            assertEquals("Lobby not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveIdleLobbiesWithNoIdle() throws LobbyException {
        PairUpModule pum = new PairUpModule();

        // Create two active lobbies
        String id1 = pum.createLobby("p1");
        String id2 = pum.createLobby("p2");

        // Set both lobbies as recently created
        for (Lobby lobby : pum.refreshLobbies()) {
            lobby.setCreationTime(System.currentTimeMillis());
        }

        // Call the method that should remove idle lobbies
        pum.removeIdleLobbies();

        // Both lobbies should remain
        List<Lobby> remaining = pum.refreshLobbies();
        assertEquals(2, remaining.size());
        assertTrue(remaining.stream().anyMatch(lobby -> lobby.getLobbyId().equals(id1)));
        assertTrue(remaining.stream().anyMatch(lobby -> lobby.getLobbyId().equals(id2)));
    }

    @Test
    public void testCheckLobbyFull() throws LobbyException {
        PairUpModule pum = new PairUpModule();
        String id = pum.createLobby("p1");

        // Initially, the lobby should not be full
        assertFalse("Lobby should not be full initially", pum.checkLobbyFull(id));

        // Add a second player to fill the lobby
        pum.joinLobby(id, "p2");

        // Now, the lobby should be full
        assertTrue("Lobby should be full after two players join", pum.checkLobbyFull(id));
    }
}
