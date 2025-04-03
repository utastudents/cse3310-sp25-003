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