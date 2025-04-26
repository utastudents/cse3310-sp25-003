package uta.cse3310.DB;
// import uta.cse3310.DB.PlayerInfo;

// import uta.cse3310.DB.MatchHistory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Compile with
// javac *.java  

// Run with Command below when on Windows
// java -cp ".;sqlite-jdbc-3.49.1.0.jar" .\DB.java

// or use bat File
// .\run-db.bat

public class DB {

   public Connection connection;
   private static DB db;

   private DB() {
      try {
         this.initConnection();

         Statement st = connection.createStatement();

         this.createDatabase(st);

         st.close();

      } catch (SQLException e) {
         System.out.println("Falied to initialize database");
      } catch (ClassNotFoundException e) {
         System.out.println("Falied to connect to database");
      }

   }

   public static DB getDB() {
      if (db != null) {
         return db;
      } else {
         db = new DB();
         return db;
      }
   }

   /**
    * CREATES THE DATABASE IF THERE IS NOT ONE ALREADY
    * CAN BE USED TO CHANGE INPUTTED DATA INTO OBJECTS IN THE FUTURE
    */
   private void createDatabase(Statement statement) throws SQLException {
      String sql = "CREATE TABLE IF NOT EXISTS USER_DATABASE " +
            "(PLAYERID       INT     PRIMARY KEY     NOT NULL," +
            " USERNAME       TEXT    UNIQUE          NOT NULL," +
            " EMAIL          TEXT    UNIQUE          NOT NULL," +
            " PASSWORD       TEXT    NOT NULL," +
            " WINS           INT     NOT NULL," +
            " LOSSES         INT     NOT NULL)";
      statement.executeUpdate(sql);

      System.out.println("USER DATABASE CREATED");

      sql = "CREATE TABLE IF NOT EXISTS MATCH_DATABASE " +
            "(MATCHID        INT     PRIMARY KEY     NOT NULL," +
            " BLACKPLAYERID  INT     NOT NULL," +
            " REDPLAYERID    INT     NOT NULL," +
            " BOARDSTATE     TEXT    NOT NULL," +
            " WINNER         INT     NOT NULL," +
            " LOSER          INT     NOT NULL)";

      statement.executeUpdate(sql);
      System.out.println("MATCH DATABASE CREATED");

   }

   /**
    * RECEIVES A CONNECTION FROM THE DATABASE TO
    * DO OPERATIONS LIKE INSERTING, UPDATING, AND SEARCHING
    * WITH THE CONNECTION
    */
   public void initConnection() throws SQLException, ClassNotFoundException {
      Class.forName("org.sqlite.JDBC");
      this.connection = DriverManager.getConnection("jdbc:sqlite:uta/cse3310/DB/Database.db");
   }

   /** CLOSE CONNECTION ONCE FINISHED WITH DATABASE */
   public void closeConnection() throws SQLException {
      this.connection.commit();
      this.connection.close();
   }

   /** GET THE NUMBER OF ROWS IN THE USER DATABASE */
   public int getSizeOfData(String database) throws SQLException {
      Statement stmt = this.connection.createStatement();
      String sql = "SELECT COUNT(*) AS row_count FROM '" + database + "_DATABASE'";
      ResultSet rs = stmt.executeQuery(sql);

      int rowCount = rs.getInt("row_count");
      stmt.close();
      return rowCount;

   }

   /** CREATE A NEW USER IN THE DATABASE */
   public void initUser(String userName, String email, String password) throws SQLException {
      // PLAYERID (INT), USERNAME(STRING), EMAIL(STRING), PASSWORD(STRING), WINS(INT),
      // LOSSES(INT)
      Statement stmt = this.connection.createStatement();
      int playerID = getSizeOfData("USER") + 1;
      String sql = "INSERT INTO USER_DATABASE (PLAYERID,USERNAME,EMAIL,PASSWORD, WINS, LOSSES) " +
            "VALUES (" + playerID + ",'" + userName + "','" + email + "','" + password + "', 0, 0 );";
      stmt.executeUpdate(sql);
      stmt.close();
   }

   /** CREATE A NEW MATCH IN THE DATABASE */
   public void initMatch(int BLACKPLAYERID, int REDPLAYERID, String BOARDSTATE, int WINNERID,
         int LOSERID) throws SQLException {
      Statement stmt = this.connection.createStatement();
      // BLACKPLAYERID(INT), REDPLAYERID(INT), BOARDSTATE(STRING), WINNERID(INT),
      // LOSERID(INT)
      int matchID = getSizeOfData("MATCH") + 1;
      String sql = "INSERT INTO MATCH_DATABASE (MATCHID,BLACKPLAYERID,REDPLAYERID,BOARDSTATE, WINNER, LOSER) " +
            "VALUES (" + matchID + "," + BLACKPLAYERID + "," + REDPLAYERID + ",'" + BOARDSTATE + "'," + WINNERID + ","
            + LOSERID + " );";

      stmt.executeUpdate(sql);
      stmt.close();
   }

