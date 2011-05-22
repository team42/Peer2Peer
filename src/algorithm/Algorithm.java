package algorithm;

import java.util.ArrayList;
import config.Configuration;
import model.*;

/**
 * 
 * This class is responsible for all use of the shortest path algorithm
 * 
 * @author Kenni, Anders, Nicolai
 *
 */
public class Algorithm {

	// Variable
	Configuration config = Configuration.getConfiguration();
	
	ArrayList<Intersection> mapList = config.getMap();

	ArrayList<Integer> openList = new ArrayList<Integer>();
	ArrayList<Integer> closedList = new ArrayList<Integer>();

	/**
	 * Adds an intersection ID to the open list
	 * 
	 * @param c
	 */
	private void AddToOpenList(int c) {
		openList.add(c);
	}

	/**
	 * 
	 * Removes an intersection ID from the open list
	 * 
	 * @param c
	 */
	private void RemoveFromOpenlist(int c) {
		openList.remove(c);
	}

	/**
	 * 
	 * Add an intersection ID to the closed list
	 * 
	 * @param c
	 */
	private void AddToClosedList(int c) {
		closedList.add(c);
	}

	/**
	 * 
	 * Calculate the heuristics between intersection a and b.
	 * Uses pythagoras for calculating the heuristics.
	 * 
	 * @param a - intersection ID a
	 * @param b - intersection ID b
	 * @return Length between the two points
	 */
	private double CalcDist(int a, int b) {
		int xLength = Math.abs(mapList.get(a).getXCoord() - mapList.get(b).getXCoord());
		int yLength = Math.abs(mapList.get(a).getYCoord() - mapList.get(b).getYCoord());
		
		return Math.sqrt(Math.pow(xLength, 2)+Math.pow(yLength, 2));
	}

	/**
	 * 
	 * Calculate G when calculation the shortest path
	 * 
	 * @param a
	 * @return
	 */
	private double CalcG(int a) {
		int parentCell = mapList.get(a).getParentID();
		double g = mapList.get(parentCell).getG();

		g += CalcDist(a, parentCell);

		return g;
	}

	/**
	 * 
	 * Calculate H when calculation the shortest path
	 * 
	 * @param a
	 * @return
	 */
	private double CalcH(int a, int goal) {
		double h = CalcDist(a, goal);

		return h;
	}

	/**
	 * 
	 * Calculate F when calculation the shortest path
	 * 
	 * @param a
	 * @return
	 */
	private double CalcF(int a) {
		double g = mapList.get(a).getG();
		double h = mapList.get(a).getH();

		double f = g + h;

		return f;
	}

	/**
	 * 
	 * Return the temporary g
	 * 
	 * @param a
	 * @return
	 */
	private double CalcTempG(int a) {
		int parentCell = mapList.get(a).getParentID();
		double g = mapList.get(parentCell).getG();

		g = g + CalcDist(a, parentCell);

		return g;
	}

	/**
	 * Calculate the route between two coordinates.
	 * 
	 * Parameters is in coordinate format "xxxx,yyyy"
	 * These parameters are converted to the closest intersection
	 * and the route is return in an arraylist of intersection IDs
	 * 
	 * @param begin - start coordinate
	 * @param end - end coordinate
	 * @return ArrayList of intersection ID, which represent the route
	 */
	public ArrayList<Integer> Route(String begin, String end) {

		openList.clear();
		closedList.clear();
		
		// Switch Start and goal to backtrack route
		int start = findClosestPoint(end);
		int goal = findClosestPoint(begin);
		ArrayList<Integer> routeList = new ArrayList<Integer>();

		AddToOpenList(start); // add start point to open list
		
		mapList.get(start).setG(0); // save G
		mapList.get(start).setH(CalcH(start, goal)); // calculate and save H
		mapList.get(start).setF(CalcF(start)); // calculate and save F
		
		int currentID = start;
		
		// Goal is not on closed list and open list is not empty
		while (!closedList.contains(goal) && !openList.isEmpty()) {

			// select from open list where f is lowest;
			int currentLowestF = 2147483647;
			int currentLowestFID = 9999;

			for (int i = 0; i < mapList.size(); i++) {
				if (mapList.get(i).getF() < currentLowestF && openList.contains(mapList.get(i).getID())) {
					currentLowestF = (int) mapList.get(i).getF();
					currentLowestFID = (int) mapList.get(i).getID();
				}
			}

			currentID = currentLowestFID;
			int i = mapList.get(currentID).getLinks(); // Number of Neighbors
			int n = 1; // Neighbor number

			while (i > 0) {
				int currentNeighbor = mapList.get(currentID).getNn(n);
				
				// (neighbor is on closed list)
				if (closedList.contains(currentNeighbor)) {
					// Look at next neighbor
					i--;
					n++;
				// (neighbor is not on open list)
				} else if (!openList.contains(currentNeighbor)) {
					// add neighbor to open list
					AddToOpenList(currentNeighbor);
					// set parentID
					mapList.get(currentNeighbor).setParentID(currentID); // set parentID
					// calculate and save G
					mapList.get(currentNeighbor).setG(CalcG(currentNeighbor));
					// calculate and save H
					mapList.get(currentNeighbor).setH(CalcH(currentNeighbor, goal));
					// calculate and save F
					mapList.get(currentNeighbor).setF(CalcF(currentNeighbor));
					i--;
					n++;
				} else {
					// Set tempG
					mapList.get(currentNeighbor).setTempG(CalcTempG(currentNeighbor));
					double tempG = mapList.get(currentNeighbor).getTempG();
					// (g is lower than before)
					if (tempG < mapList.get(currentNeighbor).getG()) {
						// change parent
						mapList.get(currentNeighbor).setParentID(currentID);
						// calculate and save G
						mapList.get(currentNeighbor).setG(CalcG(currentNeighbor));
						// calculate and save F
						mapList.get(currentNeighbor).setF(CalcF(currentNeighbor));
						i--;
						n++;
					// (g is NOT lower than before)
					} else {
						// look at next neighbor
						i--;
						n++;
					}
				}
			} // end while(i > 0) loop

			AddToClosedList(currentID);
			RemoveFromOpenlist(openList.indexOf(currentID));

		} // end while (!closedList.contains(goal) && !openList.isEmpty()) loop

		if (closedList.contains(goal)) {
			// BackTrack and show route
			int from = goal;
			int to = mapList.get(goal).getParentID();
			routeList.add(goal);
			while (to != start) {
				from = mapList.get(from).getParentID();
				to = mapList.get(from).getParentID();
				routeList.add(from);
			}

			routeList.add(start);
		} else {
			System.out.println("No route was found!");
		}
		return routeList;
	} // End method Route

