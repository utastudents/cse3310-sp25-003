package uta.cse3310.Bot.BotII;

import uta.cse3310.GameManager.Move;
import uta.cse3310.GameManager.Position;
import java.util.ArrayList;
import java.util.Random;

public class BotII {
    // Bot's player ID
    private static final String PLAYER_ID = "BotII";

    // Create bot instance for GameManager
    public BotII() 
    {
    }


    // Return the bot's next move based on board state
    public Move onUserMove(char[][] board) 
    {
        // Collect all possible capture and standard moves
        ArrayList<Move> captureMoves = new ArrayList<>();
        ArrayList<Move> standardMoves = new ArrayList<>();

        getLegalMoves(board, captureMoves, standardMoves);

        // Prioritize capture moves if available
        Random rand = new Random();
        if (!captureMoves.isEmpty()) 
        {
            return captureMoves.get(rand.nextInt(captureMoves.size()));
        }
        
        
        // Evaluate board and choose strategic moves
        if (!standardMoves.isEmpty()) 
        {
            int score = evaluateBoard(board);
            
            ArrayList<Move> strategicMoves = new ArrayList<>();
            for (Move move : standardMoves)
            {
                if (score >= 0 && isForwardMove(move)) 
                {
                    strategicMoves.add(move); // Attack by advancing
                } 
                else if (score < 0 && findSafeMove(move, board[move.getFrom().getX()][move.getFrom().getY()] == 'O'))
                {
                    strategicMoves.add(move); // Defend by retreating
                }
            }
            
            // Return random strategic move if available, else any standard move
            if (!strategicMoves.isEmpty()) 
            {
                return strategicMoves.get(rand.nextInt(strategicMoves.size()));
            }
            return standardMoves.get(rand.nextInt(standardMoves.size()));
        }
        
        return null;
    }
// Evaluate board based on pieces and kings
    private int evaluateBoard(char[][] board) 
    {
        
        // Count bot and opponent pieces and kings
        int botPieces = 0, oppPieces = 0, botKings = 0, oppKings = 0;
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++) 
            {
                char c = board[row][col];
                if (c == 'o') {
                    botPieces++;
                } else if (c == 'O') {
                    botPieces++;
                    botKings++;
                } else if (c == 'x') {
                    oppPieces++;
                } else if (c == 'X') {
                    oppPieces++;
                    oppKings++;
                }
            }
        }
        
        // Calculate score with higher weight for kings
        return botPieces + 2 * botKings - (oppPieces + 2 * oppKings);
    }
      // Check if position is valid for a standard move
    private boolean makeValidMove(char[][] board, Position pos) 
    {
        // Verify position is on board and empty
        if (!isWithinBoard(pos)) return false;
        return board[pos.getX()][pos.getY()] == ' ';
    }

