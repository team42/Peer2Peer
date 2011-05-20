package model;

/**
 * 
 * Model used for representing an intersection
 * Used for storing the map.
 * 
 * @author Anders, Kenni, Nicolai
 *
 */
public class Intersection 
{

	// Variables
	private int id, xCoord, yCoord, parentID, links, n1, n2, n3, n4, n5;
	private double F, G, H, TempG;
	
	/**'
	 * 
	 * Constructor
	 * Sets the:
	 * - Intersection ID
	 * - X Coordinate
	 * - Y Coordinate
	 * - Number of Links
	 * - Intersection ID of all neighbors
	 * 
	 * F, G, H, TempG and ParentID have default values
	 * 
	 * @param id
	 * @param x
	 * @param y
	 * @param link
	 * @param n1
	 * @param n2
	 * @param n3
	 * @param n4
	 * @param n5
	 */
	public Intersection(int id, int x, int y, int link, int n1, int n2, int n3, int n4, int n5)
	{
		this.id = id;
		this.xCoord = x;
		this.yCoord = y;
		this.parentID = 9999;
		this.F = 9999;
		this.G = 0;
		this.H = 0;
		this.TempG = 0;
		this.links = link;
		this.n1 = n1;
		this.n2 = n2;
		this.n3 = n3;
		this.n4 = n4;
		this.n5 = n5;
	}
    
	/**
	 * Returns the ParentID
	 * 
	 * @return
	 */
	public int getParentID()
    {
        return parentID;
    }

	/**
	 * Sets the ParentID
	 * [Used for calculating shortest path]
	 * 
	 * @param parID
	 */
    public void setParentID (int parID)
    {
        parentID = parID;
    }
    
    /**
     * Returns F value
     * [Used for calculating shortest path]
     * 
     * @return
     */
    public double getF()
    {
        return F;
    }

    /**
     * Sets the F value
     * [Used for calculating shortest path]
     * 
     * @param f
     */
    public void setF (double f)
    {
        F = f;
    }
    
    /**
     * Returns the G value
     * [Used for calculating shortest path]
     * 
     * @return
     */
    public double getG()
    {
        return G;
    }//end method getG

    /**
     * Set the G value
     * [Used for calculating shortest path]
     * 
     * @param g
     */
    public void setG (double g)
    {
        G = g;
    }
    
    /**
     * Return the H Value
     * [Used for calculating shortest path]
     * 
     * @return
     */
    public double getH()
    {
        return H;
    }

    /**
     * Set the H value
     * [Used for calculating shortest path]
     * 
     * @param h
     */
    public void setH (double h)
    {
        H = h;
    }
    
    /**
     * Returns the tempG value
     * [Used for calculating shortest path]
     * 
     * @return
     */
    public double getTempG()
    {
        return TempG;
    }

    /**
     * Set the tempG value
     * 
     * @param g
     */
    public void setTempG (double g)
    {
        TempG = g;
    }
    
    /**
     * Returns the amount of neighbors
     * 
     * @return
     */
    public int getLinks()
    {
        return links;
    }

    /**
     * Set the amount of neighbors
     * 
     * @param links
     */
    public void setLinks (int links)
    {
        this.links = links;
    }
    
    /**
     * Returns the intersection ID
     * 
     * @return
     */
    public int getID() {
    	return id;
    }
    
    /**
     * Returns the X coordinate
     * 
     * @return
     */
    public int getXCoord() {
    	return xCoord;
    }
    
    /**
     * Returns the Y coordinate
     * 
     * @return
     */
    public int getYCoord() {
    	return yCoord;
    }
    
    /**
     * Returns neighbor one
     * 
     * @return
     */
    public int getN1()
    {
        return n1;
    }

    /**
     * Returns neighbor two
     * 
     * @return
     */
    public int getN2()
    {
        return n2;
    }

    /**
     * Returns neighbor three
     * 
     * @return
     */
    public int getN3()
    {
        return n3;
    }

    /**
     * Returns neighbor four
     * 
     * @return
     */
    public int getN4()
    {
        return n4;
    }
    
    /**
     * Returns neighbor five
     * 
     * @return
     */
    public int getN5()
    {
        return n5;
    }

    /**
     * Returns neighbor by parameter
     * 
     * @return
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
    }
}