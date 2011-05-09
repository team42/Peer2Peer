package database;

import java.sql.*;

public class DAO {
   private Connection con;
   private PreparedStatement preparedStatement;
   private ResultSet resultSet;
   //private PostgresqlConnectionFactory PostgresqlConnectionFactory;
   
   public void listPeers() {
      con = null;
      resultSet = null;
      
      String query = "SELECT * FROM test";
      
      try {
         con = PostgresqlConnectionFactory.createConnection();
         preparedStatement = con.prepareStatement(query);
         resultSet = preparedStatement.executeQuery();
         System.out.println("Listing peers...");
         while(resultSet.next()) {
            System.out.println(resultSet.getString("ip"));
         }
         
      } catch(SQLException e) {
         e.printStackTrace();
      }
   }
    
   public boolean insertTrip(String taxiID, String tripID, String tripCoord) {
      String cardsQuery = "INSERT INTO trips (`Taxi ID`, `Trip ID`, `Destination`, `Started`)"
                + "VALUES (?, ?, ?, Now()) ";

        int rowCount = 0;
        con = null;

        try {
            con = PostgresqlConnectionFactory.createConnection();
            preparedStatement = con.prepareStatement(cardsQuery);
            preparedStatement.setString(1, taxiID);
            preparedStatement.setString(2, tripID);
            preparedStatement.setString(3, tripCoord);

            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try { con.close(); }
                catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
            }
        }
        // We want to return false if INSERT was unsuccesfull, else return true
        if (rowCount == 0) { return false; }
        else { return true; }
    }
    
    public boolean confirmTrip(String taxiID, String tripID) {
    	
        String cardsQuery1 = "UPDATE trips SET `Accepted` = 1 WHERE `Taxi ID` = ? AND `Trip ID` = ?";
        
        String cardsQuery2 = "DELETE FROM trips WHERE `Taxi ID` <> ? AND `Trip ID` = ?";
        
        int rowCount = 0;
        con = null;
        
        try {
        	
        	con = PostgresqlConnectionFactory.createConnection();
            preparedStatement = con.prepareStatement(cardsQuery1);
            preparedStatement.setString(1, taxiID);
            preparedStatement.setString(2, tripID);
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
            
            preparedStatement = con.prepareStatement(cardsQuery2);
            preparedStatement.setString(1, taxiID);
            preparedStatement.setString(2, tripID);
            rowCount = preparedStatement.executeUpdate();
            preparedStatement.close();
        	
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) {
	            try { con.close(); }
	            catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
	        }
	    }
	    // We want to return false if INSERT was unsuccesfull, else return true
	    if (rowCount == 0) { return false; }
	    else { return true; }
	}
    
    public boolean deleteTrip(String tripID) {
    	
    	String Query = "DELETE FROM Trips WHERE `Trip ID` = ?";
        
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
	            try { con.close(); }
	            catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
	        }
	    }
	    // We want to return false if INSERT was unsuccesfull, else return true
	    if (rowCount == 0) { return false; }
	    else { return true; }
	}

    public boolean addAwaitingTaxis(String taxiString) {
    	String Query = "INSERT INTO OngoinTrips (`Trip ID`, `Taxi ID`, `Taxi Coordinate`, `Company`)"
            + "VALUES (?, ?, ?, ?) ";

    	String[] taxis = taxiString.split("%");
    	
    	int rowCount = 0;
    	con = null;

	    try {
	        con = PostgresqlConnectionFactory.createConnection();
	        preparedStatement = con.prepareStatement(Query);
	        
	        for(int i=0; i>taxis.length; i++) {
	        	preparedStatement.setString(1, taxis[i].substring(0, 3));
		        preparedStatement.setString(2, taxis[i].substring(3, 6));
		        preparedStatement.setString(3, taxis[i].substring(6, 15));
		        preparedStatement.setString(4, taxis[i].substring(15));
		        
		        rowCount = preparedStatement.executeUpdate();
	        }
	        
	        preparedStatement.close();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (con != null) {
	            try { con.close(); }
	            catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
	        }
	    }
	    // We want to return false if INSERT was unsuccesfull, else return true
	    if (rowCount == 0) { return false; }
	    else { return true; }
	}
    
    public boolean deleteOngoingTrip(String tripID) {
    	
    	String Query = "DELETE FROM OngoingTrips WHERE `Trip ID` = ?";
        
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
	            try { con.close(); }
	            catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
	        }
	    }
	    // We want to return false if INSERT was unsuccesfull, else return true
	    if (rowCount == 0) { return false; }
	    else { return true; }
	}

    public boolean deleteTripForTaxi(String tripID, String taxiID) {
    	
    	String Query = "DELETE FROM OngoingTrips WHERE `Trip ID` = ? AND `Taxi ID` = ?";
        
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
	            try { con.close(); }
	            catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
	        }
	    }
	    // We want to return false if INSERT was unsuccesfull, else return true
	    if (rowCount == 0) { return false; }
	    else { return true; }
	}
}
