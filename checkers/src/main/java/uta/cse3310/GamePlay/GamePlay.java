package uta.cse3310.GamePlay;
import uta.cse3310.GameManager.Move;
import uta.cse3310.GameManager.GameState;

public class GamePlay {

	public boolean isInBounds(Move move){//Checks if the coords of the "to" posititon is in the bounds of the array
	    if(move.getTo().getX() >= 0 && move.getTo().getX() <= 7){ //If X and Y are both within 0-7
	        if(move.getTo().getY() >= 0 && move.getTo().getY() <= 7){
	            return true;
	        }
	    } 
	    return false;
	}

	public boolean isDiagonal(Move move) { //Checks if the intended move is diagonal from its current position
		if(Math.abs(move.getTo().getX() - move.getFrom().getX()) == 1){ //If X2 - X1 && Y2 - Y1 both are equal to 1
		    if(Math.abs(move.getTo().getY() - move.getFrom().getY()) == 1){
		        return true;
		    }
		}
		return false;
	}

	private void applyMove(GameState boardState, Move move) {}

	private boolean isSameTeam(GameState boardState, Move move) 
	{
    	return false;
	}

	private void promotePiece(GameState boardState, Move move) {
        	//Implementation would check if the piece should be promoted and modify the piece accordingly.
    	}

	private boolean isEmpty(GameState boardState, int row, int col) {
        	//Would return true if table[row][col] is empty.
        	return false;
    	}
	
	public boolean isValidMove(GameState boardState, Move move) 
	{
    	return false;
	}
	public boolean isJump(GameState boardState, Move move){
		//Would return true if intended move intended move captures an enemy piece is valid
		return false;
	}
}
