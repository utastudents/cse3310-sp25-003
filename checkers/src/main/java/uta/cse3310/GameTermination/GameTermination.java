package uta.cse3310;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the possible statuses of the game.
 */
public enum GameStatus {
    RED_WIN, BLACK_WIN, DRAW, ONGOING
}

/**
 * Provides functionality to determine if the game has ended based on the
 * current board state and move history.
 * Checks for win conditions, draws, and other termination rules per English
 * draughts rules.
 */
public class GameTermination {
    private List<BoardState> boardStateHistory = new ArrayList<>();
    private TerminationReason terminationReason;

    /**
     * Represents the reason for game termination.
     */
    public enum TerminationReason {
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
    public GameStatus checkForGameEnd(BoardState state, List<Move> moveHistory) {
        boardStateHistory.add(state.copy());
        terminationReason = TerminationReason.ONGOING;

        // Check for piece elimination
        int redPieces = countPieces(state, PieceColor.RED);
        int blackPieces = countPieces(state, PieceColor.BLACK);

        if (redPieces == 0) {
            terminationReason = TerminationReason.ALL_PIECES_CAPTURED;
            return GameStatus.BLACK_WIN;
        } else if (blackPieces == 0) {
            terminationReason = TerminationReason.ALL_PIECES_CAPTURED;
            return GameStatus.RED_WIN;
        }

        // Check current player's valid moves
        PieceColor currentPlayer = state.getCurrentPlayer();
        if (!hasAnyValidMoves(state, currentPlayer)) {
            terminationReason = TerminationReason.NO_LEGAL_MOVES;
            return (currentPlayer == PieceColor.RED) ? GameStatus.BLACK_WIN : GameStatus.RED_WIN;
        }

        // Check 40-move rule
        if (is40MoveRuleDraw(moveHistory)) {
            terminationReason = TerminationReason.FORTY_MOVE_RULE;
            return GameStatus.DRAW;
        }

        // Check 25-move rule (kings only)
        if (areAllPiecesKings(state) && check25MoveRuleKingOnly(moveHistory)) {
            terminationReason = TerminationReason.TWENTYFIVE_MOVE_KING_RULE;
            return GameStatus.DRAW;
        }

        // Check threefold repetition
        if (checkThreefoldRepetition()) {
            terminationReason = TerminationReason.THREEFOLD_REPETITION;
            return GameStatus.DRAW;
        }

        // Check mutual stalemate
        if (!hasAnyValidMoves(state, PieceColor.RED) && !hasAnyValidMoves(state, PieceColor.BLACK)) {
            terminationReason = TerminationReason.MUTUAL_STALEMATE;
            return GameStatus.DRAW;
        }

        return GameStatus.ONGOING;
    }

    /**
     * Resets internal state for a new game.
     */
    public void reset() {
        boardStateHistory.clear();
        terminationReason = TerminationReason.ONGOING;
    }

    /**
     * Retrieves the reason for the game termination.
     * 
     * @return TerminationReason enum indicating the termination cause.
     */
    public TerminationReason getTerminationReason() {
        return terminationReason;
    }

    // ------------------- Helper Methods ------------------- //

    /**
     * Counts pieces of a specific color on the board.
     * 
     * @param state The board state to analyze.
     * @param color The color of the pieces to count.
     * @return Total pieces of the specified color.
     */
    private int countPieces(BoardState state, PieceColor color) {
        int count = 0;
        for (int i = 0; i < state.getSize(); i++) {
            for (int j = 0; j < state.getSize(); j++) {
                Piece piece = state.getPiece(i, j);
                if (piece != null && piece.getColor() == color) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if a player has any valid moves.
     * 
     * @param state The board state.
     * @param color The player's color.
     * @return True if the player has legal moves, false otherwise.
     */
    private boolean hasAnyValidMoves(BoardState state, PieceColor color) {
        return state.hasValidMovesFor(color);
    }

    /**
     * Validates the 40-move draw rule (no captures in 40 consecutive moves).
     * 
     * @param moveHistory List of all moves in the game.
     * @return True if the rule is triggered.
     */
    private boolean is40MoveRuleDraw(List<Move> moveHistory) {
        int movesToCheck = Math.min(moveHistory.size(), 40);
        int lastCaptureIndex = Math.max(0, moveHistory.size() - movesToCheck);

        for (int i = moveHistory.size() - 1; i >= lastCaptureIndex; i--) {
            if (moveHistory.get(i).isCapture()) {
                return false;
            }
        }
        return movesToCheck >= 40;
    }

    /**
     * Checks for threefold repetition of the same board state.
     * 
     * @return True if the current state repeats three times.
     */
    private boolean checkThreefoldRepetition() {
        if (boardStateHistory.size() < 3)
            return false;
        BoardState current = boardStateHistory.get(boardStateHistory.size() - 1);
        long count = boardStateHistory.stream()
                .filter(state -> state.equals(current))
                .count();
        return count >= 3;
    }

    /**
     * Validates the 25-move rule when only kings are on the board.
     * 
     * @param moveHistory List of all moves in the game.
     * @return True if the rule is triggered.
     */
    private boolean check25MoveRuleKingOnly(List<Move> moveHistory) {
        if (moveHistory.size() < 25)
            return false;
        for (int i = moveHistory.size() - 25; i < moveHistory.size(); i++) {
            Move move = moveHistory.get(i);
            if (move.isCapture() || !move.isKingMove()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all remaining pieces on the board are kings.
     * 
     * @param state The board state to analyze.
     * @return True if all pieces are kings.
     */
    private boolean areAllPiecesKings(BoardState state) {
        for (int i = 0; i < state.getSize(); i++) {
            for (int j = 0; j < state.getSize(); j++) {
                Piece piece = state.getPiece(i, j);
                if (piece != null && !piece.isKing()) {
                    return false;
                }
            }
        }
        return true;
    }
}