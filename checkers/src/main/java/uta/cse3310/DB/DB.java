
// package uta.cse3310.DB;
// import uta.cse3310.DB.PlayerInfo;
// import uta.cse3310.DB.MatchHistory;

import java.sql.*;

// Compile with
// javac *.java  

// Run with Command below when on Windows
// java -cp ".;sqlite-jdbc-3.49.1.0.jar" .\DB.java

// or use bat File
// .\run-db.bat


public class DB {

  public static void main( String args[] ){

      Connection c = null;
      Statement stmt = null;
      PlayerInfo player = new PlayerInfo();
      
      
      
      try {
         String x= player.getEmail(1);
         System.out.println(x);

         // Create a Connection to the database or create database if not found
         // And create staement which will be used for inserting data
         c = initConnection();
         stmt = c.createStatement();
         // *************************************************
         // ******   CREATE USER + MATCH DATABASE      ******
         // ****** ONLY USE WHEN DATABASE IS CORRUPTED ******
         // *************************************************
         // createDatabase(stmt);


         // *************************************************
         // ******   TEST YOUR FUNCTIONS UNDER HERE    ******
         // *************************************************

         
         // initUser(stmt,userName,email,password);
         // initUser(stmt, "sada","joh5605@gmail.com" , "urghqwe");
         getAllUserData(stmt);
         // int size = getSizeOfUserData(stmt);
         // getSpecificUserData(stmt, 4);
         // incrementWin(stmt, 1);
         // incrementLoss(stmt, 2);
         // player.getEmail(4);


         closeConnection(c, stmt);

         
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }

   }

