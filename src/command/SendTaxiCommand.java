package command;

import java.net.DatagramPacket;
import database.OngoingTripsDAO;
import model.Taxi;
import java.util.*;

/**
 * Another peer has send the taxis that this peer requested.
 * 
 * @author Nicolai
 *
 */
public class SendTaxiCommand extends Command {

	private OngoingTripsDAO dao = new OngoingTripsDAO();
	
	/**
	 * Taxis are indentified and converted to an arraylist of taxi objects.
	 * These are added to the database to be used for calculations later.
	 * 
	 * @param receivedMessage - The received message
	 * @param receivePacket - The packet containing IP etc of sender
	 */
	public void execute(String receivedMessage, DatagramPacket receivePacket) {
		System.out.println("================ SEND TAXI COORDINATES ================");
		
		int taxiAmount = Integer.parseInt(receivedMessage.substring(15, 17));
		
		String tripID = receivedMessage.substring(5, 15);
		String taxiString = receivedMessage.substring(17);
		String taxiID, taxiCoordinate;
		
		ArrayList<Taxi> taxiList = new ArrayList<Taxi>();
		
		System.out.println("Taxis added:");
		for(int i=0; i < taxiAmount; i++) {
			taxiID = taxiString.substring(i*15, (i*15)+6);
			taxiCoordinate = taxiString.substring((i*15)+6, (i+1)*15);
			taxiList.add(new Taxi(taxiID, taxiCoordinate));
			System.out.println("Taxi ID: " + taxiID + "  Taxi Coordinate: " + taxiCoordinate);
		}
		
		String company = receivePacket.getAddress().getHostAddress();
		
		dao.addAwaitingTaxis(tripID, taxiList, company);
	}

}
