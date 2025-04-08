package uta.cse3310.DB;

import java.sql.*;

// Compile with
// javac ./DB.java

// Open Database with Command below
// java -classpath ".;sqlite-jdbc-3.7.2.jar" DB.java


public class DB {
  public static void main( String args[] ) {
      Connection c = null;
      
      try {

        Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:Database.db");
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
      System.out.println("Opened database successfully");
   }
}