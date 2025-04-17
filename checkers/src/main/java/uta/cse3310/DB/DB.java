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

   public static void main(String args[]) {

      Connection c = null;
      Statement stmt = null;
      PlayerInfo player = new PlayerInfo();

      try {
         // String x= player.getEmail(1);
         // System.out.println(x);

         // Create a Connection to the database or create database if not found
         // And create staement which will be used for inserting data
         c = initConnection();
         stmt = c.createStatement();

         // *************************************************
         // ****** CREATE USER + MATCH DATABASE ******
         // ****** ONLY USE WHEN DATABASE IS CORRUPTED ******
         // *************************************************
         // createDatabase(stmt);

         // *************************************************
         // ****** TEST YOUR FUNCTIONS UNDER HERE ******
         // *************************************************

         // setSpecificDataString(stmt, 2, "USERNAME", "w21312", "USER");
         initMatch(stmt, 1, 2, "SADASDSADAS", 2, 1);
         // player.setEmail(stmt,"asddsa123", 1);

         // String x = player.getUserName(1);
         // System.out.println(x);
         // player.setUserName(stmt, "YOUR", 1);

         // String x = player.getPassWord(1);
         // System.out.println(x);
         // player.setPassWord(stmt, "YOUR", 1);

         // initUser(stmt,userName,email,password);
         // initUser(stmt, "sada","joh5605@gmail.com" , "urghqwe");

         // setSpecificUserDataInt(stmt, 1, "WINS", 300);

         // getAllUserData(stmt);

         // int size = getSizeOfData(stmt, "USER");
         // System.out.println(size);
         // getSpecificUserData(stmt, 4);

         // incrementWin(stmt, 1);
         // incrementLoss(stmt, 2);

         closeConnection(c, stmt);

      } catch (Exception e) {
         System.err.println(e.getClass().getName() + ": " + e.getMessage());
         System.exit(0);
      }

   }

   /**
    * CREATES THE DATABASE IF THERE IS NOT ONE ALREADY
    * CAN BE USED TO CHANGE INPUTTED DATA INTO OBJECTS IN THE FUTURE
    */
   private static void createDatabase(Statement statement) throws SQLException {
      String sql = "CREATE TABLE USER_DATABASE " +
            "(PLAYERID       INT     PRIMARY KEY     NOT NULL," +
            " USERNAME       TEXT    UNIQUE          NOT NULL," +
            " EMAIL          TEXT    UNIQUE          NOT NULL," +
            " PASSWORD       TEXT    NOT NULL," +
            " WINS           INT     NOT NULL," +
            " LOSSES         INT     NOT NULL)";
      statement.executeUpdate(sql);

      System.out.println("USER DATABASE CREATED");

      sql = "CREATE TABLE MATCH_DATABASE " +
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
   public static Connection initConnection() throws SQLException, ClassNotFoundException {
      Connection c = null;
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:uta\\cse3310\\DB\\Database.db");
      c.setAutoCommit(false);
      return c;

   }

   /** CLOSE CONNECTION ONCE FINISHED WITH DATABASE */
   public static void closeConnection(Connection connection, Statement statement) throws SQLException {
      statement.close();
      connection.commit();
      connection.close();
   }

   /** GET THE NUMBER OF ROWS IN THE USER DATABASE */
   public static int getSizeOfData(Statement stmt, String database) throws SQLException {
      String sql = "SELECT COUNT(*) AS row_count FROM '" + database + "_DATABASE'";
      ResultSet rs = stmt.executeQuery(sql);

      int rowCount = rs.getInt("row_count");
      return rowCount;

   }

   /** CREATE A NEW USER IN THE DATABASE */
   public static void initUser(Statement stmt, String userName, String email, String password) throws SQLException {
      // PLAYERID (INT), USERNAME(STRING), EMAIL(STRING), PASSWORD(STRING), WINS(INT),
      // LOSSES(INT)
      int playerID = getSizeOfData(stmt, "USER") + 1;
      String sql = "INSERT INTO USER_DATABASE (PLAYERID,USERNAME,EMAIL,PASSWORD, WINS, LOSSES) " +
            "VALUES (" + playerID + ",'" + userName + "','" + email + "','" + password + "', 0, 0 );";
      stmt.executeUpdate(sql);
   }

   /** CREATE A NEW MATCH IN THE DATABASE */
   public static void initMatch(Statement stmt, int BLACKPLAYERID, int REDPLAYERID, String BOARDSTATE, int WINNERID,
         int LOSERID) throws SQLException {
      // BLACKPLAYERID(INT), REDPLAYERID(INT), BOARDSTATE(STRING), WINNERID(INT),
      // LOSERID(INT)
      int matchID = getSizeOfData(stmt, "MATCH") + 1;
      String sql = "INSERT INTO MATCH_DATABASE (MATCHID,BLACKPLAYERID,REDPLAYERID,BOARDSTATE, WINNER, LOSER) " +
            "VALUES (" + matchID + "," + BLACKPLAYERID + "," + REDPLAYERID + ",'" + BOARDSTATE + "'," + WINNERID + ","
            + LOSERID + " );";

      stmt.executeUpdate(sql);
   }

   /**
    * PRINT ALL THE USER DATA
    * RETURN A VECTOR OF PLAYERINFO
    * WHEN THAT CLASS IS FINISHED
    */
   public static void getAllUserData() throws SQLException, ClassNotFoundException {
      Connection c = DB.initConnection();
      Statement stmt = c.createStatement();
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

         System.out.printf("%-20d %-20s %-25s %-25s %-20d %-20d\n", playerID, username, email, password, wins, losses);
         // System.out.println("\n");
      }
   }

   /**
    * PRINT A SPECIFIC DATA
    * RETURN A VECTOR OF THE DATABSE INFO
    * WHEN THAT CLASS IS FINISHED
    */
   public static ResultSet getSpecificData(int targetID, String database) throws SQLException, ClassNotFoundException {
      Connection c = initConnection();
      Statement stmt = c.createStatement();

      String sql = "SELECT * FROM " + database + "_DATABASE WHERE PLAYERID == " + targetID + "";
      ResultSet rs = stmt.executeQuery(sql);

      // String email = rs.getString("EMAIL");
      // String password = rs.getString("PASSWORD");
      // int wins = rs.getInt("WINS");
      // int losses = rs.getInt("LOSSES");

      // System.out.printf("%-20s %-20s %-25s %-25s %-20s
      // %-20s\n","PLAYERID","USERNAME","EMAIL","PASSWORD","WINS","LOSSES");
      // System.out.printf("%-20d %-20s %-25s %-25s %-20d
      // %-20d\n",targetUserID,username,email,password,wins,losses);
      return rs;

   }

   public static void setSpecificDataString(Statement stmt, int targetUserID, String setValueString, String value,
         String database) throws SQLException, ClassNotFoundException {
      String sql = "UPDATE " + database + "_DATABASE SET " + setValueString + " = '" + value + "' WHERE PLAYERID = "
            + targetUserID + "";
      stmt.executeUpdate(sql);
   }

   public static void setSpecificDataInt(Statement stmt, int targetUserID, String setValueString, int value,
         String database) throws SQLException, ClassNotFoundException {
      String sql = "UPDATE " + database + "_DATABASE SET " + setValueString + " = " + value + " WHERE PLAYERID = "
            + targetUserID + "";
      stmt.executeUpdate(sql);
   }

   /** GET PLAYER ID FROM USERNAME */
   public static int getPlayerIdByUsername(String username) throws SQLException, ClassNotFoundException {
      Connection c = initConnection();
      Statement stmt = c.createStatement();
      int playerId = -1; // Default to -1 if not found

      String sql = "SELECT PLAYERID FROM USER_DATABASE WHERE USERNAME = '" + username + "'";
      ResultSet rs = stmt.executeQuery(sql);

      if (rs.next()) {
         playerId = rs.getInt("PLAYERID");
      }

      rs.close();
      closeConnection(c, stmt);
      return playerId;
   }

   /** INCREMENT THE PLAYER WINS BY 1 */
   public static void incrementWin(int playerID) throws SQLException, ClassNotFoundException {
      Connection c = DB.initConnection();
      Statement stmt = c.createStatement();

      ResultSet rs = getSpecificData(playerID, "USER");
      int wins = rs.getInt("WINS") + 1;
      String sql = "UPDATE USER_DATABASE SET WINS = " + wins + " WHERE PLAYERID = " + playerID + "";
      stmt.executeUpdate(sql);
   }

   /** INCREMENT THE PLAYER LOSSES BY 1 */
   public static void incrementLoss(int playerID) throws SQLException, ClassNotFoundException {
      Connection c = DB.initConnection();
      Statement stmt = c.createStatement();

      ResultSet rs = getSpecificData(playerID, "USER");
      int loss = rs.getInt("LOSSES") + 1;
      String sql = "UPDATE USER_DATABASE SET LOSSES = " + loss + " WHERE PLAYERID = " + playerID + "";
      stmt.executeUpdate(sql);
   }

}