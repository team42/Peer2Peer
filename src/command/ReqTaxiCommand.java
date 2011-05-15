package command;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import model.Taxi;
import model.Taxi;
import database.*;

/**
 * This command is received when another peer requests the coordinates of your taxis.
 * 
 * @author Nicolai
 *
 */
public class ReqTaxiCommand extends Command {

	private TaxiDAO dao = new TaxiDAO();
	
	/**
	 * The trip ID and customer coordinate is identified.
	 * Taxis are found and converted to a string.
	 * A string is made to fit the P2P protocol.
	 * This is send back to the sender of the request.
	 * 
	 * @param receivedMessage - The received message
	 * @param peerSocket - The socket to respond at
	 * @param receivePacket - The packet containing IP etc of sender
	 */
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		String tripID = receivedMessage.substring(5,15);
		String customerCoord = receivedMessage.substring(15);
		
		String strTaxiList = "";
		String localIP = "";
		String taxiAmount = "";
		
		int amount;
		
		// Get Taxis from database
		ArrayList<Taxi> taxiList = dao.getActiveTaxis();
		
		// Calculate heuristics for taxis
		for(int i=0; i<taxiList.size(); i++) {
			taxiList.set(i, calcHeuristics(taxiList.get(i), customerCoord));
		}
		
		//!!! NOT IMPLEMENTED
		// Sort by Selection Sort
		//!!! NOT IMPLEMENTED
		
		//Set taxi amount
		if(taxiList.size() < 10) {
			amount = taxiList.size();
		} else {
			amount = 10;
		}
		
		// Convert taxi list to string format
		for(int i=0; i > amount; i++) {
			strTaxiList += strTaxiList + taxiList.get(i).getTaxiID() + taxiList.get(i).getTaxiCoord();
		}
		
		// Find local IP
		try {
			localIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		// Set correct taxi Amount length
		if(amount < 10) {
			taxiAmount = "0" + amount;
		} else {
			taxiAmount = Integer.toString(amount);
		}
		
		// Create reply
		String reply = "SENTC" + tripID + taxiAmount + strTaxiList + localIP;
		
		// Get sender port
		int peer = receivePacket.getPort();
        
		// Send package
		byte[] replyRaw = new byte[1024];
        replyRaw = reply.getBytes();
        
        DatagramPacket sendPacket = new DatagramPacket(replyRaw, replyRaw.length, receivePacket.getAddress(), peer);
        
        try {
			peerSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private Taxi calcHeuristics(Taxi taxi, String tripCoordinate) {
		int x1 = Integer.parseInt(taxi.getCompanyIP().substring(0, 4));
		int y1 = Integer.parseInt(taxi.getCompanyIP().substring(5, 9));
		int x2 = Integer.parseInt(tripCoordinate.substring(0, 4));
		int y2 = Integer.parseInt(tripCoordinate.substring(5, 9));
		int xLength = Math.abs(x1-x2);
		int yLength = Math.abs(y1-y2);
		int heuristic = (int) Math.sqrt(Math.pow(xLength, 2)+Math.pow(yLength, 2));
		taxi.setHeuristic(heuristic);
		return taxi;
	}

}