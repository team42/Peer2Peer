package map;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 
 * @author Kenni, Anders
 * 
 * The Algorithm class is responsible for calculating the shortest path
 * between two points, for calculating the length of this path and for
 * finding the closest intersection to a given point. 
 * To do this the class has different functions to add and remove items 
 * from arraylists as well as some functions to calculate distance and
 * G, F and H values.
 */

public class Algorithm 
{
	Connection conn = null;  
	ResultSet res = null;  
	Statement statement = null;  
	DatabaseMetaData meta = null;

	ArrayList<Coordinates> stations = new ArrayList<Coordinates>();
	ArrayList<Integer> openList = new ArrayList<Integer>();
	ArrayList<Integer> closedList = new ArrayList<Integer>();
	ArrayList<Integer> taxiList = new ArrayList<Integer>();
	int id, x, y, NON, N1, N2, N3, N4, N5;

	public ArrayList<Coordinates> Algorithm()
	{
		try 
		{  
			Class.forName("org.postgresql.Driver");  

		}
		catch (ClassNotFoundException e)
		{
			System.out.println("Couldn't find the PostgreSQL driver!");
			System.out.println("Error: ");
			e.printStackTrace();
		}
		try
		{
			conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/taxiPath", "postgres", "crosser");
		}
		catch (SQLException e) 
		{          
			System.out.println("Connection Failed!");
			System.out.println("Error: ");
			e.printStackTrace();
		}

		try{
			Statement stat = conn.createStatement();

			meta = conn.getMetaData();
			res = meta.getTables(null, null, "coordinates", null);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		try {  
			Statement stat = conn.createStatement();

			ResultSet rs = stat.executeQuery("SELECT * FROM \"coordinates\" ORDER BY \"id\" ASC");         

			while(rs.next()) // process results one row at a time
			{
				id = rs.getInt(1);
				x = rs.getInt(2);
				y = rs.getInt(3);
				NON = rs.getInt(4);
				N1 = rs.getInt(5);
				N2 = rs.getInt(6);
				if (rs.wasNull()){N2 = 9999;}
				N3 = rs.getInt(7);
				if (rs.wasNull()){N3 = 9999;}
				N4 = rs.getInt(8);
				if (rs.wasNull()){N4 = 9999;}                   
				N5 = rs.getInt(9);
				if (rs.wasNull()){N5 = 9999;}

				stations.add(new Coordinates(id, x, y, NON, N1, N2, N3, N4, N5));
			}
			stat.close();
			conn.close();
		}
		catch (SQLException e) {
			System.out.println("SQL Error!");
			System.out.println("Error: ");
			e.printStackTrace();
		} 

		catch (Exception g)
		{
			System.out.println("Something went wrong.");
			System.out.println("Error: ");
			g.printStackTrace();
		}
		return stations; 
	}
	
	/**
	 * Adds a point to the open list
	 * 
	 * @param c The ID of the point to be added to the open list
	 */
	public void AddToOpenList(int c)
	{
		openList.add(c);
	}
	
	/**
	 * Removes a point from the open list
	 * 
	 * @param c The index of the point to be removed from the open list
	 */
	public void RemoveFromOpenlist(int c)
	{
		openList.remove(c);
	}
	
	/**
	 * Adds a point to the closed list
	 * 
	 * @param c The ID of the point to be added to the closed list
	 */
	public void AddToClosedList(int c)

	{
		closedList.add(c);
	}
	/**
	 * Adds a point to the taxi list
	 * 
	 * @param c The ID of the point to be added to the taxi list
	 */
	public void AddToTaxiList(int c)
	{
		taxiList.add(c);
	}
	
	/**
	 * Calculates the distance between two point as the crow flies
	 * 
	 * @param a The point to measure from
	 * @param b The point to measure to
	 * @return  The distance between the points as a double
	 */
	public double CalcDist(int a, int b)
	{
		int latA = stations.get(a).ownX;
		int lonA = stations.get(a).ownY;

		int latB = stations.get(b).ownX;
		int lonB = stations.get(b).ownY;

		return Math.sqrt(((latA-latB)*(latA-latB))+((lonA-lonB)*(lonA-lonB)));
	}
	
	/**
	 * Calculates the G value of a point
	 * 
	 * @param a The ID of the point whose G value is to be calculated
	 * @return  The calculated G value
	 */
	public double CalcG(int a)
	{
		int parentCell = stations.get(a).parentID;
		double g = stations.get(parentCell).G;

		g = g + CalcDist(a,parentCell);

		return g;
	}
	
	/**
	 * Calculates the H value of a point
	 * 
	 * @param a    The ID of the point whose H value is to be calculated
	 * @param goal The ID of the end point of the route
	 * @return     The calculated H value
	 */
	public double CalcH(int a, int goal)
	{
		double h = CalcDist(a,goal);

		return h;
	}
	
	/**
	 * Calculates the F value of a point
	 * 
	 * @param a The ID of the point whose F value is to be calculated
	 * @return  The calculated F value
	 */
	public double CalcF(int a)
	{
		double g = stations.get(a).G;
		double h = stations.get(a).H;

		double f = g + h;

		return f;
	}
	
	/**
	 * Calculates a temporary G value to be compared with the current G value
	 * 
	 * @param a The ID of the point whose temporary G value is to be calculated
	 * @return  The calculated temporary G value
	 */
	public double CalcTempG(int a)
	{
		int parentCell = stations.get(a).parentID;
		double g = stations.get(parentCell).getG();

		g = g + CalcDist(a,parentCell);

		return g;
	}
	
	/**
	 * Calculates the shortest path between two points
	 * 
	 * The Route method uses the A* algorithm to calculate the shortest path
	 * between two points. The IDs of all the points past on the route
	 * (including the start and end point) is added to an ArrayList  
	 * 
	 * @param ST  The ID of the start point
	 * @param END The ID of the end point
	 */
	public void Route(int ST, int END)
	{

		// Switch Start and goal to backtrack route
		int start = END;
		int goal = ST;
		ArrayList<Integer> routeList = new ArrayList<Integer>();

		AddToOpenList(start);                     		// add start point to open list
		stations.get(start).setG(0);              		// save G
		stations.get(start).setH(CalcH(start,goal));    // calculate and save H
		stations.get(start).setF(CalcF(start));     	// calculate and save F
		int currentID = start;
		while(!closedList.contains(goal) && !openList.isEmpty()) // (Goal is not on closed list and open list is not empty)
		{
			// select from open list where f is lowest;
			int currentLowestF = 2147483647;
			int currentLowestFID = 9999;

			for(int i=0; i < stations.size(); i++)
			{
				if(stations.get(i).getF() < currentLowestF && openList.contains(stations.get(i).ID))
				{
					currentLowestF = (int) stations.get(i).getF();
					currentLowestFID = (int) stations.get(i).ID;
				}

			}        

			currentID = currentLowestFID;
			int i = stations.get(currentID).NON; // Number of Neighbors
			int n = 1; // Neighbor number

			while(i > 0)
			{
				int currentNeighbor = stations.get(currentID).getNn(n);

				if(closedList.contains(currentNeighbor)) // (neighbor is on closed list)
				{
					// Look at next neighbor
					i--;
					n++;
				}
				else if(!openList.contains(currentNeighbor)) // (neighbor is not on open list)
				{
					AddToOpenList(currentNeighbor); // add neighbor to open list
					stations.get(currentNeighbor).setParentID(currentID);             // set parentID
					stations.get(currentNeighbor).setG(CalcG(currentNeighbor));       // calculate and save G
					stations.get(currentNeighbor).setH(CalcH(currentNeighbor,goal));  // calculate and save H
					stations.get(currentNeighbor).setF(CalcF(currentNeighbor));       // calculate and save F
					i--;
					n++;
				}
				else
				{
					// Set tempG
					stations.get(currentNeighbor).setTempG(CalcTempG(currentNeighbor));
					double tempG = stations.get(currentNeighbor).getTempG();

					if(tempG < stations.get(currentNeighbor).getG()) // (g is lower than before)
					{
						stations.get(currentNeighbor).setParentID(currentID);          // change parent
						stations.get(currentNeighbor).setG(CalcG(currentNeighbor));    // calculate and save G
						stations.get(currentNeighbor).setF(CalcF(currentNeighbor));    // calculate and save F
						i--;
						n++;
					}
					else // (g is NOT lower than before)
					{
						// look at next neighbor
						i--;
						n++;
					}
				}
			} // end while(i > 0) loop

			AddToClosedList(currentID);
			RemoveFromOpenlist(openList.indexOf(currentID));

		} // end while (!closedList.contains(goal) && !openList.isEmpty()) loop

		if(closedList.contains(goal))
		{
			// BackTrack and show route
			int from = goal;
			int to = stations.get(goal).getParentID();
			routeList.add(goal);
			while(to != start)
			{
				from = stations.get(from).getParentID();
				to = stations.get(from).getParentID();
				routeList.add(from);
			}

			routeList.add(start);
		}
		else
		{
			System.out.println("No route was found!");
		}
	} // End method Route
	
	/**
	 * Calculates the length of a route
	 * 
	 * The method first finds the shortest path between the two points and
	 * then returns the total length of this path.
	 * 
	 * @param X1 First point's x coordinate
	 * @param Y1 First point's y coordinate
	 * @param X2 Second point's x coordinate
	 * @param Y2 Second point's y coordinate
	 * @return   The total length of the route as a double
	 */
	public double RouteLength(int X1, int Y1, int X2, int Y2)
	{
		int start = findClosestPoint(X2,Y2,"StringNotUsedHere");
		int goal  = findClosestPoint(X1,Y1,"StringNotUsedHere");

		AddToOpenList(start);                     		// add start point to open list
		stations.get(start).setG(0);             		// save G
		stations.get(start).setH(CalcH(start,goal));    // calculate and save H
		stations.get(start).setF(CalcF(start));      	// calculate and save F
		int currentID = start;
		
		while(!closedList.contains(goal) && !openList.isEmpty()) // (Goal is not on closed list and open list is not empty)
		{
			// select from open list where f is lowest;
			int currentLowestF = 2147483647;
			int currentLowestFID = 9999;

			for(int i=0; i < stations.size(); i++)
			{
				if(stations.get(i).getF() < currentLowestF && openList.contains(stations.get(i).ID))
				{
					currentLowestF = (int) stations.get(i).getF();
					currentLowestFID = (int) stations.get(i).ID;
				}
			}        

			currentID = currentLowestFID;
			int i = stations.get(currentID).NON; // Number of Neighbors
			int n = 1; // Neighbor number

			while(i > 0)
			{
				int currentNeighbor = stations.get(currentID).getNn(n);

				if(closedList.contains(currentNeighbor)) // (neighbor is on closed list)
				{
					// Look at next neighbor
					i--;
					n++;
				}
				else if(!openList.contains(currentNeighbor)) // (neighbor is not on open list)
				{
					AddToOpenList(currentNeighbor); // add neighbor to open list
					stations.get(currentNeighbor).setParentID(currentID);             // set parentID
					stations.get(currentNeighbor).setG(CalcG(currentNeighbor));       // calculate and save G
					stations.get(currentNeighbor).setH(CalcH(currentNeighbor,goal));  // calculate and save H
					stations.get(currentNeighbor).setF(CalcF(currentNeighbor));       // calculate and save F
					i--;
					n++;
				}
				else
				{
					// Set tempG
					stations.get(currentNeighbor).setTempG(CalcTempG(currentNeighbor));
					double tempG = stations.get(currentNeighbor).getTempG();

					if(tempG < stations.get(currentNeighbor).getG()) // (g is lower than before)
					{
						stations.get(currentNeighbor).setParentID(currentID);          // change parent
						stations.get(currentNeighbor).setG(CalcG(currentNeighbor));    // calculate and save G
						stations.get(currentNeighbor).setF(CalcF(currentNeighbor));    // calculate and save F
						i--;
						n++;
					}
					else // (g is NOT lower than before)
					{
						// look at next neighbor
						i--;
						n++;
					}
				}
			} // end while(i > 0) loop

			AddToClosedList(currentID);
			RemoveFromOpenlist(openList.indexOf(currentID));

		} // end while (!closedList.contains(goal) && !openList.isEmpty()) loop

		if(closedList.contains(goal))
		{
			// Return length of route
			return stations.get(goal).getF();
		}
		else
		{
			System.out.println("No route was found!");
			return 0;
		}
	} // End method RouteLength

	//	public void closestTaxis(int NoTaxis, int ClosestTo)
	//	{ 
	//		int NoT = NoTaxis;
	//		int Point = ClosestTo;
	//		int counter = 0;
	//
	//		AddToOpenList(Point); 			// add start point to open list
	//		stations.get(Point).setG(0); 	// set G
	//		stations.get(Point).setF(0);	// set F
	//		int currentID = Point;
	//
	//		while(counter < NoT)
	//		{
	//			// select from open list where g is lowest;
	//			int currentLowestF = 2147483647;
	//			int currentLowestFID = 9999;
	//
	//			for(int i=0; i < stations.size(); i++)
	//			{
	//				if(stations.get(i).getF() < currentLowestF && openList.contains(stations.get(i).ID))
	//				{
	//					currentLowestF = (int) stations.get(i).getF();
	//					currentLowestFID = (int) stations.get(i).ID;
	//				}
	//			}			
	//
	//			currentID = currentLowestFID;
	//			int i = stations.get(currentID).NON; // Number of Neighbors
	//			int n = 1;
	//
	//			while(i > 0)
	//			{
	//				int currentNeighbor = stations.get(currentID).getNn(n);
	//				
	//				if(closedList.contains(currentNeighbor)) // (neighbor is on closed list)
	//				{
	//					// Look at next neighbor
	//					i--;
	//					n++;
	//				}
	//				else if(!openList.contains(currentNeighbor)) // (neighbor is not on open list)
	//				{
	//					AddToOpenList(currentNeighbor); // add neighbor to open list
	//					stations.get(currentNeighbor).setParentID(currentID); 						// set parentID
	//					stations.get(currentNeighbor).setG(CalcG(currentNeighbor)); 				// calculate and save G
	//					stations.get(currentNeighbor).setF(stations.get(currentNeighbor).getG());	// save F
	//					i--;
	//					n++;
	//				}
	//				else
	//				{
	//					// Set tempG
	//					stations.get(currentNeighbor).setTempG(CalcTempG(currentNeighbor));
	//					double tempG = stations.get(currentNeighbor).getTempG();
	//					
	//					if(tempG < stations.get(currentNeighbor).getG()) // (g is lower than before)
	//					{
	//						stations.get(currentNeighbor).setParentID(currentID); 						// change parent
	//						stations.get(currentNeighbor).setG(CalcG(currentNeighbor)); 				// calculate and save G
	//						stations.get(currentNeighbor).setF(stations.get(currentNeighbor).getG());	// calculate and save F
	//						i--;
	//						n++;
	//					}
	//					else // (g is NOT lower than before)
	//					{
	//						// look at next neighbor
	//						i--;
	//						n++;
	//					}
	//				}
	//
	//			} // end while(i > 0) loop
	//
	//			if(stations.get(currentID).getTaxi())
	//			{
	//				AddToTaxiList(currentID);
	//				counter = counter + stations.get(currentID).getTaxiIDs().size();
	//			} // End if
	//
	//			AddToClosedList(currentID);
	//			RemoveFromOpenlist(openList.indexOf(currentID));
	//			
	//		} // End while (counter < NoT)
	//
	//		System.out.println("\n\nThe closest " + NoT + " taxis to node " + Point + " is:");
	//		for(int k = 0; k < taxiList.size(); k++)
	//		{
	//			int tempNodeID = taxiList.get(k);
	//
	//			for(int z = 0; z < stations.get(tempNodeID).taxiIDs.size(); z++)
	//			{
	//				System.out.printf("\nTaxi with ID: %03d found at node " + tempNodeID, stations.get(tempNodeID).taxiIDs.get(z));
	//			}
	//		}
	//	} // End function closestTaxis
	
	/**
	 * Find the closest point to a given coordinate
	 * 
	 * The method finds the closest intersection to a given coordinate. 
	 * It marks that there is a taxi at this point, adds the taxi ID 
	 * to the taxi IDs list and returns the ID of the point found.
	 * 
	 * @param xvalue The x value of the given coordinate 
	 * @param yvalue The y value of the given coordinate
	 * @param taxiID The ID of the taxi which is to be located at the found closest point
	 * @return       The ID of the closest point found
	 */
	public int findClosestPoint(int xvalue, int yvalue, String taxiID)
	{
		int thisX = xvalue;
		int thisY = yvalue;
		int nodeID = 9999;
		double close = 9999.9;
		double tempValue;

		for(int i=0; i < stations.size(); i++)
		{
			tempValue = Math.sqrt(((thisX-stations.get(i).ownX)*(thisX-stations.get(i).ownX))+((thisY-stations.get(i).ownY)*(thisY-stations.get(i).ownY)));

			if(tempValue < close)
			{
				close = tempValue;
				nodeID = stations.get(i).ID;
			} // End if

		} // End for loop

		stations.get(nodeID).setTaxi(true);
		stations.get(nodeID).AddTaxiIDs(taxiID);
		return nodeID;

	} // End function findClosestPoint

}