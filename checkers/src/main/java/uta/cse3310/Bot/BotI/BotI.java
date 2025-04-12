package uta.cse3310.Bot.BotI;

import java.util.ArrayList;

import uta.cse3310.GameManager.GameManager;
import uta.cse3310.GameManager.Move;

// TODO: Temporary
class Pieces {
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
	// TODO: Implement
	private boolean validateMove(Pieces currentBoard, Move m) {
		return false;
	}

	// Generates a complete list of (legal) moves the bot can make
	// TODO: Implement
	private ArrayList<Move> getAvailableMoves(Pieces currentBoard) {
		ArrayList<Move> availableMoves = new ArrayList<Move>();
		// Any moves added to availableMoves should be validated with validateMove()
		return availableMoves;
	}

	// Takes the current 8x8 board state (X = human, O = bot, V = valid, ! = invalid),
	// and returns a (legal/validated) move
	// TODO: Implement
	private Move generateMove(Pieces currentBoard) {
    ArrayList<Move> availableMoves = getAvailableMoves(currentBoard);
    
    // If no moves available, return null
    if (availableMoves.isEmpty()) {
        return null;
    }
    
    // Prioritize moves that capture opponent pieces
    ArrayList<Move> captureMoves = new ArrayList<>();
    for (Move move : availableMoves) {
        //if (move.isCapture()) {
        //    captureMoves.add(move);
        //}
    }
    
    // If there are capture moves, select one (prefer multi-captures)
    if (!captureMoves.isEmpty()) {
        // Find the move with the most captures
        Move bestCapture = captureMoves.get(0);
        for (Move move : captureMoves) {
            //if (move.getCapturedPieces().size() > bestCapture.getCapturedPieces().size()) {
            //    bestCapture = move;
            //}
        }
        return bestCapture;
    }
    
    // If no captures, prefer moves that promote to kings
    ArrayList<Move> promotionMoves = new ArrayList<>();
    for (Move move : availableMoves) {
        if (willPromoteToKing(move)) {
            promotionMoves.add(move);
        }
    }
    
    if (!promotionMoves.isEmpty()) {
        // Select a random promotion move for variety
        return promotionMoves.get((int)(Math.random() * promotionMoves.size()));
    }
    
    // If no special moves, prefer moving pieces toward the center for better positioning
    ArrayList<Move> centerMoves = new ArrayList<>();
    for (Move move : availableMoves) {
        if (isMovingTowardCenter(move)) {
            centerMoves.add(move);
        }
    }
    
    if (!centerMoves.isEmpty()) {
        // Select a random center move
        return centerMoves.get((int)(Math.random() * centerMoves.size()));
    }
    
    // Default: select a random move from available moves
    return availableMoves.get((int)(Math.random() * availableMoves.size()));
}

// Helper method to check if a move will promote the piece to a king
private boolean willPromoteToKing(Move move) {
    // Assuming Move has getEndRow() method and we know bot pieces are 'O'
    // In checkers, promotion happens when a piece reaches the opposite end
    //return (move.getPieceType() == 'O' && move.getEndRow() == 0) || 
    //       (move.getPieceType() == 'o' && move.getEndRow() == 7);
    return true;
}

// Helper method to check if a move is toward the center of the board
private boolean isMovingTowardCenter(Move move) {
    //int startCol = move.getStartCol();
    //int endCol = move.getEndCol();
    
    // Center columns are 3 and 4 in an 8x8 board (0-indexed)
    //return Math.abs(endCol - 3.5) < Math.abs(startCol - 3.5);
    return true;
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
