package uta.cse3310.GameTermination;

import java.util.List;

import uta.cse3310.GameManager.GameState;
import uta.cse3310.GameManager.Move;
import uta.cse3310.GameManager.Piece;
import uta.cse3310.GameManager.PieceColor;

/**
 * Defines the contract for game termination logic.
 */
public interface IGameTermination {

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
     * @param state       The current board state.
     * @param moveHistory The list of moves made in the game.
     * @return GameStatus indicating the game result (RED_WIN, BLACK_WIN, DRAW,
     *         ONGOING).
     */
    GameStatus checkForGameEnd(GameState state, List<Move> moveHistory);

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
}
