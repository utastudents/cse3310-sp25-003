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
    public int evaluateBoard(char[][] board) 
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

    // Find all possible capture and standard moves
    public void getLegalMoves(char[][] board, ArrayList<Move> captureMoves, ArrayList<Move> standardMoves) 
    {
        // Check each square for bot pieces
        for (int row = 0; row < 8; row++) 
        {
            for (int col = 0; col < 8; col++)
            {
                if (board[row][col] == 'o') 
                {
                    // Handle standard pieces (downward moves)
                    
                    Position currentPos = new Position(row, col);
                    
                    Position diagDownLeft = new Position(row + 1, col - 1);
                    Position diagDownLeftJump = new Position(row + 2, col - 2);
                    Position diagDownRight = new Position(row + 1, col + 1);
                    Position diagDownRightJump = new Position(row + 2, col + 2);

                    if (findCaptureOpportunity(board, diagDownLeft, diagDownLeftJump))
                        captureMoves.add(new Move(currentPos, diagDownLeftJump, PLAYER_ID));
                    if (findCaptureOpportunity(board, diagDownRight, diagDownRightJump))
                        captureMoves.add(new Move(currentPos, diagDownRightJump, PLAYER_ID));

                    if (makeValidMove(board, diagDownLeft))
                        standardMoves.add(new Move(currentPos, diagDownLeft, PLAYER_ID));
                    if (makeValidMove(board, diagDownRight))
                        standardMoves.add(new Move(currentPos, diagDownRight, PLAYER_ID));
                        
                        
                } 
                else if (board[row][col] == 'O') 
                {
                    // Handle kings (all directions)
                    Position currentPos = new Position(row, col);
                    
                    Position diagUpLeft = new Position(row - 1, col - 1);
                    Position diagUpLeftJump = new Position(row - 2, col - 2);
                    Position diagUpRight = new Position(row - 1, col + 1);
                    Position diagUpRightJump = new Position(row - 2, col + 2);
                    
                    Position diagDownLeft = new Position(row + 1, col - 1);
                    Position diagDownLeftJump = new Position(row + 2, col - 2);
                    Position diagDownRight = new Position(row + 1, col + 1);
                    Position diagDownRightJump = new Position(row + 2, col + 2);

                    if (findCaptureOpportunity(board, diagUpLeft, diagUpLeftJump))
                        captureMoves.add(new Move(currentPos, diagUpLeftJump, PLAYER_ID));
                    if (findCaptureOpportunity(board, diagUpRight, diagUpRightJump))
                        captureMoves.add(new Move(currentPos, diagUpRightJump, PLAYER_ID));
                    if (findCaptureOpportunity(board, diagDownLeft, diagDownLeftJump))
                        captureMoves.add(new Move(currentPos, diagDownLeftJump, PLAYER_ID));
                    if (findCaptureOpportunity(board, diagDownRight, diagDownRightJump))
                        captureMoves.add(new Move(currentPos, diagDownRightJump, PLAYER_ID));

                    if (makeValidMove(board, diagUpLeft))
                        standardMoves.add(new Move(currentPos, diagUpLeft, PLAYER_ID));
                    if (makeValidMove(board, diagUpRight))
                        standardMoves.add(new Move(currentPos, diagUpRight, PLAYER_ID));
                    if (makeValidMove(board, diagDownLeft))
                        standardMoves.add(new Move(currentPos, diagDownLeft, PLAYER_ID));
                    if (makeValidMove(board, diagDownRight))
                        standardMoves.add(new Move(currentPos, diagDownRight, PLAYER_ID));
                        
                }
            }
        }
    }

    // Check if position is valid for a standard move
    public boolean makeValidMove(char[][] board, Position pos) 
    {
        // Verify position is on board and empty
        if (!isWithinBoard(pos)) return false;
        return board[pos.getX()][pos.getY()] == ' ';
    }
    // Check if position allows a capture move
    public boolean findCaptureOpportunity(char[][] board, Position opponentPos, Position jumpPos) 
    {
        // Verify opponent piece and empty landing spot
        if (!isWithinBoard(opponentPos) || !isWithinBoard(jumpPos)) return false;
        char opp = board[opponentPos.getX()][opponentPos.getY()];
        return (opp == 'x' || opp == 'X') && board[jumpPos.getX()][jumpPos.getY()] == ' ';
    }

    
    public boolean isWithinBoard(Position pos) 
    {
        return pos.getX() >= 0 && pos.getX() < 8 && pos.getY() >= 0 && pos.getY() < 8;
    }

    // Check if move advances toward opponent side
    public boolean isForwardMove(Move move) 
    {
        // Compare row to detect downward movement
        int fromX = move.getFrom().getX();
        int toX = move.getTo().getX();
        return toX > fromX; // Downward for top player (RED)
    }

    // Check if move is safe for defense
    public boolean findSafeMove(Move move, boolean isKing) 
    {
        // Check for king retreat or safe standard move
        int fromX = move.getFrom().getX();
        int toX = move.getTo().getX();
        if (isKing) 
        {
            return toX < fromX; // Upward for kings (toward rows 0-2)
        } 
        else
        {
            return toX <= 2; // Stay in rows 0-2 for standard pieces
        }
    }
}

