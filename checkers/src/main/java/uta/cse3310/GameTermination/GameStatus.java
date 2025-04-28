package uta.cse3310.GameTermination;

import uta.cse3310.GameManager.Player;
import uta.cse3310.GamePlay.GamePlay;
import java.util.Map;

/**
 * Represents the possible statuses of the game.
 * This enum is used to indicate the current state of the game.
 */
public enum GameStatus {
    /**
     * Indicates that the black player has won the game.
     */
    BLACK_WIN,

    /**
     * Indicates that the red player has won the game.
     */
    RED_WIN,

    /**
     * Indicates that the game is still ongoing.
     */
    ONGOING,

    /**
     * Indicates that the game has ended in a draw.
     */
    DRAW
}
