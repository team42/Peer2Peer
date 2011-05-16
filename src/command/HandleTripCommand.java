package command;

import java.io.*;
import java.net.*;
import java.util.*;

import peer.UDPPeer;

import model.Taxi;
import model.CalcedTaxi;
import model.Peer;
import config.Configuration;
import database.FinishedTripsDAO;
import database.OngoingTripsDAO;
import database.TripOffersDAO;
import database.TripsDAO;

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
	
	OngoingTripsDAO ongoingDAO = new OngoingTripsDAO();
	TripOffersDAO tripOfferDAO = new TripOffersDAO();
	FinishedTripsDAO finishedDAO = new FinishedTripsDAO();
	
	ArrayList<CalcedTaxi> calcTaxis = new ArrayList<CalcedTaxi>();
	
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
	
	class firstSend extends TimerTask {
		public void run() {
			ArrayList<Taxi> taxis = new ArrayList<Taxi>();
			
			int messages;
			
			// get all onging_taxis by tripID
			taxis = ongoingDAO.getTaxiByTrip(tripID);
			
			for(int i=0; i<taxis.size(); i++) {
				// sortest path length by algorithm for taxis.get(i)
				//calcTaxis.add(new CalcedTaxi(taxiID, taxiCoord, company, shortestPath));
			}
			
			// Sort calcTaxis by Selection Sort
			calcTaxis = sortTaxis(calcTaxis);
			
			if(calcTaxis.size() < 5) {
				messages = calcTaxis.size();
			} else {
				messages = 5;
			}
			
			for(int i=0; i<messages; i++) {
				String query = "TAXOF" + calcTaxis.get(0).getTaxiID() + tripID + tripCoordinate;
				try {
					InetAddress ip = InetAddress.getByName(calcTaxis.get(i).getCompanyIP());
					UDPPeer.sendMessages(ip, query);
				} catch (IOException e) {
					e.printStackTrace();
				}
				calcTaxis.remove(0);
			}
			
			timer = new Timer();
			timer.schedule(new Send(), 1000*60*5);
		}
	}
	
	class Send extends TimerTask{
		public void run() {
			if(!finishedDAO.isTripFinished(tripID)) {
				if (calcTaxis.size() > 0) {
					int messages;
				
					if(calcTaxis.size() < 5) {
						messages = calcTaxis.size();
					} else {
						messages = 5;
					}
					
					for(int i=0; i<messages; i++) {
						String query = "TAXOF" + calcTaxis.get(0).getTaxiID() + tripID + tripCoordinate;
						try {
							InetAddress ip = InetAddress.getByName(calcTaxis.get(i).getCompanyIP());
							UDPPeer.sendMessages(ip, query);
						} catch (IOException e) {
							e.printStackTrace();
						}
						calcTaxis.remove(0);
					}
					
					timer = new Timer();
					timer.schedule(new Send(), 1000*60*5);
				} else {
					tripOfferDAO.addTrip(tripCoordinate);
					
					ArrayList<String> companyIPs = ongoingDAO.getCompanyIP(tripID);
					ongoingDAO.deleteOngoingTrip(tripID);
					
					String query = "MISTR" + tripID;
					
					for(int i=0; i<companyIPs.size(); i++) {
						try {
							UDPPeer.sendMessages(InetAddress.getByName(companyIPs.get(i)), query);
						} catch (UnknownHostException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	
	public static ArrayList<CalcedTaxi> sortTaxis(ArrayList<CalcedTaxi> taxiList) {
		CalcedTaxi tempTaxi;
		
		for (int i = 0; i < taxiList.size(); i++) {
			for (int j = 0; j < taxiList.size(); j++) {
				if(taxiList.get(i).getShortestPath() < taxiList.get(j).getShortestPath()) {
					tempTaxi = taxiList.get(i);
					taxiList.set(i, taxiList.get(j));
					taxiList.set(j, tempTaxi);
				}
			}
		}

		return taxiList;
	}
}
