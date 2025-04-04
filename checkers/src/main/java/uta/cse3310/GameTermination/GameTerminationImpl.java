package uta.cse3310.GameTermination;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the game termination logic as defined by the GameTermination
 * interface.
 */
public class GameTerminationImpl implements GameTermination {

    @Override
    public void reset() {
        boardStateHistory.clear();
        terminationReason = TerminationReason.ONGOING;
    }

    @Override
    public TerminationReason getTerminationReason() {
        return terminationReason;
    }
}