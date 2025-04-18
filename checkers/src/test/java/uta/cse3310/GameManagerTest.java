package uta.cse3310;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import uta.cse3310.GameManager.Position;
import uta.cse3310.GameManager.GameState;

public class GameManagerTest {
    
    private GameState game;
    @Test
    public void testPosition() {
        // Test constructor and getters
        Position pos = new Position(3, 5);
        assertEquals(3, pos.getX());
        assertEquals(5, pos.getY());
        
        // Test setters
        pos.setX(4);
        pos.setY(6);
        assertEquals(4, pos.getX());
        assertEquals(6, pos.getY());
        
        // Test isValid method
        Position validPos = new Position(7, 7);
        Position invalidPos = new Position(8, 7);
        assertTrue(validPos.isValid(8));
        assertFalse(invalidPos.isValid(8));
        
        // Test middle method
        Position start = new Position(2, 2);
        Position end = new Position(4, 4);
        Position middle = start.middle(end);
        assertEquals(3, middle.getX());
        assertEquals(3, middle.getY());
        
        // Test distanceTo method
        Position p1 = new Position(1, 1);
        Position p2 = new Position(4, 5);
        assertEquals(4, p1.distanceTo(p2)); // Max of |4-1| and |5-1| is 4
        
        // Test add method
        Position original = new Position(2, 3);
        Position added = original.add(2, -1);
        assertEquals(4, added.getX());
        assertEquals(2, added.getY());
        
        // Test equals method
        Position a = new Position(1, 2);
        Position b = new Position(1, 2);
        Position c = new Position(2, 1);
        assertTrue(a.equals(b));
        assertFalse(a.equals(c));
        
        // Test array manipulation methods
        Integer[][] array = new Integer[5][5];
        Position arrayPos = new Position(2, 3);
        arrayPos.setInArray(array, 42);
        assertEquals(Integer.valueOf(42), arrayPos.getFromArray(array));
        
        // Test toString method
        String expected = "Position{x=2, y=3}";
        assertEquals(expected, arrayPos.toString());
    }

    @Before
    public void setUp() {
        game = new GameState("game123", "Player1");
    }

    @Test
    public void testInitialPlayerId() {
        assertEquals("Player1", game.getCurrentPlayerId());
    }

    @Test
    public void testSwitchTurn() {
        game.switchTurn("BotI");
        assertEquals("BotI", game.getCurrentPlayerId());
    }

}
