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
}
