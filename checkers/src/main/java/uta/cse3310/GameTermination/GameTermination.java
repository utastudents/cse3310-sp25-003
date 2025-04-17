package uta.cse3310.GameTermination;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the game termination logic as defined by the GameTermination
 * interface.
 */
public class GameTermination implements IGameTermination {

    private final List<BoardState> boardStateHistory = new ArrayList<>();
    private TerminationReason terminationReason;

    @Override
    public GameStatus checkForGameEnd(BoardState state, List<Move> moveHistory) {
        boardStateHistory.add(state.copy());
        terminationReason = TerminationReason.ONGOING;

        // Check for piece elimination
        int redPieces = countPieces(state, PieceColor.RED);
        int blackPieces = countPieces(state, PieceColor.BLACK);

        // Check mutual stalemate
        if (!hasAnyValidMoves(state, PieceColor.RED) && !hasAnyValidMoves(state, PieceColor.BLACK)) {
            terminationReason = TerminationReason.MUTUAL_STALEMATE;
            return GameStatus.DRAW;
        }

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

        return GameStatus.ONGOING;
    }

    @Override
    public void reset() {
        boardStateHistory.clear();
        terminationReason = TerminationReason.ONGOING;
    }

    @Override
    public TerminationReason getTerminationReason() {
        return terminationReason;
    }

    // ------------------- Helper Methods ------------------- //
    /**
     * Counts pieces of a specific color on the board.
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
     */
    private boolean hasAnyValidMoves(BoardState state, PieceColor color) {
        return state.hasValidMovesFor(color);
    }

    /**
     * Validates the 40-move draw rule (no captures in 40 consecutive moves).
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
     */
    private boolean checkThreefoldRepetition() {
        if (boardStateHistory.size() < 3) {
            return false;
        }
        BoardState current = boardStateHistory.get(boardStateHistory.size() - 1);
        long count = boardStateHistory.stream()
                .filter(state -> state.equals(current))
                .count();
        return count >= 3;
    }

    /**
     * Validates the 25-move rule when only kings are on the board.
     */
    private boolean check25MoveRuleKingOnly(List<Move> moveHistory) {
        if (moveHistory.size() < 25) {
            return false;
        }
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
