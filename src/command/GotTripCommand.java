package command;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import database.DAO;

public class GotTripCommand extends Command {

	private DAO dao = new DAO();
	
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		String tripID = receivedMessage.substring(5, 8);
		String taxiID = receivedMessage.substring(8);
		
		dao.confirmTrip(taxiID, tripID);
	}
}
