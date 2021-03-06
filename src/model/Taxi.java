package model;

/**
 * 
 * Taxi model used to store taxi information
 * 
 * @author Nicolai
 *
 */
public class Taxi {

	private String taxiID;
	private String taxiCoord;
	private String company;
	private int heuristic;
	
	/**
	 * 
	 * Constructor
	 * 
	 * Sets the taxiID, taxi coordinates and IP of company
	 * the taxi belongs to
	 * 
	 * @param taxiID
	 * @param taxiCoord
	 * @param company
	 */
	public Taxi(String taxiID, String taxiCoord, String company) {
		this.taxiID = taxiID;
		this.taxiCoord = taxiCoord;
		this.company = company;
	}
	
	/**
	 * Constructor
	 * 
	 * Taxi ID and coordinate is required to make an object
	 * 
	 * @param taxiID
	 * @param taxiCoord
	 */
	public Taxi(String taxiID, String taxiCoord) {
		this.taxiID = taxiID;
		this.taxiCoord = taxiCoord;
	}
	
	/**
	 * Return the taxi ID
	 * 
	 * @return taxiID
	 */
	public String getTaxiID() {
		return taxiID;
	}
	
	/**
	 * Return the taxi coordinate
	 * 
	 * @return taxiCoord
	 */
	public String getTaxiCoord() {
		return taxiCoord;
	}
	
	/**
	 * Returns the IP of the company the taxi belongs to
	 * 
	 * @return value of company
	 */
	public String getCompanyIP() {
		return company;
	}
	
	/**
	 * Set heuristics for a taxi
	 * 
	 * @param heuristic
	 */
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
	
	/**
	 * Returns heuristics of a taxi
	 * 
	 * @return value of heuristic
	 */
	public int getHeuristic() {
		return heuristic;
	}
	
}
