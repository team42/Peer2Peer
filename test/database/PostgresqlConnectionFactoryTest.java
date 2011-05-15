package database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

/**
 * <code>PostgresqlConnectionFactoryTest</code> will test if we can setup a
 * connection to the database.
 *
 * @author Lasse
 */
public class PostgresqlConnectionFactoryTest {

   @Test
   public void testCreateConnection() throws SQLException {
      System.out.println("Start of test: PostgresqlConnectionFactory.createConnection()");
      Connection result = PostgresqlConnectionFactoryScylla.createConnection();
      System.out.println("End test: PostgresqlConnectionFactory.createConnection()\n");
   }

}
