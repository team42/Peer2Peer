package command;

import java.util.ArrayList;

public class ReqTaxiCommand extends Command {

	public void execute(String receivedMessage) {
		String TripID = receivedMessage.substring(5,8);
		String customerCoord = receivedMessage.substring(8);
		
		// Find closest taxis by customerCoord (Exclude taxis who already got the trip)
		// Send taxis back to the one who requested. 
	}

}