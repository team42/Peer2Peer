package command;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import peer.Peer;
import config.Configuration;

/**
 * This command is used when another Peer announces itself.
 * 
 * @author Nicolai
 *
 */
public class HandleTripCommand extends Command {

	/**
	 * The execute method will get it's own PeerList and send it back to the sender.
	 * 
	 * @param receivedMessage - The received message
	 * @param peerSocket - The socket to respond at
	 * @param receivePacket - The packet containing IP etc of sender
	 */
	public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
		
		
	}

}
