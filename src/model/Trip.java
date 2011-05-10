package model;

import java.util.*;

public class Trip {

	private String tripID;
	private String coords;
	private int accepted;
	private Date date;
	
	public Trip(String tripID, int accepted, String coords) {
		this.tripID = tripID;
		this.accepted = accepted;
		this.coords = coords;
		date = Calendar.getInstance().getTime();
	}
	
	public Trip(String tripID, int accepted, String coords, Date date) {
		this.tripID = tripID;
		this.accepted = accepted;
		this.coords = coords;
		this.date = date;
	}
	
	public String getTripID() {
		return tripID;
	}
	
	public int getAccepted() {
		return accepted;
	}
	
	public String getCoords() {
		return coords;
	}
	
	public Date getDate() {
		return date;
	}
}
