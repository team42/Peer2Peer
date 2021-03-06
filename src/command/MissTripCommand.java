package command;

import java.net.DatagramPacket;
import database.*;

/**
 * If another company got the trip first this command will be received to tell
 * the Peer that it didn't get the trip.
 * 
 * @author Nicolai
 *
 */
public class MissTripCommand extends Command {
	
	private TripsDAO dao = new TripsDAO();
	
	/**
	 * The trip ID is identified and the trip is deleted for all taxis.
	 * 
	 * @param receivedMessage - The received message
	 * @param receivePacket - The packet containing IP etc of sender
	 */
	public void execute(String receivedMessage, DatagramPacket receivePacket) {
		System.out.println("================ MISSED TRIP ================");
		
		String tripID = receivedMessage.substring(5);

		System.out.println("Missed trip: " + tripID);
		
		dao.deleteTrip(tripID);
	}

}
