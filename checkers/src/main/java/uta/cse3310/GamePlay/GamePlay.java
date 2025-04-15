package uta.cse3310.GamePlay;

import uta.cse3310.GameManager.Move;
import uta.cse3310.GameManager.GameState;
import uta.cse3310.GameManager.Position;
import uta.cse3310.GameTermination.IGameTermination.Piece;
/* Game termination has a piece class that Game Manager is using in their Gamestate.java file. 
	I could implement it here, but I would have to talk to Game Manager first, so for compilation
	purposes I imported Game termination's piece class. (Written by Devyn)
*/

public class GamePlay {

	public boolean isInBounds(Move move) {// Checks if the coords of the "to" posititon is in the bounds of the array
		if (move.getTo().getX() >= 0 && move.getTo().getX() <= 7) { // If X and Y are both within 0-7
			if (move.getTo().getY() >= 0 && move.getTo().getY() <= 7) {
				return true;
			}
		}
		return false;
	}

	public boolean isDiagonal(Move move) { // Checks if the intended move is diagonal from its current position
		if (Math.abs(move.getTo().getX() - move.getFrom().getX()) == 1) { // If X2 - X1 && Y2 - Y1 both are equal to 1
			if (Math.abs(move.getTo().getY() - move.getFrom().getY()) == 1) {
				return true;
			}
		}
		return false;
	}

	private void applyMove(GameState boardState, Move move) {
		Position from = new Position(move.getFrom().getX(),
				move.getFrom().getY());
		Position to = new Position(move.getTo().getX(),
				move.getTo().getY());

		boardState.applyMove(from, to);
	}

	private boolean isSameTeam(GameState boardState, Move move) {
		Piece fromPiece = boardState.getPieceAt(move.getFrom());
		Piece toPiece = boardState.getPieceAt(move.getTo());

		if (fromPiece == null || toPiece == null) {
			return false;
		}

		return fromPiece.getColor() == toPiece.getColor(); // piece color class from iGameTermination
	}

	private void promotePiece(GameState boardState, Move move) {
		// Implementation would check if the piece should be promoted and modify the
		// piece accordingly.
	}

	private boolean isEmpty(GameState boardState, int row, int col) {
		// Would return true if table[row][col] is empty.
		return false;
	}

	public boolean isValidMove(GameState boardState, Move move) {
		return false;
	}

	public boolean isJump(GameState boardState, Move move) {// Would return true if intended move intended move captures
															// an enemy piece is valid
		boolean jump2;
		boolean empty;
		boolean teammate;
		boolean isKing = false; // Placeholder until I know when to use a king piece

		if (isKing == true) {
			if (Math.abs(move.getTo().getX() - move.getFrom().getX()) == 2) { // If X2 - X1 && Y2 - Y1 both are equal to
																				// 2
				if (Math.abs(move.getTo().getY() - move.getFrom().getY()) == 2) {
					jump2 = true;
				}
			}
		} else {
			jump2 = false;
		}

		if (isKing = false) {
			if (Math.abs(move.getTo().getX() - move.getFrom().getX()) == 2) {// X value can the absolute value of 2 but
																				// Y cannot
				if (move.getTo().getY() - move.getFrom().getY() == 2) {
					jump2 = true;
				}
			}
		} else {
			jump2 = false;
		}
		return false;
	}
}
