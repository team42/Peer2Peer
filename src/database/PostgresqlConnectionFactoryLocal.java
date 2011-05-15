package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresqlConnectionFactoryLocal {
   private static final String URL = "jdbc:postgresql://localhost/nicolai3sf11";
   private static final String USERNAME = "nicolai3sf11";
   private static final String PASSWORD = "ihk100050eit";

   public static Connection createConnection() throws SQLException {
      return DriverManager.getConnection(URL, USERNAME, PASSWORD);
   }

}
