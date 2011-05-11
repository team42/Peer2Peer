package database;

import java.sql.*;
import java.util.*;
import model.*;

/**
 * This class handles the SQL strings/commands for the MySQL database.
 *
 * This code was originally written by hbe and has been modified to fit the 
 * goal of this project.
 *
 * @author Nicolai, Lasse
 */
public class DAO {
   private Connection con;
   private PreparedStatement preparedStatement;
   private ResultSet resultSet;
   //private PostgresqlConnectionFactory PostgresqlConnectionFactory;

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
         while(resultSet.next()) {
            System.out.println(resultSet.getString("ip"));
         }
         return true;

      } catch(SQLException e) {
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
   public boolean insertTrip(String taxiID, String tripID, String tripCoord) {
      String cardsQuery = "INSERT INTO trips (taxi_id, trip_id, destination, started)"
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

   /**
    * 
    * @param taxiID
    * @param tripID
    * @return
    */
   public boolean confirmTrip(String taxiID, String tripID) {

      String cardsQuery1 = "UPDATE trips SET accepted = 1 WHERE taxi_id = ? AND trip_id = ?";

      String cardsQuery2 = "DELETE FROM trips WHERE taxi_id <> ? AND trip_id = ?";

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

   /**
    * 
    * @param tripID
    * @return
    */
   public boolean deleteTrip(String tripID) {

      String Query = "DELETE FROM trips WHERE trip_id = ?";

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

   /**
    * 
    * @param taxiString
    * @return
    */
   public boolean addAwaitingTaxis(String tripID, ArrayList<Taxi> taxiList, String company) {
      String Query = "INSERT INTO ongoing_trips (trip_id, taxi_id, taxi_coordinate, company)"
         + "VALUES (?, ?, ?, ?) ";

      int rowCount = 0;
      con = null;

      try {
         con = PostgresqlConnectionFactory.createConnection();
         preparedStatement = con.prepareStatement(Query);

         for(int i=0; i < taxiList.size(); i++) {
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
            try { con.close(); }
            catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
         }
      }
      // We want to return false if INSERT was unsuccesfull, else return true
      if (rowCount == 0) { return false; }
      else { return true; }
   }

   /**
    * 
    * @param tripID
    * @return
    */
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
            try { con.close(); }
            catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
         }
      }
      // We want to return false if INSERT was unsuccesfull, else return true
      if (rowCount == 0) { return false; }
      else { return true; }
   }

   /**
    * 
    * @param tripID
    * @param taxiID
    * @return
    */
   public boolean deleteTripForTaxi(String tripID, String taxiID) {

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
            try { con.close(); }
            catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
         }
      }
      // We want to return false if INSERT was unsuccesfull, else return true
      if (rowCount == 0) { return false; }
      else { return true; }
   }
   
   public ArrayList<Taxi> getActiveTaxis() {
	   
	   ArrayList<Taxi> taxiList = new ArrayList<Taxi>();
	   
	   String taxiID, taxiCoord;
	   java.sql.Date lastConnected;
	   int persistent;
	   
	   String Query = "SELECT * FROM taxi";

	   con = null;

	   try {
	      con = PostgresqlConnectionFactory.createConnection();
	      preparedStatement = con.prepareStatement(Query);
	         
	      resultSet = preparedStatement.executeQuery();

	      preparedStatement.close();
	         
       } catch (SQLException e) {
	      e.printStackTrace();
	   } finally {
	      if (con != null) {
	         try { con.close(); }
	         catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
	      }
	   }

	   try {
		   while(resultSet.next()) {
			   taxiID = resultSet.getString("taxi_id");
			   taxiCoord = resultSet.getString("taxi_coord");
			   lastConnected = resultSet.getDate("last_connected");
			   persistent = resultSet.getInt("persistent");
			   
			   java.util.Date lastCon = new java.util.Date(lastConnected.getTime());
			   
			   long difference = Math.abs(lastCon.getTime()-Calendar.getInstance().getTime().getTime());
			   difference = difference / 1000;
			   
			   Math.abs(Calendar.getInstance().getTime().getTime() - lastConnected.getTime());
			   
			   if(persistent == 1 || difference < (5*60)) {
				   taxiList.add(new Taxi(taxiID, taxiCoord));
			   }
		   }
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
	   
	   return taxiList;
   }

   public ArrayList<Trip> getTripsByTaxiID(String taxiID) {
	   ArrayList<Trip> tripList = new ArrayList<Trip>();
	   
	   String tripID, destination;
	   int accepted;
	   java.sql.Date started;
	   
	   String Query = "SELECT * FROM trips WHERE taxi_id = ? ORDER BY accepted DESC";

	   con = null;

	   try {
	      con = PostgresqlConnectionFactory.createConnection();
	      preparedStatement = con.prepareStatement(Query);
	      
	      preparedStatement.setString(1, taxiID);
	      resultSet = preparedStatement.executeQuery();

	      preparedStatement.close();
	         
       } catch (SQLException e) {
	      e.printStackTrace();
	   } finally {
	      if (con != null) {
	         try { con.close(); }
	         catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
	      }
	   }

	   try {
		   while(resultSet.next()) {
			   tripID = resultSet.getString("trip_id");
			   destination = resultSet.getString("destination");
			   accepted = resultSet.getInt("accepted");
			   started = resultSet.getDate("started");
			   
			   java.util.Date date = new java.util.Date(started.getTime());
			   
			   tripList.add(new Trip(tripID, accepted, destination, date));
		   }
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
	   
	   return tripList;
   }

   public String getReturnIP(String tripID) {
	   
	   String returnIP = "";
	   
	   String Query = "SELECT return_ip FROM trips WHERE trip_id = ?";

	   con = null;

	   try {
	      con = PostgresqlConnectionFactory.createConnection();
	      preparedStatement = con.prepareStatement(Query);
	      preparedStatement.setString(1, tripID);   
	      
	      resultSet = preparedStatement.executeQuery();

	      preparedStatement.close();
	         
       } catch (SQLException e) {
	      e.printStackTrace();
	   } finally {
	      if (con != null) {
	         try { con.close(); }
	         catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
	      }
	   }

	   try {
		   resultSet.next();
		   returnIP = resultSet.getString("return_ip");
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
	   
	   return returnIP;
   }
   
   public boolean taxiAcceptTrip(String taxiID, String tripID) {
	   String query = "UPDATE trips SET accepted = 1 WHERE taxi_id = ? AND trip_id = ?";
	   
	   int rowCount = 0;
	      con = null;

	      try {
	    	 con = PostgresqlConnectionFactory.createConnection();
	         
	    	 preparedStatement = con.prepareStatement(query);
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

   public boolean taxiDeleteTrip(String taxiID, String tripID) {
	   String query = "DELETE FROM trips WHERE taxi_id = ? AND trip_id = ?";
	   
	   int rowCount = 0;
	      con = null;

	      try {
	    	 con = PostgresqlConnectionFactory.createConnection();
	         
	    	 preparedStatement = con.prepareStatement(query);
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
   
   public boolean updateTaxiPosition(String taxiID, String taxiCoord) {
	   String query = "UPDATE taxi SET taxi_coordinate = ?, last_connected = Now() WHERE taxi_id = ?";
	   
	   int rowCount = 0;
	      con = null;

	      try {
	    	 con = PostgresqlConnectionFactory.createConnection();
	         
	    	 preparedStatement = con.prepareStatement(query);
	         preparedStatement.setString(1, taxiCoord);
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
	      if (rowCount == 0) {
	    	  String cardsQuery = "INSERT INTO taxi (taxi_id, taxi_coord, last_connected)"
	    	         + "VALUES (?, ?, Now()) ";

	    	      con = null;

	    	      try {
	    	         con = PostgresqlConnectionFactory.createConnection();
	    	         preparedStatement = con.prepareStatement(cardsQuery);
	    	         preparedStatement.setString(1, taxiID);
	    	         preparedStatement.setString(2, taxiCoord);

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
	      else { return true; }
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

	      preparedStatement.close();
	         
       } catch (SQLException e) {
	      e.printStackTrace();
	   } finally {
	      if (con != null) {
	         try { con.close(); }
	         catch (SQLException e1) { System.out.println("Failed Closing of Database!"); }
	      }
	   }

	   int found = 0;
	   
	   try{
		   while(resultSet.next()) {
			   companyIP = resultSet.getString("company");
			   companyIpList.add(companyIP);
		   }
	   } catch (SQLException e) {
		   e.printStackTrace();
	   }
	   
	   return companyIpList;
   }

}

