package command;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import database.DAO;
import database.OngoingTripsDAO;
import model.Taxi;
import java.util.*;

public class SendTaxiCommand extends Command {

	private OngoingTripsDAO dao = new OngoingTripsDAO();
	
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		int taxiAmount = Integer.parseInt(receivedMessage.substring(15, 17));
		
		String tripID = receivedMessage.substring(5, 15);
		String taxiString = receivedMessage.substring(17);
		String taxiID, taxiCoordinate;
		
		ArrayList<Taxi> taxiList = new ArrayList<Taxi>();
		
		for(int i=0; i < taxiAmount; i++) {
			taxiID = taxiString.substring(i*15, (i*15)+6);
			taxiCoordinate = taxiString.substring((i*15)+6, (i+1)*15);
			
			taxiList.add(new Taxi(taxiID, taxiCoordinate));
		}
		
		String company = taxiString.substring(taxiAmount*15);
		
		dao.addAwaitingTaxis(tripID, taxiList, company);
	}

}
