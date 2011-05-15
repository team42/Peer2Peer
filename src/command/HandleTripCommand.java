package command;

import java.io.*;
import java.net.*;
import java.util.*;

import peer.UDPPeer;

import model.AwaitingTaxi;
import model.CalcedTaxi;
import model.Peer;
import config.Configuration;

/**
 * This command is used when another Peer announces itself.
 * 
 * @author Nicolai
 *
 */
public class HandleTripCommand extends Command {

	private String tripID = "";
	private String tripCoordinate = "";
	
	Configuration config = Configuration.getConfiguration();
	Timer timer;
	
	/**
	 * The execute method will get it's own PeerList and send it back to the sender.
	 * 
	 * @param receivedMessage - The received message
	 * @param peerSocket - The socket to respond at
	 * @param receivePacket - The packet containing IP etc of sender
	 */
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		timer = new Timer();
		
		tripID = receivedMessage.substring(5, 15);
		tripCoordinate = receivedMessage.substring(15);
		
		ArrayList<Peer> peers = new ArrayList<Peer>();
		
		for(int i= 0; i<peers.size(); i++) {
			String query = "REQTC" + tripID + tripCoordinate;
			InetAddress ip;
			try {
				ip = InetAddress.getByName(peers.get(i).getIp());
				UDPPeer.sendMessages(ip, query);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		timer.schedule(new firstSend(), 5000);
	}
	
	private AwaitingTaxi calcHeuristics(AwaitingTaxi taxi, String tripCoordinate) {
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
	
	class firstSend extends TimerTask {
		public void run() {
			ArrayList<AwaitingTaxi> taxis = new ArrayList<AwaitingTaxi>();
			ArrayList<CalcedTaxi> calcTaxis = new ArrayList<CalcedTaxi>();
			
			// get all onging_taxis by tripID
			
			for(int i=0; i<taxis.size(); i++) {
				taxis.set(i, calcHeuristics(taxis.get(i), tripCoordinate));
			}
			
			// Sort taxis by heuristics (Selection Sort)
			
			for(int i=0; i<25; i++) {
				// sortest path length by algorithm for taxis.get(i)
			}
			
			for(int i=0; i<5; i++) {
				String query = "TAXOF";
				try {
					InetAddress ip = InetAddress.getByName(calcTaxis.get(i).getCompanyIP());
					UDPPeer.sendMessages(ip, query);
				} catch (IOException e) {
					e.printStackTrace();
				}
				calcTaxis.remove(0);
			}
			
			timer = new Timer();
			timer.schedule(new Send(), 5000);
		}
	}
	
	class Send extends TimerTask{
		public void run() {
			
		}
	}
}
