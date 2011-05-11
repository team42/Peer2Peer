package command;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import peer.UDPPeer;

import database.DAO;
import java.util.*;

public class TaxiAcceptCommand extends Command {

	private DAO dao = new DAO();
	
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		String taxiID = receivedMessage.substring(5, 8);
		String tripID = receivedMessage.substring(8);
		
		ArrayList<String> companyIPs = dao.getCompanyIP(tripID);
		dao.deleteOngoingTrip(tripID);
		
		String companyGot = receivePacket.getAddress().getHostAddress()
		String query = "MISTR" + tripID;
		
		for(int i=0; i<companyIPs.size(); i++) {
			if(!companyIPs.get(i).equals(companyGot)) {
				UDPPeer.sendMessages(InetAddress.getByName(companyIPs.get(i)), query);
			}
		}
		
		query = "GOTTR" + tripID + taxiID;
		
		UDPPeer.sendMessages(receivePacket.getAddress(), query);
	}

}
