
package uta.cse3310.DB;

import java.sql.*;

// Compile with
// javac ./DB.java

// Run with Command below
// java -classpath ".\sqlite-jdbc-3.49.1.0.jar" .\DB.java


public class DB {

  public static void main( String args[] ) {

      Connection c = null;
      Statement stmt = null;
      
      try {
         // Create a Connection to the database or create database if not found
         // And create staement which will be used for inserting data
         c = initConnection();
         stmt = c.createStatement();

         // ******   CREATE USER + MATCH DATABASE      ******
         // ****** ONLY USE WHEN DATABASE IS CORRUPTED ******
         // createDatabase(stmt);

         // String userName = "zxc";
         // String email = "asdasd";
         // String password= "thusaintagoodpassword";

         // initUser(stmt,userName,email,password);
         initUser(stmt, "Shawnabe12","johnabe5605@gmail.com" , "urgh");
         getAllUserData(stmt);

         closeConnection(c, stmt);

         
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }

   }
   public static Connection initConnection() throws SQLException, ClassNotFoundException{
      Connection c = null;
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:Database.db");
      c.setAutoCommit(false);
      return c;

   }
   public static void closeConnection(Connection c, Statement stmt) throws SQLException{
      stmt.close();
      c.commit();
      c.close();
   }
   @SuppressWarnings("unused")
   private static void createDatabase(Statement stmt) throws SQLException{
         String sql = "CREATE TABLE USER_DATABASE " +
                        "(PLAYERID       INT     PRIMARY KEY     NOT NULL," +
                        " USERNAME       TEXT    UNIQUE          NOT NULL," + 
                        " EMAIL          TEXT    UNIQUE          NOT NULL," + 
                        " PASSWORD       TEXT    NOT NULL," +
                        " WINS           INT     NOT NULL," +
                        " LOSSES         INT     NOT NULL)"; 
         stmt.executeUpdate(sql);

         System.out.println("USER DATABASE CREATED");

         sql = "CREATE TABLE MATCH_DATABASE " +
                        "(MATCHID        INT     PRIMARY KEY     NOT NULL," + 
                        " BLACKPLAYERID  INT     NOT NULL," +
                        " REDPLAYERID    INT     NOT NULL," +
                        " BOARDSTATE     TEXT    NOT NULL," + 
                        " WINNER         INT     NOT NULL," +
                        " LOSER          INT     NOT NULL)"; 
         
         stmt.executeUpdate(sql);
         System.out.println("MATCH DATABASE CREATED");


   }
   public static void initUser(Statement stmt, String userName, String email, String password) throws SQLException{
      // PLAYERID (INT), USERNAME(STRING), EMAIL(STRING), PASSWORD(STRING), WINS(INT), LOSSES(INT)
      String sql = "INSERT INTO USER_DATABASE (PLAYERID,USERNAME,EMAIL,PASSWORD, WINS, LOSSES) " +
                     "VALUES (3,'"+userName+"','"+email+"','"+password+"', 0, 0 );"; 
      System.err.println(sql);
                     
      stmt.executeUpdate(sql);
   }
   public static void initMatch(Statement stmt, int BLACKPLAYERID, int REDPLAYERID, String BOARDSTATE, int WINNERID, int LOSERID)throws SQLException{
      // BLACKPLAYERID(INT), REDPLAYERID(INT), BOARDSTATE(STRING), WINNERID(INT), LOSERID(INT)
      String sql = "INSERT INTO MATCH_DATABASE (MATCHID,BLACKPLAYERID,REDPLAYERID,BOARDSTATE, WINNERID, LOSERID) " +
                     "VALUES (1,"+BLACKPLAYERID+","+REDPLAYERID+","+BOARDSTATE+","+WINNERID+","+LOSERID+" );"; 
      stmt.executeUpdate(sql);
   }
   public static void getAllUserData(Statement stmt) throws SQLException{
      // PLAYERID (INT), USERNAME(STRING), EMAIL(STRING), PASSWORD(STRING), WINS(INT), LOSSES(INT)
      System.out.printf("%-20s %-20s %-25s %-25s %-20s %-20s","PLAYERID","USERNAME","EMAIL","PASSWORD","WINS","LOSSES");
      System.out.print("\n");
      
      String sql = "SELECT * FROM USER_DATABASE";
      ResultSet rs = stmt.executeQuery(sql);
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
}