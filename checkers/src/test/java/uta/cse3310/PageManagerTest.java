package uta.cse3310;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.Before;

import uta.cse3310.PageManager.PageManager;
import uta.cse3310.PageManager.UserEvent;
import uta.cse3310.PageManager.UserEventReply;

public class PageManagerTest {

    private PageManager pageManager;
    private UserEvent testEvent;

    @Before
    public void setUp() {
        pageManager = new PageManager();
        testEvent = new UserEvent();
        testEvent.id = 1; // Test User ID
    }

    @Test
    public void testHandleGoToLoginPage() {
        testEvent.eventType = "goToLoginPage";
        UserEventReply reply = pageManager.ProcessInput(testEvent);

        // Check success and message
        assertTrue(reply.isSuccess());
        assertEquals("Navigate to login page", reply.getMessage());
        assertEquals(List.of(1), reply.getRecipients());
    }

    @Test
    public void testHandleGetPlayersUsername_empty() {
        testEvent.eventType = "getPlayersUsername";
        UserEventReply reply = pageManager.ProcessInput(testEvent);

        // Check success and message
        assertTrue(reply.isSuccess());
        assertEquals("Players list retrieved successfully", reply.getMessage());
        
        // Check that the players list is empty
        assertTrue(reply.getPlayersList().isEmpty());
        assertEquals(List.of(1), reply.getRecipients());
    }

    @Test
    public void testHandleSummaryRequest() {
        testEvent.eventType = "summaryRequest";
        UserEventReply reply = pageManager.ProcessInput(testEvent);

        // Check success and message
        assertTrue(reply.isSuccess());
        assertEquals("Summary retrieved successfully", reply.getMessage());
        assertEquals(List.of(1), reply.getRecipients());
    }

}
