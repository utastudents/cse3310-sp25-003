package uta.cse3310.GameManager;

public class GameState {
    private String gameId;
    private String currentPlayerId;
    private Piece[][] board;

    public GameState(String gameId, String startingPlayerId) {
        this.gameId = gameId;
        this.currentPlayerId = startingPlayerId;
        this.board = new Piece[8][8]; // 8x8 checkers board
        initializeBoard();
    }

    private void initializeBoard() {
        // BotI pieces (top) - PlayerId = "BotI"
        for (int row = 0; row < 3; row++) {
            for (int col = (row + 1) % 2; col < 8; col += 2) {
                board[row][col] = new Piece("BotI", false);
            }
        }

        // Player1 pieces (bottom)
        for (int row = 5; row < 8; row++) {
            for (int col = (row + 1) % 2; col < 8; col += 2) {
                board[row][col] = new Piece("Player1", false);
            }
        }
        // rows 3–4 are left null (empty) by default
    }

    public String getGameId() {
        return gameId;
    }

    public String getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void switchTurn(String nextPlayerId) {
        this.currentPlayerId = nextPlayerId;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void applyMove(int fromRow, int fromCol, int toRow, int toCol) {
        Piece movingPiece = board[fromRow][fromCol];
        board[fromRow][fromCol] = null;
        board[toRow][toCol] = movingPiece;
    }

    public void removePiece(int row, int col) {
        board[row][col] = null;
    }

    public Piece getPieceAt(int row, int col) {
        return board[row][col];
    }

    public void applyMove(Position from, Position to) {
        applyMove(from.getX(), from.getY(), to.getX(), to.getY());
    }

    public Piece getPieceAt(Position pos) {
        return getPieceAt(pos.getX(), pos.getY());
    }

    // ✅ Converts internal board to char[][] for BotI
    public char[][] getBoardAsArray() {
        char[][] array = new char[8][8];

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];

                if (piece == null) {
                    array[row][col] = ' ';
                } else if (piece.getPlayerId().equals("Player1")) {
                    array[row][col] = piece.isKing() ? 'X' : 'x'; // Player1 (user)
                } else if (piece.getPlayerId().equals("BotI")) {
                    array[row][col] = piece.isKing() ? 'O' : 'o'; // BotI
                } else {
                    array[row][col] = '?'; // Optional: handle unexpected player
                }
            }
        }

        return array;
    }
}
