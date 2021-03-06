package command;

import java.net.DatagramPacket;
import database.*;

/**
 * This class is used when another Peer is telling us that we got the trip.
 * 
 * @author Nicolai
 * 
 */
public class GotTripCommand extends Command {

	private TripsDAO tripsDAO = new TripsDAO();

	/**
	 * The trip ID and taxi ID is identified. With these parameter, the trip is
	 * accepted for the taxi with the taxi ID and deleted for the rest of the
	 * taxis.
	 * 
	 * @param receivedMessage - The received message
	 * @param receivePacket - The packet containing IP etc of sender
	 */
	public void execute(String receivedMessage, DatagramPacket receivePacket) {
		String tripID = receivedMessage.substring(5, 15);
		String taxiID = receivedMessage.substring(15);

		System.out.println("================ GOT TRIP ================");
		System.out.println("Taxi: " + taxiID + " got trip: " + tripID);
		
		tripsDAO.confirmTrip(taxiID, tripID);
	}
}
