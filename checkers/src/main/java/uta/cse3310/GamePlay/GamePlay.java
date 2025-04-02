package uta.cse3310.GamePlay;

// *** Temporary
class Move {}
class Pieces {}

public class GamePlay {
	private boolean dark = false;
	private boolean diagonal = false;

	private boolean isDark(Move move) {
		//Checks if the intended move spot is dark
		return false;
	}
	private boolean isDiagonal(Pieces[][] table, Move move) {
		//Checks if the intended move is diagonal from its current position
		return false;
	}

	private void applyMove(Pieces[][] table, Move move) {}
	private void notifyTermination(Pieces[][] table) {}
}
