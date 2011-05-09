package command;

import database.DAO;

public class MissTripCommand extends Command {
	
	private DAO dao = new DAO();
	
	public void execute(String receivedMessage) {
		String tripID = receivedMessage.substring(5);
		
		dao.deleteTrip(tripID);
	}

}
