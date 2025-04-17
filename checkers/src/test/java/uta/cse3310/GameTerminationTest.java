package cse3310.uta;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import uta.cse3310.GameTermination.GameTermination;
import uta.cse3310.GameTermination.GameStatus;
import uta.cse3310.GameTermination.IGameTermination.*;



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
                return 2;
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

    //TC-003: Draw when neither player has valid moves
    @Test
    public void tetsDrawWhenNoLegalMovesForBothPlayers() {
        BoardState state = new BoardState() {
        
            @Override
            public int getSize() {
                return 8; // Size of Board 8x8
            }





            @Override
            public Piece getPiece (int i, int j) {
                //A RED king at (2,2)
                if (i == 2 && j == 2) {
                    return new Piece() {
                        @Override
                        public PieceColor getColor() {
                            return PieceColor.RED; 
                        }

                        @Override
                        public boolean isKing() {
                            return true;
                        }
                    };
                    //A BLACK king at (1,1)
                } else if (i == 1 && j ==1) {
                    return new Piece() {
                        @Override
                        public PieceColor getColor() {
                            return PieceColor.BLACK;
                        }

                        @Override
                        public boolean isKing() {
                            return true;
                        }
                    };
                }
                return null;
            }

            @Override
            public boolean hasValidMovesFor(PieceColor color) {
                return false; // both players are stuck
            }


            @Override
            public BoardState copy () {
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

        assertEquals(GameStatus.DRAW,result); // Expected Both of Players are DRAW
    }   
}
