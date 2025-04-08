
package uta.cse3310.DB;

import java.sql.*;

// Compile with
// javac ./DB.java

// Open Databases with Command below
// java -classpath ".;sqlite-jdbc-3.7.2.jar" ./DB.java


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

         String userName = "";
         String email = "";
         String password= "";

         initUser(stmt,userName,email,password);

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
                        " WHITEPLAYERID  INT     NOT NULL," +
                        " BOARDSTATE     TEXT    NOT NULL," + 
                        " WINNER         INT     NOT NULL," +
                        " LOSER          INT     NOT NULL)"; 
         
         stmt.executeUpdate(sql);
         System.out.println("MATCH DATABASE CREATED");


   }
   public static void initUser(Statement stmt, String userName, String email, String password) throws SQLException{
      // PLAYERID (INT), USERNAME(STRING), EMAIL(STRING), PASSWORD(STRING), WINS(INT), LOSSES(INT)
      String sql = "INSERT INTO USER_DATABASE (PLAYERID,USERNAME,EMAIL,PASSWORD, WINS, LOSSES) " +
                     "VALUES (2,"+userName+","+email+","+password+", 0, 0 )"; 
      stmt.executeUpdate(sql);
   }
}