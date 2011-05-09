package command;

import java.util.ArrayList;

public class PeerListCommand extends Command {

	public void execute(String receivedMessage) {
		String peerList = receivedMessage.substring(5);
		String newIP = "";
		
		ArrayList<String> arPeerList = new ArrayList<String>();
		
		int begin = 0;
		
		for(int i=0; i>peerList.length(); i++) {
			if(peerList.charAt(i) == '%') {
				newIP = peerList.substring(begin, i);
				
				arPeerList.add(newIP);
				
				begin = i+1;
			}
		}
		
		// Compare arPeerList with peerList in database
		// If IP doesn't exist >> Add it!
	}

}
