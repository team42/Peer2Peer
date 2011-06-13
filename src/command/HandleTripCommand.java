package command;

import java.net.*;

/**
 * This class handles trips.
 * 
 * Sends a request for taxi id and coordinates to all peers in its list.
 * After 5 seconds it will collect all taxis relevant for the trip from the 
 * database. Calculate shortest path for relevant taxis and sort them.
 * Then it sends a taxi offer to the closest taxi and deletes offered taxis 
 * from taxi list.
 * 
 * Starts a 5 minute timer that checks if any of the offered taxi have 
 * accepted the trip. If the timer runs out and no taxis have accepted trip, 
 * it will send offers to the next 5 closest taxis. This continues until no 
 * more taxis are available in which case the trip is reset and added to 
 * the trips table again.
 * 
 * @author Nicolai
 *
 */
public class HandleTripCommand extends Command {
	
	private String tripID = "";
	private String tripCoordinate = "";
	
	/**
	 * The execute method will send a request for taxis to the peers in its 
	 * list and starts the timer.
	 * 
	 * @param receivedMessage - The received message
	 * @param receivePacket - The packet containing IP etc of sender
	 */
	public void execute(String receivedMessage, DatagramPacket receivePacket) {
		System.out.println("================ HANDLE TRIP ================");
		
		tripID = receivedMessage.substring(5, 15);
		tripCoordinate = receivedMessage.substring(15);
		
		HandleTripThread thread = new HandleTripThread(tripID, tripCoordinate);
		
		thread.run();
	}
}