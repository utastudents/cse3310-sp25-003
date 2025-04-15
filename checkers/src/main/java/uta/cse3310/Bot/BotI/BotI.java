package uta.cse3310.Bot.BotI;

import java.util.ArrayList;
import java.util.Random;

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
    private boolean validateStandardMove(char[][] currentBoard, Position pos) {

        boolean isValid = false;
        
        // Verify x, y coordinates (pos) are within bounds of of the board (8x8)
        if (withinBounds(pos))
            // Verify the square at located at (pos) is unoccupied
		    if (currentBoard[ pos.getX() ][ pos.getY() ] == ' ')
                isValid = true;
        
        return isValid;
    }
    
    // opponentPos - 1x diagnoal move from the bot piece
    // jumpPos - 2x diagonal moves from the bot piece
    private boolean validateCaptureMove(char[][] currentBoard, Position opponentPos, Position jumpPos) {

        boolean isValid = false;

        // Check that 2x diagonal moves are within boundaries (8x8)
        if (withinBounds(opponentPos) && withinBounds(jumpPos))
            // Check that there's actually an opponent ('x' or 'X') at opponentPos
            if (currentBoard[ opponentPos.getX() ][ opponentPos.getY() ] == 'x' ||
                currentBoard[ opponentPos.getX() ][ opponentPos.getY() ] == 'X')
                // Check that the space diagonal to the opponent is unoccupied
                if (currentBoard[ jumpPos.getX() ][ jumpPos.getY() ] == ' ')
                    isValid = true;

        return isValid;
    }
    
    // Returns a list of legal, capture move(s) the bot can make
    // NOTE: Changed method from private -> public for the sake of visibility in BotITest.java
    public ArrayList<Move> getAvailableCaptureMoves(char[][] currentBoard) {

		ArrayList<Move> availableMoves = new ArrayList<Move>();
        
		// Traverse through the board (8x8)
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
                
				// Bot piece (standard)
				if (currentBoard[row][col] == 'o') {
					// Current position of bot piece being evaluated
					Position currentPos = new Position(row, col);

                    // Potential positions of opponents, spaces to jump to
					Position diagUpLeft = new Position(row - 1, col - 1);
					Position diagUpLeftJump = new Position(row - 2, col - 2);  
					Position diagUpRight = new Position(row - 1, col + 1);
					Position diagUpRightJump = new Position(row - 2, col + 2);

                    if (validateCaptureMove(currentBoard, diagUpLeft, diagUpLeftJump))
                        availableMoves.add( new Move(currentPos, diagUpLeftJump, "BotI") );
                    if (validateCaptureMove(currentBoard, diagUpRight, diagUpRightJump))
                        availableMoves.add( new Move(currentPos, diagUpRightJump, "BotI") );
                }
				// Bot piece (king)
				else if (currentBoard[row][col] == 'O') {
					// Current position of bot piece being evaluated
					Position currentPos = new Position(row, col);

                    // Potential positions of opponents, spaces to jump to
					Position diagUpLeft = new Position(row - 1, col - 1);
					Position diagUpLeftJump = new Position(row - 2, col - 2);  
					Position diagUpRight = new Position(row - 1, col + 1);
					Position diagUpRightJump = new Position(row - 2, col + 2);
					Position diagDownLeft = new Position(row + 1, col - 1);
					Position diagDownLeftJump = new Position(row + 2, col - 2);
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
    // NOTE: Changed method from private -> public for the sake of visibility in BotITest.java
    public ArrayList<Move> getAvailableStandardMoves(char[][] currentBoard) {

		ArrayList<Move> availableMoves = new ArrayList<Move>();
		
		// Traverse through the board (8x8)
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
                
				// Bot piece (standard)
				if (currentBoard[row][col] == 'o') {
					// Current position of bot piece being evaluated
					Position currentPos = new Position(row, col);
                    
					// Potential positions to move to
					Position diagUpLeft = new Position(row - 1, col - 1);
					Position diagUpRight = new Position(row - 1, col + 1);
                    
					// Check if potential moves are valid
					if (validateStandardMove(currentBoard, diagUpLeft))
						availableMoves.add( new Move(currentPos, diagUpLeft, "BotI") );
					if (validateStandardMove(currentBoard, diagUpRight))
					    availableMoves.add( new Move(currentPos, diagUpRight, "BotI") );
				}
				// Bot piece (king)
				else if (currentBoard[row][col] == 'O') {
					// Current position of bot piece being evaluated
					Position currentPos = new Position(row, col);

					// Potential positions to move to
					Position diagUpLeft = new Position(row - 1, col - 1);
					Position diagUpRight = new Position(row - 1, col + 1);
					Position diagDownLeft = new Position(row + 1, col - 1);
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
    private Move generateMove(char[][] currentBoard) {
        Random random = new Random();

        // Pick capture move if available. If multiple are available, pick at random
        ArrayList<Move> availableCaptureMoves = getAvailableStandardMoves(currentBoard);
        if (!availableCaptureMoves.isEmpty()) {
            // If there are capture moves, pick one at random
            int index = random.nextInt(availableCaptureMoves.size());
            return availableCaptureMoves.get(index);
        }

        // Pick standard move if no capture moves available. If multiple are available, pick at random
        ArrayList<Move> availableStandardMoves = getAvailableStandardMoves(currentBoard);
		if (!availableStandardMoves.isEmpty()) {
            // If we have standard moves, randomly choose one
            int index = random.nextInt(availableStandardMoves.size());
            return availableStandardMoves.get(index);
        }
        
        // If no moves are available, should return null
        return null;
    }

    // This method gets called by the connected GameManager whenever a user has
    // made their move.
    // TODO: Implement
    public void onUserMove(char[][] currentBoard, Move userMove) {
        Move botMove = generateMove(currentBoard);

        // botMove will be null if no moves are available. The game should end here
        if (botMove == null) {
            // Game should end here
        } else {
            // Make move
        }
    }
}
