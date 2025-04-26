package uta.cse3310.DB;

public class PlayerInfo {
    private int id;

    private String username;
    private String password;
    private String email;
    private int wins;
    private int losses;
    private double elo;

    public PlayerInfo(int id, String username, String password, String email, int wins, int losses) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.wins = wins;
        this.losses = losses;
        this.elo = 0;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public double getElo() {

        return elo;
    }

    public double calculateElo() {
        double kFactor = 32; // K-factor used for Elo rating systems
        int opponentElo = 1200; // Example opponent Elo

        // Calculate expected score (score = 1/(1 + 10^((opponent Elo - Elo) / 400)))
        double expectedScore = 1 / (1 + Math.pow(10, (opponentElo - this.elo) / 400.0));

        // Calculate actual score based on win/loss
        double actualScore = this.wins > this.losses ? 1 : 0; // 1 for win, 0 for loss

        // Update Elo rating (Elo = Elo + K-factor * (actual score - expected score))
        this.elo = this.elo + kFactor * (actualScore - expectedScore);

        return this.elo;

        // USAGE:
        // PlayerInfo player = new PlayerInfo();
        // player.setElo(1200); // Initial Elo (example value)
        // player.setWins(0);
        // player.setLosses(0);

        // double newElo = player.calculateElo(1, 0); // Player wins the match
        // System.out.println("Updated Elo: " + newElo);
        // System.out.println("Total Wins: " + player.getWins(0));
        // System.out.println("Total Losses: " + player.getLosses(0));
    }

}
