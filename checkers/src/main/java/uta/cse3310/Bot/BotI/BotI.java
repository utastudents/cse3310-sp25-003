package uta.cse3310.Bot.BotI;

import java.util.ArrayList;

import uta.cse3310.GameManager.GameManager;
// TODO: Would like to use existing Move class, but I don't know if we need String playerId
//       Temporarily just putting "idk" for that when constructing Move objects lol
import uta.cse3310.GameManager.Move;
import uta.cse3310.GameManager.Position;

// TODO: Temporary
class Pieces {
    char[][] board;
};

public class BotI {

    private GameManager gameManager;

    public BotI(GameManager gameManager) {
        // BotI needs a reference to GameManager, as this is how it will communicate
        // with the game (e.g., receiving notice a user has made a move, retrieving
        // the current state of the checkers board)
        this.gameManager = gameManager;
    }

    // Checks if a potential move is legal
    private boolean validateMove(Pieces currentBoard, Position pos) {

        boolean isValid = false;
        
        // Verify x, y coordinates (pos) are within bounds of of the board (8x8)
        if (pos.getX() >= 0 && pos.getX() <= 8 && pos.getY() >= 0 && pos.getY() <= 8)
            // Verify the square at located at (pos) is unoccupied
		    if (currentBoard.board[ pos.getX() ][ pos.getY() ] == ' ')
                isValid = true;
        
        return isValid;
    }
    
    // Returns a list of legal, capture move(s) the bot can make
    // TODO: Implement
    private ArrayList<Move> getAvailableCaptureMoves(Pieces currentBoard) {

		ArrayList<Move> availableMoves = new ArrayList<Move>();
        
        return availableMoves;
    }

    // Returns a list of legal, standard (non-capture) move(s) the bot can make
    private ArrayList<Move> getAvailableStandardMoves(Pieces currentBoard) {

		ArrayList<Move> availableMoves = new ArrayList<Move>();
		
		// Traverse through the board (8x8)
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
                
				// Bot piece - standard
				if (currentBoard.board[x][y] == 'o') {
					// Current position of piece being evaluated
					Position currentPos = new Position(x, y);
					
					// Potential moves
					Position diagUpLeft = new Position(x-1, y-1);
					Position diagUpRight = new Position(x+1, y-1);
					
					// Check if potential moves are valid
					if (validateMove(currentBoard, diagUpLeft))
						availableMoves.add( new Move(currentPos, diagUpLeft, "idk") );
					if (validateMove(currentBoard, diagUpRight))
						availableMoves.add( new Move(currentPos, diagUpRight, "idk") );
				}
				// Bot piece - king					
				else if (currentBoard.board[x][y] == 'O') {
					// Current position being evaluated
					Position currentPos = new Position(x, y);

					// Potential moves
					Position diagUpLeft = new Position(x-1, y-1);
					Position diagUpRight = new Position(x+1, y-1);
					Position diagDownLeft = new Position(x-1, y+1);
					Position diagDownRight = new Position(x+1, y+1);
					
					// Check if potential moves are valid
					if (validateMove(currentBoard, diagUpLeft))
						availableMoves.add( new Move(currentPos, diagUpLeft, "idk") );
					if (validateMove(currentBoard, diagUpRight))
						availableMoves.add( new Move(currentPos, diagUpRight, "idk") );
					if (validateMove(currentBoard, diagDownLeft))
						availableMoves.add( new Move(currentPos, diagDownLeft, "idk") );
					if (validateMove(currentBoard, diagDownRight))
						availableMoves.add( new Move(currentPos, diagDownRight, "idk") );
				}
			}
		}
		
		return availableMoves;
    }

    // Takes the current board state (8x8), attempts to collect all available moves, then either:
    // - Picks a move (return Move object of the move chosen)
    //     - Pick capture move if available. If multiple are available, pick at random
    //     - Pick standard move if no capture moves available. If multiple are available, pick at random
    // - If no moves available, return null.
    // TODO: Implement
    private Move generateMove(Pieces currentBoard) {
        
        // Pick capture move if available. If multiple are available, pick at random
        ArrayList<Move> availableCaptureMoves = getAvailableStandardMoves(currentBoard);
        
        // Pick standard move if no capture moves available. If multiple are available, pick at random
        ArrayList<Move> availableStandardMoves = getAvailableStandardMoves(currentBoard);

        // If no moves are available, should return null
        return null;
    }

    // This method gets called by the connected GameManager whenever a user has
    // made their move.
    // TODO: Implement
    public void onUserMove(Pieces currentBoard, Move userMove) {
        Move botMove = generateMove(currentBoard);

        // botMove will be null if no moves are available. The game should end here
        if (botMove == null) {
            // Game should end here
        } else {
            // Make move
        }
    }
}
