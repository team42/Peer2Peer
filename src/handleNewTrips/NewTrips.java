package handleNewTrips;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import peer.Peer;
import peer.UDPPeer;
import database.TripOffersDAO;
import config.*;

public class NewTrips {

	Timer timer;
	TripOffersDAO tripOfferDAO = new TripOffersDAO();
	Configuration config = Configuration.getConfiguration();
	
	public NewTrips() {
		timer = new Timer();
		timer.schedule(new addNewTrip(), 5000, 10000);
	}
	
	public void handleTrip(String customer) {
		
		
		
		String[] coords = customer.split(",");
		int xCoord = Integer.parseInt(coords[0]);
		int yCoord = Integer.parseInt(coords[1]);
		
		System.out.println("X-coord: " + xCoord);
		
		ArrayList<Peer> peers = new ArrayList<Peer>();
		
		peers.add(new Peer("1.1.1.1", 1));
		peers.add(new Peer("2.2.2.2", 1));
		peers.add(new Peer("3.3.3.3", 1));
		peers.add(new Peer("4.4.4.4", 1));
		
		for(int i=0; i<peers.size(); i++) {
			if(xCoord >= ((i*1000)/peers.size()) && xCoord < (((i+1)*1000)/peers.size())) {
				String query = "IP got: " + peers.get(i).getIp();
				System.out.println(query);
				/*try {
					UDPPeer.sendMessages(InetAddress.getByName(peers.get(i).getIp()), query);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}*/
			}
		}
	}
	
	class addNewTrip extends TimerTask {
		public void run() {
			String customer = tripOfferDAO.getCustomer();
			
			if(!customer.equals("none")) {
				handleTrip(customer);
			}
		}
	}
}
