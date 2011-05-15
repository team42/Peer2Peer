package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import model.Taxi;

public class TaxiDAO {

	private Connection con;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	public boolean updateTaxiPosition(String taxiID, String taxiCoord) {
		String query = "UPDATE taxi SET taxi_coordinate = ?, last_connected = Now() WHERE taxi_id = ?";

		int rowCount = 0;
		con = null;

		try {
			con = PostgresqlConnectionFactoryScylla.createConnection();

			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, taxiCoord);
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
			String cardsQuery = "INSERT INTO taxi (taxi_id, taxi_coordinate, last_connected)"
					+ "VALUES (?, ?, Now()) ";

			con = null;

			try {
				con = PostgresqlConnectionFactoryScylla.createConnection();
				preparedStatement = con.prepareStatement(cardsQuery);
				preparedStatement.setString(1, taxiID);
				preparedStatement.setString(2, taxiCoord);

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
			// We want to return false if INSERT was unsuccesfull, else return
			// true
			if (rowCount == 0) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public boolean setTaxiFree(String taxiID) {
		String query = "UPDATE taxi SET status = 0 WHERE taxi_id = ?";

		int rowCount = 0;
		con = null;

		try {
			con = PostgresqlConnectionFactoryScylla.createConnection();

			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, taxiID);

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
	
	public boolean setTaxiTaken(String taxiID) {
		String query = "UPDATE taxi SET status = 1 WHERE taxi_id = ?";

		int rowCount = 0;
		con = null;

		try {
			con = PostgresqlConnectionFactoryScylla.createConnection();

			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, taxiID);

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
	
	public ArrayList<Taxi> getActiveTaxis() {

		ArrayList<Taxi> taxiList = new ArrayList<Taxi>();

		String taxiID, taxiCoord;
		java.sql.Date lastConnected;
		int persistent;

		String Query = "SELECT * FROM taxi WHERE status = 0";

		con = null;

		try {
			con = PostgresqlConnectionFactoryScylla.createConnection();
			preparedStatement = con.prepareStatement(Query);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				taxiID = resultSet.getString("taxi_id");
				taxiCoord = resultSet.getString("taxi_coordinate");
				lastConnected = resultSet.getDate("last_connected");
				persistent = resultSet.getInt("persistent");

				java.util.Date lastCon = new java.util.Date(
						lastConnected.getTime());

				long difference = Math.abs(lastCon.getTime()
						- Calendar.getInstance().getTime().getTime());
				difference = difference / 1000;

				Math.abs(Calendar.getInstance().getTime().getTime()
						- lastConnected.getTime());

				if (persistent == 1 || difference < (5 * 60)) {
					taxiList.add(new Taxi(taxiID, taxiCoord));
				}
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

		return taxiList;
	}

}
