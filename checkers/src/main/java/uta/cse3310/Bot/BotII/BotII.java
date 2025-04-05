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
}
