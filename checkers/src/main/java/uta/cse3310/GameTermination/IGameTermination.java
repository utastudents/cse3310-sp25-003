package uta.cse3310.GameTermination;

import java.util.List;

import uta.cse3310.GameTermination.IGameTermination.PieceColor;

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

    // ---------- For Complation Purposes ONLY ----------//
    // [TODO]: Laxman- Remove this class once the game logic is implemented by other
    // teams
    public static class PieceColor {

        public static final PieceColor RED = new PieceColor();
        public static final PieceColor BLACK = new PieceColor();

        private PieceColor() {
        }

        public PieceColor getColor() {
            return this;
        }

        public int getSize() {
            return 0;
        }

    }

    public static class BoardState {

        public BoardState() {
        }

        public Piece getPiece(int i, int j) {
            return new Piece();
        }

        public int getSize() {
            return 0;
        }

        public BoardState applyMove(Piece piece, Move move) {
            return new BoardState();
        }

        public boolean hasValidMovesFor(PieceColor color) {
            return false;
        }

        public BoardState copy() {
            BoardState newState = new BoardState();
            for (int i = 0; i < this.getSize(); i++) {
                for (int j = 0; j < this.getSize(); j++) {
                    Piece piece = this.getPiece(i, j);
                    if (piece != null) {
                        // Assuming a copy constructor or similar method exists for Piece
                        newState.applyMove(piece, new Move());
                    }
                }
            }
            return newState;
        }

        public PieceColor getCurrentPlayer() {
            return new PieceColor();
        }
    }

    public static class Piece {
        private String playerId;
        private boolean isKing;

        // To accomodate GameState class. This is a placeholder
        public Piece(String playerId, boolean isKing) {
            this.playerId = playerId;
            this.isKing = isKing;
        }

        public Piece() {
        }

        public PieceColor getColor() {
            return new PieceColor();
        }

        public boolean isKing() {
            return false;
        }
    }

    public static class Move {

        public Move() {
        }

        public boolean isCapture() {
            return false;
        }

        public boolean isJump() {
            return false;
        }

        public boolean isDiagonal(Piece[][] table, Move move) {
            return false;
        }

        public boolean isValidMove(Piece[][] table, Move move) {
            return false;
        }

        public boolean isEmpty(Piece[][] table, int row, int col) {
            return false;
        }

        public boolean isKingMove() {
            return false;
        }
    }
}
