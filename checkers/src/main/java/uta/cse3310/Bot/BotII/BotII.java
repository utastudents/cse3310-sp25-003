package uta.cse3310.Bot.BotII;

public class BotII {
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
}
