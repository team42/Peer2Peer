package command;

import java.net.*;

import database.TripsDAO;

/**
 * A taxi has been offered a trip.
 * 
 * @author Nicolai
 *
 */
public class TaxiOfferCommand extends Command {

	private TripsDAO dao = new TripsDAO();
	
	/**
	 * All data is identified and the trip is added the taxi
	 * 
	 * @param receivedMessage - The received message
	 * @param receivePacket - The packet containing IP etc of sender
	 */
	public void execute(String receivedMessage, DatagramPacket receivePacket) {
		System.out.println("================ TAXI OFFER ================");
		
		String taxiID = receivedMessage.substring(5, 11);
		String tripID = receivedMessage.substring(11, 21);
		String tripCoord = receivedMessage.substring(21);
		String returnIP = receivePacket.getAddress().getHostAddress();

		System.out.println("Added trip: " + tripID + " to taxi: " + taxiID);
		
		dao.insertTrip(taxiID, tripID, tripCoord, returnIP);
	}

}
