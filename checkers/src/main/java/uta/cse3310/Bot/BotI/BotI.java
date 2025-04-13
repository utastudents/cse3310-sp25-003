package uta.cse3310.Bot.BotI;

import java.util.ArrayList;

import uta.cse3310.GameManager.GameManager;
// TODO: Would like to use existing Move class, but I don't know if we need String playerId
//       Temporarily just putting "BotI" for that when constructing Move objects
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
    
	// Helper function that verifies x, y positions are on the board
	// TODO: We can possibly leverage the same function found in GamePlay
	private boolean withinBounds(Position pos) {
		return pos.getX() >= 0 && pos.getX() < 8 && pos.getY() >= 0 && pos.getY() < 8;
	}
    
    // Checks if a potential move is legal
    private boolean validateStandardMove(Pieces currentBoard, Position pos) {

        boolean isValid = false;
        
        // Verify x, y coordinates (pos) are within bounds of of the board (8x8)
        if (withinBounds(pos))
            // Verify the square at located at (pos) is unoccupied
		    if (currentBoard.board[ pos.getX() ][ pos.getY() ] == ' ')
                isValid = true;
        
        return isValid;
    }
    
    // opponentPos - 1x diagnoal move from the bot piece
    // jumpPos - 2x diagonal moves from the bot piece
    private boolean validateCaptureMove(Pieces currentBoard, Position opponentPos, Position jumpPos) {

        boolean isValid = false;

        // Check that 2x diagonal moves are within boundaries (8x8)
        if (withinBounds(opponentPos) && withinBounds(jumpPos))
            // Check that there's actually an opponent ('X') at opponentPos
            if ( currentBoard.board[ opponentPos.getX() ][ opponentPos.getY() ] == 'X' )
                // Check that the space diagonal to the opponent is unoccupied
                if ( currentBoard.board[ jumpPos.getX() ][ jumpPos.getY() ] == ' ' )
                    isValid = true;

        return isValid;
    }
    
    // Returns a list of legal, capture move(s) the bot can make
    private ArrayList<Move> getAvailableCaptureMoves(Pieces currentBoard) {

		ArrayList<Move> availableMoves = new ArrayList<Move>();
        
		// Traverse through the board (8x8)
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
                
				// Bot piece - standard
				if (currentBoard.board[row][col] == 'o') {
					// Current position of bot piece being evaluated
					Position currentPos = new Position(row, col);
                    // Potential positions of opponents, spaces to jump to
					Position diagUpLeft = new Position(row - 1, col - 1);
					Position diagUpLeftJump = new Position(row - 2, col - 2);  
					Position diagUpRight = new Position(row + 1, col - 1);
					Position diagUpRightJump = new Position(row + 2, col - 2);

                    if (validateCaptureMove(currentBoard, diagUpLeft, diagUpLeftJump))
                        availableMoves.add( new Move(currentPos, diagUpLeftJump, "BotI") );
                    if (validateCaptureMove(currentBoard, diagUpRight, diagUpRightJump))
                        availableMoves.add( new Move(currentPos, diagUpRightJump, "BotI") );
                }
				// Bot piece - king					
				else if (currentBoard.board[row][col] == 'O') {
					// Current position of bot piece being evaluated
					Position currentPos = new Position(row, col);
                    // Potential positions of opponents, spaces to jump to
					Position diagUpLeft = new Position(row - 1, col - 1);
					Position diagUpLeftJump = new Position(row - 2, col - 2);  
					Position diagUpRight = new Position(row + 1, col - 1);
					Position diagUpRightJump = new Position(row + 2, col - 2);
					Position diagDownLeft = new Position(row - 1, col + 1);
					Position diagDownLeftJump = new Position(row - 2, col + 2);
					Position diagDownRight = new Position(row + 1, col + 1);
					Position diagDownRightJump = new Position(row + 2, col + 2);

                    if (validateCaptureMove(currentBoard, diagUpLeft, diagUpLeftJump))
                        availableMoves.add( new Move(currentPos, diagUpLeftJump, "BotI") );
                    if (validateCaptureMove(currentBoard, diagUpRight, diagUpRightJump))
                        availableMoves.add( new Move(currentPos, diagUpRightJump, "BotI") );
                    if (validateCaptureMove(currentBoard, diagDownLeft, diagDownLeftJump))
                        availableMoves.add( new Move(currentPos, diagDownLeftJump, "BotI") );
                    if (validateCaptureMove(currentBoard, diagDownRight, diagDownRightJump))
                        availableMoves.add( new Move(currentPos, diagDownRightJump, "BotI") );
                }
            }
        }
        
        return availableMoves;
    }

    // Returns a list of legal, standard (non-capture) move(s) the bot can make
    private ArrayList<Move> getAvailableStandardMoves(Pieces currentBoard) {

		ArrayList<Move> availableMoves = new ArrayList<Move>();
		
		// Traverse through the board (8x8)
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
                
				// Bot piece - standard
				if (currentBoard.board[row][col] == 'o') {
					// Current position of bot piece being evaluated
					Position currentPos = new Position(row, col);
					// Potential positions to move to
					Position diagUpLeft = new Position(row - 1, col - 1);
					Position diagUpRight = new Position(row + 1, col - 1);
					
					if (validateStandardMove(currentBoard, diagUpLeft))
						availableMoves.add( new Move(currentPos, diagUpLeft, "BotI") );

					if (validateStandardMove(currentBoard, diagUpRight))
						availableMoves.add( new Move(currentPos, diagUpRight, "BotI") );
				}
				// Bot piece - king					
				else if (currentBoard.board[row][col] == 'O') {
					// Current position of bot piece being evaluated
					Position currentPos = new Position(row, col);
					// Potential positions to move to
					Position diagUpLeft = new Position(row - 1, col - 1);
					Position diagUpRight = new Position(row + 1, col - 1);
					Position diagDownLeft = new Position(row - 1, col + 1);
					Position diagDownRight = new Position(row + 1, col + 1);
					
					// Check if potential moves are valid
					if (validateStandardMove(currentBoard, diagUpLeft))
						availableMoves.add( new Move(currentPos, diagUpLeft, "BotI") );
					if (validateStandardMove(currentBoard, diagUpRight))
						availableMoves.add( new Move(currentPos, diagUpRight, "BotI") );
					if (validateStandardMove(currentBoard, diagDownLeft))
						availableMoves.add( new Move(currentPos, diagDownLeft, "BotI") );
					if (validateStandardMove(currentBoard, diagDownRight))
						availableMoves.add( new Move(currentPos, diagDownRight, "BotI") );
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
