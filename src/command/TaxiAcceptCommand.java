package command;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import peer.UDPPeer;

import database.DAO;
import database.OngoingTripsDAO;

import java.util.*;

public class TaxiAcceptCommand extends Command {

	private OngoingTripsDAO dao = new OngoingTripsDAO();
	
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		String taxiID = receivedMessage.substring(5, 8);
		String tripID = receivedMessage.substring(8);
		
		ArrayList<String> companyIPs = dao.getCompanyIP(tripID);
		dao.deleteOngoingTrip(tripID);
		
		String companyGot = receivePacket.getAddress().getHostAddress();
		String query = "MISTR" + tripID;
		
		for(int i=0; i<companyIPs.size(); i++) {
			if(!companyIPs.get(i).equals(companyGot)) {
				try {
					UDPPeer.sendMessages(InetAddress.getByName(companyIPs.get(i)), query);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		query = "GOTTR" + tripID + taxiID;
		
		try {
			UDPPeer.sendMessages(receivePacket.getAddress(), query);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
