package database;

import java.sql.*;

import config.Configuration;

public class TripOffersDAO {

	private Connection con;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
	Configuration config = Configuration.getConfiguration();
	
	public String[] getCustomer() {
		
		String customer[] = new String[2];
		customer[0] = "none";
		int id = 0;
		
		String query = "SELECT * FROM trip_offers ORDER BY time_ordered";
		
		con = null;

		try {

			con = PostgresqlConnectionFactory.createConnection();
			preparedStatement = con.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				id = resultSet.getInt("id");
				customer[0] = Integer.toString(id);
				
				customer[1] = resultSet.getString("destination");
				
				while(customer[0].length() < 8) {
					customer[0] = "0" + customer[0];
				}
				
				customer[0] = config.getCompanyID() + customer[0];
				
			}
			
			preparedStatement.close();
			
			deleteCustomer(id);
			
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
		
		return customer;
	}
	
	public boolean deleteCustomer(int id) {

		String query = "DELETE FROM trip_offers WHERE id = ?";

		int rowCount = 0;
		con = null;

		try {

			con = PostgresqlConnectionFactory.createConnection();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);

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
}
