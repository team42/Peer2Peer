package command;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import database.*;

public class GotTripCommand extends Command {

	private TripsDAO dao = new TripsDAO();
	
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		String tripID = receivedMessage.substring(5, 8);
		String taxiID = receivedMessage.substring(8);
		
		dao.confirmTrip(taxiID, tripID);
	}
}
