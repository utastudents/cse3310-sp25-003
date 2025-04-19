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


}