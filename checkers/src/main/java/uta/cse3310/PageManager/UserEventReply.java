package uta.cse3310.PageManager;

import java.util.ArrayList;
import uta.cse3310.GameManager.GameState;
import uta.cse3310.GameTermination.GameStatus;

public class UserEventReply {
    private GameState gameState;
    private ArrayList<Integer> recipients;
    private String type;
    private String message;
    private boolean success;
    private ArrayList<String> playersList;
    private GameStatus status;

    public UserEventReply() {
        this.recipients = new ArrayList<>();
        this.playersList = new ArrayList<>();
    }

    public UserEventReply(String message, boolean success) {
        this();
        this.message = message;
        this.success = success;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public ArrayList<Integer> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<Integer> recipients) {
        this.recipients = recipients;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<String> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(ArrayList<String> playersList) {
        this.playersList = playersList;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
