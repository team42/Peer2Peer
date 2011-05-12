package command;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import database.DAO;
import database.TripsDAO;

public class TaxiOfferCommand extends Command {

	private TripsDAO dao = new TripsDAO();
	
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		String taxiID = receivedMessage.substring(5, 8);
		String tripID = receivedMessage.substring(8, 11);
		String tripCoord = receivedMessage.substring(11);
		
		dao.insertTrip(taxiID, tripID, tripCoord);
	}

}
