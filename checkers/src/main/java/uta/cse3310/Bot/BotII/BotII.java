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

