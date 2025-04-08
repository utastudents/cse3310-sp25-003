package uta.cse3310.DB;

import java.sql.*;

// Compile with
// javac ./DB.java

// Open Database with Command below
// java -classpath ".;sqlite-jdbc-3.7.2.jar" DB.java


public class DB {
  public static void main( String args[] ) {
      Connection c = null;
      Statement stmt = null;
      
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:Database.db");
         c.setAutoCommit(false);
         stmt = c.createStatement();

         System.out.println("Opened database successfully");

         String sql = "CREATE TABLE USER_DATABASE " +
                        "(PLAYERID       INT     PRIMARY KEY     NOT NULL," +
                        " USERNAME       TEXT    NOT NULL," + 
                        " EMAIL          TEXT    NOT NULL," + 
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


         // sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
         //                "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
         // stmt.executeUpdate(sql);

         // sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
         //          "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );"; 
         // stmt.executeUpdate(sql);

         // sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
         //          "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );"; 
         // stmt.executeUpdate(sql);

         // sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
         //          "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );"; 
         // stmt.executeUpdate(sql);

         stmt.close();
         c.commit();
         c.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }

   }
}