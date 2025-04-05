package uta.cse3310.GameManager;

import uta.cse3310.GamePlay.GamePlay;
import uta.cse3310.GameTermination.GameTermination;
import uta.cse3310.Bot.BotI.BotI;
import uta.cse3310.Bot.BotII.BotII;

import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private GamePlay gp;
    private GameTermination gt;
    private BotI b1;
    private BotII b2;

    private Map<String, GameSession> activeGames;

    public GameManager() {
        gp = new GamePlay();
        gt = new GameTermination();
        b1 = new BotI();
        b2 = new BotII();
        activeGames = new HashMap<>();
    }

    public void createGame(String gameId) {
        // Stub for now
        System.out.println("Game created with ID: " + gameId);
        activeGames.put(gameId, new GameSession(gameId));
    }

    public void receiveMove(String gameId, String move) {
        // Placeholder move format
        GameSession session = activeGames.get(gameId);
        if (session != null) {
            System.out.println("Received move for game " + gameId + ": " + move);
            // [TODO]: pass move to GamePlay later
        } else {
            System.out.println("Game not found: " + gameId);
        }
    }
}
