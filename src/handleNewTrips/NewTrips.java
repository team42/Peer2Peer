package handleNewTrips;

import java.util.*;
import database.TripOffersDAO;

public class NewTrips {

	Timer timer;
	TripOffersDAO tripOfferDAO = new TripOffersDAO();
	
	public NewTrips() {
		timer = new Timer();
		timer.schedule(new addNewTrip(), 5000, 10000);
	}
	
	class addNewTrip extends TimerTask {
		public void run() {
			String customer = tripOfferDAO.getCustomer();
			
			if(!customer.equals("none")) {
				System.out.println(customer);
			} else {
				System.out.println("No customers!");
			}
		}
	}
}
