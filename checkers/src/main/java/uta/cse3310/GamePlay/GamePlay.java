package uta.cse3310.GamePlay;

import uta.cse3310.GameManager.Move;
import uta.cse3310.GameManager.GameState;
import uta.cse3310.GameManager.Position;
import uta.cse3310.GameManager.Piece;
import uta.cse3310.GameManager.Player;

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

	public void applyMove(GameState boardState, Move move) {
		Position from = new Position(move.getFrom().getX(),
				move.getFrom().getY());
		Position to = new Position(move.getTo().getX(),
				move.getTo().getY());

		boardState.applyMove(from, to);
	}

	private boolean isSameTeam(GameState boardState, Move move) {
        Piece fromPiece = boardState.getPieceAt(move.getFrom()); //should be fixed since piece class is properly determined 
        Piece toPiece = boardState.getPieceAt(move.getTo());

        if (fromPiece == null || toPiece == null) {
            return false;
        }

        return fromPiece.getPlayerId().equals(toPiece.getPlayerId()); //Now using GameManager's player class
    }

	private void promotePiece(GameState boardState, Move move) {
		// String currentPlayer = gameSession.getCurrentPlayer();
		// getCurrentPlayer() is not public, need to talk with GameManager on making it
		// public as
		// the logic can't work unless we know whose peice is on the edge

		String currentPlayer = "Player1"; // Placeholder till we get the current player
		boolean isKing = false; // Placeholder until I know when to use a king piece

		// Piece piece = boardState.getPieceAt(move.getTo());
		Piece piece = null;
		if (piece == null) {
			return;
		}

		if (isKing == true) {
			return;
		}

		// Promotion Logic
		// This might require some modifications in the future regarding if the
		// appropriate peice on the oppoent's edge
		if (currentPlayer == "Player1" && move.getTo().getY() == 0) {
			isKing = true;
		} else if (currentPlayer == "Player2" && move.getTo().getY() == 7) {
			isKing = true;
		}
	}

	private boolean isEmpty(GameState boardState, int row, int col) {
		Position pos = new Position(row, col);
		return boardState.getPieceAt(pos) == null;
	}

	public boolean isValidMove(GameState boardState, Move move) {
    if (!isInBounds(move)) {
        return false;
    }

    if (!isDiagonal(move)) {
        return false;
    }

    if (!isEmpty(boardState, move.getTo().getX(), move.getTo().getY())) {
        return false;
    }

    if (isSameTeam(boardState, move)) {
        return false;
    }

    return true; //changed back to return true (should be fixed if not will change it back)
}

	public boolean isJump(GameState boardState, Move move) {// Would return true if intended move intended move captures
		boolean jump2 = true;
		boolean isKing = false; // Placeholder until I know when to use a king piece
		int positionX;
		int positionY;
		boolean bounds = isInBounds(move);

		if (move.getTo().getX() < move.getFrom().getX()) {
			positionX = move.getTo().getX() - 1;
		} else {
			positionX = move.getTo().getX() + 1;
		}

		if (move.getTo().getY() < move.getFrom().getY()) {
			positionY = move.getTo().getY() - 1;
		} else {
			positionY = move.getTo().getY() + 1;
		}

		if ((isKing == true) && (bounds = true)) {
			if (Math.abs(move.getTo().getX() - move.getFrom().getX()) == 2) { // If X2 - X1 && Y2 - Y1 both are equal to
				if (Math.abs(move.getTo().getY() - move.getFrom().getY()) == 2) {
					if (isEmpty(boardState, positionX, positionY) == false || isSameTeam(boardState, move) == true) {
						jump2 = true;
					}
				}
			}
		} else {
			jump2 = false;
		}

		if ((isKing = false) && (bounds = true)) {
			if (Math.abs(move.getTo().getX() - move.getFrom().getX()) == 2) {// X value can the absolute value of 2 but
																				// Y cannot
				if (move.getTo().getY() - move.getFrom().getY() == 1) {
					if (isEmpty(boardState, positionX, positionY) == false || isSameTeam(boardState, move) == true) {
						jump2 = true;
					}
				}
			}
		} else {
			jump2 = false;
		}
		return jump2;
	}
}