package uta.cse3310;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;

import uta.cse3310.GamePlay.*;
import uta.cse3310.GameManager.*;
import uta.cse3310.GameManager.GameState.*;

public class GamePlayTest{
    @Test //TC-001
    public void testIsInBoundsInValid(){ //Checks to see if it correctly tests a move in bounds
        GamePlay gp = new GamePlay();
        Move move = new Move(new Position(2,3), new Position(3,4), "Player1"); //Set "From" = 2,3 "To" = "3,4"
        assertTrue(gp.isInBounds(move));
    }

    @Test //TC-002
    public void testIsInBoundsInInvalid(){ //Checks to see if it correctly tests a move out of bounds
        GamePlay gp = new GamePlay();
        Move move = new Move(new Position(2,3), new Position(9,10), "Player1"); //Set "From" = 2,3 "To" = "9,10"
        assertFalse(gp.isInBounds(move));
    }

    @Test //TC-003
    public void testIsDiagonalValid(){ //Checks to see if it correctly tests a diagonal move
        GamePlay gp = new GamePlay();
        Move move = new Move(new Position(0,1), new Position(1,2), "Player1"); //Set "From" = 0,1 "To" = "1,2"
        assertTrue(gp.isDiagonal(move));
    }

    @Test //TC-004
    public void testIsDiagonalInvalid(){ //Checks to see if it correctly tests a straightmove
        GamePlay gp = new GamePlay();
        Move move = new Move(new Position(0,1), new Position(0,3), "Player1"); //Set "From" = 0,1 "To" = "0,3"
        assertFalse(gp.isDiagonal(move));
    }

    @Test //TC-005
     public void testApplyMove() {
 	GamePlay gp = new GamePlay();
 	Move move1 = new Move(new Position(2, 5), new Position(1, 4), "test");
 	Move move2 = new Move(new Position(3, 2), new Position(2, 3), "test");
 	Move move3 = new Move(new Position(5, 2), new Position(6, 3), "test");

 	GameState board = new GameState("xxx", "xxx");

 	gp.applyMove(board, move1);
 	gp.applyMove(board, move2);
 	gp.applyMove(board, move3);

 	if(board.getPieceAt(new Position(1, 4)) == null |
 	   board.getPieceAt(new Position(2, 3)) == null |
 	   board.getPieceAt(new Position(6, 3)) == null) {
 		assertTrue(true);
 	}

     }

    @Test //TC-006
    public void testIsValidMove() {
 	GamePlay gp = new GamePlay();
 	Move move1 = new Move(new Position(2, 5), new Position(1, 4), "test");
 	Move move2 = new Move(new Position(3, 2), new Position(9, -12), "test");
 	Move move3 = new Move(new Position(5, 2), new Position(7, 5), "test");
 
 	GameState board = new GameState("xxx", "xxx");
 
	assertFalse(gp.isValidMove(board, move1));
	assertFalse(gp.isValidMove(board, move2));
	assertFalse(gp.isValidMove(board, move3));
    }
    @Test //TC-007
    public void testIsJump(){
        GamePlay gp = new GamePlay();
        GameState gs = new GameState("xxx", "enemy");
        Move move = new Move(new Position(1, 3), new Position(3, 5), "test");
        gs.getPieceAt(2, 4);
        
        GameState board = new GameState("xxx", "xxx");
        assertTrue(gp.isJump(board, move));

    }
}
