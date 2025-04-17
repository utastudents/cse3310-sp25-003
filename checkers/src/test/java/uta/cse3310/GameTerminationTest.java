package uta.cse3310;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import uta.cse3310.GameManager.GameState;
import uta.cse3310.GameManager.Move;
import uta.cse3310.GameManager.Piece;
import uta.cse3310.GameTermination.GameStatus;
import uta.cse3310.GameTermination.GameTermination;
import uta.cse3310.GameTermination.GameTermination.TerminationReason;

public class GameTerminationTest {

    private GameTermination gameTermination;

    @Before
    public void setUp() {
        gameTermination = new GameTermination();
    }

    // TC-001: Black wins when Red has no pieces
    @Test
    public void testBlackWinsWhenRedHasNoPieces() {
        // Create a game state and remove BotI (Red) pieces
        GameState state = new GameState("test-game", "Player1"); // Start with Player1's turn

        // Remove all BotI pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = state.getPieceAt(i, j);
                if (piece != null && piece.getPlayerId().equals("BotI")) { // Remove RED pieces
                    state.removePiece(i, j);
                }
            }
        }

        List<Move> moveHistory = new ArrayList<>();
        GameStatus result = gameTermination.checkForGameEnd(state, moveHistory);

        assertEquals(GameStatus.BLACK_WIN, result); // Expect BLACK to win when RED has no pieces
        assertEquals(TerminationReason.ALL_PIECES_CAPTURED, gameTermination.getTerminationReason());
    }

    // TC-002: Red wins when Black has no pieces
    @Test
    public void testRedWinsWhenBlackHasNoPieces() {
        // Create a game state and remove Player1 (Black) pieces
        GameState state = new GameState("test-game", "BotI"); // Start with BotI's turn

        // Remove all Player1 pieces
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = state.getPieceAt(i, j);
                if (piece != null && piece.getPlayerId().equals("Player1")) { // Remove BLACK pieces
                    state.removePiece(i, j);
                }
            }
        }

        List<Move> moveHistory = new ArrayList<>();
        GameStatus result = gameTermination.checkForGameEnd(state, moveHistory);

        assertEquals(GameStatus.RED_WIN, result); // Expect RED to win when BLACK has no pieces
        assertEquals(TerminationReason.ALL_PIECES_CAPTURED, gameTermination.getTerminationReason());
    }

    // TC-003: Draw when neither player has valid moves
    @Test
    public void testDrawWhenNoLegalMovesForBothPlayers() {
        // Create a game state where both players are stuck
        GameState state = new MockGameStateWithNoMoves();

        List<Move> moveHistory = new ArrayList<>();
        GameStatus result = gameTermination.checkForGameEnd(state, moveHistory);

        assertEquals(GameStatus.DRAW, result);
        assertEquals(TerminationReason.MUTUAL_STALEMATE, gameTermination.getTerminationReason());
    }

    // TC-004: Test 40-move rule draw
    @Test
    public void testFortyMoveRuleDraw() {
        GameState state = new GameState("test-game", "Player1");
        List<Move> moveHistory = new ArrayList<>();

        // Add 40 non-capture moves
        for (int i = 0; i < 40; i++) {
            Move move = new Move(i % 8, 0, (i + 1) % 8, 1, i % 2 == 0 ? "Player1" : "BotI");
            moveHistory.add(move);
        }

        GameStatus result = gameTermination.checkForGameEnd(state, moveHistory);

        assertEquals(GameStatus.DRAW, result);
        assertEquals(TerminationReason.FORTY_MOVE_RULE, gameTermination.getTerminationReason());
    }

    // TC-005: Test game still ongoing
    @Test
    public void testGameStillOngoing() {
        GameState state = new GameState("test-game", "Player1");
        List<Move> moveHistory = new ArrayList<>();

        // Add a few moves
        Move move1 = new Move(2, 1, 3, 2, "Player1");
        Move move2 = new Move(5, 2, 4, 3, "BotI");
        moveHistory.add(move1);
        moveHistory.add(move2);

        GameStatus result = gameTermination.checkForGameEnd(state, moveHistory);

        assertEquals(GameStatus.ONGOING, result);
        assertEquals(TerminationReason.ONGOING, gameTermination.getTerminationReason());
    }

    // Mock GameState with no legal moves for testing
    private class MockGameStateWithNoMoves extends GameState {
        public MockGameStateWithNoMoves() {
            super("test-game", "Player1");
        }

        // Override getPieceAt to return pieces in a specific setup where no moves are
        // possible
        @Override
        public Piece getPieceAt(int row, int col) {
            // Create a setup with a RED king at (2,2) and a BLACK king at (1,1)
            // positioned so neither can move
            if (row == 2 && col == 2) {
                return new Piece("Player1", true); // RED king
            } else if (row == 1 && col == 1) {
                return new Piece("BotI", true); // BLACK king
            }
            // Block all adjacent squares to simulate no valid moves
            else if ((row == 1 && col == 3) || (row == 3 && col == 1) ||
                    (row == 3 && col == 3) || (row == 0 && col == 0) ||
                    (row == 0 && col == 2) || (row == 2 && col == 0)) {
                return new Piece((row + col) % 2 == 0 ? "Player1" : "BotI", false);
            }
            return null;
        }
    }
}
