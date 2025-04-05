package uta.cse3310.Bot.BotI;

import uta.cse3310.GameManager.GameManager;
import java.util.ArrayList;

class Pieces {
};

class Move {
};

public class BotI {

	private GameManager gameManager;


	public BotI(GameManager gameManager) {
	this.gameManager = gameManager;
	}


	private boolean validateMove(Pieces currentBoard, Move m) {
		return false;
	}

	private ArrayList<Move> getAvailableMoves(Pieces currentBoard) {
		ArrayList<Move> availableMoves = new ArrayList<Move>();
		return availableMoves;
	}

	private Move generateMove(Pieces currentBoard) {
		ArrayList<Move> availableMoves = getAvailableMoves(currentBoard);
		Move tmp = new Move();
		return tmp;
	}

	public void onUserMove(Pieces currentBoard, Move userMove) {
		Move botMove = generateMove(currentBoard);
		if (botMove == null) {

		} else {

		}
	}
}
