package handleNewTrips;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import model.Peer;

import peer.UDPPeer;
import database.TripOffersDAO;
import config.*;

/**
 * 
 * Class used for polling for new trips on the taxi_offers table
 * If the operator orders a trip, this class will copy the trip
 * and then delete it from the table.
 * Then sending a handle trip request to the company responsible
 * for the area the trip is located in.
 * The polling is done every 3 second
 * 
 * @author Nicolai
 *
 */
public class NewTrips {

	Timer timer;
	TripOffersDAO tripOfferDAO = new TripOffersDAO();
	Configuration config = Configuration.getConfiguration();
	
	/**
	 * 
	 * Constructor
	 * 
	 * Start a timer that execute handle trip every third second
	 * 
	 */
	public NewTrips() {
		timer = new Timer();
		timer.schedule(new addNewTrip(), 3000, 3000);
	}
	
	/**
	 * 
	 * Parameters are tripID and trip coordinate
	 * By the x-coordinate, the peer responsible for the 
	 * coordinate of the trip is decided.
	 * A handle trip is sent using the peer2peer protocol
	 * 
	 * @param tripID
	 * @param coordinate
	 */
	public void handleTrip(String tripID, String coordinate) {
		
		String[] coords = coordinate.split(",");
		int xCoord = Integer.parseInt(coords[0]);
		
		ArrayList<Peer> peers = new ArrayList<Peer>();
		peers = config.getPeers();
		
		String correctCoordinate = coordinateSyntax(coords);
		
		for(int i=0; i<peers.size(); i++) {
			if(xCoord >= ((i*2000)/peers.size()) && xCoord < (((i+1)*2000)/peers.size())) {
				String query = "HANTR" + tripID + correctCoordinate;
				
				System.out.println("================ HANDLE TRIP SEND ================");
				System.out.println("Handle trip: " + tripID + " sent to " + peers.get(i).getIp());
				
				try {
					UDPPeer.sendMessages(InetAddress.getByName(peers.get(i).getIp()), query);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String coordinateSyntax(String[] coords) {
		
		for(int i=0; i<coords.length; i++) {
			while(coords[i].length() < 4) {
				coords[i] = "0" + coords[i];
			}
		}
		
		String coordinate = coords[0] + "," + coords[1]; 
		
		return coordinate;
	}
	
	/**
	 * 
	 * Timertask that handles new trip
	 * Creates a customer string array
	 * customer[0] is the tripID
	 * customer[1] is the coordinate for the customer
	 * If there's not trip <handleTrip> won't be called
	 * 
	 * @author Nicolai
	 *
	 */
	class addNewTrip extends TimerTask {
		public void run() {
			String[] customer = tripOfferDAO.getCustomer();
			
			if(!customer[0].equals("none")) {
				handleTrip(customer[0], customer[1]); 
			}
		}
	}
}
