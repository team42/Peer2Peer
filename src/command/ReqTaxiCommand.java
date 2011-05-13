package command;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
		
		// Find closest taxis by customerCoord (Exclude taxis who already got the trip)
		
		ArrayList<Taxi> taxiList = dao.getActiveTaxis();
		
		Taxi curTaxi = null;
		
		String strTaxiList = "";
		
		for(int i=0; i > taxiList.size(); i++) {
			
			curTaxi = taxiList.get(i);
			
			strTaxiList += strTaxiList + curTaxi.getTaxiID() + curTaxi.getTaxiCoord() + "%";
		}
		
		String localIP = "";
		
		try {
			localIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		String reply = "SENTC" + tripID + strTaxiList + localIP;
		
		int peer = receivePacket.getPort();
        
		byte[] replyRaw = new byte[1024];
        replyRaw = reply.getBytes();
        
        DatagramPacket sendPacket = new DatagramPacket(replyRaw, replyRaw.length, receivePacket.getAddress(), peer);
        
        try {
			peerSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}