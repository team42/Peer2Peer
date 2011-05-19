package command;

import java.io.*;
import java.net.*;
import java.util.*;

import algorithm.Algorithm;

import peer.UDPPeer;
import model.*;
import config.Configuration;
import database.*;

/**
 * This class handles trips.
 * 
 * Sends a request for taxi id and coordinates to all peers in its list.
 * After 5 seconds it will collect all taxis relevant for the trip from the 
 * database. Calculate shortest path for relevant taxis and sort them.
 * Then it sends a taxi offer to the closest taxi and deletes offered taxis 
 * from taxi list.
 * 
 * Starts a 5 minute timer that checks if any of the offered taxi have 
 * accepted the trip. If the timer runs out and no taxis have accepted trip, 
 * it will send offers to the next 5 closest taxis. This continues until no 
 * more taxis are available in which case the trip is reset and added to 
 * the trips table again.
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
	
	Algorithm algorithm = new Algorithm();
	
	ArrayList<CalcedTaxi> calcTaxis = new ArrayList<CalcedTaxi>();
	
	/**
	 * The execute method will send a request for taxis to the peers in its 
	 * list and starts the timer.
	 * 
	 * @param receivedMessage - The received message
	 * @param peerSocket - The socket to respond at
	 * @param receivePacket - The packet containing IP etc of sender
	 */
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		System.out.println("================ HANDLE TRIP ================");
		timer = new Timer();
		
		tripID = receivedMessage.substring(5, 15);
		tripCoordinate = receivedMessage.substring(15);

		System.out.println("Handle Trip for:\n" + tripID + "  " + tripCoordinate);
		
		ArrayList<Peer> peers = config.getPeers();
		
		for(int i= 0; i<peers.size(); i++) {
			String query = "REQTC" + tripID + tripCoordinate;
			InetAddress ip;
			try {
				ip = InetAddress.getByName(peers.get(i).getIp());
				
				System.out.println("Request Taxis sent to: " + ip.getHostAddress());
				
				UDPPeer.sendMessages(ip, query);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		timer.schedule(new firstSend(), 5000);
	}
	
	/**
	 * 
	 * @author Nicolai
	 *
	 */
	class firstSend extends TimerTask {
		public void run() {
			ArrayList<Taxi> taxis = new ArrayList<Taxi>();
			
			String taxiID, taxiCoord, company;
			int shortestPath, messages;
			
			// get all ongoing_taxis by tripID
			taxis = ongoingDAO.getTaxiByTrip(tripID);
			
			for(int i=0; i<taxis.size(); i++) {
				taxiID = taxis.get(i).getTaxiID();
				taxiCoord = taxis.get(i).getTaxiCoord();
				company = taxis.get(i).getCompanyIP();
				shortestPath = (int)algorithm.RouteLength(taxiCoord, tripCoordinate);
				calcTaxis.add(new CalcedTaxi(taxiID, taxiCoord, company, shortestPath));
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
					InetAddress ip = InetAddress.getByName(calcTaxis.get(0).getCompanyIP());
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
							InetAddress ip = InetAddress.getByName(calcTaxis.get(0).getCompanyIP());
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
