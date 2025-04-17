package uta.cse3310;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import uta.cse3310.GamePlay.*;
import uta.cse3310.GameManager.*;

public class GamePlayTest {
    @Test // TC-001
    public void testIsInBoundsInValid() { // Checks to see if it correctly tests a move in bounds
        GamePlay gp = new GamePlay();
        Move move = new Move(new Position(2, 3), new Position(3, 4), "Player1"); // Set "From" = 2,3 "To" = "3,4"
        assertTrue(gp.isInBounds(move));
    }

    @Test // TC-002
    public void testIsInBoundsInInvalid() { // Checks to see if it correctly tests a move out of bounds
        GamePlay gp = new GamePlay();
        Move move = new Move(new Position(2, 3), new Position(9, 10), "Player1"); // Set "From" = 2,3 "To" = "9,10"
        assertFalse(gp.isInBounds(move));
    }

    @Test // TC-003
    public void testIsDiagonalValid() { // Checks to see if it correctly tests a diagonal move
        GamePlay gp = new GamePlay();
        Move move = new Move(new Position(0, 1), new Position(1, 2), "Player1"); // Set "From" = 0,1 "To" = "1,2"
        assertTrue(gp.isDiagonal(move));
    }

    @Test // TC-004
    public void testIsDiagonalInvalid() { // Checks to see if it correctly tests a straightmove
        GamePlay gp = new GamePlay();
        Move move = new Move(new Position(0, 1), new Position(2, 2), "Player1"); // Set "From" = 0,1 "To" = "2,2"
        assertFalse(gp.isDiagonal(move));
    }
}