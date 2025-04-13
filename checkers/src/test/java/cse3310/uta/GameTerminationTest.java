package cse3310.uta;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;


import org.junit.Test;

import uta.cse3310.GameTermination.GameTermination;
import uta.cse3310.GameTermination.IGameTermination.BoardState;
import uta.cse3310.GameTermination.IGameTermination.Piece;
import uta.cse3310.GameTermination.IGameTermination.PieceColor;
import uta.cse3310.GameTermination.IGameTermination.Move;
import uta.cse3310.GameTermination.GameStatus;

public class GameTerminationTest{
    //TC-001: Test when RED has no pieces -> BLACK wins
    @Test
    public void tetsBlackWinsWhenRedHasNoPieces() {
        BoardState state = new BoardState() {
            @Override
            public int getSize() {
                return 8; //Size of the board is 8x8
            }
        
            @Override
            public Piece getPiece (int i, int j) {
                return new Piece() {
                    @Override
                    public PieceColor getColor() {
                        return PieceColor.BLACK; // only BLACK pieces exist
                    }
                };
            }
        

            @Override
            public boolean hasValidMovesFor (PieceColor color) {
                return true; // moves are valid
            }

            @Override
            public BoardState copy() {
                return this;
            }


            @Override
            public PieceColor getCurrentPlayer() {
                return PieceColor.RED; // RED's turn
            }
        };

        GameTermination gt = new GameTermination();
        List<Move> moves = new ArrayList<>();
        GameStatus result = gt.checkForGameEnd(state, moves);

        assertEquals(GameStatus.BLACK_WIN, result); // Expected BLACK will be winner
    }
    //TC-001: Test when RED has no pieces -> BLACK wins
    @Test
    public void testRedWinsWhenBlackHasNoPieces() {
        BoardState state = new BoardState() {
            @Override
            public int getSize() {
                return 8; // Size of the board is 8x8 
            }

            @Override
            public Piece getPiece (int i, int j) {
                return new Piece() {
                    @Override
                    public PieceColor getColor() {
                        return PieceColor.RED; // Only RED pieces exist
                    }
                };
            }

            @Override
            public boolean hasValidMovesFor (PieceColor color) {
                return true; // Moves are valid
            }


            @Override
            public BoardState copy() {
                return this;
            }
        };
        
        GameTermination gt = new GameTermination();
        List<Move> moves = new ArrayList<>();
        GameStatus result = gt.checkForGameEnd((state), moves);

        assertEquals(GameStatus.RED_WIN, result); // Expected RED will be winner
    }
}