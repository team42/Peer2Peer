package command;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import database.DAO;

public class MissTripCommand extends Command {
	
	private DAO dao = new DAO();
	
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		String tripID = receivedMessage.substring(5);
		
		dao.deleteTrip(tripID);
	}

}
