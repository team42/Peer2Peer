package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresqlConnectionFactory {
   private static final String URL = "jdbc:postgresql://192.168.1.101/postgres";
   private static final String USERNAME = "team42";
   private static final String PASSWORD = "WINston1945!";

   public static Connection createConnection() throws SQLException {
      return DriverManager.getConnection(URL, USERNAME, PASSWORD);
   }

}
