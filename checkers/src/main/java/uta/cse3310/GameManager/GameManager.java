package uta.cse3310.GameManager;

import uta.cse3310.GamePlay.GamePlay;
import uta.cse3310.GameTermination.GameTermination;
import uta.cse3310.Bot.BotI.BotI;
import uta.cse3310.Bot.BotII.BotII;

public class GameManager {

    private GamePlay gp;
    private GameTermination gt;
    private BotI b1;
    private BotII b2;

    // ✅ Only ONE active game session
    private GameSession session;

    public GameManager() {
        gp = new GamePlay();
        gt = new GameTermination();
        b1 = new BotI(this);  // BotI expects GameManager reference
        b2 = new BotII();
    }

    // ✅ Create a single game session
    public void createGame() {
        System.out.println("Game created.");
        session = new GameSession("Game001"); //
