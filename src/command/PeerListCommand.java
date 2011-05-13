package command;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import peer.Peer;

import config.Configuration;

/**
 * 
 * This command is used after the Peer has announced itself to the network, all
 * other Peers will respond with this command to share their peer list.
 * 
 * @author Nicolai
 *
 */
public class PeerListCommand extends Command {

	private Configuration config = Configuration.getConfiguration();
	
	/**
	 * This execute will identify the peerlist and split it to a string array.
	 * This string array will be converted to an arraylist of strings.
	 * The peerlist will be added to the old peer list.
	 * 
	 * @param receivedMessage - The received message
	 * @param peerSocket - The socket to respond at
	 * @param receivePacket - The packet containing IP etc of sender
	 */
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
