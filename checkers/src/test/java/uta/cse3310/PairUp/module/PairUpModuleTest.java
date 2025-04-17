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

}
