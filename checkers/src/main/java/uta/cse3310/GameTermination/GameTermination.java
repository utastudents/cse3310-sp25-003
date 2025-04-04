package uta.cse3310.GameTermination;

import java.util.List;

/**
 * Represents the possible statuses of the game.
 */
public enum GameStatus {
    RED_WIN, BLACK_WIN, DRAW, ONGOING
}

/**
 * Defines the contract for game termination logic.
 */
public interface GameTermination {
    
    /**
     * Represents the reason for game termination.
     */
    enum TerminationReason {
        ALL_PIECES_CAPTURED,
        NO_LEGAL_MOVES,
        FORTY_MOVE_RULE,
        TWENTYFIVE_MOVE_KING_RULE,
        THREEFOLD_REPETITION,
        MUTUAL_STALEMATE,
        ONGOING
    }

    /**
     * Checks if the game has ended and determines the result.
     * 
     * @param state The current board state.
     * @param moveHistory The list of moves made in the game.
     * @return GameStatus indicating the game result (RED_WIN, BLACK_WIN, DRAW, ONGOING).
     */
    GameStatus checkForGameEnd(BoardState state, List<Move> moveHistory);

    /**
     * Resets internal state for a new game.
     */
    void reset();

    /**
     * Retrieves the reason for the game termination.
     * 
     * @return TerminationReason enum indicating the termination cause.
     */
    TerminationReason getTerminationReason();


    //---------- Helper Methods ----------//

    /*
     * Counts pieces of a specific color on the board
     * @param state the board state to analyze
     * @param color the color of the pieces to count
     * 
     * @return the total amount of pieces of the specified color 
     */

     private int countPieces(BoardSate state, PieceColor color)
     {
        int count = 0; 
        for (int i = 0; i < state; i++)
        {
            
        }
     }
}
