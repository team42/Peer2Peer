package command;

import database.DAO;

public class SendTaxiCommand extends Command {

	private DAO dao = new DAO();
	
	public void execute(String receivedMessage) {
		String taxiString = receivedMessage.substring(5);
		
		dao.addAwaitingTaxis(taxiString);
		
	}

}
