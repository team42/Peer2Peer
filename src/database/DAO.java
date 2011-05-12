package database;

import java.sql.*;
import java.util.*;
import model.*;

/**
 * This class handles the SQL strings/commands for the MySQL database.
 * 
 * This code was originally written by hbe and has been modified to fit the goal
 * of this project.
 * 
 * @author Nicolai, Lasse
 */
public class DAO {
	private Connection con;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	// private PostgresqlConnectionFactory PostgresqlConnectionFactory;

	/**
	 * List all rows in table test.
	 * 
	 */
	public boolean listPeers() {
		con = null;
		resultSet = null;

		String query = "SELECT * FROM test";

		try {
			con = PostgresqlConnectionFactory.createConnection();
			preparedStatement = con.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			System.out.println("Listing peers...");
			while (resultSet.next()) {
				System.out.println(resultSet.getString("ip"));
			}
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param taxiID
	 * @param tripID
	 * @param tripCoord
	 * @return
	 */

	/**
	 * 
	 * @param tripID
	 * @param taxiID
	 * @return
	 */
/*	public boolean deleteTripForTaxi(String tripID, String taxiID) {

		String Query = "DELETE FROM ongoing_trips WHERE trip_id = ? AND taxi_id = ?";

		int rowCount = 0;
		con = null;

		try {

			con = PostgresqlConnectionFactory.createConnection();
			preparedStatement = con.prepareStatement(Query);
			preparedStatement.setString(1, tripID);
			preparedStatement.setString(2, taxiID);

			rowCount = preparedStatement.executeUpdate();

			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					System.out.println("Failed Closing of Database!");
				}
			}
		}
		// We want to return false if INSERT was unsuccesfull, else return true
		if (rowCount == 0) {
			return false;
		} else {
			return true;
		}
	}
*/
}