   /**
    * PRINT ALL THE USER DATA
    * RETURN A VECTOR OF PLAYERINFO
    * WHEN THAT CLASS IS FINISHED
    */
   public void printAllUserData() {
      try {

         Statement stmt = this.connection.createStatement();
         // PLAYERID (INT), USERNAME(STRING), EMAIL(STRING), PASSWORD(STRING), WINS(INT),
         // LOSSES(INT)

         String sql = "SELECT * FROM USER_DATABASE";
         ResultSet rs = stmt.executeQuery(sql);

         System.out.printf("%-20s %-20s %-25s %-25s %-20s %-20s\n", "PLAYERID", "USERNAME", "EMAIL", "PASSWORD", "WINS",
               "LOSSES");

         while (rs.next()) {
            int playerID = rs.getInt("PLAYERID");
            String username = rs.getString("USERNAME");
            String email = rs.getString("EMAIL");
            String password = rs.getString("PASSWORD");
            int wins = rs.getInt("WINS");
            int losses = rs.getInt("LOSSES");

            System.out.printf("%-20d %-20s %-25s %-25s %-20d %-20d\n", playerID, username, email, password, wins,
                  losses);
            // System.out.println("\n");
         }
         stmt.close();
      } catch (SQLException e) {
         System.out.println("Falied to get data from database");
      }
   }

   /**
    * PRINT A SPECIFIC DATA
    * RETURN A VECTOR OF THE DATABSE INFO
    * WHEN THAT CLASS IS FINISHED
    */
   public PlayerInfo getPlayerInfo(int playerID) {
      try {
         Statement stmt = this.connection.createStatement();

         String sql = "SELECT * FROM USER_DATABASE WHERE PLAYERID == " + playerID + "";
         ResultSet rs = stmt.executeQuery(sql);

         if (rs.next()) {
            String username = rs.getString("USERNAME");
            String email = rs.getString("EMAIL");
            String password = rs.getString("PASSWORD");
            int wins = rs.getInt("WINS");
            int losses = rs.getInt("LOSSES");

            PlayerInfo player = new PlayerInfo(playerID, username, password, email, wins, losses);
            stmt.close();
            return player;
         }
      } catch (SQLException e) {
         System.out.println("Failed to get player info");
      }

      // System.out.printf("%-20s %-20s %-25s %-25s %-20s
      // %-20s\n","PLAYERID","USERNAME","EMAIL","PASSWORD","WINS","LOSSES");
      // System.out.printf("%-20d %-20s %-25s %-25s %-20d
      // %-20d\n",targetUserID,username,email,password,wins,losses);
      return null;
   }

   public void updatePlayerInfo(PlayerInfo player) throws SQLException, ClassNotFoundException {
      Statement stmt = this.connection.createStatement();
      String sql = "UPDATE USER_DATABASE SET WINS = '" + player.getWins() + "',LOSSES = '" + player.getLosses()
            + "' WHERE PLAYERID = "
            + player.getId() + "";
      stmt.executeUpdate(sql);
   }

   public void updateMatch(MatchHistory match) throws SQLException, ClassNotFoundException {
      Statement stmt = this.connection.createStatement();
      String sql = "UPDATE MATCH_DATABASE SET BOARDSTATE = '" + match.getBoardState() + "', WINNER = '"
            + match.getWinnerId() + "', LOSSER = '" + match.getLosserId() + "' WHERE MATCHID = "
            + match.getId() + "";
      stmt.executeUpdate(sql);
   }

   /** GET PLAYER ID FROM USERNAME */
   public int getPlayerIdByUsername(String username) {

      int playerId = -1; // Default to -1 if not found
      try {
         Statement stmt = this.connection.createStatement();

         String sql = "SELECT PLAYERID FROM USER_DATABASE WHERE USERNAME = '" + username + "'";
         ResultSet rs = stmt.executeQuery(sql);

         if (rs.next()) {
            playerId = rs.getInt("PLAYERID");
         }

         rs.close();
         stmt.close();
      } catch (SQLException e) {
         System.out.println("Failed to get player id from database");
      }
      return playerId;
   }

}