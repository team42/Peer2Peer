package model;


import java.util.ArrayList;

public class Intersection 
{

	private int id, xCoord, yCoord, parentID, links, n1, n2, n3, n4, n5;
	private double F, G, H, TempG;
	private boolean taxi;
	private ArrayList<Integer> taxiIDs;
	
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
		this.taxi = false;
		this.taxiIDs = new ArrayList<Integer>();
	}
	
	public boolean getTaxi()
    {
        return taxi;
    }//end method getTaxi

    public void setTaxi (boolean TAXI)
    {
        taxi = TAXI;
    }//end method setTaxi
    
    public ArrayList<Integer> getTaxiIDs()
    {
        return taxiIDs;
    }//end method getTaxiIDs

    public void AddTaxiIDs(int TAXIID)
    {
        taxiIDs.add(TAXIID);
    }//end method setTaxiIDs
    
	public int getParentID()
    {
        return parentID;
    }//end method getParentID

    public void setParentID (int parID)
    {
        parentID = parID;
    }//end method setParentID
    
    public double getF()
    {
        return F;
    }//end method getF

    public void setF (double f)
    {
        F = f;
    }//end method setF
    
    public double getG()
    {
        return G;
    }//end method getG

    public void setG (double g)
    {
        G = g;
    }//end method setG
    
    public double getH()
    {
        return H;
    }//end method getH

    public void setH (double h)
    {
        H = h;
    }//end method setH
    
    public double getTempG()
    {
        return TempG;
    }//end method getTempG

    public void setTempG (double g)
    {
        TempG = g;
    }//end method setTempG
    
    public int getLinks()
    {
        return links;
    }//end method getNON

    public void setLinks (int links)
    {
        this.links = links;
    }//end method setNON
    
    public int getID() {
    	return id;
    }
    
    public int getXCoord() {
    	return xCoord;
    }
    
    public int getYCoord() {
    	return yCoord;
    }
    
    public int getN1()
    {
        return n1;
    }//end method getN1

    public int getN2()
    {
        return n2;
    }//end method getN2

    public int getN3()
    {
        return n3;
    }//end method getN3

    public int getN4()
    {
        return n4;
    }//end method getN4
    
    public int getN5()
    {
        return n5;
    }//end method getN5

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