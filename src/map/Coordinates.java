package map;

import java.util.ArrayList;

/**
 * 
 * @author Kenni
 *
 * The Coordinates class keeps track of all the data of each intersection.
 * The class contains information about the intersection̈́'s own ID and
 * coordinates as well as the ID of the parent cell and the neighbors.
 * Furthermore the class has information about F, G and H values used
 * for path finding and also keeps track on which intersections there
 * are taxis at an the ID of those taxis. 
 * 
 * The class has different set and get functions for these values.
 */

public class Coordinates 
{

	public int ID, ownX, ownY, parentID, NON, N1, N2, N3, N4, N5;
	public double F, G, H, TempG;
	public boolean taxi;
	public ArrayList<String> taxiIDs;
	
	/**
	 * Constructor that creates a coordinate object
	 * 
	 * @param id  The ID of the coordinate
	 * @param x   The x value of the coordinate
	 * @param y   The y value of the coordinate
	 * @param NON The number of neighbors of the coordinate
	 * @param N1  The ID of the first neighbor
	 * @param N2  The ID of the second neighbor
	 * @param N3  The ID of the third neighbor
	 * @param N4  The ID of the fourth neighbor
	 * @param N5  The ID of the fifth neighbor
	 */
	Coordinates(int id, int x, int y, int NON, int N1, int N2, int N3, int N4, int N5)
	{
		this.ID = id;
		this.ownX = x;
		this.ownY = y;
		this.parentID = 9999;
		this.F = 9999;
		this.G = 0;
		this.H = 0;
		this.TempG = 0;
		this.NON = NON;
		this.N1 = N1;
		this.N2 = N2;
		this.N3 = N3;
		this.N4 = N4;
		this.N5 = N5;
		this.taxi = false;
		this.taxiIDs = new ArrayList<String>();
	}
	
	/**
	 * Method that checks if there are any taxis at a point
	 * 
	 * @return True or False
	 */
	public boolean getTaxi()
    {
        return taxi;
    }//end method getTaxi

	/**
	 * Method that sets if there is a taxi at a point
	 * 
	 * @param TAXI Boolean that sets taxi to True or False 
	 */
    public void setTaxi (boolean TAXI)
    {
        taxi = TAXI;
    }//end method setTaxi
    
    /**
     * Method to get the IDs of the taxis at a point
     * 
     * @return An arraylist with the IDs of the taxis at the point
     */
    public ArrayList<String> getTaxiIDs()
    {
        return taxiIDs;
    }//end method getTaxiIDs

    /**
     * Method to add a taxi ID to a point
     * 
     * @param TAXIID The taxi ID to be added
     */
    public void AddTaxiIDs(String TAXIID)
    {
        taxiIDs.add(TAXIID);
    }//end method setTaxiIDs
    
    /**
     * Method to get the parent ID of a point
     * 
     * This method is used when finding the shortest path between two points.
     * When the shortest path has been found the route can be traced by
     * going to the parent point until the start point is the parent.
     * 
     * @return The ID of the parent point
     */
	public int getParentID()
    {
        return parentID;
    }//end method getParentID

	/**
	 * Method that sets the parent ID of a point
	 * 
	 * This method is used when finding the shortest path between two points.
     * When the shortest path has been found the route can be traced by
     * going to the parent point until the start point is the parent.
	 * 
	 * @param parID The ID of the point to be set as parent
	 */
    public void setParentID (int parID)
    {
        parentID = parID;
    }//end method setParentID
    
    /**
     * Method to get the F value of a point
     * 
     * This method is used when finding the shortest path between two points.
     * The F value of a point is the sum if the G and H values.
     * 
     * @return The F value of the point
     */
    public double getF()
    {
        return F;
    }//end method getF

    /**
     * Method to set the F value of a point
     * 
     * This method is used when finding the shortest path between two points.
     * The F value of a point is the sum if the G and H values.
     * 
     * @param f The value to set as F value
     */
    public void setF (double f)
    {
        F = f;
    }//end method setF
    
    /**
     * Method to get the G value of a point
     * 
     * This method is used when finding the shortest path between two points.
     * The G value of a point is the cost from the starting point to the point.
     * 
     * @return The G value of the point
     */
    public double getG()
    {
        return G;
    }//end method getG