   /**CREATES THE DATABASE IF THERE IS NOT ONE ALREADY 
    * CAN BE USED TO CHANGE INPUTTED DATA INTO OBJECTS IN THE FUTURE*/ 
   private static void createDatabase(Statement statement) throws SQLException{
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

   /** RECEIVES A CONNECTION FROM THE DATABASE TO
    * DO OPERATIONS LIKE INSERTING, UPDATING, AND SEARCHING
    * WITH THE CONNECTION*/
   public static Connection initConnection() throws SQLException, ClassNotFoundException{
      Connection c = null;
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:Database.db");
      c.setAutoCommit(false);
      return c;

   }

   /**CLOSE CONNECTION ONCE FINISHED WITH DATABASE */
   public static void closeConnection(Connection connection, Statement statement) throws SQLException{
      statement.close();
      connection.commit();
      connection.close();
   }
   
  /**GET THE NUMBER OF ROWS IN THE USER DATABASE*/ 
   public static int getSizeOfUserData(Statement stmt) throws SQLException{
      String sql = "SELECT COUNT(*) AS row_count FROM 'USER_DATABASE'";
      ResultSet rs = stmt.executeQuery(sql);

      
      int rowCount = rs.getInt("row_count");
      return rowCount;
      

   } 

   /**GET THE NUMBER OF ROWS IN THE MATCH DATABASE*/ 
   public static int getSizeofMatchData(Statement stmt) throws SQLException{
      String sql = "SELECT COUNT(*) AS row_count FROM 'MATCH_DATABASE'";
      ResultSet rs = stmt.executeQuery(sql);

      int rowCount = rs.getInt("row_count");
      return rowCount;      
   }

   /** CREATE A NEW USER IN THE DATABASE */
   public static void initUser(Statement stmt, String userName, String email, String password) throws SQLException{
      // PLAYERID (INT), USERNAME(STRING), EMAIL(STRING), PASSWORD(STRING), WINS(INT), LOSSES(INT)
      int playerID = getSizeOfUserData(stmt) + 1;
      String sql = "INSERT INTO USER_DATABASE (PLAYERID,USERNAME,EMAIL,PASSWORD, WINS, LOSSES) " +
                     "VALUES ("+playerID+",'"+userName+"','"+email+"','"+password+"', 0, 0 );"; 
      System.err.println(sql);
                     
      stmt.executeUpdate(sql);
   }
   /** CREATE A NEW MATCH IN THE DATABASE */
   public static void initMatch(Statement stmt, int BLACKPLAYERID, int REDPLAYERID, String BOARDSTATE, int WINNERID, int LOSERID)throws SQLException{
      // BLACKPLAYERID(INT), REDPLAYERID(INT), BOARDSTATE(STRING), WINNERID(INT), LOSERID(INT)
      String sql = "INSERT INTO MATCH_DATABASE (MATCHID,BLACKPLAYERID,REDPLAYERID,BOARDSTATE, WINNERID, LOSERID) " +
                     "VALUES (1,"+BLACKPLAYERID+","+REDPLAYERID+","+BOARDSTATE+","+WINNERID+","+LOSERID+" );"; 
      stmt.executeUpdate(sql);
   }
   
   /** PRINT ALL THE USER DATA
    *RETURN A VECTOR OF PLAYERINFO 
    *WHEN THAT CLASS IS FINISHED */
   public static void getAllUserData(Statement stmt) throws SQLException{
      // PLAYERID (INT), USERNAME(STRING), EMAIL(STRING), PASSWORD(STRING), WINS(INT), LOSSES(INT)
      
      String sql = "SELECT * FROM USER_DATABASE";
      ResultSet rs = stmt.executeQuery(sql);

      System.out.printf("%-20s %-20s %-25s %-25s %-20s %-20s\n","PLAYERID","USERNAME","EMAIL","PASSWORD","WINS","LOSSES");

      while(rs.next()){
         int playerID = rs.getInt("PLAYERID");
         String username = rs.getString("USERNAME");
         String email = rs.getString("EMAIL");
         String password = rs.getString("PASSWORD");
         int wins = rs.getInt("WINS");
         int losses = rs.getInt("LOSSES");

         System.out.printf("%-20d %-20s %-25s %-25s %-20d %-20d\n",playerID,username,email,password,wins,losses);
         // System.out.println("\n");
      }
   }
   
   /** PRINT A SPECIFIC USER'S DATA
    *  RETURN A VECTOR OF THE PLAYER INFO 
    *  WHEN THAT CLASS IS FINISHED */
   public static ResultSet getSpecificUserData(Statement stmt, int targetUserID) throws SQLException{
      
      String sql = "SELECT * FROM USER_DATABASE WHERE PLAYERID == "+targetUserID+"";
      ResultSet rs = stmt.executeQuery(sql);
      String username = rs.getString("USERNAME");
      if (username == null){
         System.out.println("targetUserID does not exist");
         return null;
      }
      // String email = rs.getString("EMAIL");
      // String password = rs.getString("PASSWORD");
      // int wins = rs.getInt("WINS");
      // int losses = rs.getInt("LOSSES");

      // System.out.printf("%-20s %-20s %-25s %-25s %-20s %-20s\n","PLAYERID","USERNAME","EMAIL","PASSWORD","WINS","LOSSES");
      // System.out.printf("%-20d %-20s %-25s %-25s %-20d %-20d\n",targetUserID,username,email,password,wins,losses);
      return rs;

   }
   
   /**  INCREMENT THE PLAYER WINS BY 1*/
   public static void incrementWin(Statement stmt, int playerID) throws SQLException{
      ResultSet rs = getSpecificUserData(stmt, playerID);
      int wins = rs.getInt("WINS") + 1;
      String sql = "UPDATE USER_DATABASE SET WINS = "+wins+" WHERE PLAYERID = "+playerID+"";
      stmt.executeUpdate(sql);

   }

   /** INCREMENT THE PLAYER LOSSES BY 1*/
   public static void incrementLoss(Statement stmt, int playerID) throws SQLException{
      ResultSet rs = getSpecificUserData(stmt, playerID);
      int loss = rs.getInt("LOSSES") + 1;
      String sql = "UPDATE USER_DATABASE SET LOSSES = "+loss+" WHERE PLAYERID = "+playerID+"";
      stmt.executeUpdate(sql);
   }
   
}