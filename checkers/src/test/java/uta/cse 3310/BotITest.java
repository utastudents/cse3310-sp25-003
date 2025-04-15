package cse3310.uta;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Test;

import uta.cse3310.Bot.BotI.*;
import uta.cse3310.GameManager.Move;
import uta.cse3310.GameManager.Position;

// Unit tests for Bot I
public class BotITest 
{
    //TC-001: Test getAvailableStandardMoves()
    @Test
    public void testGetAvailableStandardMoves()
    {
        BotI botI = new BotI(null);

        char[][] board1 = new char[][] {
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
        };
        ArrayList<Move> availableStandardMoves1 = botI.getAvailableStandardMoves(board1);
        assertTrue(availableStandardMoves1.isEmpty());

        char[][] board2 = new char[][] {
            { ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
            { 'x', ' ', 'x', ' ', 'x', ' ', 'x', ' ' },
            { ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { 'o', ' ', 'o', ' ', 'o', ' ', 'o', ' ' },
            { ' ', 'o', ' ', 'o', ' ', 'o', ' ', 'o' },
            { 'o', ' ', 'o', ' ', 'o', ' ', 'o', ' ' }
        };
        ArrayList<Move> availableStandardMoves2 = botI.getAvailableStandardMoves(board2);
        assertTrue(availableStandardMoves2.size() == 7);

        char[][] board3 = new char[][] {
            { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
            { 'X', ' ', 'X', ' ', 'X', ' ', 'X', ' ' },
            { ' ', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
        };
        ArrayList<Move> availableStandardMoves3 = botI.getAvailableStandardMoves(board3);
        assertTrue(availableStandardMoves3.size() == 7);

        char[][] board4 = new char[][] {
            { 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O' },
            { 'X', ' ', 'X', ' ', 'X', ' ', 'X', ' ' },
            { ' ', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
        };
        ArrayList<Move> availableStandardMoves4 = botI.getAvailableStandardMoves(board4);
        assertTrue(availableStandardMoves4.size() == 7);
    }
    
    //TC-002: Test getAvailableCaptureMoves()
    @Test
    public void testGetAvailableCaptureMoves()
    {
        BotI botI = new BotI(null);

        char[][] board1 = new char[][] {
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }
        };
        ArrayList<Move> availableCaptureMoves1 = botI.getAvailableCaptureMoves(board1);
        assertTrue(availableCaptureMoves1.isEmpty());

        char[][] board2 = new char[][] {
            { ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
            { 'x', ' ', 'x', ' ', 'x', ' ', 'x', ' ' },
            { ' ', 'x', ' ', 'x', ' ', 'x', ' ', 'x' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { 'o', ' ', 'o', ' ', 'o', ' ', 'o', ' ' },
            { ' ', 'o', ' ', 'o', ' ', 'o', ' ', 'o' },
            { 'o', ' ', 'o', ' ', 'o', ' ', 'o', ' ' }
        };
        ArrayList<Move> availableCaptureMoves2 = botI.getAvailableCaptureMoves(board2);
        assertTrue(availableCaptureMoves2.isEmpty());

        char[][] board3 = new char[][] {
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', 'x', ' ', ' ', ' ', 'X', ' ' },
            { ' ', 'x', ' ', ' ', ' ', 'x', ' ', ' ' },
            { 'o', ' ', ' ', ' ', 'o', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', 'o', ' ', ' ', ' ', 'O', ' ' },
            { ' ', 'x', ' ', ' ', ' ', 'x', ' ', ' ' },
            { 'o', ' ', ' ', ' ', 'o', ' ', ' ', ' ' }
        };
        ArrayList<Move> availableCaptureMoves3 = botI.getAvailableCaptureMoves(board3);
        assertTrue(availableCaptureMoves3.isEmpty());

        char[][] board4 = new char[][] {
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', 'x', ' ', ' ', ' ', 'X', ' ', ' ' },
            { 'o', ' ', 'o', ' ', 'o', ' ', 'o', ' ' }
        };
        ArrayList<Move> availableCaptureMoves4 = botI.getAvailableCaptureMoves(board4);
        assertTrue(availableCaptureMoves4.size() == 4);

        char[][] board5 = new char[][] {
            { 'O', ' ', 'O', ' ', 'O', ' ', 'O', ' ' },
            { ' ', 'x', ' ', ' ', ' ', 'X', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', 'x', ' ', ' ', ' ', 'X', ' ', ' ' },
            { 'O', ' ', 'O', ' ', 'O', ' ', 'O', ' ' }
        };
        ArrayList<Move> availableCaptureMoves5 = botI.getAvailableCaptureMoves(board5);
        assertTrue(availableCaptureMoves5.size() == 8);
    }
}
