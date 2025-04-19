package uta.cse3310;
import static org.junit.Assert.*;
import org.junit.Test;
import uta.cse3310.Bot.BotII.BotII;
import uta.cse3310.GameManager.Move;
import uta.cse3310.GameManager.Position;

public class BotIITest 
{

    // Testing the evaluateBoard method
    @Test
    public void testEvaluateBoard() 
    {
        BotII bot = new BotII();
        
        // Creating a 8x8 board
        char[][] board = new char[8][8];
        
        for (int i = 0; i < 8; i++) 
        {
            for (int j = 0; j < 8; j++) 
            {
                board[i][j] = ' ';
            }
        }
        
        // Placing pieces: 2 bot pieces, 1 bot king, 1 opponent piece, 1 opponent king
        board[0][0] = 'o'; // Bot piece
        board[1][1] = 'o'; // Bot piece
        board[2][2] = 'O'; // Bot king
        board[3][3] = 'x'; // Opponent piece
        board[4][4] = 'X'; // Opponent king

        // (2 + 2) - (1 + 2) = 4 - 3 = 1
        int score = bot.evaluateBoard(board);
        assertEquals("Expected score 1 for board with 2 bot pieces, 1 bot king, 1 opponent piece, 1 opponent king", 1, score);
    }
    //Test the isForwardMove method
    @Test
    public void testIsForwardMove() {
        BotII bot = new BotII();

        //Create a move from (2,2) to (3,3) 
        Position from = new Position(2, 2);
        Position to = new Position(3, 3);
        Move move = new Move(from, to, "BotII");

        //should be true since toX (3) > fromX (2)
        assertTrue("Move from (2,2) to (3,3) should be forward", bot.isForwardMove(move));

        //Create a non-forward move from (2,2) to (1,1) (upward)
        to = new Position(1, 1);
        move = new Move(from, to, "BotII");

        //should be false since toX (1) < fromX (2)
        assertFalse("Move from (2,2) to (1,1) should not be forward", bot.isForwardMove(move));
    }

    //Test the findCaptureOpportunity method
    @Test
    public void testFindCaptureOpportunity() 
    {
        BotII bot = new BotII();
        char[][] board = new char[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = ' ';
            }
        }

        //capture oppurtunity with opponent at (1,1), landing spot at (2,2) empty
        board[1][1] = 'x';
        Position opponentPos = new Position(1, 1);
        Position jumpPos = new Position(2, 2);

        //should be true since opponentPos has 'x' and jumpPos is empty
        assertTrue("Capture should exist with opponent at (1,1) and empty (2,2)",  bot.findCaptureOpportunity(board, opponentPos, jumpPos));

        //no capture oppurtunity, opponentPos empty
        board[1][1] = ' ';
        assertFalse("Capture should not exist with empty (1,1)", bot.findCaptureOpportunity(board, opponentPos, jumpPos));
    }
    
}

