package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Taxi;

public class OngoingTripsDAO {

	private Connection con;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public boolean addAwaitingTaxis(String tripID, ArrayList<Taxi> taxiList,
			String company) {
		String Query = "INSERT INTO ongoing_trips (trip_id, taxi_id, taxi_coordinate, company)"
				+ "VALUES (?, ?, ?, ?) ";

		int rowCount = 0;
		con = null;

		try {
			con = PostgresqlConnectionFactory.createConnection();
			preparedStatement = con.prepareStatement(Query);

			for (int i = 0; i < taxiList.size(); i++) {
				preparedStatement.setString(1, tripID);
				preparedStatement.setString(2, taxiList.get(i).getTaxiID());
				preparedStatement.setString(3, taxiList.get(i).getTaxiCoord());
				preparedStatement.setString(4, company);

				rowCount = preparedStatement.executeUpdate();
			}

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

	public boolean deleteOngoingTrip(String tripID) {

		String Query = "DELETE FROM ongoing_trips WHERE trip_id = ?";

		int rowCount = 0;
		con = null;

		try {

			con = PostgresqlConnectionFactory.createConnection();
			preparedStatement = con.prepareStatement(Query);
			preparedStatement.setString(1, tripID);

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

	public ArrayList<String> getCompanyIP(String tripID) {

		String companyIP = "";
		ArrayList<String> companyIpList = new ArrayList<String>();

		String Query = "SELECT DISTINCT company FROM ongoing_trips WHERE trip_id = ?";

		con = null;

		try {
			con = PostgresqlConnectionFactory.createConnection();
			preparedStatement = con.prepareStatement(Query);
			preparedStatement.setString(1, tripID);

			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
            companyIP = resultSet.getString("company");
            companyIpList.add(companyIP);
         }

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

		int found = 0;
		return companyIpList;
	}
}
