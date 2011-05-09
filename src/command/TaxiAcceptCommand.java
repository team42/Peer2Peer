package command;

import database.DAO;

public class TaxiAcceptCommand extends Command {

	private DAO dao = new DAO();
	
	public void execute(String receivedMessage) {
		String taxiID = receivedMessage.substring(5, 8);
		String tripID = receivedMessage.substring(8);
		
		dao.deleteOngoingTrip(tripID);
		
		// Send gottrip to sender and missedtrip to the rest
	}

}
