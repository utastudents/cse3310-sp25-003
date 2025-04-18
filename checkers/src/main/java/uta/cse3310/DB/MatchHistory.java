package uta.cse3310.DB;

import java.io.*;

public class MatchHistory {

    private int playerID;
    private String boardState;

    // Getter for playerID
    public int getPlayerID() {
        return playerID;
    }

    // Setter for playerID
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    // Getter for boardState
    public String getBoardState() {
        return boardState;
    }

    // Setter for boardState
    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    
    public void saveGame() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("match_" + playerID + ".txt"))) {
            writer.write(playerID + "\n");
            writer.write(boardState + "\n");
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    
    public void loadGame() {
        try (BufferedReader reader = new BufferedReader(new FileReader("match_" + playerID + ".txt"))) {
            String idLine = reader.readLine();
            String boardStateLine = reader.readLine();

            this.playerID = Integer.parseInt(idLine);
            this.boardState = boardStateLine;

            System.out.println("Game loaded successfully.");
        } catch (IOException e) {
            System.err.println("Error loading game: " + e.getMessage());
        }
    }
}
