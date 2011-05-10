package model;

public class Taxi {

	private String taxiID;
	private String taxiCoord;
	
	public Taxi(String taxiID, String taxiCoord) {
		
		this.taxiID = taxiID;
		this.taxiCoord = taxiCoord;
		
	}
	
	public String getTaxiID() {
		return taxiID;
	}
	
	public String getTaxiCoord() {
		return taxiCoord;
	}
	
}