	/**
	 * Calculate the shortest route length between two coordinates.
	 * 
	 * Parameters is in coordinate format "xxxx,yyyy"
	 * These parameters are converted to the closest intersection
	 * and the route length is returned as an integer.
	 * 
	 * @param begin - start coordinate
	 * @param end - end coordinate
	 * @return double representing the length of the shortest route
	 */
	public double RouteLength(String begin, String end) {
		// Switch Start and goal to backtrack route
		int start = findClosestPoint(end);
		int goal = findClosestPoint(begin);

		if(start == goal) return 0;
		
		AddToOpenList(start); // add start point to open list
		mapList.get(start).setG(0); // save G
		mapList.get(start).setH(CalcH(start, goal)); // calculate and save H
		mapList.get(start).setF(CalcF(start)); // calculate and save F
		int currentID = start;
		
		// (Goal is not on closed list and open list is not empty)
		while (!closedList.contains(goal) && !openList.isEmpty()) {
			// select from open list where f is lowest;
			int currentLowestF = 2147483647;
			int currentLowestFID = 9999;

			for (int i = 0; i < mapList.size(); i++) {
				if (mapList.get(i).getF() < currentLowestF && openList.contains(mapList.get(i).getID())) {
					currentLowestF = (int) mapList.get(i).getF();
					currentLowestFID = (int) mapList.get(i).getID();
				}
			}

			currentID = currentLowestFID;
			int i = mapList.get(currentID).getLinks(); // Number of Neighbors
			int n = 1; // Neighbor number

			while (i > 0) {
				int currentNeighbor = mapList.get(currentID).getNn(n);
				// (neighbor is on closed list)
				if (closedList.contains(currentNeighbor)) {
					// Look at next neighbor
					i--;
					n++;
				// (neighbor is not on open list)
				} else if (!openList.contains(currentNeighbor)) {
					// add neighbor to open list
					AddToOpenList(currentNeighbor);
					// set parentID
					mapList.get(currentNeighbor).setParentID(currentID);
					// calculate and save G
					mapList.get(currentNeighbor).setG(CalcG(currentNeighbor));
					// calculate and save H
					mapList.get(currentNeighbor).setH(CalcH(currentNeighbor, goal));
					// calculate and save F
					mapList.get(currentNeighbor).setF(CalcF(currentNeighbor));
					i--;
					n++;
				} else {
					// Set tempG
					mapList.get(currentNeighbor).setTempG(CalcTempG(currentNeighbor));
					double tempG = mapList.get(currentNeighbor).getTempG();

					// (g is lower than before)
					if (tempG < mapList.get(currentNeighbor).getG()) {
						// change parent
						mapList.get(currentNeighbor).setParentID(currentID);
						// calculate and save G
						mapList.get(currentNeighbor).setG(CalcG(currentNeighbor));
						// calculate and save F
						mapList.get(currentNeighbor).setF(CalcF(currentNeighbor));
						i--;
						n++;
					// (g is NOT lower than before)
					} else {
						// look at next neighbor
						//System.out .println("This Neighbor is on Open list, calculating new G value... G is not lower than before");
						i--;
						n++;
					}
				}
			} // end while(i > 0) loop

			AddToClosedList(currentID);
			RemoveFromOpenlist(openList.indexOf(currentID));

		} // end while (!closedList.contains(goal) && !openList.isEmpty()) loop

		if (closedList.contains(goal)) {
			// Return length of route
			return mapList.get(goal).getF();
		} else {
			System.out.println("No route was found!");
			return 0;
		}
	} // End method RouteLength

	/**
	 * 
	 * Finds the intersection closest to a coordinate
	 * This is done using pythagoras
	 * 
	 * @param coordinate
	 * @return Intersection ID of the closest intersection
	 */
	public int findClosestPoint(String coordinate) {
		int thisX = Integer.parseInt(coordinate.substring(0, 4));
		int thisY = Integer.parseInt(coordinate.substring(5, 9));
		int nodeID = 9999;
		double close = 9999.9;
		double tempValue, xLength, yLength;

		for (int i = 0; i < mapList.size(); i++) {
			xLength = Math.abs(thisX - mapList.get(i).getXCoord());
			yLength = Math.abs(thisY - mapList.get(i).getYCoord());
			
			tempValue = Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));

			if (tempValue < close) {
				close = tempValue;
				nodeID = mapList.get(i).getID();
			} // End if

		} // End for loop

		return nodeID;
	}

}