    /**
     * Method to set the G value of a point
     * 
     * This method is used when finding the shortest path between two points.
     * The G value of a point is the cost from the starting point to the point.
     * 
     * @param g The value to be set as G value
     */
    public void setG (double g)
    {
        G = g;
    }//end method setG
    
    /**
     * Method to get the H value of a point
     * 
     * This method is used when finding the shortest path between two points.
     * The H value of a point is the cost from the point to the end point
     * as the crow flies.
     * 
     * @return The H value of the point
     */
    public double getH()
    {
        return H;
    }//end method getH

    /**
     * Method to set the H value of a point
     * 
     * This method is used when finding the shortest path between two points.
     * The H value of a point is the cost from the point to the end point
     * as the crow flies.
     * 
     * @param h The value to be set as H value
     */
    public void setH (double h)
    {
        H = h;
    }//end method setH
    
    /**
     * Method to get the temporary G value of a point
     * 
     * This method is used when finding the shortest path between two points.
     * The temporary G value of a point is used to check if the current path
     * to a point is shorter than the known path.
     * 
     * @return The temporary G value of the point
     */
    public double getTempG()
    {
        return TempG;
    }//end method getTempG

    /**
     * Method to set the temporary G value of a point
     * 
     * This method is used when finding the shortest path between two points.
     * The temporary G value of a point is used to check if the current path
     * to a point is shorter than the known path.
     * 
     * @param g
     */
    public void setTempG (double g)
    {
        TempG = g;
    }//end method setTempG
    
    /**
     * Method to get the number of neighbors of a point
     * 
     * @return The number of neighbors of a point
     */
    public int getNON()
    {
        return NON;
    }//end method getNON

    /**
     * Method to set the number of neighbors of a point
     * 
     * @param n The value to be set as number of neighbor
     */
    public void setNON (int n)
    {
        NON = n;
    }//end method setNON
    
    /**
     * Method to get the ID of neighbor number one
     * 
     * @return The ID of neighbor number one
     */
    public int getN1()
    {
        return N1;
    }//end method getN1

    /**
     * Method to set the ID of neighbor number one
     * 
     * @param n The value to be set as ID of neighbor number one
     */
    public void setN1 (int n)
    {
        N1 = n;
    }//end method setN1
    
    /**
     * Method to get the ID of neighbor number two
     * 
     * @return The ID of neighbor number two
     */
    public int getN2()
    {
        return N2;
    }//end method getN2

    /**
     * Method to set the ID of neighbor number two
     * 
     * @param n The value to be set as ID of neighbor number two
     */
    public void setN2 (int n)
    {
        N2 = n;
    }//end method setN2
    
    /**
     * Method to get the ID of neighbor number three
     * 
     * @return The ID of neighbor number three
     */
    public int getN3()
    {
        return N3;
    }//end method getN3

    /**
     * Method to set the ID of neighbor number three
     * 
     * @param n The value to be set as ID of neighbor number three
     */
    public void setN3 (int n)
    {
        N3 = n;
    }//end method setN3
    
    /**
     * Method to get the ID of neighbor number four
     * 
     * @return The ID of neighbor number four
     */
    public int getN4()
    {
        return N4;
    }//end method getN4

    /**
     * Method to set the ID of neighbor number four
     * 
     * @param n The value to be set as ID of neighbor number four
     */
    public void setN4 (int n)
    {
        N4 = n;
    }//end method setN4
    
    /**
     * Method to get the ID of neighbor number five
     * 
     * @return The ID of neighbor number five
     */
    public int getN5()
    {
        return N5;
    }//end method getN5

    /**
     * Method to set the ID of neighbor number five
     * 
     * @param n The value to be set as ID of neighbor number five
     */
    public void setN5 (int n)
    {
        N5 = n;
    }//end method setN5

    /**
     * Method to get the ID of neighbor number n
     * 
     * @return The ID of neighbor number n
     */
    public int getNn(int n)
    {
        switch(n){
        case 1:
        	return getN1();
        case 2:
        	return getN2();
        case 3:
        	return getN3();
        case 4:
        	return getN4();
        case 5:
        	return getN5();
        default:
        	return 9999;
        }
    }//end method getNn
}