package model;

public class AwaitingTaxi {

	private String taxiID;
	private String taxiCoord;
	private String company;
	private int heuristic;
	
	public AwaitingTaxi(String taxiID, String taxiCoord, String company) {
		this.taxiID = taxiID;
		this.taxiCoord = taxiCoord;
		this.company = company;
	}
	
	public String getTaxiID() {
		return taxiID;
	}
	
	public String getTaxiCoord() {
		return taxiCoord;
	}
	
	public String getCompanyIP() {
		return company;
	}
	
	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}
	
	public int getHeuristic() {
		return heuristic;
	}
	
}
