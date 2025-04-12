package uta.cse3310.Bot.BotI;

import java.util.ArrayList;

import uta.cse3310.GameManager.GameManager;
import uta.cse3310.GameManager.Move;

// TODO: Temporary
class Pieces {
};

public class BotI {

    private GameManager gameManager;

    public BotI(GameManager gameManager) {
        // BotI needs a reference to GameManager, as this is how it will communicate
        // with the game (e.g., receiving notice a user has made a move, retrieving
        // the current state of the checkers board)
        this.gameManager = gameManager;
    }

    // Checks if a potential move is legal
    // TODO: Implement
    private boolean validateMove(Pieces currentBoard, Move m) {
        return false;
    }

    // Generates a complete list of (legal) moves the bot can make
    // TODO: Implement
    private ArrayList<Move> getAvailableMoves(Pieces currentBoard) {
        ArrayList<Move> availableMoves = new ArrayList<Move>();
        // Any moves added to availableMoves should be validated with validateMove()
        return availableMoves;
    }

    // Takes the current 8x8 board state (X = human, O = bot, V = valid, ! =
    // invalid),
    // and returns a (legal/validated) move
    // TODO: Implement
    private Move generateMove(Pieces currentBoard) {
        // NOTE: availableMoves is pre-validated
        ArrayList<Move> availableMoves = getAvailableMoves(currentBoard);
        // If no moves are available, should return null
        Move tmp = null;
        return tmp;
    }

    // This method gets called by the connected GameManager whenever a user has
    // made their move.
    // TODO: Implement
    public void onUserMove(Pieces currentBoard, Move userMove) {
        Move botMove = generateMove(currentBoard);

        // botMove will be null if no moves are available. The game should end here
        if (botMove == null) {
            // Game should end here
        } else {
            // Make move
        }
    }
}
