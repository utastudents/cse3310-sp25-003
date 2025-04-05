package uta.cse3310.Bot.BotII;

import java.util.ArrayList;
import java.util.List;


// Temporary placeholders
class BoardState {
}

class Move {
}

public class BotII 
{
        //Main method that determines the bot's move
    public void makeMove(BoardState boardState) 
    {
        int score = evaluateBoard(boardState);

        //If the evaluation score is high, prioritize offensive moves, else defensive
        if (score >= 0) 
        {
            prioritizeOffense(boardState);
        } 
        else 
        {
            prioritizeDefense(boardState);
        }
    }

    //Offensive strategy
    private void prioritizeOffense(BoardState boardState) {
        //first, look for capture oppurtunities
        if (findCaptureOpportunity(boardState)) {
            capturePiece(boardState);
        } 
        else {
            //If no capture is available, make a regular valid move
            makeValidMove(boardState);
        }
    }
    private void prioritizeDefense(BoardState boardState) 
    {
        ArrayList<Move> legalMoves = getLegalMoves(boardState);
        
        Move blockMove = findBlockMove(legalMoves);
        if (blockMove != null) 
        {
            executeMove(blockMove);
            return;
        }

        Move safeMove = findSafeMove(legalMoves);
        if (safeMove != null) 
        {
            executeMove(safeMove);
        }
        
    }
    //Evaluate the board and return a score based on the amount of pieces, kings, and center control
    public int evaluateBoard(BoardState boardState) {
        int score = 0;

        //Score for number of bot pieces and opponent pieces
        score += 10 * countBotPieces(boardState);
        score -= 10 * countOpponentPieces(boardState);

        //Score for number of kings
        score += 20 * countBotKings(boardState);
        score -= 20 * countOpponentKings(boardState);

        //Evaluate center control: Number of pieces in the center
        score += 15 * countPiecesInCenter(boardState);

        return score;
    }

    //Check if there's an opportunity to capture an opponent piece
    private boolean findCaptureOpportunity(BoardState boardState) {
        //Capture Move logic will be added here.
        return true;
    }
    //capture of an opponent's piece
    private void capturePiece(BoardState boardState) 
    {
        //Perform a capture on opponent piece

    }

    // Make a valid move (not involving a capture)
    private void makeValidMove(BoardState boardState) {
        //Logic to move a piece
    }

    
    private Move findSafeMove(ArrayList<Move> legalMoves) 
    {
       //code to find safe moves
       //Avoid moving from back row
        //if (startRow != 0 && startRow != 7) 
        //{
         //       return move;
        //}
        
        return null;
    }
    
    private Move findBlockMove(List<Move> legalMoves) 
    {
       //code to find move that blocks opponents
       //takes center positions
       //if (row >= 2 && row <= 5 && col >= 2 && col <= 5) 
       //{
       //         return move;
       //}
        return null;
    }

    private void executeMove(Move move) {
        //TODO: Actually make the move on the board
    }
    
    private ArrayList<Move> getLegalMoves(BoardState boardState) 
    {
        //TODO: Return all legal moves for bot
        return new ArrayList<>();
    }
    
}
