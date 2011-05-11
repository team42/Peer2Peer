package command;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import peer.Peer;

import config.Configuration;

public class PeerListCommand extends Command {

	private Configuration config = Configuration.getConfiguration();
	
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		String peerList = receivedMessage.substring(5);
		
		String[] arPeerList = peerList.split("%");
		
		ArrayList<Peer> newPeerList = new ArrayList<Peer>();
		
		for(int i=0; i < arPeerList.length; i++) {
			newPeerList.add(new Peer(arPeerList[i], 1));
		}
		
		ArrayList<Peer> oldPeerList = config.getPeers();
		
		oldPeerList.addAll(newPeerList);
		
		config.setPeers(oldPeerList);
	}

}
