package cse3310.uta;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uta.cse3310.GameTermination.GameTermination;
import uta.cse3310.GameTermination.IGameTermination.BoardState;
import uta.cse3310.GameTermination.IGameTermination.Move;
import uta.cse3310.GameTermination.IGameTermination.Piece;
import uta.cse3310.GameTermination.IGameTermination.PieceColor;
import uta.cse3310.GameTermination.GameStatus;



public class GameTerminationTest {

    //TC-001: Black wins with no pieces of RED 
    @Test
    public void testBlackWinsWhenRedHasNoPieces() {
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
                        return PieceColor.BLACK; // only BLACK pieces exist
                    }
                };
            }


            @Override
            public boolean hasValidMovesFor(PieceColor color) {
                return true; // Moves are valid
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

        assertEquals(GameStatus.BLACK_WIN, result);
        
    }


    //TC-002: Red wins with no pieces of Black
    @Test
    public void testRedWinsWhenBlackHasNoPieces()
    {
        //creating BoardState with only Red PIeces
        BoardState state = new BoardState()
        {
            @Override
            public int getSize()
            {
                return 8;
            }

            @Override
            public Piece getPiece(int i, int j )
            {
                return new Piece()
                {
                    @Override
                    public PieceColor getColor()
                    {
                        return PieceColor.RED;
                    }
                };
            }

            @Override 
            public boolean hasValidMovesFor(PieceColor Color)
            {
                return true; 
            }

            @Override
            public BoardState copy()
            {
                return this; 

            }     
            
            public PieceColor getCurrentPlayer()
            {
                return PieceColor.BLACK;
            }
        };

        GameTermination gt = new GameTermination();
        List<Move> moves = new ArrayList<>();
        GameStatus result = gt.checkForGameEnd(state, moves); 

        assertEquals(GameStatus.RED_WIN, result);
    }
}